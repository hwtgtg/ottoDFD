package jpToolbox;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Vector;

public class DFD_Datum {

	public static Vector<String> split(String text, char c) {
		Vector<String> erg = new Vector<String>();
		int pos = -1;
		do {
			pos = -1;
			pos = text.indexOf(c);
			if (pos < 0) {
				erg.addElement(text);
				break;
			} else if (pos == 0) {
				erg.addElement("");
			} else {
				erg.addElement(text.substring(0, pos));
			}
			if (text.length() > pos) {
				text = text.substring(pos + 1);
			} else {
				text = "";
			}
		} while ((pos >= 0) && text.length() > 0);

		return erg;
	}

	public static boolean istSchaltjahr(int jahr) {
		return (jahr % 400 == 0) || (!(jahr % 100 == 0) && (jahr % 4 == 0));

	}

	public static boolean istDatum(String text) {
		Vector<String> datumTeile = split(text, '.');
		if (datumTeile.size() != 3)
			return false;
		int tag = 0;
		int monat = 0;
		int jahr = 0;
		try {
			tag = Integer.parseInt(datumTeile.get(0));
			monat = Integer.parseInt(datumTeile.get(1));
			jahr = Integer.parseInt(datumTeile.get(2));
		} catch (NumberFormatException e) {
			return false;
		}

		if ((tag < 1) || (tag > 31))
			return false;
		if ((monat < 1) || (monat > 12))
			return false;

		if (jahr <= 0)
			return false;

		boolean schaltjahr = istSchaltjahr(jahr);
		if ((monat == 2) && schaltjahr && tag > 29)
			return false;
		if ((monat == 2) && !schaltjahr && tag > 28)
			return false;
		if (((monat == 4) || (monat == 6) || (monat == 9) || (monat == 11)) && (tag == 31))
			return false;

		return true;
	}

	public static GregorianCalendar getCalender(String text) {
		if (istDatum(text)) {
			Vector<String> datumTeile = split(text, '.');
			int tag = 0;
			int monat = 0;
			int jahr = 0;
			try {
				tag = Integer.parseInt(datumTeile.get(0));
				monat = Integer.parseInt(datumTeile.get(1));
				jahr = Integer.parseInt(datumTeile.get(2));
			} catch (NumberFormatException e) {
			}

			GregorianCalendar cal = new GregorianCalendar(jahr, monat - 1, tag);
			cal.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			return cal;
		} else {
			return null;
		}
	}

	public static int getWochentagI(String text) {
		if (istDatum(text)) {
			GregorianCalendar cal = getCalender(text);
			int erg = cal.get(Calendar.DAY_OF_WEEK);

			erg = (erg == 1) ? 7 : erg - 1;

			return erg;

		} else {
			return -1;
		}
	}

	public static String getWochentagText(String text) {
		String[] wochentage = { "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag" };

		int wtg = getWochentagI(text);
		return wochentage[wtg - 1];
	}

	public static int getDifferenzInTagen(String datum1, String datum2) {
		GregorianCalendar cal1 = getCalender(datum1);
		GregorianCalendar cal2 = getCalender(datum2);
		if (cal1 != null && cal2 != null) {
			long time = cal1.getTimeInMillis() - cal2.getTimeInMillis();
			double days = time * 1.0 / (24 * 60 * 60 * 1000);
			return (int) Math.round(days);
		} else {
			return Integer.MAX_VALUE;
		}
	}

	public static GregorianCalendar datumPlusTage(String datum1, int tage) {
		GregorianCalendar cal = getCalender(datum1);
		if (cal != null) {
			cal.add(Calendar.DATE, tage);
			return cal;
		} else {
			return null;
		}
	}

	public static GregorianCalendar datumPlusMonate(String datum1, int monate) {
		GregorianCalendar cal = getCalender(datum1);
		if (cal != null) {
			cal.add(Calendar.MONTH, monate);
			return cal;
		} else {
			return null;
		}
	}

	public static GregorianCalendar datumPlusJahre(String datum1, int jahre) {
		GregorianCalendar cal = getCalender(datum1);
		if (cal != null) {
			cal.add(Calendar.YEAR, jahre);
			return cal;
		} else {
			return null;
		}
	}

	
	
	public static String getCalString(GregorianCalendar cal) {
		String erg = "";
		erg = "" + cal.get(Calendar.DATE) + "." + (cal.get(Calendar.MONTH) + 1) + "." + cal.get(Calendar.YEAR);
		return erg;
	}

}
