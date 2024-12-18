package com.ebs.rfid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ebs.common.core.domain.BaseEntity;
import com.ebs.rfid.zebra.model.Reader;
import org.epctagcoder.result.Base;

public class TagQuery implements Serializable {

    private static final long serialVersionUID = 1L;
	private List<Reader> readers;
    private String token;

    private String tagGs1 = "";

    private String epcCode1 = "";
    private String epcCode2 = "";
    private String epcCode3 = "";

    private String extension = "";

    private String filter = "";

    private boolean isGS1 = false;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public boolean getIsGS1() {
        return isGS1;
    }

    public void setIsGS1(boolean GS1) {
        isGS1 = GS1;
    }

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

    public String getTagGs1() {
        return tagGs1;
    }

    public void setTagGs1(String tagGs1) {
        this.tagGs1 = tagGs1;
    }

    public String getEpcCode1() {
        return epcCode1;
    }

    public void setEpcCode1(String epcCode1) {
        this.epcCode1 = epcCode1;
    }

    public String getEpcCode2() {
        return epcCode2;
    }

    public void setEpcCode2(String epcCode2) {
        this.epcCode2 = epcCode2;
    }

    public String getEpcCode3() {
        return epcCode3;
    }

    public void setEpcCode3(String epcCode3) {
        this.epcCode3 = epcCode3;
    }
}
