package com.ebs.generator.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ebs.common.core.text.Convert;
import com.ebs.generator.domain.GenTableColumn;
import com.ebs.generator.mapper.GenTableColumnMapper;
import com.ebs.generator.service.IGenTableColumnService;

@Service
public class GenTableColumnServiceImpl implements IGenTableColumnService
{
    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    
    @Override
    public List<GenTableColumn> selectGenTableColumnListByTableId(GenTableColumn genTableColumn)
    {
        return genTableColumnMapper.selectGenTableColumnListByTableId(genTableColumn);
    }

    
    @Override
    public int insertGenTableColumn(GenTableColumn genTableColumn)
    {
        return genTableColumnMapper.insertGenTableColumn(genTableColumn);
    }

   
    @Override
    public int updateGenTableColumn(GenTableColumn genTableColumn)
    {
        return genTableColumnMapper.updateGenTableColumn(genTableColumn);
    }

    @Override
    public int deleteGenTableColumnByIds(String ids)
    {
        return genTableColumnMapper.deleteGenTableColumnByIds(Convert.toLongArray(ids));
    }
}