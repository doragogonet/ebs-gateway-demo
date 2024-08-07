package com.ebs.framework.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ebs.system.service.ISysConfigService;


@Service("config")
public class ConfigService
{
    @Autowired
    private ISysConfigService configService;


    public String getKey(String configKey)
    {
        return configService.selectConfigByKey(configKey);
    }
}
