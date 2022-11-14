package com.daksh.springboot.blogapplication.payload;

import java.util.Date;


public class ErrorDetails {
	private Date timeStamp;
	private String message;
	private String details;
	public ErrorDetails(Date date, String message, String details) {
		this.timeStamp = date;
		this.message = message;
		this.details = details;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public String getMessage() {
		return message;
	}
	public String getDetails() {
		return details;
	}
	
	
}
