package bruc.server;

import java.awt.EventQueue;
import java.io.IOException;

import bruc.server.connectivity.server.Server;
import bruc.server.controller.Controller;

public class DiaryServer {

	public static final int PORT = 9999;
	public static final int MAX_CONNECTIONS = 20;

	private static ServerConsole console;
	private static Server server;

	public static void main(String[] args) {

		try {

			server = new Server(PORT, MAX_CONNECTIONS);
			server.start();
			console = new ServerConsole();
			EventQueue.invokeLater(() -> {
				console.initGui();
				Controller controller = new Controller(console, server);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
