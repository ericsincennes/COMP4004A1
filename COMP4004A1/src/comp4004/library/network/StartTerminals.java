package comp4004.library.network;

import comp4004.library.network.LibClient;
import comp4004.library.UtilConfig;

public class StartTerminals {
	public static void main(String[] argv) {
		new LibClient(UtilConfig.DEFAULT_HOST, UtilConfig.DEFAULT_PORT);
	}
}
