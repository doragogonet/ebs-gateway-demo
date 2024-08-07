package com.ebs.system.domain;

import com.ebs.common.core.domain.BaseEntity;

public class PageRfidData extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String readerIp;
    private String tagId;
    private String tagRssi;

    private int tagRssiLevel;
    private String tagTime;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public void setReaderIp(String readerIp) {
        this.readerIp = readerIp;
    }

    public String getReaderIp() {
        return readerIp;
    }

    public void setTagRssi(String tagRssi) {
        this.tagRssi = tagRssi;
    }

    public void setTagRssiLevel(int tagRssiLevel) {
        this.tagRssiLevel = tagRssiLevel;
    }

    public void setTagTime(String tagTime) {
        this.tagTime = tagTime;
    }

    public Long getId() {
        return id;
    }

    public String getTagId() {
        return tagId;
    }

    public String getTagRssi() {
        return tagRssi;
    }

    public int getTagRssiLevel() {
        return tagRssiLevel;
    }

    public String getTagTime() {
        return tagTime;
    }
}
