package com.ebs.rfid.zebra.model;

import java.io.Serializable;

public class Triggerinfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private String start_type;
    private String stop_type;
    private String duration_mil;
    private String tag_report;

    // Getters and Setters
    public String getStart_type() {
        return start_type;
    }

    public void setStart_type(String start_type) {
        this.start_type = start_type;
    }

    public String getStop_type() {
        return stop_type;
    }

    public void setStop_type(String stop_type) {
        this.stop_type = stop_type;
    }

    public String getDuration_mil() {
        return duration_mil;
    }

    public void setDuration_mil(String duration_mil) {
        this.duration_mil = duration_mil;
    }

    public String getTag_report() {
        return tag_report;
    }

    public void setTag_report(String tag_report) {
        this.tag_report = tag_report;
    }
}

