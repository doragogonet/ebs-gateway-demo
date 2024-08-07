package com.ebs.generator.mapper;

import java.util.List;
import com.ebs.generator.domain.GenTableColumn;

public interface GenTableColumnMapper
{
  
    public List<GenTableColumn> selectDbTableColumnsByName(String tableName);

    public List<GenTableColumn> selectGenTableColumnListByTableId(GenTableColumn genTableColumn);

    public int insertGenTableColumn(GenTableColumn genTableColumn);

    public int updateGenTableColumn(GenTableColumn genTableColumn);

    public int deleteGenTableColumns(List<GenTableColumn> genTableColumns);

    public int deleteGenTableColumnByIds(Long[] ids);
}
