package com.ebs.system.service;

import java.util.List;
import com.ebs.system.domain.SysLogininfor;

/**
 * システムアクセスログ
 * 
 * @author ebs
 */
public interface ISysLogininforService
{
   
    public void insertLogininfor(SysLogininfor logininfor);

    public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor);

    public int deleteLogininforByIds(String ids);

    public void cleanLogininfor();
}
