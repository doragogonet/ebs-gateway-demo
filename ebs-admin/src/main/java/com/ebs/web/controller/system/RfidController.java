package com.ebs.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebs.common.annotation.Log;
import com.ebs.common.constant.UserConstants;
import com.ebs.common.core.controller.BaseController;
import com.ebs.common.core.domain.AjaxResult;
import com.ebs.common.core.domain.Ztree;
import com.ebs.common.core.domain.entity.SysDept;
import com.ebs.common.core.domain.entity.SysMenu;
import com.ebs.common.core.domain.entity.SysUser;
import com.ebs.common.enums.BusinessType;
import com.ebs.common.utils.DateUtils;
import com.ebs.common.utils.ShiroUtils;
import com.ebs.common.utils.StringUtils;
import com.ebs.rfid.RfidService;
import com.ebs.rfid.TagQuery;
import com.ebs.rfid.zebra.EventsListener;
import com.ebs.rfid.zebra.Inventory;
import com.ebs.rfid.zebra.model.TagInfo;
import com.ebs.system.domain.GatewayReader;
import com.ebs.system.domain.PageRfidData;
import com.ebs.system.service.IGatewayService;
import com.ebs.system.service.ISysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/rfid")
public class RfidController extends BaseController
{
    @Autowired
    private RfidService rfidService;

    @Autowired
    private IGatewayService gatewayService;

    @GetMapping("/tagPrint/index")
    public String indexTagPrint(ModelMap mmap) {

        return "system/rfid/tagPrint";
    }

    @GetMapping("/inventory/index")
    public String indexInventory(ModelMap mmap) {

        return "system/rfid/inventory";
    }

    @GetMapping("/tagSeek/index")
    public String indexTagSeek(ModelMap mmap) {

        System.out.println("host:" + this.rfidService.host);
        //リーダーリスト取得
        List<GatewayReader> list = this.gatewayService.selectReaderListAll(new GatewayReader());
        mmap.put("readList",list);
        this.setLoginInfo(mmap);
        return "system/rfid/tagSeek";
    }

    @PostMapping("/tagSeek/startInventory")
    @ResponseBody
    public AjaxResult startInventory(@RequestBody TagQuery query)
    {

        try {
            //Inventory開始
            Inventory inventory = new Inventory(JSON.parseObject(JSON.toJSONString(query)));
            inventory.Start(new EventsListener(){
                @Override
                public void commonReadNotify(String jsonStr) {
                    System.out.println(jsonStr);
                    TagInfo tag = JSON.parseObject(JSON.toJSONString(jsonStr), TagInfo.class);
                    //RFIDデータ挿入
                    List<PageRfidData> list = new ArrayList<PageRfidData>();
                    PageRfidData rfid = new PageRfidData();
                    rfid.setReaderIp(tag.getIp());
                    rfid.setTagId(tag.getData());
                    rfid.setTagRssi(tag.getRssi());
                    int rssi = 0;
                    if (!StringUtils.isEmpty(tag.getRssi())) {
                        if (Float.parseFloat(tag.getRssi()) < -70.0) {
                            rssi = 1; //弱
                        } else if (Float.parseFloat(tag.getRssi()) < -60.0) {
                            rssi = 2; //较弱
                        } else if (Float.parseFloat(tag.getRssi()) < -50.0) {
                            rssi = 3; //中
                        } else if (Float.parseFloat(tag.getRssi()) < -40.0) {
                            rssi = 4; //较强
                        } else if (Float.parseFloat(tag.getRssi()) < -30.0) {
                            rssi = 5; //强
                        } else {
                            rssi = 6; //极强
                        }
                    }
                    rfid.setTagRssiLevel(rssi);
                    rfid.setTagTime(tag.getTime());
                    list.add(rfid);
                    RfidController.this.gatewayService.batchInsertRfidData(list);
                }
            });
            try {
                Thread.sleep(5000);
                inventory.Stop();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (Exception ex) {

        }
        return toAjax(1);
    }

    @GetMapping("/tagSeek/stopInventory")
    public AjaxResult stopInventory(TagQuery query)
    {
        try {
            this.rfidService.stopInventory();
        } catch (Exception ex) {

        }
        return toAjax(1);
    }

    @GetMapping("/tagSeek/getData")
    public AjaxResult getData(PageRfidData rfid) {

        //RFIDデータ取得
        List<PageRfidData> list = this.gatewayService.selectRfidDataList(rfid);

        return success(list);
    }

    @PostMapping("/tagSeek/tagRead")
    @ResponseBody
    public AjaxResult tagRead(@RequestBody TagQuery query)
    {
        TagInfo tag = new TagInfo();
        String jsonParam = "" +
                "{" +
                "	\"IP_PORT_ANTS\":\"" + query.getIp() + "\"," +
                "	\"TAG_ID\":\"" + query.getTagId() + "\"," +
                "	\"PASSWORD\":\"" + query.getPassword() + "\"" +
                "}";
        try {
            tag = this.rfidService.tagRead(jsonParam);
        } catch (Exception ex) {

        }

        return success(tag);
    }

    @PostMapping("/tagSeek/tagLock")
    @ResponseBody
    public AjaxResult tagLock(@RequestBody TagQuery query)
    {
        boolean result = false;
        String jsonParam = "" +
                "{" +
                "	\"IP_PORT_ANTS\":\"" + query.getIp() + "\"," +
                "	\"TAG_ID\":\"" + query.getTagId() + "\"," +
                "	\"PASSWORD\":\"" + query.getPassword() + "\"," +
                "	\"LOCK_TYPE\":\"" + query.getLockType() + "\"," +
                "	\"LOCK_TARGET\":\"" + query.getLockTarget() + "\"" +
                "}";
        try {
            result = this.rfidService.tagLock(jsonParam);
        } catch (Exception ex) {

        }

        return success(result ? 1 : 0);
    }

    @PostMapping("/tagSeek/tagUnlock")
    @ResponseBody
    public AjaxResult tagUnlock(@RequestBody TagQuery query)
    {
        boolean result = false;
        String jsonParam = "" +
                "{" +
                "	\"IP_PORT_ANTS\":\"" + query.getIp() + "\"," +
                "	\"TAG_ID\":\"" + query.getTagId() + "\"," +
                "	\"PASSWORD\":\"" + query.getPassword() + "\"," +
                "	\"LOCK_TYPE\":\"" + query.getLockType() + "\"," +
                "	\"LOCK_TARGET\":\"" + query.getLockTarget() + "\"" +
                "}";
        try {
            result = this.rfidService.tagUnlock(jsonParam);
        } catch (Exception ex) {

        }

        return success(result ? 1 : 0);
    }

    @PostMapping("/tagSeek/tagWrite")
    @ResponseBody
    public AjaxResult tagWrite(@RequestBody TagQuery query)
    {
        boolean result = false;
        String jsonParam = "" +
                "{" +
                "	\"IP_PORT_ANTS\":\"" + query.getIp() + "\"," +
                "	\"TAG_ID\":\"" + query.getTagId() + "\"," +
                "	\"PASSWORD\":\"" + query.getPassword() + "\"," +
                "	\"MEMORY_BANK\":\"" + query.getDataType() + "\"," +
                "	\"DATA\":\"" + query.getData() + "\"," +
                "	\"DATA_OFFSET\":\"" + query.getByteOffset() + "\"," +
                "	\"DATA_LENGTH\":\"" + query.getByteCount() + "\"" +
                "}";
        try {
            result = this.rfidService.tagWrite(jsonParam);
        } catch (Exception ex) {

        }

        return success(result ? 1: 0);
    }

    @PostMapping("/tagSeek/tagCheck")
    @ResponseBody
    public AjaxResult tagCheck(@RequestBody TagQuery query)
    {
        TagInfo tag = new TagInfo();
        String jsonParam = "" +
                "{" +
                "	\"IP_PORT_ANTS\":\"" + query.getIp() + "\"," +
                "	\"TAG_ID\":\"" + query.getTagId() + "\"," +
                "	\"PASSWORD\":\"" + query.getPassword() + "\"" +
                "}";
        try {
            tag = this.rfidService.tagRead(jsonParam);
        } catch (Exception ex) {

        }

        return success(tag);
    }

    @PostMapping("/tagSeek/tagEncode")
    @ResponseBody
    public AjaxResult tagEncode(@RequestBody TagQuery query)
    {
        String encode = "";
        String jsonParam = "" +
                "{" +
                "	\"GS1_TYPE\":\"" + query.getGs1() + "\"," +
                "	\"CODE1\":\"" + query.getCode1() + "\"," +
                "	\"CODE2\":\"" + query.getCode2() + "\"," +
                "	\"CODE3\":\"" + query.getCode3() + "\"," +
                "	\"FILTER\":\"" + query.getFilter() + "\"," +
                "	\"EXTENSION_DIGIT\":\"" + query.getExtension() + "\"" +
                "}";
        try {
            encode = this.rfidService.epcEncode(jsonParam);
        } catch (Exception ex) {

        }

        return success(encode);
    }

    private void setLoginInfo(ModelMap mmap) {
        SysUser user = getSysUser();

        mmap.put("user", user);
    }

}
