package com.jrwp.core.help;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SelectHelper {
	@JsonProperty("Selected")
	private Boolean selected;
	@JsonProperty("Text")
	private String text;
	@JsonProperty("Value")
	private String value;

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
