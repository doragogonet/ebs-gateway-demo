package com.ebs.rfid.zebra;

import java.util.ArrayList;
import java.util.List;

import com.mot.rfid.api3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebs.rfid.zebra.model.JsonMain;
import com.ebs.rfid.zebra.model.Reader;
import com.ebs.rfid.zebra.util.UtilsZebra;


//タグに書き込む処理
public class ActionWrite {

	private Logger logger = LoggerFactory.getLogger(ActionWrite.class);
	//リーダ接続したハンドル退避
	private List<RFIDReader>  readers = new ArrayList<>();
	//JSONデータ
	private JsonMain mainConfig;


	//引数JSONからデータ中から抽出、各リーダーに接続する
	public ActionWrite( JSONObject json ){
		this.mainConfig = JSON.parseObject(JSON.toJSONString(json), JsonMain.class);
	}


	//タグに書き込む処理
	//正常：書き込むデータ構造のJSON文字列を返す。
	//異常：ErrorのJSON文字列を返す。
	public List<String> doTagWrite() {
		//結果内容
		List<String> retStatus = new ArrayList<>();

		for (Reader r : mainConfig.getReaders()) {
			try{
				RFIDReader reader = new RFIDReader();
				reader.setHostName(r.getHostName());
				reader.setPort(Integer.parseInt(r.getPort()));
				reader.setTimeout(5000);
				readers.add(reader);
				 
				//リーダ接続と設定
				ConfigureReader conf = new ConfigureReader(reader,r);
				//有効リーダーハンドルか判断
				if (!reader.isConnected()){
					continue;
				}
		        TagAccess tagAccess = new TagAccess();
		        TagAccess.WriteAccessParams writeAccessParams = tagAccess.new WriteAccessParams();
				if(r.getAccess().getPassword() != null) {
					writeAccessParams.setAccessPassword(Long.parseLong(r.getAccess().getPassword()));
				}
				//TagPatternセット
				if( r.getAccess().getTag_pattern().getMemory_bank() != null) {
					String memryBank = r.getAccess().getTag_pattern().getMemory_bank();
					writeAccessParams.setMemoryBank(UtilsZebra.getMemoryBankEnum(memryBank));
				}
				byte[] writeData = UtilsZebra.hexStringToByteArray(r.getAccess().getTag_pattern().getData());
				writeAccessParams.setWriteData(writeData);

				if(r.getAccess().getTag_pattern().getData_length() != null) {
					String length = r.getAccess().getTag_pattern().getData_length();
					if(writeData.length <= Integer.parseInt(length)) {
						writeAccessParams.setWriteDataLength(writeData.length);
					}else {
						writeAccessParams.setWriteDataLength(Integer.parseInt(length));
					}
					String offset = r.getAccess().getTag_pattern().getData_offset();
					writeAccessParams.setByteOffset(Integer.parseInt(offset));
				}

		        // アンテナ情報の設定
		        AntennaInfo antennaInfo = conf.configureAntennaInfo(r);
		   
				//書き込み実行
			    reader.Actions.TagAccess.writeWait(r.getAccess().getTag_id(),writeAccessParams, antennaInfo);
				//正常完了 実際データを返す。
				retStatus.add(JSON.toJSONString(r));

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

