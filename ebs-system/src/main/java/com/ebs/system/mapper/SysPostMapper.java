package com.ebs.system.mapper;

import java.util.List;
import com.ebs.system.domain.SysPost;

/**
 * ポスト
 * 
 * @author ebs
 */
public interface SysPostMapper
{
  
    public List<SysPost> selectPostList(SysPost post);

    public List<SysPost> selectPostAll();

    public List<SysPost> selectPostsByUserId(Long userId);

    public SysPost selectPostById(Long postId);

    public int deletePostByIds(Long[] ids);

    public int updatePost(SysPost post);

    public int insertPost(SysPost post);

    public SysPost checkPostNameUnique(String postName);

    public SysPost checkPostCodeUnique(String postCode);
}
