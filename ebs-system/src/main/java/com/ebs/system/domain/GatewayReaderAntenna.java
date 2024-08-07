package com.ebs.system.domain;

import com.ebs.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class GatewayReaderAntenna extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long antennaId;
    private Long readerId;
    private String antennaName;
    private String type;
    private String orientation;
    private String status;

    public void setAntennaId(Long antennaId) {
        this.antennaId = antennaId;
    }

    public void setReaderId(Long readerId) {
        this.readerId = readerId;
    }

    public void setAntennaName(String antennaName) {
        this.antennaName = antennaName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getAntennaId() {
        return antennaId;
    }

    public Long getReaderId() {
        return readerId;
    }

    public String getAntennaName() {
        return antennaName;
    }

    public String getType() {
        return type;
    }

    public String getOrientation() {
        return orientation;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("antennaId", getAntennaId())
                .append("readerId", getReaderId())
                .append("antennaName", getAntennaName())
                .append("type", getType())
                .append("orientation", getOrientation())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
