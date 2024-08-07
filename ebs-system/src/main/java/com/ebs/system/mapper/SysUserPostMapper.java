package com.ebs.system.mapper;

import java.util.List;
import com.ebs.system.domain.SysUserPost;

/**
 * ユーザーとポスト
 * 
 * @author ebs
 */
public interface SysUserPostMapper
{
   
    public int deleteUserPostByUserId(Long userId);
    
    public int countUserPostById(Long postId);
    
    public int deleteUserPost(Long[] ids);

    public int batchUserPost(List<SysUserPost> userPostList);
}
