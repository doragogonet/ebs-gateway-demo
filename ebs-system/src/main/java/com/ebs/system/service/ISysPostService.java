package com.ebs.system.service;

import java.util.List;
import com.ebs.system.domain.SysPost;

/**
 * 岗位ポスト
 * 
 * @author ebs
 */
public interface ISysPostService
{
   
    public List<SysPost> selectPostList(SysPost post);

    public List<SysPost> selectPostAll();

    public List<SysPost> selectPostsByUserId(Long userId);

    public SysPost selectPostById(Long postId);

    public int deletePostByIds(String ids);

    public int insertPost(SysPost post);

    public int updatePost(SysPost post);

    public int countUserPostById(Long postId);

    public boolean checkPostNameUnique(SysPost post);

    public boolean checkPostCodeUnique(SysPost post);
}
