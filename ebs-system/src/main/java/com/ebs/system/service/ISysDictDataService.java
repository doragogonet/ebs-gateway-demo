package com.ebs.system.service;

import java.util.List;
import com.ebs.common.core.domain.entity.SysDictData;

/**
 * 辞書
 * 
 * @author ebs
 */
public interface ISysDictDataService
{
  
    public List<SysDictData> selectDictDataList(SysDictData dictData);

    public String selectDictLabel(String dictType, String dictValue);

    public SysDictData selectDictDataById(Long dictCode);

    public void deleteDictDataByIds(String ids);

    public int insertDictData(SysDictData dictData);

    public int updateDictData(SysDictData dictData);
}
