package me.h1dd3nxn1nja.chatmanager.paper.utils;

import java.util.regex.Pattern;

public class Format {
	
	protected static Pattern COLOR_PATTERN = Pattern.compile("(?i)&([0-9A-F])");
	protected static Pattern MAGIC_PATTERN = Pattern.compile("(?i)&(K)");
	protected static Pattern BOLD_PATTERN = Pattern.compile("(?i)&(L)");
	protected static Pattern STRIKETHROUGH_PATTERN = Pattern.compile("(?i)&(M)");
	protected static Pattern UNDERLINE_PATTERN = Pattern.compile("(?i)&(N)");
	protected static Pattern ITALIC_PATTERN = Pattern.compile("(?i)&(O)");
	protected static Pattern RESET_PATTERN = Pattern.compile("(?i)&(R)");
	
	public static String formatStringColor(String string) {
		string = COLOR_PATTERN.matcher(string).replaceAll("\u00A7$1");
		string = string.replaceAll("%", "\\%");

		return string;
	}
	
	public static String formatString(String string) {
		string = MAGIC_PATTERN.matcher(string).replaceAll("\u00A7$1");
		string = BOLD_PATTERN.matcher(string).replaceAll("\u00A7$1");
		string = STRIKETHROUGH_PATTERN.matcher(string).replaceAll("\u00A7$1");
		string = UNDERLINE_PATTERN.matcher(string).replaceAll("\u00A7$1");
		string = ITALIC_PATTERN.matcher(string).replaceAll("\u00A7$1");
		string = RESET_PATTERN.matcher(string).replaceAll("\u00A7$1");
		string = string.replaceAll("%", "\\%");

		return string;
	}
}