package com.ebs.rfid;

import java.util.ArrayList;
import java.util.List;
import com.ebs.rfid.zebra.model.Reader;

public class TagQuery {

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
