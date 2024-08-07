package com.ebs.system.service.impl;

import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ebs.common.constant.Constants;
import com.ebs.common.constant.UserConstants;
import com.ebs.common.core.text.Convert;
import com.ebs.common.exception.ServiceException;
import com.ebs.common.utils.CacheUtils;
import com.ebs.common.utils.StringUtils;
import com.ebs.system.domain.SysConfig;
import com.ebs.system.mapper.SysConfigMapper;
import com.ebs.system.service.ISysConfigService;

/**
 * パラメーター設定
 * 
 * @author ebs
 */
@Service
public class SysConfigServiceImpl implements ISysConfigService
{
    @Autowired
    private SysConfigMapper configMapper;

    @PostConstruct
    public void init()
    {
        loadingConfigCache();
    }

    @Override
    public SysConfig selectConfigById(Long configId)
    {
        SysConfig config = new SysConfig();
        config.setConfigId(configId);
        return configMapper.selectConfig(config);
    }

    @Override
    public String selectConfigByKey(String configKey)
    {
        String configValue = Convert.toStr(CacheUtils.get(getCacheName(), getCacheKey(configKey)));
        if (StringUtils.isNotEmpty(configValue))
        {
            return configValue;
        }
        SysConfig config = new SysConfig();
        config.setConfigKey(configKey);
        SysConfig retConfig = configMapper.selectConfig(config);
        if (StringUtils.isNotNull(retConfig))
        {
            CacheUtils.put(getCacheName(), getCacheKey(configKey), retConfig.getConfigValue());
            return retConfig.getConfigValue();
        }
        return StringUtils.EMPTY;
    }

    @Override
    public List<SysConfig> selectConfigList(SysConfig config)
    {
        return configMapper.selectConfigList(config);
    }

    @Override
    public int insertConfig(SysConfig config)
    {
        int row = configMapper.insertConfig(config);
        if (row > 0)
        {
            CacheUtils.put(getCacheName(), getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    @Override
    public int updateConfig(SysConfig config)
    {
        SysConfig temp = configMapper.selectConfigById(config.getConfigId());
        if (!StringUtils.equals(temp.getConfigKey(), config.getConfigKey()))
        {
            CacheUtils.remove(getCacheName(), getCacheKey(temp.getConfigKey()));
        }

        int row = configMapper.updateConfig(config);
        if (row > 0)
        {
            CacheUtils.put(getCacheName(), getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    @Override
    public void deleteConfigByIds(String ids)
    {
        Long[] configIds = Convert.toLongArray(ids);
        for (Long configId : configIds)
        {
            SysConfig config = selectConfigById(configId);
            if (StringUtils.equals(UserConstants.YES, config.getConfigType()))
            {
                throw new ServiceException(String.format("組み込みパラメータ「%1$s」が削除できません ", config.getConfigKey()));
            }
            configMapper.deleteConfigById(configId);
            CacheUtils.remove(getCacheName(), getCacheKey(config.getConfigKey()));
        }
    }

    @Override
    public void loadingConfigCache()
    {
        List<SysConfig> configsList = configMapper.selectConfigList(new SysConfig());
        for (SysConfig config : configsList)
        {
            CacheUtils.put(getCacheName(), getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
    }

    @Override
    public void clearConfigCache()
    {
        CacheUtils.removeAll(getCacheName());
    }

    @Override
    public void resetConfigCache()
    {
        clearConfigCache();
        loadingConfigCache();
    }

    @Override
    public boolean checkConfigKeyUnique(SysConfig config)
    {
        Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
        SysConfig info = configMapper.checkConfigKeyUnique(config.getConfigKey());
        if (StringUtils.isNotNull(info) && info.getConfigId().longValue() != configId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    private String getCacheName()
    {
        return Constants.SYS_CONFIG_CACHE;
    }

    private String getCacheKey(String configKey)
    {
        return Constants.SYS_CONFIG_KEY + configKey;
    }
}
