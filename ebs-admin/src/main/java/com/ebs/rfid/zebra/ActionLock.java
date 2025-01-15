package com.ebs.rfid.zebra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ebs.rfid.readersRFIDManager;
import com.mot.rfid.api3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebs.rfid.zebra.model.JsonMain;
import com.ebs.rfid.zebra.model.Reader;
import com.ebs.rfid.zebra.util.UtilsZebra;


//ロックとアンロック処理
public class ActionLock {

	//リーダ接続したハンドル退避
	private List<RFIDReader>  readers = new ArrayList<>();
	//JSONデータ
	private JsonMain mainConfig;

	//重複tag対策
    private Set<String> uniqueTags = Collections.synchronizedSet(new HashSet<>());
    
	//ログ処理
    private static final Logger logger = LoggerFactory.getLogger(ActionLock.class);
	private RFIDReader reader;
	private readersRFIDManager rm;

	//引数JSONからデータ中から抽出、各リーダーに接続する
	public ActionLock( JSONObject json ){
		this.mainConfig = JSON.parseObject(JSON.toJSONString(json), JsonMain.class);
	}

	public List<String> doTagLock() {
		//結果内容
		List<String> retStatus = new ArrayList<>();

		for (Reader r : mainConfig.getReaders()) {
			try{
				rm = readersRFIDManager.getInstance();
				reader = rm.getReader(r.getHostName());
				if(reader == null){
					reader = new RFIDReader();
					reader.setHostName(r.getHostName());
					reader.setPort(Integer.parseInt(r.getPort()));
					reader.setTimeout(5000);
					rm.addReader(r.getHostName(), reader);
				}else {
					System.out.println("reader ==" + reader.toString());
				}
				readers.add(reader);
				 
				//リーダ接続と設定
				ConfigureReader conf = new ConfigureReader(reader,r);
				//有効リーダーハンドルか判断
				if (!reader.isConnected()){
					continue;
				}
		        TagAccess tagAccess = new TagAccess();
				TagAccess.LockAccessParams lockAccessParams = tagAccess.new LockAccessParams();
				if(r.getAccess().getPassword() != null) {
					lockAccessParams.setAccessPassword(Long.parseLong(r.getAccess().getPassword()));
				}
		        // アンテナ情報の設定
		        AntennaInfo antennaInfo = conf.configureAntennaInfo(r);

				String lockPrivilege = "";
				String lockField = "";
				if(r.getAccess().getLock_type() != null) {
					lockPrivilege = r.getAccess().getLock_type();
				}
				if(r.getAccess().getLock_target() != null) {
					lockField = r.getAccess().getLock_target();
				}
	            lockAccessParams.setLockPrivilege(UtilsZebra.getLockDataField(lockField) , UtilsZebra.getPrivilege(lockPrivilege));
	            reader.Actions.TagAccess.lockWait(r.getAccess().getTag_id(),lockAccessParams, antennaInfo);
				//正常完了 実際データを返す。
				retStatus.add(JSON.toJSONString(r));

            } catch (Exception e) {
                logger.error("Unexpected error when disconnecting reader: " + r.getHostName());
				String rerr = ConfigureReader.getReturnError("ERR","Unexpected error when disconnecting reader: " + r.getHostName());
				retStatus.add(rerr);
            }
        }
		//リーダ接続を終了する。
		ConfigureReader.disconnectReaders(readers);
		
		return retStatus;

	}
}
