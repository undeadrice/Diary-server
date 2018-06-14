package bruc.server.connectivity.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection implements Runnable {

	private final Socket s;
	private final ObjectInputStream in;
	private final ObjectOutputStream out;

	private Future<?> future;
	private Server server;

	
	
	private final ExecutorService inOutThreads = Executors.newFixedThreadPool(2);;
	private final BlockingQueue<Object> queueIn = new ArrayBlockingQueue<>(10);
	private final BlockingQueue<Object> queueOut = new ArrayBlockingQueue<>(10);
	
	public static final Logger logger = Logger.getLogger(Connection.class.getName());

	public Connection(Server server, Socket s) throws IOException {
		this.s = s;
		this.server = server;
		out = new ObjectOutputStream(s.getOutputStream());
		in = new ObjectInputStream(s.getInputStream());
		
	
		future = server.startConnection(this); // bez sensu
	}

	@Override
	public void run() {
		send();
		read();
		while (true) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				logger.log(Level.INFO,"Connection threads have been interrupted. Port: " + s.getPort());
				break;
			}
		}
		server.removeConnection(this);
	}

	private void send() {
		Runnable r = () -> {
			try (s) {
				while (true) {
					Thread.sleep(50);
					out.writeObject(queueOut.take());
				}
			} catch (InterruptedException | IOException e) {
				inOutThreads.shutdownNow();
				future.cancel(true);
			}
		};
		inOutThreads.execute(r);
	}

	private void read() {
		Runnable r = () -> {
			try (s) {
				while (true) {
					Thread.sleep(50);
					queueIn.put(in.readObject());
				}
			} catch (InterruptedException | ClassNotFoundException | IOException e) {
				inOutThreads.shutdownNow();
				future.cancel(true);	
			}
		};
		inOutThreads.execute(r);
	}

	public void interruptConnection() {

	}

	public BlockingQueue<Object> getQueueIn() {
		return queueIn;
	}

	public BlockingQueue<Object> getQueueOut() {
		return queueOut;
	}
}
