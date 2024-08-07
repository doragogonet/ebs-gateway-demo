package com.ebs.system.service;

import java.util.List;
import com.ebs.system.domain.SysConfig;

/**
 * パラメータ設定
 * 
 * @author ebs
 */
public interface ISysConfigService
{
  
    public SysConfig selectConfigById(Long configId);

    public String selectConfigByKey(String configKey);

    public List<SysConfig> selectConfigList(SysConfig config);

    public int insertConfig(SysConfig config);

    public int updateConfig(SysConfig config);

    public void deleteConfigByIds(String ids);

    public void loadingConfigCache();

    public void clearConfigCache();

    public void resetConfigCache();

    public boolean checkConfigKeyUnique(SysConfig config);
}
