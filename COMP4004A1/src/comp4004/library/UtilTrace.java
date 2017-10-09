package comp4004.library;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class UtilTrace {
	
	private static UtilTrace _instance = null;		
	public Logger getLogger (Object o) {
		return Logger.getLogger(o.getClass().getName());
	}
	
	private UtilTrace() {
		String userDir = System.getProperty("user.dir");
		String configFile = String.format("%s\\%s\\%s.properties",userDir, "properties","log4j");
		PropertyConfigurator.configure(configFile);
	}

	public static UtilTrace getInstance() {
		if (_instance == null) {
			synchronized (UtilTrace.class) {
				_instance = new UtilTrace();
			}
		}
		return _instance;
	}
}