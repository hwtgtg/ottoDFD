package LOG;
import java.awt.Color;

/**
 * Klasse zum Logging von Programmmeldungen
 * 
 * @author Witt
 *  nach Martin Pabst, 2009
 *
 */
public class LogMeldungen {

	public static final Color red = Color.RED ;
	public static final Color green = Color.GREEN ;
	public static final Color yellow = Color.YELLOW;
	public static final Color blue  = Color.BLUE ;
	public static final Color black  = Color.BLACK ;
	
	
	/**
	 * all log output is written to this object which implements LogOutput
	 */
	LogInterface logOutput = null;
	/**
	 * minimum log level. log entries with log level lower than this are not
	 * printed
	 */
	LogLevel minimumLogLevel = LogLevel.useful;
	
	/**
	 * 
	 */
	public LogMeldungen(LogInterface logOutput) {
		this.logOutput = logOutput;
	}


	/**
	 * print given string if logLevel <= minimumLogLevel
	 * 
	 * @param s
	 * @param logLevel
	 */
	public void out(String s, LogLevel logLevel) {
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
	public void outln(String s, LogLevel logLevel) {
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
	public void outColor(String s, LogLevel logLevel, Color c) {
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
	public void outlnColor(String s, LogLevel logLevel, Color c) {
		if (logLevel.compareTo(minimumLogLevel) >= 0 && logOutput != null) {
			if (logLevel == LogLevel.error) {
				logOutput.outlnColor(s, red);
			} else {
				logOutput.outlnColor(s, c);
			}
		}
	}

	public void setLogOutput(LogInterface lo) {
		logOutput = lo;
	}

	public LogLevel getMinimumLogLevel() {
		return minimumLogLevel;
	}

	public void setMinimumLogLevel(LogLevel minimumLogLevel) {
		this.minimumLogLevel = minimumLogLevel;
	}

	public void setProgressBarMaximum(int n) {
		logOutput.setProgressBarMaximum(n);
	}

	public void setProgressBarValue(int value, String s) {
		logOutput.setProgressBarValue(value, s);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LogMeldungen mld = new LogMeldungen(new LogColorTextfeld("Testfenster"));
		mld.outln("Text", LogLevel.error);
		mld.outln("Text2", LogLevel.warning);
	}

}
