package LOG;
import java.awt.Color;

import jtoolbox.StaticTools;

/**
 * Klasse zum Logging von Programmmeldungen
 * 
 * @author Witt nach Martin Pabst, 2009
 * 
 */
public class LOG {

	public static final Color red = Color.RED;
	public static final Color green = Color.GREEN;
	public static final Color yellow = Color.YELLOW;
	public static final Color blue = Color.BLUE;
	public static final Color black = Color.BLACK;

	/**
	 * all log output is written to this object which implements LogOutput
	 */
	public static LogInterface logOutput = new LogConsole();

	/**
	 * minimum log level. log entries with log level lower than this are not
	 * printed
	 */
	public static LogLevel minimumLogLevel = LogLevel.info;

	/**
	 * print given string if logLevel <= minimumLogLevel
	 * 
	 * @param s
	 * @param logLevel
	 */
	public static void out(String s) {
		out(s,LogLevel.useful);
	}

	/**
	 * print given string if logLevel <= minimumLogLevel
	 * 
	 * @param s
	 * @param logLevel
	 */
	public static void outln(String s) {
		outln(s,LogLevel.useful);
	}
	
	/**
	 * print given string if logLevel <= minimumLogLevel
	 * 
	 * @param s
	 * @param logLevel
	 */
	public static void out(String s, LogLevel logLevel) {
		if (logLevel.compareTo(minimumLogLevel) >= 0 && logOutput != null) {
			if (logLevel == LogLevel.error) {
				logOutput.outColor(s, red);
			} else {
				logOutput.out(s);
			}
		}
	}

	/**
	 * print given string + '\n' if logLevel <= minimumLogLevel
	 * 
	 * @param s
	 * @param logLevel
	 */
	public static void outln(String s, LogLevel logLevel) {
		if (logLevel.compareTo(minimumLogLevel) >= 0 && logOutput != null) {
			if (logLevel == LogLevel.error) {
				logOutput.outlnColor(s, red);
			} else {
				logOutput.outln(s);
			}
		}
	}

	/**
	 * print given string in given color if logLevel <= minimumLogLevel
	 * 
	 * @param s
	 * @param logLevel
	 */
	public static void outColor(String s, LogLevel logLevel, Color c) {
		if (logLevel.compareTo(minimumLogLevel) >= 0 && logOutput != null) {
			if (logLevel == LogLevel.error) {
				logOutput.outColor(s, red);
			} else {
				logOutput.outColor(s, c);
			}
		}
	}

	/**
	 * print given string + '\n' in given color if logLevel <= minimumLogLevel
	 * 
	 * @param s
	 * @param logLevel
	 */
	public static void outlnColor(String s, LogLevel logLevel, Color c) {
		if (logLevel.compareTo(minimumLogLevel) >= 0 && logOutput != null) {
			if (logLevel == LogLevel.error) {
				logOutput.outlnColor(s, red);
			} else {
				logOutput.outlnColor(s, c);
			}
		}
	}

	public static void setLogOutput(LogInterface lo) {
		logOutput = lo;
	}

	public static LogLevel getMinimumLogLevel() {
		return minimumLogLevel;
	}

	public static void setMinimumLogLevel(LogLevel minimumLogLevel) {
		LOG.minimumLogLevel = minimumLogLevel;
	}

	public static void setProgressBarMaximum(int n) {
		if (logOutput != null) {
			LOG.logOutput.setProgressBarMaximum(n);
		}
	}

	public static void setProgressBarValue(int value, String s) {
		if (logOutput != null) {
			LOG.logOutput.setProgressBarValue(value, s);
		}
	}

	public static void warten() {
		StaticTools.warte(5000);
	}

//	/** 
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		LOG.setLogOutput( new LogColorTextfeld("Testfenster"));
//		LOG.outln("Text", LogLevel.error);
//		LOG.outln("Text2", LogLevel.warning);
//	}

}
