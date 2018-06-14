package bruc.server.connectivity.server;

import java.util.concurrent.BlockingQueue;

public class PacketProcessor {

	private Connection connection;
	private final BlockingQueue<Object> qin;
	private final BlockingQueue<Object> qout;

	public PacketProcessor(Connection connection) {
		this.connection = connection;
		this.qin = connection.getQueueIn();
		this.qout = connection.getQueueOut();
	}

	public void analyze() throws InterruptedException {
		Object packet = qin.take();
	}

}
