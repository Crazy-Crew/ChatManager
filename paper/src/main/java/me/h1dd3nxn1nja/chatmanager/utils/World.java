package me.h1dd3nxn1nja.chatmanager.utils;

import java.util.List;

public class World {

	private String name;
	private List<String> messages;
	private int index;

	public World(String name, List<String> messages, int index) {
		this.name = name;
		this.messages = messages;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public List<String> getMessages() {
		return messages;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}