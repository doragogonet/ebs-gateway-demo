package com.ebs.system.domain;

import com.ebs.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

public class GatewayStorageStore extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long storeId;
    private String storeName;
    private String storeBarCode;
    private List<GatewayStorageRack> storageRackList = new ArrayList<GatewayStorageRack>();

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setStoreBarCode(String storeBarCode) {
        this.storeBarCode = storeBarCode;
    }

    public Long getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreBarCode() {
        return storeBarCode;
    }

    public void setStorageRackList(List<GatewayStorageRack> storageRackList) {
        this.storageRackList = storageRackList;
    }

    public List<GatewayStorageRack> getStorageRackList() {
        return storageRackList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("storeId", getStoreId())
                .append("storeName", getStoreName())
                .append("storeBarCode", getStoreBarCode())
                .append("storageRackList", getStorageRackList())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
