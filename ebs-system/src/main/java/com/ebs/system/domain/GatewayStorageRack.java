package com.ebs.system.domain;

import com.ebs.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

public class GatewayStorageRack extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long rackId;
    private Long storeId;
    private String rackName;
    private String rackBarCode;

    private List<GatewayStorageCell> storageCellList = new ArrayList<GatewayStorageCell>();

    public void setRackId(Long rackId) {
        this.rackId = rackId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public void setRackName(String rackName) {
        this.rackName = rackName;
    }

    public void setRackBarCode(String rackBarCode) {
        this.rackBarCode = rackBarCode;
    }

    public Long getRackId() {
        return rackId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public String getRackName() {
        return rackName;
    }

    public String getRackBarCode() {
        return rackBarCode;
    }

    public void setStorageCellList(List<GatewayStorageCell> storageCellList) {
        this.storageCellList = storageCellList;
    }

    public List<GatewayStorageCell> getStorageCellList() {
        return storageCellList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("rackId", getRackId())
                .append("storeId", getStoreId())
                .append("rackName", getRackName())
                .append("rackBarCode", getRackBarCode())
                .append("storageCellList", getStorageCellList())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
