package com.ebs.system.domain;

import com.ebs.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

public class GatewayReaderPortAntenna extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long portAntennaId;
    private Long readerId;
    private Long portNumber;
    private Long antennaId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date connectionTimes;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date disconnectionTimes;

    private GatewayReader reader = new GatewayReader();
    private GatewayReaderAntenna readerAntenna = new GatewayReaderAntenna();

    public void setPortAntennaId(Long portAntennaId) {
        this.portAntennaId = portAntennaId;
    }

    public void setReaderId(Long readerId) {
        this.readerId = readerId;
    }

    public void setPortNumber(Long portNumber) {
        this.portNumber = portNumber;
    }

    public void setAntennaId(Long antennaId) {
        this.antennaId = antennaId;
    }

    public void setConnectionTimes(Date connectionTimes) {
        this.connectionTimes = connectionTimes;
    }

    public void setDisconnectionTimes(Date disconnectionTimes) {
        this.disconnectionTimes = disconnectionTimes;
    }

    public Long getPortAntennaId() {
        return portAntennaId;
    }

    public Long getReaderId() {
        return readerId;
    }

    public Long getPortNumber() {
        return portNumber;
    }

    public Long getAntennaId() {
        return antennaId;
    }

    public Date getConnectionTimes() {
        return connectionTimes;
    }

    public Date getDisconnectionTimes() {
        return disconnectionTimes;
    }

    public void setReader(GatewayReader reader) {
        this.reader = reader;
    }

    public void setReaderAntenna(GatewayReaderAntenna readerAntenna) {
        this.readerAntenna = readerAntenna;
    }

    public GatewayReader getReader() {
        return reader;
    }

    public GatewayReaderAntenna getReaderAntenna() {
        return readerAntenna;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("portAntennaId", getAntennaId())
                .append("readerId", getReaderId())
                .append("portNumber", getPortNumber())
                .append("antennaId", getAntennaId())
                .append("connectionTimes", getConnectionTimes())
                .append("disConnectionTimes", getDisconnectionTimes())
                .append("reader", getReader())
                .append("readerAntenna", getReaderAntenna())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
