package com.jrwp.core.help;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidationResult {
	@JsonProperty(value = "MemberNames")
	private List<String> MemberNames = new ArrayList<String>();
	@JsonProperty(value = "ErrorMessage")
	private String ErrorMessage;

	public List<String> getMemberNames() {
		return MemberNames;
	}

	public void setMemberNames(List<String> memberNames) {
		MemberNames = memberNames;
	}

	public String getErrorMessage() {
		return ErrorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		ErrorMessage = errorMessage;
	}

}
