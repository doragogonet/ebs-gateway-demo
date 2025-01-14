package com.ebs.rfid.zebra.model;

import java.io.Serializable;

public class Access implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private String tag_id;
    private String password;
    private String lock_type; 	//LOCK_PRIVILEGE
    private String lock_target;	//LOCK_DATA_FIELD
    private TagPattern tag_pattern;

    // Getters and Setters
    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLock_type() {
        return lock_type;
    }

    public void setLock_type(String lock_type) {
        this.lock_type = lock_type;
    }

    public String getLock_target() {
        return lock_target;
    }

    public void setLock_target(String lock_target) {
        this.lock_target = lock_target;
    }

    public TagPattern getTag_pattern() {
        return tag_pattern;
    }

    public void setTag_pattern(TagPattern tag_pattern) {
        this.tag_pattern = tag_pattern;
    }
}
