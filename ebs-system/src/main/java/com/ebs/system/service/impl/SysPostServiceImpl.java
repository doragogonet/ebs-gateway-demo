package com.ebs.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ebs.common.constant.UserConstants;
import com.ebs.common.core.text.Convert;
import com.ebs.common.exception.ServiceException;
import com.ebs.common.utils.StringUtils;
import com.ebs.system.domain.SysPost;
import com.ebs.system.mapper.SysPostMapper;
import com.ebs.system.mapper.SysUserPostMapper;
import com.ebs.system.service.ISysPostService;

/**
 * ポスト
 * 
 * @author ebs
 */
@Service
public class SysPostServiceImpl implements ISysPostService
{
    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

  
    @Override
    public List<SysPost> selectPostList(SysPost post)
    {
        return postMapper.selectPostList(post);
    }

    @Override
    public List<SysPost> selectPostAll()
    {
        return postMapper.selectPostAll();
    }

    @Override
    public List<SysPost> selectPostsByUserId(Long userId)
    {
        List<SysPost> userPosts = postMapper.selectPostsByUserId(userId);
        List<SysPost> posts = postMapper.selectPostAll();
        for (SysPost post : posts)
        {
            for (SysPost userRole : userPosts)
            {
                if (post.getPostId().longValue() == userRole.getPostId().longValue())
                {
                    post.setFlag(true);
                    break;
                }
            }
        }
        return posts;
    }

    @Override
    public SysPost selectPostById(Long postId)
    {
        return postMapper.selectPostById(postId);
    }

    @Override
    public int deletePostByIds(String ids)
    {
        Long[] postIds = Convert.toLongArray(ids);
        for (Long postId : postIds)
        {
            SysPost post = selectPostById(postId);
            if (countUserPostById(postId) > 0)
            {
                throw new ServiceException(String.format("%1$sが使用中なので、削除できません", post.getPostName()));
            }
        }
        return postMapper.deletePostByIds(postIds);
    }

    @Override
    public int insertPost(SysPost post)
    {
        return postMapper.insertPost(post);
    }

    @Override
    public int updatePost(SysPost post)
    {
        return postMapper.updatePost(post);
    }

    @Override
    public int countUserPostById(Long postId)
    {
        return userPostMapper.countUserPostById(postId);
    }

    @Override
    public boolean checkPostNameUnique(SysPost post)
    {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        SysPost info = postMapper.checkPostNameUnique(post.getPostName());
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public boolean checkPostCodeUnique(SysPost post)
    {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        SysPost info = postMapper.checkPostCodeUnique(post.getPostCode());
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
