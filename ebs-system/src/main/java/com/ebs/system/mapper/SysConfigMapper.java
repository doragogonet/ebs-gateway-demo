package com.ebs.system.mapper;

import java.util.List;
import com.ebs.system.domain.SysConfig;

/**
 * パラメータ設定
 * 
 * @author ebs
 */
public interface SysConfigMapper
{
    
    public SysConfig selectConfig(SysConfig config);

    public SysConfig selectConfigById(Long configId);

    public List<SysConfig> selectConfigList(SysConfig config);

    public SysConfig checkConfigKeyUnique(String configKey);

    public int insertConfig(SysConfig config);

    public int updateConfig(SysConfig config);

    public int deleteConfigById(Long configId);

    public int deleteConfigByIds(String[] configIds);
}