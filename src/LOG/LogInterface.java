package LOG;
import java.awt.Color;

/**
 * Interface for a class which is used as output for log messages
 * 
 * @author Hans Witt 
 *  nach Martin Pabst, 2009
 * 
 */

public interface LogInterface {


	/**
	 * clear output ;
	 */
	public void clear() ;

	
	/**
	 * print given string if logLevel <= minimumLogLevel
	 * 
	 * @param s
	 * @param logLevel
	 */
	public void out(String s);

	/**
	 * print given string + '\n' if logLevel <= minimumLogLevel
	 * 
	 * @param s
	 * @param logLevel
	 */
	public void outln(String s);

	/**
	 * print given string in given color if logLevel <= minimumLogLevel
	 * 
	 * @param s
	 * @param logLevel
	 */
	public void outColor(String s, Color c);

	/**
	 * print given string + '\n' in given color if logLevel <= minimumLogLevel
	 * 
	 * @param s
	 * @param logLevel
	 */
	public void outlnColor(String s, Color c);

	/**
	 * set maximum value of progressbar; n == -1 sets progressbar in indefinite
	 * mode.
	 * 
	 * @param n
	 */
	public void setProgressBarMaximum(int n);

	/**
	 * set current value of progressbar
	 * 
	 * @param value
	 * @param s
	 */
	public void setProgressBarValue(int value, String s);

}


