package cash;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

import java.util.logging.Logger;

public class Logger_Manager {
private static final Logger logger=Logger.getLogger(Logger_Manager.class.getName());
static{
	try {
		FileHandler fileHandler=new FileHandler("application.log");
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
		
	} catch (Exception e) {
		logger.log(Level.SEVERE, "eRROR SETTING UP FILE",e);
	}
}
public static Logger getLogger(Class<?> clazz) {
	return logger;
}
}
