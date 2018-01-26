package com.jagriti;

public class HeaderItem {

	private final String name;
	private final String value;

	public HeaderItem(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

}
