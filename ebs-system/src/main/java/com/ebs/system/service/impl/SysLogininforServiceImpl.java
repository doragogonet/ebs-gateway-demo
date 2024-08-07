package com.ebs.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ebs.common.core.text.Convert;
import com.ebs.system.domain.SysLogininfor;
import com.ebs.system.mapper.SysLogininforMapper;
import com.ebs.system.service.ISysLogininforService;

/**
 * システムアクセスログ
 * 
 * @author ebs
 */
@Service
public class SysLogininforServiceImpl implements ISysLogininforService
{

    @Autowired
    private SysLogininforMapper logininforMapper;

    @Override
    public void insertLogininfor(SysLogininfor logininfor)
    {
        logininforMapper.insertLogininfor(logininfor);
    }

    @Override
    public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor)
    {
        return logininforMapper.selectLogininforList(logininfor);
    }

    @Override
    public int deleteLogininforByIds(String ids)
    {
        return logininforMapper.deleteLogininforByIds(Convert.toStrArray(ids));
    }

    @Override
    public void cleanLogininfor()
    {
        logininforMapper.cleanLogininfor();
    }
}
