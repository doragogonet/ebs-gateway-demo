package com.ebs.rfid.zebra.model;


import java.io.Serializable;
import java.util.List;

public class Reader implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private String hostName;
    private String port;
    private List<String> antennas;
    private String power;
    private List<Prefilter> prefilters;
    private Triggerinfo trigger_info;
    private Postfilter postfilter;
    private Access access;


    // Getters and Setters
    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public List<String> getAntennas() {
        return antennas;
    }

    public void setAntennas(List<String> antennas) {
        this.antennas = antennas;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }


    public List<Prefilter> getPrefilters() {
        return prefilters;
    }

    public void setPrefilters(List<Prefilter> prefilters) {
        this.prefilters = prefilters;
    }

    public Triggerinfo getTrigger_info() {
        return trigger_info;
    }

    public void setTrigger_info(Triggerinfo trigger_info) {
        this.trigger_info = trigger_info;
    }

    public Postfilter getPostfilter() {
        return postfilter;
    }

    public void setPostfilter(Postfilter postfilter) {
        this.postfilter = postfilter;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

}
