package com.ebs.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebs.common.core.controller.BaseController;
import com.ebs.common.core.domain.AjaxResult;
import com.ebs.common.core.domain.entity.SysUser;
import com.ebs.common.utils.StringUtils;
import com.ebs.framework.websocket.WebSocketServer;
import com.ebs.rfid.GS1Item;
import com.ebs.rfid.TagQuery;
import com.ebs.rfid.zebra.*;
import com.ebs.rfid.zebra.model.JsonMain;
import com.ebs.rfid.zebra.model.Reader;
import com.ebs.rfid.zebra.util.UtilsZebra;
import com.ebs.system.domain.GatewayReader;
import com.ebs.system.domain.PageRfidData;
import com.ebs.system.service.IGatewayService;
import com.ebs.web.controller.tool.GS1Shift;
import com.ebs.web.controller.tool.RfidParamJsonFileUtil;
import org.apache.poi.ss.formula.functions.T;
import org.epctagcoder.result.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private List<String> tagDataList = new ArrayList<>();

    @GetMapping("/tagPrint/index")
    public String indexTagPrint(ModelMap mmap) {

        this.setLoginInfo(mmap);
        return "system/rfid/tagPrint";
    }

    @GetMapping("/inventory/index")
    public String indexInventory(ModelMap mmap) {

        this.setLoginInfo(mmap);
        return "system/rfid/inventory";
    }

    @GetMapping("/inventorySearch/index")
    public String indexInventorySearch(ModelMap mmap) {

        //リーダーリスト取得
        List<GatewayReader> list = this.gatewayService.selectReaderListAll(new GatewayReader());
        mmap.put("readList",list);
        TagQuery tagQuery = RfidParamJsonFileUtil.loadJsonFile();
        if (tagQuery != null) {
            System.out.println("tagQuery:" + JSON.toJSONString(tagQuery));
            mmap.put("readerParamInfo",tagQuery);
        }
        this.setLoginInfo(mmap);
        return "system/rfid/inventorySearch";
    }
    @GetMapping("/tagSeek/index")
    public String indexTagSeek(ModelMap mmap) {

        //リーダーリスト取得
        List<GatewayReader> list = this.gatewayService.selectReaderListAll(new GatewayReader());
        mmap.put("readList",list);
        TagQuery tagQuery = RfidParamJsonFileUtil.loadJsonFile();
        if (tagQuery != null) {
            System.out.println("tagQuery:" + JSON.toJSONString(tagQuery));
            mmap.put("readerParamInfo",tagQuery);
        }
        this.setLoginInfo(mmap);
        return "system/rfid/tagSeek";
    }

    @PostMapping("/tagSeek/startInventory")
    @ResponseBody
    public AjaxResult startInventory(@RequestBody TagQuery query)
    {
        System.out.println("inventory start ");
        try {
            this.tagDataList.clear();
            //JSONファイルに画面値を書込み
            RfidParamJsonFileUtil.writeJsonFile(query);
            //Inventory開始
            String strJson = JSON.toJSONString(query);
            System.out.println(strJson);
            JsonMain jsonMain = JSON.parseObject(strJson, JsonMain.class);
            System.out.println(JSON.toJSONString(jsonMain));
            this.inventory = new Inventory(JSON.parseObject(JSON.toJSONString(jsonMain)));
            this.inventory.Start(new EventsListener(){
                @Override
                public void commonReadNotify(String jsonStr) {
                    System.out.println(jsonStr);
                    tagDataList.add(jsonStr);
                    JSONObject obj = JSON.parseObject(jsonStr);
                    PageRfidData rfid = new PageRfidData();
                    rfid.setTagId(obj.getString("tagID"));
                    try {
                        if (!GS1Shift.checkEpc(query, rfid.getTagId())) {
                            return;
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    //RFIDデータ挿入
                    List<PageRfidData> list = new ArrayList<PageRfidData>();
                    rfid.setReaderIp(obj.getString("hostName"));
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
                    try {
                        WebSocketServer.sendInfo(JSON.toJSONString(list),"ebs_rfid");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("inventory start ok！");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("inventory start end");
        return success(1);
    }

//    @PostMapping("/tagSeek/startInventory")
//    @ResponseBody
//    public AjaxResult startInventory(@RequestBody TagQuery query)
//    {
//        System.out.println("inventory start ");
//        try {
//            this.gatewayService.deleteRfidDataById(0L);
//            //Inventory開始
//            System.out.println(JSON.toJSONString(query));
//            //System.out.println("token:" + query.getReaders().get(0).getHostName());
//            //System.out.println("token:" + query.getToken());
//            this.inventory = new Inventory(JSON.parseObject(JSON.toJSONString(query)));
//            this.inventory.Start(new EventsListener(){
//                @Override
//                public void commonReadNotify(String jsonStr) {
//                    System.out.println(jsonStr);
//                    JSONObject obj = JSON.parseObject(jsonStr);
//                    //RFIDデータ挿入
//                    List<PageRfidData> list = new ArrayList<PageRfidData>();
//                    PageRfidData rfid = new PageRfidData();
//                    rfid.setReaderIp(obj.getString("hostName"));
//                    rfid.setTagId(obj.getString("tagID"));
//                    rfid.setTagRssi(obj.getString("peakRSSI"));
//                    int rssi = 0;
//                    if (!StringUtils.isEmpty(rfid.getTagRssi())) {
//                        float rssiFloat = Float.parseFloat(rfid.getTagRssi());
//                        if (rssiFloat < -70.0) {
//                            rssi = 1; //弱
//                        } else if (rssiFloat < -60.0) {
//                            rssi = 2; //较弱
//                        } else if (rssiFloat < -50.0) {
//                            rssi = 3; //中
//                        } else if (rssiFloat < -40.0) {
//                            rssi = 4; //较强
//                        } else if (rssiFloat < -30.0) {
//                            rssi = 5; //强
//                        } else {
//                            rssi = 6; //极强
//                        }
//                    }
//                    rfid.setTagRssiLevel(rssi);
//                    rfid.setTagTime(obj.getString("seenTime"));
//                    list.add(rfid);
//                    RfidController.this.gatewayService.batchInsertRfidData(list);
//                }
//            });
//            System.out.println("inventory start ok！");
////            try {
////                Thread.sleep(5000);
////            } catch (InterruptedException e) {
////                // TODO Auto-generated catch block
////                e.printStackTrace();
////            }
//        } catch (Exception ex) {
//
//        }
//
//        System.out.println("inventory start end");
//        return success(1);
//    }

    @GetMapping("/tagSeek/stopInventory")
    @ResponseBody
    public AjaxResult stopInventory(TagQuery query)
    {
        try {
            if (this.inventory != null) {
                this.inventory.Stop();
                System.out.println("inventory stop ok!");
            }
            RfidParamJsonFileUtil.openRfidDataFile();
            for (String jsonStr : this.tagDataList) {
                RfidParamJsonFileUtil.writeRfidDataToFile(jsonStr);
            }
            RfidParamJsonFileUtil.closeRfidDataFile();
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
        return success(this.readMemoryData(query, false));
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
        System.out.println(JSON.toJSONString(query));;
        boolean result = false;
        try {
            ActionWrite write = new ActionWrite(JSON.parseObject(JSON.toJSONString(query)));
            List<String> retStatus = write.doTagWrite();
            for (String status : retStatus) {
                System.out.println(status);
                if (status.indexOf("tag_id") > -1) {
                    result = true;
                }
            }

        } catch (Exception ex) {

        }

        return success(result ? 1: 0);
    }

    @PostMapping("/tagSeek/tagCheck")
    @ResponseBody
    public AjaxResult tagCheck(@RequestBody TagQuery query)
    {
        return success(this.readMemoryData(query, true));
    }

    @PostMapping("/tagSeek/tagEncode")
    @ResponseBody
    public AjaxResult tagEncode(@RequestBody TagQuery query)  {
        System.out.println(JSON.toJSONString(query));;
        AjaxResult ajax = AjaxResult.success();
        try {

            GS1Item gs1 = new GS1Item();
            gs1.setGs1Type(query.getTagGs1());
            gs1.setCode1(query.getEpcCode1());
            gs1.setCode2(query.getEpcCode2());
            gs1.setCode3(query.getEpcCode3());
            gs1.setExtension(query.getExtension());
            gs1.setFilter(query.getFilter());
            ajax.put("epc", GS1Shift.encodeEpc(gs1));
        } catch(Exception ex) {

        }

        return success(ajax);
    }

    private void setLoginInfo(ModelMap mmap) {
        SysUser user = getSysUser();

        mmap.put("user", user);
    }

    private AjaxResult readMemoryData(TagQuery query, boolean isCheck) {
        AjaxResult ajax = AjaxResult.success();
        List<String> tagList = new ArrayList<String>();
        try {
            System.out.println("tagRead:" + JSON.toJSONString(query));
            System.out.println("MEMORY_BANK_EPC");
            //MEMORY_BANK.MEMORY_BANK_EPC
            ActionRead read = new ActionRead(JSON.parseObject(JSON.toJSONString(query)));
            tagList = read.doTagRead();
            for (String tag : tagList) {
                try {
                    System.out.println(tag);
                    JSONObject obj = JSON.parseObject(tag);
                    ajax.put("epc", obj.getString("memoryBankData"));
                    if (!isCheck) {
                        GS1Item gs1 = GS1Shift.decodeEpc(obj.getString("tagID"));
                        if (gs1 != null) {
                            ajax.put("gs1Data", gs1.getJsonStr());
                            ajax.put("gs1Name", gs1.getGs1Type().toUpperCase());
                        }
                    }
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }

            System.out.println("MEMORY_BANK_TID");
            for (Reader r : query.getReaders()) {
                r.getAccess().getTag_pattern().setMemory_bank("2");
            }
            //MEMORY_BANK.MEMORY_BANK_TID
            read = new ActionRead(JSON.parseObject(JSON.toJSONString(query)));
            tagList = read.doTagRead();
            for (String tag : tagList) {
                try {
                    System.out.println(tag);
                    JSONObject obj = JSON.parseObject(tag);
                    ajax.put("tid", obj.getString("memoryBankData"));
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }

            for (Reader r : query.getReaders()) {
                r.getAccess().getTag_pattern().setMemory_bank("3");
            }
            System.out.println("MEMORY_BANK_USER");
            //MEMORY_BANK.MEMORY_BANK_USER
            read = new ActionRead(JSON.parseObject(JSON.toJSONString(query)));
            tagList = read.doTagRead();
            for (String tag : tagList) {
                try {
                    System.out.println(tag);
                    JSONObject obj = JSON.parseObject(tag);
                    ajax.put("user", obj.getString("memoryBankData"));
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }

            for (Reader r : query.getReaders()) {
                r.getAccess().getTag_pattern().setMemory_bank("0");
            }
            System.out.println("MEMORY_BANK_RESERVED");
            //MEMORY_BANK.MEMORY_BANK_RESERVED
            read = new ActionRead(JSON.parseObject(JSON.toJSONString(query)));
            tagList = read.doTagRead();
            for (String tag : tagList) {
                try {
                    System.out.println(tag);
                    JSONObject obj = JSON.parseObject(tag);
                    ajax.put("reserved", obj.getString("memoryBankData"));
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ajax;
    }

}
