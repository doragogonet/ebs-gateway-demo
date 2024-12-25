package com.ebs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.File;

/**
 * 启动程序
 * 
 * @author ebs
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class EbsApplication
{
    public static void main(String[] args)
    {

        // System.setProperty("spring.devtools.restart.enabled", "false");
        //logback.xmlのパスを変更
        System.setProperty("logging.config", System.getProperty("user.dir") + File.separator + "logback.xml");
        SpringApplication.run(EbsApplication.class, args);
        System.out.println("サーバー起動成功");

    }
}