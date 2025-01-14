package com.ebs.rfid.zebra.util;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import com.mot.rfid.api3.FILTER_MATCH_PATTERN;
import com.mot.rfid.api3.LOCK_DATA_FIELD;
import com.mot.rfid.api3.LOCK_PRIVILEGE;
import com.mot.rfid.api3.MATCH_RANGE;
import com.mot.rfid.api3.MEMORY_BANK;
import com.mot.rfid.api3.START_TRIGGER_TYPE;
import com.mot.rfid.api3.STATE_AWARE_ACTION;
import com.mot.rfid.api3.STOP_TRIGGER_TYPE;
import com.mot.rfid.api3.TARGET;
import com.mot.rfid.api3.TagData;

public class UtilsZebra {
	
    public static long stringToTimeStamp(String dateTime, String format){
        LocalDateTime parse = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(format));
        return parse.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public static double getRSSI(String rssi){
        int bm=Integer.parseInt(rssi,16);
        return Double.parseDouble(-Integer.parseInt(Integer.toBinaryString(~(bm-1)).substring(16),2)+"")/10;
    }

    public static String getRSSIString(String rssi){
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(getRSSI(rssi));
    }
    
    public static LOCK_PRIVILEGE getPrivilege(String index)
    {
        LOCK_PRIVILEGE lockPrivilege = LOCK_PRIVILEGE.LOCK_PRIVILEGE_NONE;
        switch (index)
        {
            //Read-Write
            case "0":
                lockPrivilege = LOCK_PRIVILEGE.LOCK_PRIVILEGE_READ_WRITE;
                break;
            //Permanent Lock
            case "1":
                lockPrivilege = LOCK_PRIVILEGE.LOCK_PRIVILEGE_PERMA_LOCK;
                break;
            //Permanent unlock
            case "2":
                lockPrivilege = LOCK_PRIVILEGE.LOCK_PRIVILEGE_PERMA_UNLOCK;
                break;
            //Unlock
            case "3":
                lockPrivilege = LOCK_PRIVILEGE.LOCK_PRIVILEGE_UNLOCK;
                break;
        }

        return lockPrivilege;
    }
    public static LOCK_DATA_FIELD getLockDataField(String index)
    {
        LOCK_DATA_FIELD lockDataField =LOCK_DATA_FIELD.LOCK_EPC_MEMORY;
        switch (index)
        {
            //Kill Password
            case "0":
            	lockDataField = LOCK_DATA_FIELD.LOCK_KILL_PASSWORD;
                break;
            //Access Password
            case "1":
            	lockDataField = LOCK_DATA_FIELD.LOCK_ACCESS_PASSWORD;
                break;
            //EPC Memory
            case "2":
            	lockDataField = LOCK_DATA_FIELD.LOCK_EPC_MEMORY;
                break;
            //TID Memory
            case "3":
            	lockDataField = LOCK_DATA_FIELD.LOCK_TID_MEMORY;
                break;
            //User Memory
            case "4":
            	lockDataField = LOCK_DATA_FIELD.LOCK_USER_MEMORY;
                break;
        }

        return lockDataField;
    }
    
    public static  MATCH_RANGE getMatchRange(String index)
    {
    	MATCH_RANGE range = MATCH_RANGE.WITHIN_RANGE;
        switch(index)
        {
            case "0":
            	range = MATCH_RANGE.WITHIN_RANGE;
                break;
            case "1":
            	range = MATCH_RANGE.OUTSIDE_RANGE;
                break;
            case "2":
            	range = MATCH_RANGE.GREATER_THAN_LOWER_LIMIT;
                break;
            case "3":
            	range = MATCH_RANGE.LOWER_THAN_UPPER_LIMIT;
                break;
        }
        return range;
    }
    
    public static  MEMORY_BANK getMemoryBankEnum(String index)
    {
        MEMORY_BANK memBank = MEMORY_BANK.MEMORY_BANK_EPC;
        switch(index)
        {
            case "0":
                memBank = MEMORY_BANK.MEMORY_BANK_RESERVED;
                break;
            case "1":
                memBank = MEMORY_BANK.MEMORY_BANK_EPC;
                break;
            case "2":
                memBank = MEMORY_BANK.MEMORY_BANK_TID;
                break;
            case "3":
                memBank = MEMORY_BANK.MEMORY_BANK_USER;
                break;
        }
        return memBank;
    }
    
	public static STATE_AWARE_ACTION getStateAwareAction(String actionIndex)
	{
	    STATE_AWARE_ACTION stateAwareAction = STATE_AWARE_ACTION.STATE_AWARE_ACTION_ASRT_SL_NOT_DSRT_SL;
	    switch (actionIndex)
	    {
	        case "0":
	            stateAwareAction = STATE_AWARE_ACTION.STATE_AWARE_ACTION_INV_A_NOT_INV_B;
	            break;
	        case "1":
	            stateAwareAction = STATE_AWARE_ACTION.STATE_AWARE_ACTION_ASRT_SL_NOT_DSRT_SL;
	            break;
	        case "2":
	            stateAwareAction = STATE_AWARE_ACTION.STATE_AWARE_ACTION_INV_A;
	            break;
	        case "3":
	            stateAwareAction = STATE_AWARE_ACTION.STATE_AWARE_ACTION_ASRT_SL;
	            break;
	        case "4":
	            stateAwareAction = STATE_AWARE_ACTION.STATE_AWARE_ACTION_NOT_INV_B;           
	            break;
	        case "5":
	            stateAwareAction = STATE_AWARE_ACTION.STATE_AWARE_ACTION_NOT_DSRT_SL;            
	            break;
	        case "6":
	            stateAwareAction = STATE_AWARE_ACTION.STATE_AWARE_ACTION_INV_A2BB2A;            
	            break;
	        case "7":
	            stateAwareAction = STATE_AWARE_ACTION.STATE_AWARE_ACTION_NEG_SL;          
	            break;
	        case "8":
	            stateAwareAction = STATE_AWARE_ACTION.STATE_AWARE_ACTION_INV_B_NOT_INV_A;           
	            break;
	        case "9":
	            stateAwareAction = STATE_AWARE_ACTION.STATE_AWARE_ACTION_DSRT_SL_NOT_ASRT_SL;           
	            break;
	        case "10":
	            stateAwareAction = STATE_AWARE_ACTION.STATE_AWARE_ACTION_INV_B;
	            break;
	        case "11":
	            stateAwareAction = STATE_AWARE_ACTION.STATE_AWARE_ACTION_DSRT_SL;           
	            break;
	        case "12":
	            stateAwareAction = STATE_AWARE_ACTION.STATE_AWARE_ACTION_NOT_INV_A;           
	            break;
	        case "13":
	            stateAwareAction = STATE_AWARE_ACTION.STATE_AWARE_ACTION_NOT_ASRT_SL;           
	            break;
	        case "14":
	            stateAwareAction = STATE_AWARE_ACTION.STATE_AWARE_ACTION_NOT_INV_A2BB2A;           
	            break;
	        case "15":
	            stateAwareAction = STATE_AWARE_ACTION.STATE_AWARE_ACTION_NOT_NEG_SL;           
	            break;
	    }
	    return stateAwareAction;
	}

	public static TARGET getStateAwareTarget(String index)
	{
	    TARGET target = TARGET.TARGET_SL;
	    switch (index)
	    {
	        case "0":
	            target = TARGET.TARGET_SL;
	            break;
	        case "1":
	            target = TARGET.TARGET_INVENTORIED_STATE_S0;
	            break;
	        case "2":
	            target = TARGET.TARGET_INVENTORIED_STATE_S1;;
	            break;
	        case "3":
	            target = TARGET.TARGET_INVENTORIED_STATE_S2;
	            break;
	        case "4":
	            target = TARGET.TARGET_INVENTORIED_STATE_S3;
	            break;
	
	    }
	    return target;
	}


	public static START_TRIGGER_TYPE getStartTriggerType(String index)
	{
	    START_TRIGGER_TYPE target = START_TRIGGER_TYPE.START_TRIGGER_TYPE_NONE;		
	    switch (index)
	    {
	        case "0":
	            target = START_TRIGGER_TYPE.START_TRIGGER_TYPE_DISTANCE;
	            break;
	        case "1":
	            target = START_TRIGGER_TYPE.START_TRIGGER_TYPE_GPI;
	            break;
	        case "2":
	            target = START_TRIGGER_TYPE.START_TRIGGER_TYPE_HANDHELD;	
	            break;
	        case "3":
	            target = START_TRIGGER_TYPE.START_TRIGGER_TYPE_IMMEDIATE;	
	            break;
	        case "4":
	            target = START_TRIGGER_TYPE.START_TRIGGER_TYPE_PERIODIC;
	            break;
	        case "5":
	            target = START_TRIGGER_TYPE.START_TRIGGER_TYPE_TIMELAPSE;
	            break;
	
	    }
	    return target;

	}


	public static STOP_TRIGGER_TYPE getStopTriggerType(String index)
	{
	    STOP_TRIGGER_TYPE target = STOP_TRIGGER_TYPE.STOP_TRIGGER_TYPE_DURATION;	
	    switch (index)
	    {
	        case "0":
	            target = STOP_TRIGGER_TYPE.STOP_TRIGGER_TYPE_DURATION;	
	            break;
	        case "1":
	            target = STOP_TRIGGER_TYPE.STOP_TRIGGER_TYPE_GPI_WITH_TIMEOUT;	
	            break;
	        case "2":
	            target = STOP_TRIGGER_TYPE.STOP_TRIGGER_TYPE_HANDHELD_WITH_TIMEOUT;	
	            break;
	        case "3":
	            target = STOP_TRIGGER_TYPE.STOP_TRIGGER_TYPE_IMMEDIATE;	
	            break;
	        case "4":
	            target = STOP_TRIGGER_TYPE.STOP_TRIGGER_TYPE_N_ATTEMPTS_WITH_TIMEOUT;
	            break;
	        case "5":
	            target = STOP_TRIGGER_TYPE.STOP_TRIGGER_TYPE_NONE;
	            break;
	        case "6":
	            target = STOP_TRIGGER_TYPE.STOP_TRIGGER_TYPE_TAG_OBSERVATION_WITH_TIMEOUT;
	            break;
	        case "7":
	            target = STOP_TRIGGER_TYPE.STOP_TRIGGER_TYPE_TIMELAPSE;
	            break;
	
	    }
	    return target;


	}

	public static FILTER_MATCH_PATTERN getFilterMatchPattern(String index)
	{
		FILTER_MATCH_PATTERN pat = FILTER_MATCH_PATTERN.A;
	    switch(index)
	    {
	        case "0":
	        	pat = FILTER_MATCH_PATTERN.A;
	            break;
	        case "1":
	        	pat = FILTER_MATCH_PATTERN.A_AND_B;
	            break;
	        case "2":
	        	pat = FILTER_MATCH_PATTERN.A_AND_NOTB;
	            break;
	        case "3":
	        	pat = FILTER_MATCH_PATTERN.NOTA_AND_B;
	            break;
	        case "4":
	        	pat = FILTER_MATCH_PATTERN.NOTA_AND_NOTB;
	            break;

	    }
	    return pat;
	}
	
	public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        if (len % 2 != 0) {
        	s = "0" + s;
        	len += 1;
        }
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
	
	public static String hexToBinary(String hexString) {
        StringBuilder binaryBuilder = new StringBuilder();
        for (int i = 0; i < hexString.length(); i++) {
            String binary = Integer.toBinaryString(Integer.parseInt(hexString.charAt(i) + "", 16));
            binaryBuilder.append(String.format("%4s", binary).replace(' ', '0')); // 4ビットのバイナリに書式設定
        }
        return binaryBuilder.toString();
    }

    // List<String>をshort[]に変換するメソッド
    public static short[] convertStringListToShortList(List<String> stringList) {
        short[] shortArr = new short[stringList.size()];
        int i = 0;
        for (String s : stringList) {
            try {
                // 文字列をショート型に変換し、配列に追加
            	shortArr[i++] = Short.parseShort(s);
            } catch (NumberFormatException e) {
                // 数字に変換できない場合のエラーハンドリング
                System.err.println("変換エラー: " + s + " はshortに変換できません。");
            }
        }
        return shortArr;
    }
    
    public static boolean addTagToMap(Map<String, TagData> tags, TagData tag) {
    	if (tags.containsKey(tag.getTagID())) {
    		if (Math.abs(Math.abs(tag.getPeakRSSI()) - Math.abs(tags.get(tag.getTagID()).getPeakRSSI())) >= 5) {
    			 tags.put(tag.getTagID(), tag);
    			return true;
    		}
    		return false;
    	}
    	tags.put(tag.getTagID(), tag);
    	return true;
    }
}

