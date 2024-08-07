package com.ebs.generator.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.fastjson.JSON;
import com.ebs.common.annotation.Log;
import com.ebs.common.core.controller.BaseController;
import com.ebs.common.core.domain.AjaxResult;
import com.ebs.common.core.domain.CxSelect;
import com.ebs.common.core.page.TableDataInfo;
import com.ebs.common.core.text.Convert;
import com.ebs.common.enums.BusinessType;
import com.ebs.common.utils.StringUtils;
import com.ebs.common.utils.security.PermissionUtils;
import com.ebs.common.utils.sql.SqlUtil;
import com.ebs.generator.domain.GenTable;
import com.ebs.generator.domain.GenTableColumn;
import com.ebs.generator.service.IGenTableColumnService;
import com.ebs.generator.service.IGenTableService;


@Controller
@RequestMapping("/tool/gen")
public class GenController extends BaseController
{
    private String prefix = "tool/gen";

    @Autowired
    private IGenTableService genTableService;

    @Autowired
    private IGenTableColumnService genTableColumnService;

    @RequiresPermissions("tool:gen:view")
    @GetMapping()
    public String gen()
    {
        return prefix + "/gen";
    }

  
    @RequiresPermissions("tool:gen:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo genList(GenTable genTable)
    {
        startPage();
        List<GenTable> list = genTableService.selectGenTableList(genTable);
        return getDataTable(list);
    }


    @RequiresPermissions("tool:gen:list")
    @PostMapping("/db/list")
    @ResponseBody
    public TableDataInfo dataList(GenTable genTable)
    {
        startPage();
        List<GenTable> list = genTableService.selectDbTableList(genTable);
        return getDataTable(list);
    }

    @RequiresPermissions("tool:gen:list")
    @PostMapping("/column/list")
    @ResponseBody
    public TableDataInfo columnList(GenTableColumn genTableColumn)
    {
        TableDataInfo dataInfo = new TableDataInfo();
        List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(genTableColumn);
        dataInfo.setRows(list);
        dataInfo.setTotal(list.size());
        return dataInfo;
    }

    @RequiresPermissions("tool:gen:list")
    @GetMapping("/importTable")
    public String importTable()
    {
        return prefix + "/importTable";
    }

    @GetMapping("/createTable")
    public String createTable()
    {
        return prefix + "/createTable";
    }

    @RequiresPermissions("tool:gen:list")
    @Log(title = "コード生成", businessType = BusinessType.IMPORT)
    @PostMapping("/importTable")
    @ResponseBody
    public AjaxResult importTableSave(String tables)
    {
        String[] tableNames = Convert.toStrArray(tables);
      
        List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames);
        String operName = Convert.toStr(PermissionUtils.getPrincipalProperty("loginName"));
        genTableService.importGenTable(tableList, operName);
        return AjaxResult.success();
    }

   
    @RequiresPermissions("tool:gen:edit")
    @GetMapping("/edit/{tableId}")
    public String edit(@PathVariable("tableId") Long tableId, ModelMap mmap)
    {
        GenTable table = genTableService.selectGenTableById(tableId);
        List<GenTable> genTables = genTableService.selectGenTableAll();
        List<CxSelect> cxSelect = new ArrayList<CxSelect>();
        for (GenTable genTable : genTables)
        {
            if (!StringUtils.equals(table.getTableName(), genTable.getTableName()))
            {
                CxSelect cxTable = new CxSelect(genTable.getTableName(), genTable.getTableName() + '：' + genTable.getTableComment());
                List<CxSelect> cxColumns = new ArrayList<CxSelect>();
                for (GenTableColumn tableColumn : genTable.getColumns())
                {
                    cxColumns.add(new CxSelect(tableColumn.getColumnName(), tableColumn.getColumnName() + '：' + tableColumn.getColumnComment()));
                }
                cxTable.setS(cxColumns);
                cxSelect.add(cxTable);
            }
        }
        mmap.put("table", table);
        mmap.put("data", JSON.toJSON(cxSelect));
        return prefix + "/edit";
    }

    @RequiresPermissions("tool:gen:edit")
    @Log(title = "コード生成", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated GenTable genTable)
    {
        genTableService.validateEdit(genTable);
        genTableService.updateGenTable(genTable);
        return AjaxResult.success();
    }

    @RequiresPermissions("tool:gen:remove")
    @Log(title = "コード生成", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        genTableService.deleteGenTableByIds(ids);
        return AjaxResult.success();
    }

    @RequiresRoles("admin")
    @Log(title = "表作成", businessType = BusinessType.OTHER)
    @PostMapping("/createTable")
    @ResponseBody
    public AjaxResult create(String sql)
    {
        try
        {
            SqlUtil.filterKeyword(sql);
            List<SQLStatement> sqlStatements = SQLUtils.parseStatements(sql, DbType.mysql);
            List<String> tableNames = new ArrayList<>();
            for (SQLStatement sqlStatement : sqlStatements)
            {
                if (sqlStatement instanceof MySqlCreateTableStatement)
                {
                    MySqlCreateTableStatement createTableStatement = (MySqlCreateTableStatement) sqlStatement;
                    if (genTableService.createTable(createTableStatement.toString()))
                    {
                        String tableName = createTableStatement.getTableName().replaceAll("`", "");
                        tableNames.add(tableName);
                    }
                }
            }
            List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames.toArray(new String[tableNames.size()]));
            String operName = Convert.toStr(PermissionUtils.getPrincipalProperty("loginName"));
            genTableService.importGenTable(tableList, operName);
            return AjaxResult.success();
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            return AjaxResult.error("表作成異常");
        }
    }

    @RequiresPermissions("tool:gen:preview")
    @GetMapping("/preview/{tableId}")
    @ResponseBody
    public AjaxResult preview(@PathVariable("tableId") Long tableId) throws IOException
    {
        Map<String, String> dataMap = genTableService.previewCode(tableId);
        return AjaxResult.success(dataMap);
    }

    @RequiresPermissions("tool:gen:code")
    @Log(title = "コード生成", businessType = BusinessType.GENCODE)
    @GetMapping("/download/{tableName}")
    public void download(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException
    {
        byte[] data = genTableService.downloadCode(tableName);
        genCode(response, data);
    }

    @RequiresPermissions("tool:gen:code")
    @Log(title = "コード生成", businessType = BusinessType.GENCODE)
    @GetMapping("/genCode/{tableName}")
    @ResponseBody
    public AjaxResult genCode(@PathVariable("tableName") String tableName)
    {
        genTableService.generatorCode(tableName);
        return AjaxResult.success();
    }

    @RequiresPermissions("tool:gen:edit")
    @Log(title = "コード生成", businessType = BusinessType.UPDATE)
    @GetMapping("/synchDb/{tableName}")
    @ResponseBody
    public AjaxResult synchDb(@PathVariable("tableName") String tableName)
    {
        genTableService.synchDb(tableName);
        return AjaxResult.success();
    }

    @RequiresPermissions("tool:gen:code")
    @Log(title = "コード生成", businessType = BusinessType.GENCODE)
    @GetMapping("/batchGenCode")
    @ResponseBody
    public void batchGenCode(HttpServletResponse response, String tables) throws IOException
    {
        String[] tableNames = Convert.toStrArray(tables);
        byte[] data = genTableService.downloadCode(tableNames);
        genCode(response, data);
    }

    private void genCode(HttpServletResponse response, byte[] data) throws IOException
    {
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"ebs.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}