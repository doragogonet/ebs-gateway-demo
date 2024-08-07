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
import com.ebs.system.domain.SysConfig;
import com.ebs.system.service.ISysConfigService;


@Controller
@RequestMapping("/system/config")
public class SysConfigController extends BaseController
{
    private String prefix = "system/config";

    @Autowired
    private ISysConfigService configService;

    @RequiresPermissions("system:config:view")
    @GetMapping()
    public String config()
    {
        return prefix + "/config";
    }

 
    @RequiresPermissions("system:config:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysConfig config)
    {
        startPage();
        List<SysConfig> list = configService.selectConfigList(config);
        return getDataTable(list);
    }

    @Log(title = "パラメータ管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:config:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysConfig config)
    {
        List<SysConfig> list = configService.selectConfigList(config);
        ExcelUtil<SysConfig> util = new ExcelUtil<SysConfig>(SysConfig.class);
        return util.exportExcel(list, "パラメータデータ");
    }

  
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    @RequiresPermissions("system:config:add")
    @Log(title = "パラメータ管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysConfig config)
    {
        if (!configService.checkConfigKeyUnique(config))
        {
            return error("「" + config.getConfigName() + "」が存在しました");
        }
        config.setCreateBy(getLoginName());
        return toAjax(configService.insertConfig(config));
    }


    @RequiresPermissions("system:config:edit")
    @GetMapping("/edit/{configId}")
    public String edit(@PathVariable("configId") Long configId, ModelMap mmap)
    {
        mmap.put("config", configService.selectConfigById(configId));
        return prefix + "/edit";
    }

   
    @RequiresPermissions("system:config:edit")
    @Log(title = "パラメータ管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysConfig config)
    {
        if (!configService.checkConfigKeyUnique(config))
        {
            return error("「" + config.getConfigName() + "」が存在しました");
        }
        config.setUpdateBy(getLoginName());
        return toAjax(configService.updateConfig(config));
    }


    @RequiresPermissions("system:config:remove")
    @Log(title = "パラメータ管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        configService.deleteConfigByIds(ids);
        return success();
    }

 
    @RequiresPermissions("system:config:remove")
    @Log(title = "パラメータ管理", businessType = BusinessType.CLEAN)
    @GetMapping("/refreshCache")
    @ResponseBody
    public AjaxResult refreshCache()
    {
        configService.resetConfigCache();
        return success();
    }

  
    @PostMapping("/checkConfigKeyUnique")
    @ResponseBody
    public boolean checkConfigKeyUnique(SysConfig config)
    {
        return configService.checkConfigKeyUnique(config);
    }
}
