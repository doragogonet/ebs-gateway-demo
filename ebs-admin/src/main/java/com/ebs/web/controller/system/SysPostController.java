package com.ebs.web.controller.system;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ebs.common.annotation.Log;
import com.ebs.common.core.controller.BaseController;
import com.ebs.common.core.domain.AjaxResult;
import com.ebs.common.core.page.TableDataInfo;
import com.ebs.common.enums.BusinessType;
import com.ebs.common.utils.poi.ExcelUtil;
import com.ebs.system.domain.SysPost;
import com.ebs.system.service.ISysPostService;


@Controller
@RequestMapping("/system/post")
public class SysPostController extends BaseController
{
    private String prefix = "system/post";

    @Autowired
    private ISysPostService postService;

    @RequiresPermissions("system:post:view")
    @GetMapping()
    public String operlog()
    {
        return prefix + "/post";
    }

    @RequiresPermissions("system:post:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysPost post)
    {
        startPage();
        List<SysPost> list = postService.selectPostList(post);
        return getDataTable(list);
    }

    @Log(title = "ポスト管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:post:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysPost post)
    {
        List<SysPost> list = postService.selectPostList(post);
        ExcelUtil<SysPost> util = new ExcelUtil<SysPost>(SysPost.class);
        return util.exportExcel(list, "ポストデータ");
    }

    @RequiresPermissions("system:post:remove")
    @Log(title = "ポスト管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try
        {
            return toAjax(postService.deletePostByIds(ids));
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }

  
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

   
    @RequiresPermissions("system:post:add")
    @Log(title = "ポスト管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysPost post)
    {
        if (!postService.checkPostNameUnique(post))
        {
            return error("「" + post.getPostName() + "」が存在しました");
        }
        else if (!postService.checkPostCodeUnique(post))
        {
            return error("「" + post.getPostName() + "」が存在しました");
        }
        post.setCreateBy(getLoginName());
        return toAjax(postService.insertPost(post));
    }


    @RequiresPermissions("system:post:edit")
    @GetMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") Long postId, ModelMap mmap)
    {
        mmap.put("post", postService.selectPostById(postId));
        return prefix + "/edit";
    }

 
    @RequiresPermissions("system:post:edit")
    @Log(title = "ポスト管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysPost post)
    {
        if (!postService.checkPostNameUnique(post))
        {
            return error("「" + post.getPostName() + "」が存在しました");
        }
        else if (!postService.checkPostCodeUnique(post))
        {
            return error("「" + post.getPostName() + "」が存在しました");
        }
        post.setUpdateBy(getLoginName());
        return toAjax(postService.updatePost(post));
    }

   
    @PostMapping("/checkPostNameUnique")
    @ResponseBody
    public boolean checkPostNameUnique(SysPost post)
    {
        return postService.checkPostNameUnique(post);
    }


    @PostMapping("/checkPostCodeUnique")
    @ResponseBody
    public boolean checkPostCodeUnique(SysPost post)
    {
        return postService.checkPostCodeUnique(post);
    }
}
