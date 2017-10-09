package comp4004.library.network;

import comp4004.library.network.LibServer;
import comp4004.library.UtilConfig;

public class StartServer {
	public static void main(String[] argv) {
		System.out.println("Starting server ...");
		new LibServer(UtilConfig.DEFAULT_PORT);
	}
}
