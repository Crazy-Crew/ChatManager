package me.h1dd3nxn1nja.chatmanager.utils;

import me.h1dd3nxn1nja.chatmanager.ChatManager;

/**
 * @author Badbones69
 */
public enum ServerProtocol {

	TOO_OLD(-1), v1_7_R1(171), v1_7_R2(172), v1_7_R3(173), v1_7_R4(174),
	v1_8_R1(181), v1_8_R2(182), v1_8_R3(183),
	v1_9_R1(191), v1_9_R2(192),
	v1_10_R1(1101),
	v1_11_R1(1111),
	v1_12_R1(1121),
	v1_13_R2(1132),
	v1_14_R1(1141),
	v1_15_R1(1151),
	v1_15_R2(1152),
	v1_16_R1(1161),
	v1_16_R2(1162),
	v1_17_R1(1171),
	v1_17_R2(1172),
	v1_18_R1(1181),
	v1_18_R2(1182),
	v1_19_R1(1191),
	// Mojang didn't bump the protocol so this covers both 1.19.2 & 1.19.3
	v1_19_R23(1192),
	TOO_NEW(-2);

	private static ServerProtocol currentProtocol;
	private static ServerProtocol latest;

	private final int versionProtocol;

	private static final ChatManager plugin = ChatManager.getPlugin();

	ServerProtocol(int versionProtocol) {
		this.versionProtocol = versionProtocol;
	}

	public static void getCurrentProtocol() {
		String serVer = plugin.getServer().getClass().getPackage().getName();

		int serProt = Integer.parseInt(
				serVer.substring(
						serVer.lastIndexOf('.') + 1
				).replace("_", "").replace("R", "").replace("v", "")
		);

		for (ServerProtocol protocol : values()) {
			if (protocol.versionProtocol == serProt) {
				currentProtocol = protocol;
				break;
			}
		}

		if (currentProtocol == null) currentProtocol = ServerProtocol.TOO_NEW;
	}

	public static boolean isLegacy() {
		return isOlder(ServerProtocol.v1_18_R1);
	}

	public static ServerProtocol getLatestProtocol() {

		if (latest != null) return latest;

		ServerProtocol old = ServerProtocol.TOO_OLD;

		for (ServerProtocol protocol : values()) {
			if (protocol.compare(old) == 1) {
				old = protocol;
			}
		}

		return old;
	}

	public static boolean isAtLeast(ServerProtocol protocol) {
		if (currentProtocol == null) getCurrentProtocol();

		int proto = currentProtocol.versionProtocol;

		return proto >= protocol.versionProtocol || proto == -2;
	}

	public static boolean isNewer(ServerProtocol protocol) {
		if (currentProtocol == null) getCurrentProtocol();

		return currentProtocol.versionProtocol > protocol.versionProtocol || currentProtocol.versionProtocol == -2;
	}

	public static boolean isSame(ServerProtocol protocol) {
		if (currentProtocol == null) getCurrentProtocol();

		return currentProtocol.versionProtocol == protocol.versionProtocol;
	}

	public static boolean isOlder(ServerProtocol protocol) {
		if (currentProtocol == null) getCurrentProtocol();

		int proto = currentProtocol.versionProtocol;

		return proto < protocol.versionProtocol || proto == -1;
	}

	public int compare(ServerProtocol protocol) {
		int result = -1;
		int current = versionProtocol;
		int check = protocol.versionProtocol;

		if (current > check || check == -2) {
			result = 1;
		} else if (current == check) {
			result = 0;
		}

		return result;
	}
}