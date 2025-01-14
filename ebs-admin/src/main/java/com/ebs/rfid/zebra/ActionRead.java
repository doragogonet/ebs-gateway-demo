package com.ebs.rfid.zebra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mot.rfid.api3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebs.rfid.zebra.model.JsonMain;
import com.ebs.rfid.zebra.model.Reader;
import com.ebs.rfid.zebra.util.StringUtils;
import com.ebs.rfid.zebra.util.UtilsZebra;


//TAG読み取る処理
public class ActionRead {

	//リーダ接続したハンドル退避
	private List<RFIDReader>  readers = new ArrayList<>();
	//JSONデータ
	private JsonMain mainConfig;

	//重複tag対策
    private Set<String> uniqueTags = Collections.synchronizedSet(new HashSet<>());
    
	//ログ処理
    private static final Logger logger = LoggerFactory.getLogger(ActionRead.class);

	//引数JSONからデータ中から抽出、各リーダーに接続する
	public ActionRead( JSONObject json ){
		this.mainConfig = JSON.parseObject(JSON.toJSONString(json), JsonMain.class);
	}
	

	//タグ読み取る処理
	//正常：tagData構造のJSON文字列を返す。
	//異常：ErrorのJSON文字列を返す。
	public List<String> doTagRead() {
		//結果内容
		List<String> retStatus = new ArrayList<>();

		for (Reader r : mainConfig.getReaders()) {
			try{
				RFIDReader reader = new RFIDReader();
				reader.setHostName(r.getHostName());
				reader.setPort(Integer.parseInt(r.getPort()));
				reader.setTimeout(3000);
				readers.add(reader);

				//リーダ接続と設定
				ConfigureReader conf = new ConfigureReader(reader,r);
				//有効リーダーハンドルか判断
				if (!reader.isConnected()){
					continue;
				}

		        TagAccess tagAccess = new TagAccess();
		        TagAccess.ReadAccessParams readAccessParams = tagAccess.new ReadAccessParams();
				if(r.getAccess().getPassword() != null ) {
					readAccessParams.setAccessPassword(Long.parseLong(r.getAccess().getPassword()));
				}
				String memryBank = "1";
				if(r.getAccess().getTag_pattern().getMemory_bank() != null ) {
					memryBank = r.getAccess().getTag_pattern().getMemory_bank();
					readAccessParams.setMemoryBank(UtilsZebra.getMemoryBankEnum(memryBank));
				}
				if(r.getAccess().getTag_pattern().getData_length() != null ) {
					String length = r.getAccess().getTag_pattern().getData_length();
					if( Integer.parseInt(length) % 2 == 0) {
						readAccessParams.setByteCount(Integer.parseInt(length));
					}else{
						readAccessParams.setByteCount(12);
					}
		        }
				if(r.getAccess().getTag_pattern().getData_offset() != null ) {
					String offset = r.getAccess().getTag_pattern().getData_offset();
					readAccessParams.setByteOffset(Integer.parseInt(offset));
				}else{
					//EPCの場合はCRC PCを除く
			//		if(MEMORY_BANK.GetMemoryBankValue(Integer.parseInt(memryBank)) == MEMORY_BANK.MEMORY_BANK_EPC) {
			//			readAccessParams.setByteOffset(4);
			//		}else {
						readAccessParams.setByteOffset(0);
			//		}
				}

				String tag = r.getAccess().getTag_id();
				if(tag.length() % 2 != 0){
					//偶数処理
					tag = "0" + tag;
				}

				// アンテナ情報の設定
		        AntennaInfo antennaInfo = conf.configureAntennaInfo(r);

				System.out.println("memryBank---" + memryBank);
		        // タグデータの読み取り
				TagData tagData =
						reader.Actions.TagAccess.readWait(tag, readAccessParams, antennaInfo);

				System.out.println("readWait---" + JSON.toJSONString(tagData));
		        retStatus.add(JSON.toJSONString(tagData));

            } catch (InvalidUsageException e) {
                logger.error("Invalid usage when disconnecting reader: " + r.getHostName());
				String rerr =  ConfigureReader.getReturnError("ERR","Invalid usage when disconnecting reader: " + r.getHostName());
				retStatus.add(rerr);

            } catch (OperationFailureException e) {
                logger.error("Operation failure when disconnecting reader: " + r.getHostName() + " Reason: " + e.getVendorMessage());
				String rerr =  ConfigureReader.getReturnError("ERR","Operation failure when disconnecting reader: " + r.getHostName() + " Reason: " + e.getVendorMessage());
				retStatus.add(rerr);
            } catch (Exception e) {
                logger.error("Unexpected error when disconnecting reader: " + r.getHostName());
				String rerr =  ConfigureReader.getReturnError("ERR","Unexpected error when disconnecting reader: " + r.getHostName());
				retStatus.add(rerr);
            }


		}
		//リーダ接続を終了する。
		ConfigureReader.disconnectReaders(readers);

		return retStatus;
    }

	 
}
