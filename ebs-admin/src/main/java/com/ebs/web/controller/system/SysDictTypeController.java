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
import com.ebs.common.core.domain.Ztree;
import com.ebs.common.core.domain.entity.SysDictType;
import com.ebs.common.core.page.TableDataInfo;
import com.ebs.common.enums.BusinessType;
import com.ebs.common.utils.poi.ExcelUtil;
import com.ebs.system.service.ISysDictTypeService;


@Controller
@RequestMapping("/system/dict")
public class SysDictTypeController extends BaseController
{
    private String prefix = "system/dict/type";

    @Autowired
    private ISysDictTypeService dictTypeService;

    @RequiresPermissions("system:dict:view")
    @GetMapping()
    public String dictType()
    {
        return prefix + "/type";
    }

    @PostMapping("/list")
    @RequiresPermissions("system:dict:list")
    @ResponseBody
    public TableDataInfo list(SysDictType dictType)
    {
        startPage();
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        return getDataTable(list);
    }

    @Log(title = "辞書タイプ", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:dict:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysDictType dictType)
    {

        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        ExcelUtil<SysDictType> util = new ExcelUtil<SysDictType>(SysDictType.class);
        return util.exportExcel(list, "辞書タイプ");
    }


    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

   
    @Log(title = "辞書タイプ", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:dict:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysDictType dict)
    {
        if (!dictTypeService.checkDictTypeUnique(dict))
        {
            return error("「" + dict.getDictName() + "」が存在しました");
        }
        dict.setCreateBy(getLoginName());
        return toAjax(dictTypeService.insertDictType(dict));
    }


    @RequiresPermissions("system:dict:edit")
    @GetMapping("/edit/{dictId}")
    public String edit(@PathVariable("dictId") Long dictId, ModelMap mmap)
    {
        mmap.put("dict", dictTypeService.selectDictTypeById(dictId));
        return prefix + "/edit";
    }

  
    @Log(title = "辞書タイプ", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:dict:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysDictType dict)
    {
        if (!dictTypeService.checkDictTypeUnique(dict))
        {
            return error("「" + dict.getDictName() + "」が存在しました");
        }
        dict.setUpdateBy(getLoginName());
        return toAjax(dictTypeService.updateDictType(dict));
    }

    @Log(title = "辞書タイプ", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:dict:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        dictTypeService.deleteDictTypeByIds(ids);
        return success();
    }

  
    @RequiresPermissions("system:dict:remove")
    @Log(title = "辞書タイプ", businessType = BusinessType.CLEAN)
    @GetMapping("/refreshCache")
    @ResponseBody
    public AjaxResult refreshCache()
    {
        dictTypeService.resetDictCache();
        return success();
    }


    @RequiresPermissions("system:dict:list")
    @GetMapping("/detail/{dictId}")
    public String detail(@PathVariable("dictId") Long dictId, ModelMap mmap)
    {
        mmap.put("dict", dictTypeService.selectDictTypeById(dictId));
        mmap.put("dictList", dictTypeService.selectDictTypeAll());
        return "system/dict/data/data";
    }


    @PostMapping("/checkDictTypeUnique")
    @ResponseBody
    public boolean checkDictTypeUnique(SysDictType dictType)
    {
        return dictTypeService.checkDictTypeUnique(dictType);
    }

   
    @GetMapping("/selectDictTree/{columnId}/{dictType}")
    public String selectDeptTree(@PathVariable("columnId") Long columnId, @PathVariable("dictType") String dictType,
            ModelMap mmap)
    {
        mmap.put("columnId", columnId);
        mmap.put("dict", dictTypeService.selectDictTypeByType(dictType));
        return prefix + "/tree";
    }

  
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> ztrees = dictTypeService.selectDictTree(new SysDictType());
        return ztrees;
    }
}
