package com.ebs.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebs.common.core.controller.BaseController;
import com.ebs.common.core.domain.AjaxResult;
import com.ebs.common.core.domain.entity.SysUser;
import com.ebs.common.utils.StringUtils;
import com.ebs.rfid.TagQuery;
import com.ebs.rfid.zebra.*;
import com.ebs.rfid.zebra.model.JsonMain;
import com.ebs.system.domain.GatewayReader;
import com.ebs.system.domain.PageRfidData;
import com.ebs.system.service.IGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import com.mot.rfid.api3.InvalidUsageException;
import com.mot.rfid.api3.OperationFailureException;
import com.mot.rfid.api3.RFIDReader;
import com.mot.rfid.api3.RfidEventsListener;
import com.mot.rfid.api3.RfidReadEvents;
import com.mot.rfid.api3.RfidStatusEvents;
import com.mot.rfid.api3.TagData;
import com.mot.rfid.api3.RfidEventsListener;


@Controller
@RequestMapping("/rfid")
public class RfidController extends BaseController
{
    @Autowired
    private IGatewayService gatewayService;

    private Inventory inventory;

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
        System.out.println("inventory start ");
        try {
            //Inventory開始
            System.out.println(JSON.toJSONString(query));
            //System.out.println("token:" + query.getReaders().get(0).getHostName());
            //System.out.println("token:" + query.getToken());
            this.inventory = new Inventory(JSON.parseObject(JSON.toJSONString(query)));
            this.inventory.Start(new EventsListener(){
                @Override
                public void commonReadNotify(String jsonStr) {
                    System.out.println(jsonStr);
                    JSONObject obj = JSON.parseObject(jsonStr);
                    //RFIDデータ挿入
                    List<PageRfidData> list = new ArrayList<PageRfidData>();
                    PageRfidData rfid = new PageRfidData();
                    rfid.setReaderIp(obj.getString("hostName"));
                    rfid.setTagId(obj.getString("tagID"));
                    rfid.setTagRssi(obj.getString("peakRSSI"));
                    int rssi = 0;
                    if (!StringUtils.isEmpty(rfid.getTagRssi())) {
                        float rssiFloat = Float.parseFloat(rfid.getTagRssi());
                        if (rssiFloat < -70.0) {
                            rssi = 1; //弱
                        } else if (rssiFloat < -60.0) {
                            rssi = 2; //较弱
                        } else if (rssiFloat < -50.0) {
                            rssi = 3; //中
                        } else if (rssiFloat < -40.0) {
                            rssi = 4; //较强
                        } else if (rssiFloat < -30.0) {
                            rssi = 5; //强
                        } else {
                            rssi = 6; //极强
                        }
                    }
                    rfid.setTagRssiLevel(rssi);
                    rfid.setTagTime(obj.getString("seenTime"));
                    list.add(rfid);
                    RfidController.this.gatewayService.batchInsertRfidData(list);
                }
            });
            System.out.println("inventory start ok！");
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
        } catch (Exception ex) {

        }

        System.out.println("inventory start end");
        return success(1);
    }

    @GetMapping("/tagSeek/stopInventory")
    @ResponseBody
    public AjaxResult stopInventory(TagQuery query)
    {
        try {
            if (this.inventory != null) {
                this.inventory.Stop();
                System.out.println("inventory stop ok!");
            }
        } catch (Exception ex) {

        }
        return success(1);
    }

    @GetMapping("/tagSeek/getData")
    @ResponseBody
    public AjaxResult getData(PageRfidData rfid) {

        //RFIDデータ取得
        List<PageRfidData> list = this.gatewayService.selectRfidDataList(rfid);
        System.out.println("inventory getData : " + list.size());
        return success(list);
    }

    @PostMapping("/tagSeek/tagRead")
    @ResponseBody
    public AjaxResult tagRead(@RequestBody TagQuery query)
    {
        //AjaxResult ajax = AjaxResult.success();
        List<String> tagList = new ArrayList<String>();
        try {
            ActionRead read = new ActionRead(JSON.parseObject(JSON.toJSONString(query)));
            tagList = read.doTagRead();
            for (String tag : tagList) {
                System.out.println(tag);
            }
        } catch (Exception ex) {

        }

        return success(tagList);
    }

    @PostMapping("/tagSeek/tagLock")
    @ResponseBody
    public AjaxResult tagLock(@RequestBody TagQuery query)
    {
        boolean result = false;
        try {
            ActionLock lock = new ActionLock(JSON.parseObject(JSON.toJSONString(query)));
            List<String> retStatus = lock.doTagLock();
            for (String status : retStatus) {
                System.out.println(status);
            }
            result = true;
        } catch (Exception ex) {

        }

        return success(result ? 1 : 0);
    }

    @PostMapping("/tagSeek/tagUnlock")
    @ResponseBody
    public AjaxResult tagUnlock(@RequestBody TagQuery query)
    {
        boolean result = false;
        try {
            ActionLock lock = new ActionLock(JSON.parseObject(JSON.toJSONString(query)));
            List<String> retStatus = lock.doTagLock();
            for (String status : retStatus) {
                System.out.println(status);
            }
            result = true;
        } catch (Exception ex) {

        }

        return success(result ? 1 : 0);
    }

    @PostMapping("/tagSeek/tagWrite")
    @ResponseBody
    public AjaxResult tagWrite(@RequestBody TagQuery query)
    {
        boolean result = false;
        try {
            ActionWrite write = new ActionWrite(JSON.parseObject(JSON.toJSONString(query)));
            List<String> retStatus = write.doTagWrite();
            for (String status : retStatus) {
                System.out.println(status);
            }
            result = true;
        } catch (Exception ex) {

        }

        return success(result ? 1: 0);
    }

    @PostMapping("/tagSeek/tagCheck")
    @ResponseBody
    public AjaxResult tagCheck(@RequestBody TagQuery query)
    {
//        TagInfo tag = new TagInfo();
//        String jsonParam = "" +
//                "{" +
//                "	\"IP_PORT_ANTS\":\"" + query.getIp() + "\"," +
//                "	\"TAG_ID\":\"" + query.getTagId() + "\"," +
//                "	\"PASSWORD\":\"" + query.getPassword() + "\"" +
//                "}";
//        try {
//            tag = this.rfidService.tagRead(jsonParam);
//        } catch (Exception ex) {
//
//        }

        return success(1);
    }

    @PostMapping("/tagSeek/tagEncode")
    @ResponseBody
    public AjaxResult tagEncode(@RequestBody TagQuery query)
    {
//        String encode = "";
//        String jsonParam = "" +
//                "{" +
//                "	\"GS1_TYPE\":\"" + query.getGs1() + "\"," +
//                "	\"CODE1\":\"" + query.getCode1() + "\"," +
//                "	\"CODE2\":\"" + query.getCode2() + "\"," +
//                "	\"CODE3\":\"" + query.getCode3() + "\"," +
//                "	\"FILTER\":\"" + query.getFilter() + "\"," +
//                "	\"EXTENSION_DIGIT\":\"" + query.getExtension() + "\"" +
//                "}";
//        try {
//            encode = this.rfidService.epcEncode(jsonParam);
//        } catch (Exception ex) {
//
//        }

        return success(1);
    }

    private void setLoginInfo(ModelMap mmap) {
        SysUser user = getSysUser();

        mmap.put("user", user);
    }

}
