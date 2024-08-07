package com.ebs.system.mapper;

import java.util.List;
import com.ebs.system.domain.SysLogininfor;

/**
 * システムアクセスログ
 * 
 * @author ebs
 */
public interface SysLogininforMapper
{
   
    public void insertLogininfor(SysLogininfor logininfor);

    public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor);

    public int deleteLogininforByIds(String[] ids);

    public int cleanLogininfor();
}
