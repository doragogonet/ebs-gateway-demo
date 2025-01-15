package com.ebs.rfid.zebra;

import java.util.Arrays;
import java.util.TimeZone;
import java.util.Calendar;
import java.util.List;

import com.mot.rfid.api3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.ebs.rfid.zebra.model.Prefilter;
import com.ebs.rfid.zebra.model.RError;
import com.ebs.rfid.zebra.model.Reader;
import com.ebs.rfid.zebra.util.StringUtils;
import com.ebs.rfid.zebra.util.UtilsZebra;


//------------------------------------ 
//リーダー基本設定
//------------------------------------
public class ConfigureReader {


	//ログ処理
    private static final Logger logger = LoggerFactory.getLogger(ConfigureReader.class);

	//引数JSONからデータ中から抽出、各リーダーに接続する
	public ConfigureReader( RFIDReader reader,Reader r){

 		connectReader(reader);

		configureReaderSettings(reader,r);

		configurePreFilters(reader,r);
					
	}


	//リーダ接続 3回エラーリトライを行う。
    private void connectReader(RFIDReader reader) {
        int maxRetries = 3;
        int attempt = 0;
        boolean isConnected = false;

		if(reader.isConnected()) return;
		
        while (attempt < maxRetries && !isConnected) {
            try {
                reader.connect();
                isConnected = true;
                logger.info("Connected to reader: " + reader.getHostName());
            } catch (InvalidUsageException | OperationFailureException e) {
                attempt++;
                logger.error("Failed to connect to reader: " + reader.getHostName() + " (Attempt " + attempt + "/" + maxRetries + ")");
                if (attempt >= maxRetries) {
                    logger.error("Max attempts reached. Unable to connect to reader: " + reader.getHostName());
                }
            }
        }
    }
    

    //リーダ切断処理
    public static void disconnectReaders(List<RFIDReader> readers) {
        
		for (RFIDReader reader : readers) {
			
			//有効リーダーハンドルか判断
			if (!reader.isConnected()){
				continue;
			} 
  //          new Thread(() -> {
                try {
					if (reader.isConnected()){
                    	reader.disconnect();
                    	logger.info("disconnect reader: " + reader.getHostName());
					}
                } catch (InvalidUsageException e) {
                    logger.error("Invalid usage when disconnecting reader: " + reader.getHostName());
                } catch (OperationFailureException e) {
                    logger.error("Operation failure when disconnecting reader: " + reader.getHostName() + " Reason: " + e.getVendorMessage());
                } catch (Exception e) {
                    logger.error("Unexpected error when disconnecting reader: " + reader.getHostName());
                }
//            }).start();
        }
    }

	//エラー時の戻り値処理
	public static String getReturnError( String error_no, String error_msg ){
		RError rerr = new RError();
		rerr.setMsg(error_msg);
		rerr.setError_no(error_no);
		//JSON文字列で返す。
		return JSON.toJSONString(rerr);
	}


	//リーダ基本設定
    private void configureReaderSettings(RFIDReader reader,Reader r)  {
        try {

        	if (StringUtils.isNotEmpty(r.getPower())) {
	            // リーダーの機能を取得します
	            ReaderCapabilities capabilities = reader.ReaderCapabilities;
				// 最大送信パワー値を取得します
	            int[] levelValues = capabilities.getTransmitPowerLevelValues();
				//外部パラメータ
				int powerIndex = Integer.parseInt(r.getPower());
				//有効しているアンテナを取得する。
				short[] avalis = reader.Config.Antennas.getAvailableAntennas();
				for (int avali : avalis) {
					//既存アンテナ設定
					Antennas.AntennaRfConfig  aconfig = reader.Config.Antennas.getAntennaRfConfig(avali);
					if ( levelValues.length -1 < powerIndex ) {
						powerIndex = aconfig.getTransmitPowerIndex();
					}
				    //アンテナパワー設定
					aconfig.setTransmitPowerIndex((short)powerIndex);
					//リーダーハンドルに結合
					reader.Config.Antennas.setAntennaRfConfig(avali,aconfig);
				}
        	}

			reader.Events.setInventoryStartEvent(true);
			reader.Events.setInventoryStopEvent(true);
			reader.Events.setAccessStartEvent(true);
			reader.Events.setAccessStopEvent(true);
			reader.Events.setAntennaEvent(true);
			reader.Events.setGPIEvent(true);
			reader.Events.setBufferFullEvent(true);
			reader.Events.setBufferFullWarningEvent(true);
			reader.Events.setReaderDisconnectEvent(true);
			reader.Events.setReaderExceptionEvent(true);
			reader.Events.setTagReadEvent(true);
			reader.Events.setAttachTagDataWithReadEvent(false);

			TagStorageSettings tagStorageSettings = reader.Config.getTagStorageSettings();
			TAG_FIELD[] tagField = new TAG_FIELD[8];
			tagField[0] = TAG_FIELD.PC;
			tagField[1] = TAG_FIELD.PEAK_RSSI;
			tagField[2] = TAG_FIELD.TAG_SEEN_COUNT;
			tagField[3] = TAG_FIELD.CRC;
			tagField[4] = TAG_FIELD.ANTENNA_ID;
			tagField[5] = TAG_FIELD.LAST_SEEN_TIME_STAMP;
			tagField[6] = TAG_FIELD.PHASE_INFO;
			tagField[7] = TAG_FIELD.GPS_COORDINATES;

			tagStorageSettings.setTagFields(tagField);
			tagStorageSettings.enableAccessReports(true);
			tagStorageSettings.discardTagsOnInventoryStop(true);
			reader.Config.setTagStorageSettings(tagStorageSettings);

		} catch (InvalidUsageException e) {
            logger.error("Invalid usage when configuring reader: " + reader.getHostName());
        } catch (OperationFailureException e) {
            logger.error("Operation failure when configuring reader: " + reader.getHostName() + " Reason: " + e.getVendorMessage());
        } catch (Exception e) {
            logger.error("Unexpected error when configuring reader: " + reader.getHostName());
        }
	}


    //PreFilters設定
    private void configurePreFilters(RFIDReader reader,Reader r) {

    	try {
    		reader.Actions.PreFilters.deleteAll();
    	} catch (Exception e) {
    		
    	}
    	
    	if (r.getPrefilters() == null) return;
	     
		for (Prefilter pre : r.getPrefilters() ) {

			
				if ( pre != null ){
					PreFilters preFilters = new PreFilters();
					PreFilters.PreFilter preFilter = null;
					preFilter = preFilters.new PreFilter();
			
					TARGET target = UtilsZebra.getStateAwareTarget(pre.getTarget());
					STATE_AWARE_ACTION action = UtilsZebra.getStateAwareAction(pre.getAction());
			        preFilter.setFilterAction(FILTER_ACTION.FILTER_ACTION_STATE_AWARE);
			        preFilter.StateAwareAction.setTarget(target);
			        preFilter.StateAwareAction.setStateAwareAction(action);
			        
			    	preFilter.setTagPattern(UtilsZebra.hexStringToByteArray(pre.getTag_pattern().getData()));
		        	preFilter.setBitOffset(Integer.parseInt(pre.getTag_pattern().getData_offset()));		
		        	preFilter.setTagPatternBitCount(preFilter.getTagPattern().length * 4);
		        	
			        try {
						reader.Actions.PreFilters.add(preFilter);
					} catch (InvalidUsageException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (OperationFailureException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        logger.info("PreFilter configuration successful for reader: " + reader.getHostName());
				}
		}
    }


  //PostFilter設定
   public PostFilter configurePostFilters(Reader r) {
		if ( r.getPostfilter() != null ){

			PostFilter postFilter = new PostFilter();

	         // Set up the RSSI Range Filter
			if (r.getPostfilter().getRssi() != null) {
		        postFilter.RssiRangeFilter = new RssiRangeFilter();
				postFilter.setRSSIRangeFilter(true);
				postFilter.RssiRangeFilter.setMatchRange(UtilsZebra.getMatchRange(r.getPostfilter().getRssi().getMatch_range()));
				if (StringUtils.isNotEmpty( r.getPostfilter().getRssi().getLower_limit() )){  
					// Minimum RSSI value
		        	postFilter.RssiRangeFilter.setPeakRSSILowerLimit(StringUtils.toShort(r.getPostfilter().getRssi().getLower_limit(),(short)-70)); 
				}
				if (StringUtils.isNotEmpty( r.getPostfilter().getRssi().getUpper_limit() )){  
					// Maximum RSSI value
		        	postFilter.RssiRangeFilter.setPeakRSSIUpperLimit(StringUtils.toShort(r.getPostfilter().getRssi().getUpper_limit(),(short)-30)); 
				}
			}

			if (r.getPostfilter().getTag_pattern_a() != null) {
				FILTER_MATCH_PATTERN pat = UtilsZebra.getFilterMatchPattern(r.getPostfilter().getMatch());
		        postFilter.setPostFilterMatchPattern(pat);
	
		        // Set up TagPatternA         
		        postFilter.TagPatternA = new TagPatternBase();
		        postFilter.TagPatternA.setBitOffset(Integer.parseInt(r.getPostfilter().getTag_pattern_a().getData_offset())); // Bit pointer for pattern A
		        postFilter.TagPatternA.setMemoryBank(UtilsZebra.getMemoryBankEnum(r.getPostfilter().getTag_pattern_a().getMemory_bank())); // Memory bank for pattern A
		        postFilter.TagPatternA.setTagPattern(UtilsZebra.hexStringToByteArray(r.getPostfilter().getTag_pattern_a().getData())); // Tag pattern A
		        postFilter.TagPatternA.setTagMask(UtilsZebra.hexStringToByteArray(r.getPostfilter().getTag_pattern_a().getData_mask()));	//mask
		        postFilter.TagPatternA.setTagPatternBitCount(postFilter.TagPatternA.getTagPattern().length * 4);
		        postFilter.TagPatternA.setTagMaskBitCount(postFilter.TagPatternA.getTagMask().length * 4);
	
				if ( pat != FILTER_MATCH_PATTERN.A ){
			        // Set up TagPatternB
				    postFilter.TagPatternB = new TagPatternBase();
			        postFilter.TagPatternB.setBitOffset(Integer.parseInt(r.getPostfilter().getTag_pattern_b().getData_offset())); // Bit pointer for pattern B
			        postFilter.TagPatternB.setMemoryBank(UtilsZebra.getMemoryBankEnum(r.getPostfilter().getTag_pattern_b().getMemory_bank())); // Memory bank for pattern B
			        postFilter.TagPatternB.setTagPattern(UtilsZebra.hexStringToByteArray(r.getPostfilter().getTag_pattern_b().getData())); // Tag pattern B
			        postFilter.TagPatternB.setTagMask(UtilsZebra.hexStringToByteArray(r.getPostfilter().getTag_pattern_b().getData_mask()));	//mask
			        postFilter.TagPatternB.setTagPatternBitCount(postFilter.TagPatternB.getTagPattern().length * 4);
			        postFilter.TagPatternB.setTagMaskBitCount(postFilter.TagPatternB.getTagMask().length * 4);
				}
			}
	        //logger.info("PostFilter configuration successful for reader: " + r.getHostName());
	        
	        return postFilter;
		}
		
		return null;
    }

	//TriggerInfo 設定
	public TriggerInfo configureTriggerInfo(Reader r) {

			SYSTEMTIME startTime = new SYSTEMTIME();
			Calendar calendar = Calendar.getInstance();
			TimeZone utcTimeZone = TimeZone.getTimeZone("GMT");
			calendar.setTimeZone(utcTimeZone);

			startTime.Year = (short) calendar.get(Calendar.YEAR);
			startTime.Month = (short) calendar.get(Calendar.MONTH);
			startTime.Month += 1;
			startTime.Day = (short) calendar.get(Calendar.DAY_OF_MONTH);
			startTime.Hour = (short) calendar.get(Calendar.HOUR_OF_DAY);
			startTime.Minute = (short) calendar.get(Calendar.MINUTE);
			startTime.Second = (short) calendar.get(Calendar.SECOND);
			startTime.Second += 3;
			startTime.Milliseconds = 0;
			
			// Set up TriggerInfo
			TriggerInfo triggerInfo = new TriggerInfo();

			triggerInfo.StartTrigger
					.setTriggerType(START_TRIGGER_TYPE.START_TRIGGER_TYPE_IMMEDIATE);
			triggerInfo.StopTrigger
					.setTriggerType(STOP_TRIGGER_TYPE.STOP_TRIGGER_TYPE_IMMEDIATE);
			triggerInfo.TagEventReportInfo
					.setReportNewTagEvent(TAG_EVENT_REPORT_TRIGGER.MODERATED);
			triggerInfo.TagEventReportInfo
					.setNewTagEventModeratedTimeoutMilliseconds((short) 500);
			triggerInfo.TagEventReportInfo
					.setReportTagInvisibleEvent(TAG_EVENT_REPORT_TRIGGER.MODERATED);
			triggerInfo.TagEventReportInfo
					.setTagInvisibleEventModeratedTimeoutMilliseconds((short) 500);
			triggerInfo.TagEventReportInfo
					.setReportTagBackToVisibilityEvent(TAG_EVENT_REPORT_TRIGGER.MODERATED);
			triggerInfo.TagEventReportInfo
					.setTagBackToVisibilityModeratedTimeoutMilliseconds((short) 500);
			triggerInfo.setTagReportTrigger(1);

			if (r.getTrigger_info() != null) {
				System.out.println("r.getTrigger_info() != NULL");
				System.out.println("r.getTrigger_info().getStart_type()=" + r.getTrigger_info().getStart_type());

				triggerInfo.StartTrigger.Periodic.setPeriod(2000);
				triggerInfo.StartTrigger.Periodic.StartTime = startTime;
				// Configure start trigger
				triggerInfo.StartTrigger.setTriggerType(UtilsZebra.getStartTriggerType(r.getTrigger_info().getStart_type()));
				// Configure stop trigger
				triggerInfo.StopTrigger.setTriggerType(UtilsZebra.getStopTriggerType(r.getTrigger_info().getStop_type()));

				int duration_m = StringUtils.toInt(r.getTrigger_info().getDuration_mil(), 30000);
				triggerInfo.StopTrigger.setDurationMilliSeconds(duration_m); // 30秒後に自動停止

				triggerInfo.setTagReportTrigger(Integer.parseInt(r.getTrigger_info().getTag_report()));
				logger.info("r.getTrigger_info().getTag_report(): " + r.getTrigger_info().getTag_report());
				return triggerInfo;

			}
		return null;
	}
 
        //アンテナ設定
	public AntennaInfo configureAntennaInfo(Reader r) {
		AntennaInfo  antennaInfo = new AntennaInfo();
		//アンテナを指定したかチェック
		if(r.getAntennas().size() > 0){
			short [] s_ant = UtilsZebra.convertStringListToShortList(r.getAntennas());
			antennaInfo.setAntennaID(s_ant);
		}else{
			antennaInfo = null;
		}
		//logger.info("AntennaInfo configuration successful for reader: " + r.getHostName());
		return antennaInfo;

	}



}
