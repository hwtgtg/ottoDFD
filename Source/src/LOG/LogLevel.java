package LOG;
/**
 * Log levels to characterize importance of log entries
 * 
 * @author Martin Pabst, 2009
 * 
 */
public enum LogLevel {
	/**
	 * for debug purpose
	 */
	trace,
	/**
	 * additional information, not necessary
	 */
	info,
	/**
	 * useful information
	 */
	useful,
	/**
	 * warning
	 */
	warning,
	/**
	 * severe warning, error
	 */
	error

}
