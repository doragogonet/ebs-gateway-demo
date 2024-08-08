package com.ebs;

import com.mot.rfid.api3.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

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
        SpringApplication.run(EbsApplication.class, args);
        System.out.println("サーバー起動成功");
    }
}