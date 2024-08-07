package com.ebs.system.domain;

import javax.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ebs.common.annotation.Excel;
import com.ebs.common.annotation.Excel.ColumnType;
import com.ebs.common.core.domain.BaseEntity;

/**
 * ポストテーブル sys_post
 * 
 * @author ebs
 */
public class SysPost extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ポストID */
    @Excel(name = "ポストID", cellType = ColumnType.NUMERIC)
    private Long postId;

    /** ポストコード */
    @Excel(name = "ポストコード")
    private String postCode;

    /** ポスト名 */
    @Excel(name = "ポスト名")
    private String postName;

    /** 表示順序 */
    @Excel(name = "表示順序", cellType = ColumnType.NUMERIC)
    private String postSort;

    /** 状態（0正常 1停止） */
    @Excel(name = "状態", readConverterExp = "0=正常,1=停止")
    private String status;

    private boolean flag = false;

    public Long getPostId()
    {
        return postId;
    }

    public void setPostId(Long postId)
    {
        this.postId = postId;
    }

    @NotBlank(message = "ポストコードを空にすることはできません")
    @Size(min = 0, max = 64, message = "ポストコードは64文字以内")
    public String getPostCode()
    {
        return postCode;
    }

    public void setPostCode(String postCode)
    {
        this.postCode = postCode;
    }

    @NotBlank(message = "ポスト名を空にすることはできません")
    @Size(min = 0, max = 50, message = "ポスト名は50文字以内")
    public String getPostName()
    {
        return postName;
    }

    public void setPostName(String postName)
    {
        this.postName = postName;
    }

    @NotBlank(message = "表示順序を空にすることはできません")
    public String getPostSort()
    {
        return postSort;
    }

    public void setPostSort(String postSort)
    {
        this.postSort = postSort;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public boolean isFlag()
    {
        return flag;
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("postId", getPostId())
            .append("postCode", getPostCode())
            .append("postName", getPostName())
            .append("postSort", getPostSort())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
