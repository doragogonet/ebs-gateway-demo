package com.ebs.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;
import com.ebs.common.annotation.Excel;
import com.ebs.common.annotation.Excel.ColumnType;
import com.ebs.common.core.domain.BaseEntity;

/**
 * 操作ログテーブル oper_log
 * 
 * @author ebs
 */
public class SysOperLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ログのプライマリ・キー */
    @Excel(name = "ログID", cellType = ColumnType.NUMERIC)
    private Long operId;

    /** モジュールタイトル */
    @Excel(name = "モジュールタイトル")
    private String title;

    /** ビジネス・タイプ（0その他 1追加 2変更 3削除） */
    @Excel(name = "ビジネス・タイプ", readConverterExp = "0=その他 ,1=追加,2=変更,3=削除,4=権限付与,5=エクスポート,6=インポート,7=強退,8=コード生成,9=データクリア")
    private Integer businessType;

    private Integer[] businessTypes;

    /** メソッド名' */
    @Excel(name = "メソッド名'")
    private String method;

    /** リクエストモード */
    @Excel(name = "リクエストモード")
    private String requestMethod;

    /** 操作カテゴリ（0その他 1バックグラウンド・ユーザー 2携帯電話端末ユーザー） */
    @Excel(name = "操作カテゴリ", readConverterExp = "0=その他,1=バックグラウンド・ユーザー,2=携帯電話端末ユーザー")
    private Integer operatorType;

    /** オペレータ */
    @Excel(name = "オペレータ")
    private String operName;

    /** 部署名 */
    @Excel(name = "部署名")
    private String deptName;

    /** リクエストURL */
    @Excel(name = "リクエストURL")
    private String operUrl;

    /** ホストアドレス */
    @Excel(name = "ホストアドレス")
    private String operIp;

    /** オペレーション場所 */
    @Excel(name = "オペレーション場所")
    private String operLocation;

    /** リクエストパラメータ */
    @Excel(name = "リクエストパラメータ")
    private String operParam;

    /** パラメータを返す */
    @Excel(name = "パラメータを返す")
    private String jsonResult;

    /** オペレーションステータス（0正常 1異常） */
    @Excel(name = "オペレーションステータス", readConverterExp = "0=正常,1=異常")
    private Integer status;

    /** エラーメッセージ */
    @Excel(name = "エラーメッセージ")
    private String errorMsg;

    /** 操作时间 */
    @Excel(name = "操作時間", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;

    /** 消耗时间 */
    @Excel(name = "使用時間", suffix = "ミリ秒")
    private Long costTime;

    public Long getOperId()
    {
        return operId;
    }

    public void setOperId(Long operId)
    {
        this.operId = operId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Integer getBusinessType()
    {
        return businessType;
    }

    public void setBusinessType(Integer businessType)
    {
        this.businessType = businessType;
    }

    public Integer[] getBusinessTypes()
    {
        return businessTypes;
    }

    public void setBusinessTypes(Integer[] businessTypes)
    {
        this.businessTypes = businessTypes;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public String getRequestMethod()
    {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod)
    {
        this.requestMethod = requestMethod;
    }

    public Integer getOperatorType()
    {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType)
    {
        this.operatorType = operatorType;
    }

    public String getOperName()
    {
        return operName;
    }

    public void setOperName(String operName)
    {
        this.operName = operName;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getOperUrl()
    {
        return operUrl;
    }

    public void setOperUrl(String operUrl)
    {
        this.operUrl = operUrl;
    }

    public String getOperIp()
    {
        return operIp;
    }

    public void setOperIp(String operIp)
    {
        this.operIp = operIp;
    }

    public String getOperLocation()
    {
        return operLocation;
    }

    public void setOperLocation(String operLocation)
    {
        this.operLocation = operLocation;
    }

    public String getOperParam()
    {
        return operParam;
    }

    public void setOperParam(String operParam)
    {
        this.operParam = operParam;
    }

    public String getJsonResult()
    {
        return jsonResult;
    }

    public void setJsonResult(String jsonResult)
    {
        this.jsonResult = jsonResult;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public Date getOperTime()
    {
        return operTime;
    }

    public void setOperTime(Date operTime)
    {
        this.operTime = operTime;
    }

    public Long getCostTime()
    {
        return costTime;
    }

    public void setCostTime(Long costTime)
    {
        this.costTime = costTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("operId", getOperId())
            .append("title", getTitle())
            .append("businessType", getBusinessType())
            .append("businessTypes", getBusinessTypes())
            .append("method", getMethod())
            .append("requestMethod", getRequestMethod())
            .append("operatorType", getOperatorType())
            .append("operName", getOperName())
            .append("deptName", getDeptName())
            .append("operUrl", getOperUrl())
            .append("operIp", getOperIp())
            .append("operLocation", getOperLocation())
            .append("operParam", getOperParam())
            .append("status", getStatus())
            .append("errorMsg", getErrorMsg())
            .append("operTime", getOperTime())
            .append("costTime", getCostTime())
            .toString();
    }
}
