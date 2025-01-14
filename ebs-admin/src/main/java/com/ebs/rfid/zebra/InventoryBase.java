package com.ebs.rfid.zebra;

import java.text.SimpleDateFormat;
import java.util.*;

import com.mot.rfid.api3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebs.rfid.zebra.model.JsonMain;
import com.ebs.rfid.zebra.model.Reader;
import com.ebs.rfid.zebra.util.UtilsZebra;
import java.io.IOException;
import java.util.concurrent.locks.*;
import java.util.Calendar;

//------------------------------------ 
//リーダー設定管理処理
// EBS Inc
// Date 2024-07-18
//------------------------------------
public class InventoryBase {

	private boolean inventoryComplete = false;
	private Lock accessEventLock = new ReentrantLock();
	private Condition accessEventCondVar = accessEventLock.newCondition();

	private Lock inventoryStopEventLock = new ReentrantLock();
	private Condition inventoryStopCondVar = inventoryStopEventLock.newCondition();

	//リーダ接続したハンドル退避
	private List<RFIDReader>  readers = new ArrayList<>();
	//JSONデータ
	private JsonMain mainConfig	;

	//重複tag対策
    private Map<String, TagData> uniqueTags = Collections.synchronizedMap(new HashMap<String, TagData>());
        
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    
	//ログ処理
    private static final Logger logger = LoggerFactory.getLogger(InventoryBase.class);

	//引数JSONからデータ中から抽出、各リーダーに接続する
	public InventoryBase( JSONObject json ){
		this.mainConfig = JSON.parseObject(JSON.toJSONString(json), JsonMain.class);
	}


	//複数リーダーに接続するInventory開始
	protected void StartInventory(EventsListener event) {

		for (Reader r : mainConfig.getReaders()) {

			RFIDReader reader = new RFIDReader();
			reader.setHostName(r.getHostName());
			reader.setPort(Integer.parseInt(r.getPort()));
			reader.setTimeout(5000);
			readers.add(reader);

            new Thread(() -> {
					//リーダ接続と設定
					ConfigureReader conf = new ConfigureReader(reader, r);

					//有効リーダーハンドルか判断
					if (!reader.isConnected()) {
						return;
					}

					//reader.Events.setTemperatureAlarmEvent(true);
					//System.out.println("isTagLocationingSupported:" + reader.ReaderCapabilities.isTagLocationingSupported());
					//外部利用するため
					reader.Events.addEventsListener(new RfidEventsListener() {

						public void eventStatusNotify(RfidStatusEvents rse) {
							postInfoMessage(rse.StatusEventData.getStatusEventType().toString());

							STATUS_EVENT_TYPE statusType = rse.StatusEventData.getStatusEventType();
							if (statusType == STATUS_EVENT_TYPE.ACCESS_STOP_EVENT) {
								try {
									accessEventLock.lock();
									accessEventCondVar.signalAll();
								} finally {
									accessEventLock.unlock();

								}

							} else if (statusType == STATUS_EVENT_TYPE.INVENTORY_STOP_EVENT) {
								try {
									inventoryStopEventLock.lock();
									inventoryComplete = true;
									inventoryStopCondVar.signalAll();

								} finally {
									inventoryStopEventLock.unlock();
								}

							} else if (statusType == STATUS_EVENT_TYPE.BUFFER_FULL_WARNING_EVENT || statusType == STATUS_EVENT_TYPE.BUFFER_FULL_EVENT) {
								postInfoMessage(statusType.toString());
							}

						}

						public void eventReadNotify(RfidReadEvents rse) {

							getData(reader, event);
						}

					});

					try {
						//読み取り操作の開始
						 reader.Actions.Inventory.perform(conf.configurePostFilters(r), conf.configureTriggerInfo(r), conf.configureAntennaInfo(r));
						//reader.Actions.TagLocationing.Perform(tagID, antennaInfo);
						//TriggerInfo triggerInfo = conf.configureTriggerInfo(r);
						//reader.Actions.Inventory.perform(null,triggerInfo,conf.configureAntennaInfo(r));
						logger.info("start Inventory: " + reader.getHostName());
					}catch (Exception e) {
						logger.info("start Inventory ERR: " + e.getMessage());
					}

					try
					{
						inventoryStopEventLock.lock();
						if(!inventoryComplete)
						{
							inventoryStopCondVar.await();
							inventoryComplete = false;
						}

					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
					finally
					{
						inventoryStopEventLock.unlock();
					}

            }).start();
            
        }
    }


	private void getData(RFIDReader reader, EventsListener event) {

		TagData[] tags = reader.Actions.getReadTags(100);

		//外部インターフェス発火
			// TagDataをTag用JSON文字列に変換する
			//
			//
			//１件ずつ処理する。
			if (tags != null) {
				for (TagData tag : tags) {
					if ( UtilsZebra.addTagToMap(uniqueTags, tag) ) {

						JSONObject obj = JSON.parseObject(JSON.toJSONString(tag));
						obj.put("hostName", reader.getHostName());
						obj.put("seenTime",formatter.format(new Date()));
						//リーダーとTAGの距離
						if (reader.ReaderCapabilities.isTagLocationingSupported()) {
							obj.put("relativeDistance", tag.LocationInfo.getRelativeDistance());
						}
						//位相
						double dPhase = (180.0/32767)*tag.getPhase();
						obj.put("phase",dPhase);
						obj.put("TagPC",Integer.toBinaryString(tag.getPC()));
						obj.put("TagSeenCount",tag.getTagSeenCount());
						event.commonReadNotify(JSON.toJSONString(obj));
					}
				}
			}
//			if (tag != null) {
				//重複をチェックする。
//				if ( UtilsZebra.addTagToMap(uniqueTags, tag) ) {
					// インタフェース発火
					//System.out.println("GetCurrentTime:" + tag.SeenTime.getUTCTime().getFirstSeenTimeStamp().GetCurrentTime());
//					JSONObject obj = JSON.parseObject(JSON.toJSONString(tag));
//					obj.put("hostName", reader.getHostName());
//					obj.put("seenTime",formatter.format(new Date()));
//									System.out.println("::"+tag.LocationInfo);
//									//リーダーとTAGの距離
//					if (tag.isContainsLocationInfo()) {
//						obj.put("relativeDistance", tag.LocationInfo.getRelativeDistance());
//					}
					//位相
//					double dPhase = (180.0/32767)*tag.getPhase();
//					obj.put("phase",dPhase);
//					event.commonReadNotify(JSON.toJSONString(obj));
//				}
//			}

	}

	void postStatusNotification(String statusMsg, String vendorMsg)
	{
		System.out.println("Status: "+statusMsg+" Vendor Message: "+vendorMsg);
	}

	static void postInfoMessage(String msg)
	{
		System.out.println(msg);
	}

	//Inventory停止
	protected void StopInventory() {
		
		for (RFIDReader reader : readers) {
		
			//有効リーダーハンドルか判断
			if (!reader.isConnected()){
				continue;
			} 
	     //   new Thread(() -> {
	            try {
						reader.Actions.Inventory.stop();
						logger.info("stop reader: " + reader.getHostName());
		            } catch (InvalidUsageException e) {
		                logger.error("Invalid usage when disconnecting reader: " + reader.getHostName());
		            } catch (OperationFailureException e) {
		                logger.error("Operation failure when disconnecting reader: " + reader.getHostName() + " Reason: " + e.getVendorMessage());
		            } catch (Exception e) {
		                logger.error("Unexpected error when disconnecting reader: " + reader.getHostName());
		            }
		  //      }).start();
	
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//リーダ終了
			ConfigureReader.disconnectReaders(readers);
		}
    }

	private void DisplayCapability(RFIDReader myReader)
	{

		System.out.println("Reader Capabilities\n\n");
		System.out.println("FirwareVersion="+myReader.ReaderCapabilities.getFirwareVersion());
		System.out.println("ModelName= "+myReader.ReaderCapabilities.getModelName());
		System.out.println("NumAntennaSupported= "+myReader.ReaderCapabilities.getNumAntennaSupported());
		System.out.println("NumGPIPorts= "+myReader.ReaderCapabilities.getNumGPIPorts());
		System.out.println("NumGPOPorts= "+myReader.ReaderCapabilities.getNumGPOPorts());
		System.out.println("IsUTCClockSupported= "+myReader.ReaderCapabilities.isUTCClockSupported());
		System.out.println("IsBlockEraseSupported= "+myReader.ReaderCapabilities.isBlockEraseSupported());
		System.out.println("IsBlockWriteSupported= "+myReader.ReaderCapabilities.isBlockWriteSupported());
		System.out.println("IsTagInventoryStateAwareSingulationSupported= "+myReader.ReaderCapabilities.isTagInventoryStateAwareSingulationSupported());
		System.out.println("MaxNumOperationsInAccessSequence= "+myReader.ReaderCapabilities.getMaxNumOperationsInAccessSequence());
		System.out.println("MaxNumPreFilters= "+myReader.ReaderCapabilities.getMaxNumPreFilters());
		System.out.println("CommunicationStandard= "+myReader.ReaderCapabilities.getCommunicationStandard());
		System.out.println("CountryCode= "+myReader.ReaderCapabilities.getCountryCode());
		System.out.println("IsHoppingEnabled= "+myReader.ReaderCapabilities.isHoppingEnabled());

	}

}
