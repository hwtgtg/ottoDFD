package jpToolbox;

import jtoolbox.Dezimal;

public class Daten_Variant {

	public String datenS = "";
	long datenGanzzahlig = 0L;
	Dezimal datenD = new Dezimal();
	Boolean datenB = false;

	private Boolean ganzzahlig = false;

	public Boolean istGanzzahlig() {
		return ganzzahlig;
	}

	private Boolean dezimalzahl = false;

	public Boolean istDezimalzahl() {
		return dezimalzahl;
	}

	private Boolean logisch = false;

	public Boolean istBoolean() {
		return logisch;
	}

	private Boolean isError = false;

	public Boolean istError() {
		return isError;
	}

	public Daten_Variant() {
		this.datenS = "";
		this.datenD = new Dezimal();
		dezimalzahl = false;
		logisch = false;
		ganzzahlig = false;
	}

	public Daten_Variant(String s) {
		this();
		setzeWert(s);
	}

	/**
	 * // Copy-Konstruktor
	 */
	public Daten_Variant(Daten_Variant dv) {
		this.datenS = dv.datenS;
		this.datenGanzzahlig = dv.datenGanzzahlig;
		this.datenD = dv.datenD;
		this.datenB = dv.datenB;
		this.ganzzahlig = dv.ganzzahlig;
		this.dezimalzahl = dv.dezimalzahl;
		this.logisch = dv.logisch;
		this.isError = dv.isError;
	}

	public static String errorString() {
		return "----";
	}

	public void setzeError() {
		dezimalzahl = false;
		logisch = false;
		ganzzahlig = false;
		isError = true;
		datenS = errorString();

	}

	public void setzeWert(String datenS) {
		this.isError = false;
		this.datenS = datenS.trim();
		this.datenS = datenS;
		this.datenD = new Dezimal(datenS);
		dezimalzahl = this.datenD.isValid();

		this.logisch = true;
		if (this.datenS.equalsIgnoreCase("wahr")) {
			this.datenB = true;
		} else if (this.datenS.equalsIgnoreCase("falsch")) {
			this.datenB = false;
		} else if (this.datenS.equalsIgnoreCase("w")) {
			this.datenB = true;
		} else if (this.datenS.equalsIgnoreCase("f")) {
			this.datenB = false;
		} else if (this.datenS.equalsIgnoreCase("true")) {
			this.datenB = true;
		} else if (this.datenS.equalsIgnoreCase("false")) {
			this.datenB = false;
		} else {
			this.logisch = false;
		}

		try {
			this.datenGanzzahlig = Long.parseLong(datenS);
			this.ganzzahlig = true;
		} catch (NumberFormatException e) {
			this.ganzzahlig = false;
		}
	}

	public void setzeWert(long d) {
		this.datenGanzzahlig = d;
		this.datenD = new Dezimal(d, 0);
		this.dezimalzahl = this.datenD.isValid();
		this.logisch = false;
		this.ganzzahlig = true;
		this.isError = false;
		this.datenS = this.datenD.toString();
	}

	public void setzeWert(double d) {
		setzeWert(new Dezimal(d));
	}

	public void setzeWert(Dezimal d) {
		this.isError = false;
		this.datenS = d.toString();

		try {
			this.datenGanzzahlig = Long.parseLong(datenS);
			this.ganzzahlig = true;
		} catch (NumberFormatException e) {
			this.ganzzahlig = false;
		}

		this.datenD = new Dezimal(d);
		this.dezimalzahl = this.datenD.isValid();
		this.logisch = false;
	}

	public void setzeWert(Boolean d) {
		this.isError = false;
		this.datenS = d ? "WAHR" : "FALSCH";
		this.datenD = new Dezimal(datenS);
		this.dezimalzahl = false;
		this.datenB = d;
		this.logisch = true;
		this.ganzzahlig = false;
	}

	public void setzeWert(Daten_Variant d) {
		this.isError = d.isError;
		if (d.logisch) {
			setzeWert(d.getBoolean());
		} else if (d.ganzzahlig) {
			setzeWert(d.getGanzzahlig());
		} else if (d.dezimalzahl) {
			setzeWert(d.getDezimal());
		} else {
			setzeWert(d.getDatenString());
		}
	}

	public String getDatenString() {
		return this.datenS;
	}

	public String getDatenString(int stellen) {
		String ergebnis = "";
		if (datenS.length() <= stellen) {
			ergebnis = datenS;
		} else if (istDezimalzahl()) {
			ergebnis = datenD.toString(stellen);
		} else {
			// Für sonstiges
			ergebnis = "#" + datenS.substring(0, stellen - 2) + "#";
		}

		return ergebnis;
	}

	public long getGanzzahlig() {
		if (!ganzzahlig) {
			this.datenS = errorString();
			return -999999999;
		}
		this.datenS = "" + this.datenGanzzahlig;
		return datenGanzzahlig;
	}

	public Dezimal getDezimal() {
		if (!this.dezimalzahl) {
			this.datenS = errorString();
		} else {
			this.datenS = this.datenD.toString();
		}
		return this.datenD;
	}

	public boolean getBoolean() {
		if (this.logisch) {
			this.datenS = this.datenB ? "WAHR" : "FALSCH";
		} else {
			this.datenS = errorString();
		}
		return this.datenB;
	}

}
