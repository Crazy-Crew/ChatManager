package me.h1dd3nxn1nja.chatmanager.utils;

import java.util.List;

public class World {

	private final String name;
	private final List<String> messages;
	private int index;

	public World(String name, List<String> messages, int index) {
		this.name = name;
		this.messages = messages;
		this.index = index;
	}

	public String getName() {
		return this.name;
	}

	public List<String> getMessages() {
		return this.messages;
	}

	public int getIndex() {
		return this.index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}