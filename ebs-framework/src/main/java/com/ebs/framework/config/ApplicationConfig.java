package com.ebs.framework.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@Configuration

@EnableAspectJAutoProxy(exposeProxy = true)

@MapperScan("com.ebs.**.mapper")
public class ApplicationConfig
{

}
