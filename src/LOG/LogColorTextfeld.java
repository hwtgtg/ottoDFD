package LOG;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class LogColorTextfeld implements LogInterface {
	JTextPane text;
	LogColorFrame frame;

	public LogColorTextfeld(String fenstertitel) {
		text = new JTextPane();
		text.setBackground(Color.WHITE);
		text.setForeground(Color.BLACK);
		text.setEditable(true);
		text.updateUI();
		frame = new LogColorFrame(fenstertitel, text);
	}

	public void clear() {
		text.setText("");
	}

	public void out(String s) {
		outColor(s, Color.BLACK);
	}

	public void outln(String s) {
		outlnColor(s, Color.BLACK);
	}

	public void outColor(String s, Color c) {
		// StyleContext
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
				StyleConstants.Foreground, c);
		int len = text.getDocument().getLength(); // same value as
		// getText().length();
		text.setEditable(true);
		text.setCaretPosition(len); // place caret at the end (with no
									// selection)
		text.setCharacterAttributes(aset, false);
		text.replaceSelection(s); // there is no selection, so inserts at caret
		text.setEditable(true);
	}

	public void outlnColor(String s, Color c) {
		outColor(s + "\n", c);
	}

	public void setProgressBarMaximum(int n) {
	}

	public void setProgressBarValue(int value, String s) {
	}
}

/**
 * 
 * Fenster f�r ein Textfeld
 * 
 * @version 1.0 vom 19.02.2011
 * @author Hans Witt
 */
class LogColorFrame extends JFrame {

	private static final long serialVersionUID = -5185235927809584269L;
	private JScrollPane scrollPanel;

	public LogColorFrame(String title, JTextPane text) {
		// Frame-Initialisierung
		super(title);
		this.setAlwaysOnTop(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		int frameWidth = 700;
		int frameHeight = 500;
		setSize(frameWidth, frameHeight);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - getSize().width);
		int y = (d.height - getSize().height) / 2;
		setLocation(x, y);
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		scrollPanel = new JScrollPane();
		cp.add(scrollPanel, BorderLayout.CENTER);
		scrollPanel.setViewportView(text);
		this.getContentPane().add(scrollPanel);

		setResizable(true);
		setVisible(true);
	}
	
}
