package com.ebs.framework.web.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ebs.common.core.domain.entity.SysDictData;
import com.ebs.system.service.ISysDictDataService;
import com.ebs.system.service.ISysDictTypeService;


@Service("dict")
public class DictService
{
    @Autowired
    private ISysDictTypeService dictTypeService;

    @Autowired
    private ISysDictDataService dictDataService;


    public List<SysDictData> getType(String dictType)
    {
        return dictTypeService.selectDictDataByType(dictType);
    }


    public String getLabel(String dictType, String dictValue)
    {
        return dictDataService.selectDictLabel(dictType, dictValue);
    }
}
