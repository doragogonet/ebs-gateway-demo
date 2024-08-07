package com.ebs.system.domain;

import javax.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ebs.common.annotation.Excel;
import com.ebs.common.annotation.Excel.ColumnType;
import com.ebs.common.core.domain.BaseEntity;

/**
 * パラメータ設定テーブル sys_config
 * 
 * @author ebs
 */
public class SysConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** パラメータ主キー */
    @Excel(name = "パラメータ主キー", cellType = ColumnType.NUMERIC)
    private Long configId;

    /** パラメータ名 */
    @Excel(name = "パラメータ名")
    private String configName;

    /** パラメータキー名 */
    @Excel(name = "パラメータキー名")
    private String configKey;

    /** パラメータキー値 */
    @Excel(name = "パラメータキー値")
    private String configValue;

    /** システム組み込み（Yはい Nいいえ） */
    @Excel(name = "システム組み込み", readConverterExp = "Y=はい ,N=いいえ")
    private String configType;

    public Long getConfigId()
    {
        return configId;
    }

    public void setConfigId(Long configId)
    {
        this.configId = configId;
    }

    @NotBlank(message = "パラメータ名を空白にすることはできません")
    @Size(min = 0, max = 100, message = "パラメータ名は100文字を超えることはできません")
    public String getConfigName()
    {
        return configName;
    }

    public void setConfigName(String configName)
    {
        this.configName = configName;
    }

    @NotBlank(message = "パラメータキー名を空白にすることはできません")
    @Size(min = 0, max = 100, message = "パラメータキー名は100文字を超えることはできません")
    public String getConfigKey()
    {
        return configKey;
    }

    public void setConfigKey(String configKey)
    {
        this.configKey = configKey;
    }

    @NotBlank(message = "パラメータキー値を空白にすることはできません")
    @Size(min = 0, max = 500, message = "パラメータキー値は500文字を超えることはできません")
    public String getConfigValue()
    {
        return configValue;
    }

    public void setConfigValue(String configValue)
    {
        this.configValue = configValue;
    }

    public String getConfigType()
    {
        return configType;
    }

    public void setConfigType(String configType)
    {
        this.configType = configType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("configId", getConfigId())
            .append("configName", getConfigName())
            .append("configKey", getConfigKey())
            .append("configValue", getConfigValue())
            .append("configType", getConfigType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
