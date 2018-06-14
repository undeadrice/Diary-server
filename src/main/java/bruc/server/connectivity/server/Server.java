package bruc.server.connectivity.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import bruc.server.ServerConsole;

public class Server {

	private Thread serverThread;
	private ServerSocket ss;
	private ExecutorService threads;
	private Set<Connection> connections = Collections.synchronizedSet(new HashSet<>());
	
	public static final Logger logger = Logger.getLogger(Server.class.getName());

	public Server(int port, int maxConnections) throws IOException {
		ss = new ServerSocket(port);
		threads = Executors.newFixedThreadPool(maxConnections);
	}

	public void start() {
		serverThread = new Thread(() -> {
			listen();
		});
		serverThread.start();
		logger.log(Level.INFO, "Server started");
	}

	private void listen() {
		while (true) {
			Socket s;
			try {
				openConnection();
				Thread.sleep(250);
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
				/// should quit loop on interrupt
			}

		}
	}

	private void shutdown() {
		serverThread.interrupt();
		threads.shutdownNow();
	}

	public void openConnection() throws IOException {
		logger.log(Level.INFO, "Waiting for connection");
		Socket s = ss.accept();
		logger.log(Level.INFO, "New connection oppened for port: " + s.getPort());
		Connection connection = new Connection(this,s);
		connections.add(connection);
	}
	
	public Future<?> startConnection(Connection connection) {
		return threads.submit(connection);
	}
	
	

	public void closeConnection(Connection connection) {
		removeConnection(connection);

	}

	public void removeConnection(Connection connection) {
		connections.remove(connection);
		logger.log(Level.INFO,"Removing connection. Current amount of connections: " + connections.size());
		
	}

}
