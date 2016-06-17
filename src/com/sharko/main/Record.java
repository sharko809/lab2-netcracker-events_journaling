package com.sharko.main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Sharko Daniel
 *         <p>
 *         Represents single event message.
 *         </p>
 *
 */
final class Record {

	Record(final Date eventTime, Importance importance, final String messageSource, final String message) {
		this.eventTime = eventTime;
		this.importance = importance;
		this.messageSource = messageSource;
		this.message = message;
	}

	Record(String s) {
		this.eventTime = parseDate1(s);
		this.importance = parseImportance(s);
		this.messageSource = parseSource(s);
		this.message = parseMessage(s);
	}

	/**
	 * Parses the given string and returns the Date out of it.
	 * 
	 * @param s
	 *            String to parse
	 * @return Date from the string
	 */
	private Date parseDate1(String text) {
		Pattern clearPattern = Pattern.compile("[\\s]+");
		text = clearPattern.matcher(text).replaceAll(" ").trim();
		String regex = "^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		Date date = null;
		if (matcher.find()) {
			text = matcher.group(0);
			if (text == null) {
				throw new NullPointerException("Null data is invalid");
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			try {
				date = df.parse(text);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	/**
	 * Parses the given string and returns the message source. It's a string
	 * without spaces.
	 * 
	 * @param s
	 *            String to parse.
	 * @return Message source out of string.
	 */
	private String parseSource(String s) {
		// don't even ask why it looks so fucked up...
		String delims = "\\s+";
		String[] tokens = s.trim().split(delims);
		ArrayList<String> al = new ArrayList<String>();
		for (int i = 0; i < tokens.length; i++) {
			al.add(tokens[i]);
		}
		al.remove(0);
		al.remove(0);
		return al.get(0);
	}

	/**
	 * Parses the given string and returns the message.
	 * 
	 * @param s
	 *            String to parse.
	 * @return Message out of string.
	 */
	private String parseMessage(String s) {
		// I'm so embarrassed about this methods. I'm so sorry :(
		// Hadn't enough time to make good ones...
		String delims = "\\s+";
		String[] tokens = s.trim().split(delims);
		ArrayList<String> al = new ArrayList<String>();
		for (int i = 0; i < tokens.length; i++) {
			al.add(tokens[i]);
		}
		// removing date and source
		al.remove(0);
		al.remove(0);
		al.remove(0);
		// making a proper string from the elements left
		String str = "";
		for (String string : al) {
			str += string + " ";
		}
		// removing importance
		str = str.substring(0, containing(str));
		return str.trim();
	}

	/**
	 * Parses string for importance
	 * 
	 * @param s
	 *            string to parse
	 * @return importance
	 */
	private Importance parseImportance(String s) {
		return toImportance(s);
	}

	/**
	 * Returns the index of expression beginning. The expression represents
	 * importance.
	 * 
	 * @param s
	 *            string to parse
	 * @return the index, where the expression is found
	 */
	private int containing(String s) {
		if (s.contains(" .")) {
			return s.indexOf(" .");
		} else if (s.contains(" !")) {
			return s.indexOf(" !");
		} else if (s.contains(" !!!!!")) {
			return s.indexOf(" !!!!!");
		} else {
			return s.indexOf(" !!!");
		}
	}

	private Importance toImportance(String s) {
		if (s.contains(" . ")) {
			return Importance.NORMAL;
		} else if (s.contains(" ! ")) {
			return Importance.WARNING;
		} else if (s.contains(" !!!!!")) {
			return Importance.CRITICAL_ERROR;
		} else {
			return Importance.ERROR;
		}
	}

	private final Date eventTime;
	/**
	 * String without spaces.
	 * 
	 * @see com.sharko.main.Record#messageSourceFormatter(String)
	 */
	private final String messageSource;
	/**
	 * This string may contain spaces, but can not contain line breaks.
	 * 
	 * @see com.sharko.main.Record#messageFormatter(String)
	 */
	private final String message;
	private final Importance importance;

	/**
	 * Date of event in yyyy-MM-dd HH:mm:ss format.
	 * 
	 * @return date when event has occurred
	 */
	public Date getTime() {
		return eventTime;
	}

	public String getSource() {
		return messageSource;
	}

	public String getMessage() {
		return message;
	}

	public int getImportance() {
		return importance.toInt();
	}

	/**
	 * 
	 * @author Sharko Daniel
	 *         <p>
	 *         Contains importance types. Each type is represented by two
	 *         fields: String and int.
	 *         </p>
	 */
	public enum Importance {
		NORMAL(".", 1), WARNING("!", 2), ERROR("!!!", 3), CRITICAL_ERROR("!!!!!", 4);

		private final String showAs;
		private final int value;

		private Importance(final String text, int integer) {
			this.showAs = text;
			this.value = integer;
		}

		/**
		 * Return enum String value.
		 */
		@Override
		public String toString() {
			return showAs;
		}

		/**
		 * Return enum int value.
		 * 
		 * @return enum int value
		 */
		public int toInt() {
			return value;
		}
	}

	/**
	 * Returns event details as a String in format:
	 * "yyyy-MM-dd HH:mm:ss importance source message"
	 * 
	 * Following methods are used to create strictly formatted string.
	 * 
	 * @see com.sharko.main.Record#dateFormater(Date)
	 * @see com.sharko.main.Record#importanceFormater(Importance)
	 * @see com.sharko.main.Record#messageSourceFormatter(String)
	 * @see com.sharko.main.Record#messageFormatter(String)
	 */
	public String toString() throws NullPointerException {
		// TODO Can throw null pointer exception when trying to invoke on null
		return dateFormater(eventTime) + " " + importanceFormater(importance) + " "
				+ messageSourceFormatter(messageSource) + " " + messageFormatter(message);
	}

	/**
	 * Formats date to "yyyy-MM-dd HH:mm:ss" format.
	 * 
	 * @param date
	 *            date to be formatted
	 * @return Date formated like "yyyy-MM-dd HH:mm:ss"
	 */
	private String dateFormater(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}

	/**
	 * Formats importance text view to match 5-symbols space
	 * 
	 * @param importance
	 *            importance type
	 * @return Formatted importance text view
	 */
	private String importanceFormater(Importance importance) {
		switch (importance) {
		case NORMAL:
			return Importance.NORMAL.toString() + "    ";
		case WARNING:
			return Importance.WARNING.toString() + "    ";
		case ERROR:
			return Importance.ERROR.toString() + "  ";
		case CRITICAL_ERROR:
			return Importance.CRITICAL_ERROR.toString();
		default:
			return "Unexisting importance type";
		}
	}

	/**
	 * Removes all whitespaces and non visible characters such as tab.
	 * 
	 * @param messageSource
	 *            string to format
	 * @return String without any whitespace.
	 */
	private String messageSourceFormatter(String messageSource) {
		String str = messageSource.replaceAll("\\s+", "");
		return str;
	}

	/**
	 * Removes all line breaks.
	 * 
	 * @param message
	 *            message to format
	 * @return String without any line breaks.
	 */
	private String messageFormatter(String message) {
		String str = message.replace("\n", "").replace("\r", "");
		return str;
	}
	
	/**
	 * Parses the given string and returns the Date out of it.
	 * 
	 * @param s
	 *            String to parse
	 * @return Date from the string
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private Date parseDate(String s) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date result = null;
		try {
			result = df.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

}
