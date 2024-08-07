package com.ebs.framework.web.exception;

import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import com.ebs.common.core.domain.AjaxResult;
import com.ebs.common.exception.DemoModeException;
import com.ebs.common.exception.ServiceException;
import com.ebs.common.utils.ServletUtils;
import com.ebs.common.utils.security.PermissionUtils;

/**
 * グローバル例外を処理
 * 
 * @author ebs
 */
@RestControllerAdvice
public class GlobalExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(AuthorizationException.class)
    public Object handleAuthorizationException(AuthorizationException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("リクエストアドレス'{}'、権限チェック失敗'{}'", requestURI, e.getMessage());
        if (ServletUtils.isAjaxRequest(request))
        {
            return AjaxResult.error(PermissionUtils.getMsg(e.getMessage()));
        }
        else
        {
            return new ModelAndView("error/unauth");
        }
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AjaxResult handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
            HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("リクエストアドレス'{}'、サポートされていません'{}'リクエスト", requestURI, e.getMethod());
        return AjaxResult.error(e.getMessage());
    }


    @ExceptionHandler(RuntimeException.class)
    public AjaxResult handleRuntimeException(RuntimeException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("リクエストアドレス'{}'、不明な例外が発生しました。", requestURI, e);
        return AjaxResult.error(e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("リクエストアドレス'{}'、システム異常発生", requestURI, e);
        return AjaxResult.error(e.getMessage());
    }


    @ExceptionHandler(ServiceException.class)
    public Object handleServiceException(ServiceException e, HttpServletRequest request)
    {
        log.error(e.getMessage(), e);
        if (ServletUtils.isAjaxRequest(request))
        {
            return AjaxResult.error(e.getMessage());
        }
        else
        {
            return new ModelAndView("error/service", "errorMessage", e.getMessage());
        }
    }


    @ExceptionHandler(MissingPathVariableException.class)
    public AjaxResult handleMissingPathVariableException(MissingPathVariableException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("要求パスに必要なパス変数がありません'{}'、システム異常発生", requestURI, e);
        return AjaxResult.error(String.format("要求パスに必要なパス変数がありません[%s]", e.getVariableName()));
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public AjaxResult handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e,
            HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("要求パラメータタイプが一致しません'{}'、システム異常発生", requestURI, e);
        return AjaxResult.error(String.format("要求パラメータタイプが一致しません'，パラメータ[%s]要求タイプは：'%s'が、入力値は：'%s'", e.getName(), e.getRequiredType().getName(), e.getValue()));
    }


    @ExceptionHandler(BindException.class)
    public AjaxResult handleBindException(BindException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return AjaxResult.error(message);
    }

    @ExceptionHandler(DemoModeException.class)
    public AjaxResult handleDemoModeException(DemoModeException e)
    {
        return AjaxResult.error("デモモード、操作不可");
    }
}
