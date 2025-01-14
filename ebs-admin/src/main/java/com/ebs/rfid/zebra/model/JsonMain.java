package com.ebs.rfid.zebra.model;


import java.io.Serializable;
import java.util.List;

public class JsonMain implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private List<Reader> readers;
    private String token;

    // Getters and Setters
    public List<Reader> getReaders() {
        return readers;
    }

    public void setReaders(List<Reader> readers) {
        this.readers = readers;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
