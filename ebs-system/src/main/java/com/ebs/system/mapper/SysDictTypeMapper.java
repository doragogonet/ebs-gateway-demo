package com.ebs.system.mapper;

import java.util.List;
import com.ebs.common.core.domain.entity.SysDictType;

/**
 * 辞書表
 * 
 * @author ebs
 */
public interface SysDictTypeMapper
{
   
    public List<SysDictType> selectDictTypeList(SysDictType dictType);

    public List<SysDictType> selectDictTypeAll();

    public SysDictType selectDictTypeById(Long dictId);

    public SysDictType selectDictTypeByType(String dictType);

    public int deleteDictTypeById(Long dictId);

    public int deleteDictTypeByIds(Long[] ids);

    public int insertDictType(SysDictType dictType);

    public int updateDictType(SysDictType dictType);

    public SysDictType checkDictTypeUnique(String dictType);
}
