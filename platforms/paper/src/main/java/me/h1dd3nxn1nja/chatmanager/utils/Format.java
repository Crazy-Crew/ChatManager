package me.h1dd3nxn1nja.chatmanager.utils;

import org.jetbrains.annotations.NotNull;
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
		string = COLOR_PATTERN.matcher(string).replaceAll("§$1");
		string = string.replaceAll("%", "\\%");

		return string;
	}
	
	public static String formatStringMagic(String string) {
		string = MAGIC_PATTERN.matcher(string).replaceAll("§$1");

		return string;
	}
	
	public static String formatStringBold(String string) {
		string = BOLD_PATTERN.matcher(string).replaceAll("§$1");

		return string;
	}
	
	public static String formatStringStrikethrough(String string) {
		string = STRIKETHROUGH_PATTERN.matcher(string).replaceAll("§$1");

		return string;
	}
	
	public static String formatStringUnderline(String string) {
		string = UNDERLINE_PATTERN.matcher(string).replaceAll("§$1");

		return string;
	}
	
	public static String formatStringItalic(String string) {
		string = ITALIC_PATTERN.matcher(string).replaceAll("§$1");

		return string;
	}
	
	public static String formatStringReset(String string) {
		string = RESET_PATTERN.matcher(string).replaceAll("§$1");

		return string;
	}
	
	public static String formatString(String string) {
		return getString(string);
	}
	
	public static String formatStringAll(String string) {
		string = COLOR_PATTERN.matcher(string).replaceAll("§$1");
		return getString(string);
	}

	@NotNull
	private static String getString(String string) {
		string = MAGIC_PATTERN.matcher(string).replaceAll("§$1");
		string = BOLD_PATTERN.matcher(string).replaceAll("§$1");
		string = STRIKETHROUGH_PATTERN.matcher(string).replaceAll("§$1");
		string = UNDERLINE_PATTERN.matcher(string).replaceAll("§$1");
		string = ITALIC_PATTERN.matcher(string).replaceAll("§$1");
		string = RESET_PATTERN.matcher(string).replaceAll("§$1");
		string = string.replaceAll("%", "\\%");

		return string;
	}
}