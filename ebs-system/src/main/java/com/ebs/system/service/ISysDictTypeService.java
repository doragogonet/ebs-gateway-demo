package com.ebs.system.service;

import java.util.List;
import com.ebs.common.core.domain.Ztree;
import com.ebs.common.core.domain.entity.SysDictData;
import com.ebs.common.core.domain.entity.SysDictType;

/**
 * 辞書
 * 
 * @author ebs
 */
public interface ISysDictTypeService
{
   
    public List<SysDictType> selectDictTypeList(SysDictType dictType);

    public List<SysDictType> selectDictTypeAll();

    public List<SysDictData> selectDictDataByType(String dictType);

    public SysDictType selectDictTypeById(Long dictId);

    public SysDictType selectDictTypeByType(String dictType);

    public void deleteDictTypeByIds(String ids);

    public void loadingDictCache();

    public void clearDictCache();

    public void resetDictCache();

    public int insertDictType(SysDictType dictType);

    public int updateDictType(SysDictType dictType);

    public boolean checkDictTypeUnique(SysDictType dictType);

    public List<Ztree> selectDictTree(SysDictType dictType);
}
