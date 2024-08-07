package com.ebs.framework.aspectj;

import java.util.Collection;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.ebs.common.annotation.Log;
import com.ebs.common.core.domain.entity.SysUser;
import com.ebs.common.enums.BusinessStatus;
import com.ebs.common.utils.ServletUtils;
import com.ebs.common.utils.ShiroUtils;
import com.ebs.common.utils.StringUtils;
import com.ebs.framework.manager.AsyncManager;
import com.ebs.framework.manager.factory.AsyncFactory;
import com.ebs.system.domain.SysOperLog;

/**
 * 操作ログ
 * 
 * @author ruoyi
 */
@Aspect
@Component
public class LogAspect
{
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    /** 機密属性フィールドの除外 */
    public static final String[] EXCLUDE_PROPERTIES = { "password", "oldPassword", "newPassword", "confirmPassword" };

    /** オペレーション消費時間の計算 */
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new NamedThreadLocal<Long>("Cost Time");

    /**
     * リクエストを処理前に実行
     */
    @Before(value = "@annotation(controllerLog)")
    public void boBefore(JoinPoint joinPoint, Log controllerLog)
    {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    /**
     * リクエストを処理後に実行
     *
     * @param joinPoint
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult)
    {
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    /**
     * 異常操作をブロック
     * 
     * @param joinPoint 
     * @param e 
     */
    @AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e)
    {
        handleLog(joinPoint, controllerLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, Log controllerLog, final Exception e, Object jsonResult)
    {
        try
        {
      
            SysUser currentUser = ShiroUtils.getSysUser();

            SysOperLog operLog = new SysOperLog();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            
            String ip = ShiroUtils.getIp();
            operLog.setOperIp(ip);
            operLog.setOperUrl(StringUtils.substring(ServletUtils.getRequest().getRequestURI(), 0, 255));
            if (currentUser != null)
            {
                operLog.setOperName(currentUser.getLoginName());
                if (StringUtils.isNotNull(currentUser.getDept())
                        && StringUtils.isNotEmpty(currentUser.getDept().getDeptName()))
                {
                    operLog.setDeptName(currentUser.getDept().getDeptName());
                }
            }

            if (e != null)
            {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
          
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            
            getControllerMethodDescription(joinPoint, controllerLog, operLog, jsonResult);
           
            operLog.setCostTime(System.currentTimeMillis() - TIME_THREADLOCAL.get());
          
            AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
        }
        catch (Exception exp)
        {
          
            log.error("異常情報:{}", exp.getMessage());
            exp.printStackTrace();
        }
        finally
        {
            TIME_THREADLOCAL.remove();
        }
    }

    /**
     * 注記内のメソッドの説明情報を取得するControllerレイヤ注記用
     * 
     * @param log 
     * @param operLog 
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperLog operLog, Object jsonResult) throws Exception
    {
      
        operLog.setBusinessType(log.businessType().ordinal());
       
        operLog.setTitle(log.title());
       
        operLog.setOperatorType(log.operatorType().ordinal());
      
        if (log.isSaveRequestData())
        {
           
            setRequestValue(joinPoint, operLog, log.excludeParamNames());
        }
      
        if (log.isSaveResponseData() && StringUtils.isNotNull(jsonResult))
        {
            operLog.setJsonResult(StringUtils.substring(JSONObject.toJSONString(jsonResult), 0, 2000));
        }
    }

    /**
     * 要求されたパラメータを取得し、logにドロップ
     * 
     * @param operLog 
     * @throws Exception 
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperLog operLog, String[] excludeParamNames) throws Exception
    {
        Map<String, String[]> map = ServletUtils.getRequest().getParameterMap();
        if (StringUtils.isNotEmpty(map))
        {
            String params = JSONObject.toJSONString(map, excludePropertyPreFilter(excludeParamNames));
            operLog.setOperParam(StringUtils.substring(params, 0, 2000));
        }
        else
        {
            Object args = joinPoint.getArgs();
            if (StringUtils.isNotNull(args))
            {
                String params = argsArrayToString(joinPoint.getArgs(), excludeParamNames);
                operLog.setOperParam(StringUtils.substring(params, 0, 2000));
            }
        }
    }

    /**
     * 機密属性を無視
     */
    public PropertyPreFilters.MySimplePropertyPreFilter excludePropertyPreFilter(String[] excludeParamNames)
    {
        return new PropertyPreFilters().addFilter().addExcludes(ArrayUtils.addAll(EXCLUDE_PROPERTIES, excludeParamNames));
    }

    /**
     * パラメータ組立
     */
    private String argsArrayToString(Object[] paramsArray, String[] excludeParamNames)
    {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0)
        {
            for (Object o : paramsArray)
            {
                if (StringUtils.isNotNull(o) && !isFilterObject(o))
                {
                    try
                    {
                        Object jsonObj = JSONObject.toJSONString(o, excludePropertyPreFilter(excludeParamNames));
                        params += jsonObj.toString() + " ";
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
        }
        return params.trim();
    }

    /**
     * フィルタが必要かどうかを判断する
     * 
     * @param o 
     * @return 必要true　必要しないfalse
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o)
    {
        Class<?> clazz = o.getClass();
        if (clazz.isArray())
        {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        }
        else if (Collection.class.isAssignableFrom(clazz))
        {
            Collection collection = (Collection) o;
            for (Object value : collection)
            {
                return value instanceof MultipartFile;
            }
        }
        else if (Map.class.isAssignableFrom(clazz))
        {
            Map map = (Map) o;
            for (Object value : map.entrySet())
            {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}
