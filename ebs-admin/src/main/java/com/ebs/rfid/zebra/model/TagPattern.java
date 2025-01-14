package com.ebs.rfid.zebra.model;

import java.io.Serializable;

public class TagPattern implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private String memory_bank;
    private String data;
    private String data_offset;
    private String data_length;
    private String data_mask;

    // Getters and Setters
    
    public String getMemory_bank() {
        return memory_bank;
    }

    public String getData_mask() {
		return data_mask;
	}

	public void setData_mask(String data_mask) {
		this.data_mask = data_mask;
	}

	public void setMemory_bank(String memory_bank) {
        this.memory_bank = memory_bank;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData_offset() {
        return data_offset;
    }

    public void setData_offset(String data_offset) {
        this.data_offset = data_offset;
    }

    public String getData_length() {
        return data_length;
    }

    public void setData_length(String data_length) {
        this.data_length = data_length;
    }
}
