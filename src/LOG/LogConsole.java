package LOG;
import java.awt.Color;

/**
 * @author Witt
 * 
 */
public class LogConsole implements LogInterface{

	/**
	 * 
	 */
	public LogConsole() {
	}

	@Override
	public void out(String s) {
		System.out.print(s);
	}

	@Override
	public void outln(String s) {
		System.out.println(s);
	}

	@Override
	public void outColor(String s, Color c) {
		System.out.print(s);
	}

	@Override
	public void outlnColor(String s, Color c) {
		System.out.println(s);
	}

	@Override
	public void setProgressBarMaximum(int n) {
	}

	@Override
	public void setProgressBarValue(int value, String s) {
	}

	@Override
	public void clear() {
		System.console();
	}
}
