package com.ebs.rfid.zebra.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebs.rfid.zebra.EventsListener;
import com.ebs.rfid.zebra.Inventory;

public class ZebarWrapperTest {

	public static void main(String[] args) {
		
		JSONObject rootJson = new JSONObject();	
		//readers配列
		JSONArray readersArray = new JSONArray();
		//readers
		JSONObject readersJson = new JSONObject();	
		
		//readers-hostname
		readersJson.put("hostname","10.8.1.3");
		//readers-port
		readersJson.put("port","0");
		
		//readers-antennas配列
		JSONArray antennas = new JSONArray();
		antennas.add("2");
		readersJson.put("antennas",antennas);
		
		//readers-power
		readersJson.put("power","180");
		
//		//readers-prefilters
//		JSONObject preJson = new JSONObject();
//		preJson.put("target","1");
//		preJson.put("action","0");
//		
//		JSONArray prefiltersArray = new JSONArray();
//		
//		//readers-prefilters-tag_pattern
//		JSONObject patternJson = new JSONObject();
//		//readers-prefilters-tag_pattern-memory_bank
//		patternJson.put("memory_bank","1");
//		//readers-prefilters-tag_pattern-data
//		patternJson.put("data","1234");
//		//readers-prefilters-tag_pattern-data_offset
//		patternJson.put("data_offset","32");
//		preJson.put("tag_pattern",patternJson);
//		
//		prefiltersArray.add(preJson);
		
//		JSONObject preJson2 = new JSONObject();
//		preJson2.put("target","2");
//		preJson2.put("action","1");
//		JSONObject patternJson2 = new JSONObject();
//		//readers-prefilters-tag_pattern-memory_bank
//		patternJson2.put("memory_bank","2");
//		//readers-prefilters-tag_pattern-data
//		patternJson2.put("data","1234");
//		//readers-prefilters-tag_pattern-data_offset
//		patternJson2.put("data_offset","32");
//		preJson2.put("tag_pattern",patternJson2);
//		
//		prefiltersArray.add(preJson2);
		
//		readersJson.put("prefilters",prefiltersArray);
		
		//readers-trigger_info
//		JSONObject triggerJson = new JSONObject();
//		triggerJson.put("start_type","3");
//		triggerJson.put("stop_type","3");
//		triggerJson.put("duration_mil","3000");
//		triggerJson.put("tag_report","1");
//		readersJson.put("trigger_info",triggerJson);
		
//		//readers-postfilter
//		JSONObject postJson = new JSONObject();
//		postJson.put("match","0");
//		//readers-postfilter-tag_pattern_a
//		JSONObject patternAJson = new JSONObject();
//		patternAJson.put("memory_bank","1");
//		patternAJson.put("data","12");
//		patternAJson.put("data_offset","32");
//		patternAJson.put("data_mask","ff");
//		postJson.put("tag_pattern_a",patternAJson);
//		//readers-postfilter-tag_pattern_b
//		JSONObject patternBJson = new JSONObject();
//		patternBJson.put("memory_bank","1");
//		patternBJson.put("data","1234");
//		patternBJson.put("data_offset","32");
//		patternBJson.put("data_mask","ffff");
//		postJson.put("tag_pattern_b",patternBJson);
//		//readers-postfilter-rssi
//		JSONObject rssiJson = new JSONObject();
//		rssiJson.put("match_range","0");
//		rssiJson.put("lower_limit","-70");
//		rssiJson.put("upper_limit","-20");
//		postJson.put("rssi",rssiJson);
		//readersJson.put("postfilter",postJson);
		
		//readers-access
//		JSONObject accessJson = new JSONObject();
//		accessJson.put("tag_id","1234FFFE56780F2BF06F");
//		accessJson.put("password","00000000");
//		accessJson.put("lock_type","3");
//		accessJson.put("lock_target","4");
//		//readers-access-tag_pattern
//		patternJson = new JSONObject();
//		patternJson.put("memory_bank","1");
//		patternJson.put("data","5678");
//		patternJson.put("data_offset","8");
//		patternJson.put("data_length","2");
//		accessJson.put("tag_pattern",patternJson);
//		readersJson.put("access",accessJson);
		
		readersArray.add(readersJson);
		
		rootJson.put("readers",readersArray);
		rootJson.put("token","tets_token1");
		System.out.println("rootJson:" + JSON.toJSONString(rootJson));
//		ActionLock lock = new ActionLock(rootJson);
//		List<String> retStatus = lock.doTagLock();
//		for (String status : retStatus) {
//			System.out.println(status);
//		}
		
//		ActionWrite write = new ActionWrite(rootJson);
//		List<String> retStatus = write.doTagWrite();
//		for (String status : retStatus) {
//			System.out.println(status);
//		}
		
//		ActionRead read = new ActionRead(rootJson);
//		List<String> retStatus = read.doTagRead();
//		for (String status : retStatus) {
//			System.out.println(status);
//		}
		
		//2024/08/06/火曜日/12/21/17/821
		//2024/8/5/21/35/38/950
//		String utcTime = "2024-08-05T21:35:38Z";        
//        // 将字符串解析为Instant对象        
//        Instant instant = Instant.parse(utcTime);        
//        // 获取本地时间的时区        
//        ZoneId zone = ZoneId.systemDefault();        
//        // 将Instant对象转换为本地时间        
//        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);        
//        // 将本地时间格式化为字符串        
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");        
//        String localTime = localDateTime.format(formatter);        
//        // 输出本地时间        
//        System.out.println(localTime);  
		
		Inventory inventory = new Inventory(rootJson);
			inventory.Start(new EventsListener() {
				@Override
				public void commonReadNotify(String jsonStr) {
					System.out.println(jsonStr);
					JSONObject obj = JSON.parseObject(jsonStr);
					System.out.println("ip:" + obj.getString("hostName"));
//				String ymdhms = obj.getString("SeenTime");
//				ymdhms += "/" + obj.getString("SeenTime.uTCTime.firstSeenTimeStamp.Month");
//				ymdhms += "/" + obj.getString("SeenTime.uTCTime.firstSeenTimeStamp.Day");
//				System.out.println("time:" + ymdhms);
					System.out.println("getPeakRSSI:" + obj.getString("peakRSSI"));
					System.out.println("getTagID:" + obj.getString("tagID"));
					System.out.println("seenTime:" + obj.getString("seenTime"));
					System.out.println("relativeDistance:" + obj.getString("relativeDistance"));
					System.out.println("phase:" + obj.getString("phase"));
				}
			});
			try {
				Thread.sleep(7000);
				inventory.Stop();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		String json = "{";
//		json +=	  "\"readers\":";
//		json +=	  "		[";
//		json +=	  "			{";
//		json +=	  "				\"hostname\":\"169.254.10.1\",";
//		json +=	  "				\"port\":\"0\",";
//		json +=	  "				\"antennas\":[\"1\"],";
//		json +=	  "				\"power\":\"27.0\",";
//		json +=	  "				\"postfilter\":{\"match\":0}";
//		json +=	  "			}";
//		json +=	  "		],";
//		json +=	  "\"token\":\"2024/8/8 11:01:59\"";
//		json +=	  "}";
//		//System.out.println(JSON.toJSONString(rootJson));
//		//System.out.println(JSON.toJSONString(rootJson));
//		Inventory inventory = new Inventory(JSON.parseObject(json));
//		inventory.Start(new EventsListener(){
//			@Override
//			public void commonReadNotify(String jsonStr) {
//				System.out.println(jsonStr);
//				JSONObject obj = JSON.parseObject(jsonStr);
//				System.out.println("ip:" + obj.getString("hostName"));
////				String ymdhms = obj.getString("SeenTime");
////				ymdhms += "/" + obj.getString("SeenTime.uTCTime.firstSeenTimeStamp.Month");
////				ymdhms += "/" + obj.getString("SeenTime.uTCTime.firstSeenTimeStamp.Day");
////				System.out.println("time:" + ymdhms);
//				System.out.println("getPeakRSSI:" + obj.getString("peakRSSI"));
//				System.out.println("getTagID:" + obj.getString("tagID"));
//				System.out.println("seenTime:" + obj.getString("seenTime"));
//			}											
//		});
//		try {
//			Thread.sleep(5000);
//			inventory.Stop();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String strJson = "{\"readers\":[{\"access\":{\"lock_target\":\"\",\"lock_type\":\"0\",\"password\":\"00000000\",\"tag_id\":\"1234FFFE56780F2BF06F\",\"tag_pattern\":{\"memory_bank\":\"0\"}},\"antennas\":[\"1\"],\"hostName\":\"169.254.10.1\",\"port\":\"0\",\"power\":\"27.0\"}],\"token\":\"2024/8/19 13:59:49\"}";
//		ActionRead read = new ActionRead(JSON.parseObject(strJson));
//		List<String> retStatus = read.doTagRead();
//		for (String status : retStatus) {
//			System.out.println(status);
//			JSONObject obj = JSON.parseObject(status);
//			System.out.println(":" + obj.getString("memoryBankData"));
//		}
	}
	

}


