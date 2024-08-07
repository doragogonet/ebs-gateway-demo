package com.ebs.generator.service;

import java.util.List;
import com.ebs.generator.domain.GenTableColumn;

public interface IGenTableColumnService
{
   
    public List<GenTableColumn> selectGenTableColumnListByTableId(GenTableColumn genTableColumn);

   
    public int insertGenTableColumn(GenTableColumn genTableColumn);

    public int updateGenTableColumn(GenTableColumn genTableColumn);

    public int deleteGenTableColumnByIds(String ids);
}
