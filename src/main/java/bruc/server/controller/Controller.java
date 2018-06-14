package bruc.server.controller;

import bruc.server.ServerConsole;
import bruc.server.connectivity.server.Server;

public class Controller {

	private ServerConsole console;
	private Server server;

	public Controller(ServerConsole console, Server server) {
		this.console = console;
		this.server = server;
		init();
	}

	public void appendText() {

	}

	public void getCommand() {
		System.out.println(console.getFld().getText());
		console.getFld().setText("");
	}

	private void init() {
		console.getFld().addActionListener(e -> {
			getCommand();
		});

	}

}
