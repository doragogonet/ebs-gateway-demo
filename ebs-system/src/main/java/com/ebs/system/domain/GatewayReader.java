package com.ebs.system.domain;

import com.ebs.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

public class GatewayReader extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long readerId;
    private String readerName;
    private String serialNumber;
    private String ipAddress;
    private String location;
    private String status;
    private String readerImage;

    public void setReaderImage(String readerImage) {
        this.readerImage = readerImage;
    }

    public String getReaderImage() {
        return readerImage;
    }

    private List<GatewayReaderAntenna> readerAntennaList = new ArrayList<GatewayReaderAntenna>();

    public void setReaderId(Long readerId) {
        this.readerId = readerId;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getReaderId() {
        return readerId;
    }

    public String getReaderName() {
        return readerName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }

    public void setReaderAntennaList(List<GatewayReaderAntenna> readerAntennaList) {
        this.readerAntennaList = readerAntennaList;
    }

    public List<GatewayReaderAntenna> getReaderAntennaList() {
        return readerAntennaList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("readerId", getReaderId())
                .append("readerName", getReaderName())
                .append("serialNumber", getSerialNumber())
                .append("ipAddress", getIpAddress())
                .append("location", getLocation())
                .append("status", getStatus())
                .append("readerAntennaList", getReaderAntennaList())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
