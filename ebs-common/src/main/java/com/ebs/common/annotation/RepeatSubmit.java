package com.ebs.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * カスタム注記フォームの再送信を防ぐ
 * 
 * @author ebs
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit
{
    /**
     * かんかくじかん(ms)、この時間未満は重複コミットとみなされます
     */
    public int interval() default 5000;

    public String message() default "重複コミットは許可されていません、後で再試行してください";
}