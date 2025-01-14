package com.ebs.rfid.zebra.model;

import java.io.Serializable;

//Postfilter
public class Postfilter implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private String match;
    private TagPattern tag_pattern_a;
    private TagPattern tag_pattern_b;
    private Rssi rssi;

    // Getters and Setters
    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public TagPattern getTag_pattern_a() {
        return tag_pattern_a;
    }

    public void setTag_pattern_a(TagPattern tag_pattern_a) {
        this.tag_pattern_a = tag_pattern_a;
    }

    public TagPattern getTag_pattern_b() {
        return tag_pattern_b;
    }

    public void setTag_pattern_b(TagPattern tag_pattern_b) {
        this.tag_pattern_b = tag_pattern_b;
    }

    public Rssi getRssi() {
        return rssi;
    }

    public void setRssi(Rssi rssi) {
        this.rssi = rssi;
    }
}
