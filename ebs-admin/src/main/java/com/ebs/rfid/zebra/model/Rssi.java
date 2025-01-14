package com.ebs.rfid.zebra.model;

import java.io.Serializable;

public class Rssi implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String match_range;
    private String lower_limit;
    private String upper_limit;

    // Getters and Setters
    
    public String getLower_limit() {
        return lower_limit;
    }

    public String getMatch_range() {
		return match_range;
	}

	public void setMatch_range(String match_range) {
		this.match_range = match_range;
	}

	public void setLower_limit(String lower_limit) {
        this.lower_limit = lower_limit;
    }

    public String getUpper_limit() {
        return upper_limit;
    }

    public void setUpper_limit(String upper_limit) {
        this.upper_limit = upper_limit;
    }
}
