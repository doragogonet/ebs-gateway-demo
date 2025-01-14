package com.ebs.rfid.zebra;

import com.alibaba.fastjson.JSONObject;


public class Inventory  extends InventoryBase {

	
	public Inventory(JSONObject json) {

		super(json);

	}

	//Inventory開始
	public void Start(EventsListener event){
		super.StartInventory(event);
	}

	//Inventory終了
	public void Stop(){
		super.StopInventory();
	}
}


