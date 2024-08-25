package com.ryderbelserion.chatmanager.api.objects;

import java.util.List;

public class CustomWorld {

	private final String name;
	private final List<String> messages;
	private int index;

	public CustomWorld(final String name, final List<String> messages, final int index) {
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

	public void setIndex(final int index) {
		this.index = index;
	}

	public int getIndex() {
		return this.index;
	}
}