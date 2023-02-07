package com.toppan.EmployeeManagement;

public class ResponseMessage {

	private String message;
	private String reason;

	public ResponseMessage(String message, String reason) {
		this.message = message;
		this.reason = reason;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
