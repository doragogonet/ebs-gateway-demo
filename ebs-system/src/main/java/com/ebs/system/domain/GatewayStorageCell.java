package com.ebs.system.domain;

import com.ebs.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class GatewayStorageCell extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long cellId;
    private Long rackId;
    private Long storeId;
    private String cellCode;
    private String rackName;
    private String cellBarCode;

    public void setCellId(Long cellId) {
        this.cellId = cellId;
    }

    public void setRackId(Long rackId) {
        this.rackId = rackId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public void setCellCode(String cellCode) {
        this.cellCode = cellCode;
    }

    public void setRackName(String rackName) {
        this.rackName = rackName;
    }

    public void setCellBarCode(String cellBarCode) {
        this.cellBarCode = cellBarCode;
    }

    public Long getCellId() {
        return cellId;
    }

    public Long getRackId() {
        return rackId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public String getCellCode() {
        return cellCode;
    }

    public String getRackName() {
        return rackName;
    }

    public String getCellBarCode() {
        return cellBarCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("cellId", getCellId())
                .append("rackId", getRackId())
                .append("storeId", getStoreId())
                .append("cellCode", getCellCode())
                .append("rackName", getRackName())
                .append("cellBarCode", getCellBarCode())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
