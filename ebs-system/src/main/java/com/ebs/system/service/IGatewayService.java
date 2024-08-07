package com.ebs.system.service;

import com.ebs.system.domain.*;

import java.util.List;

/**
 * Gatewayサービス
 * 
 * @author ebs
 */
public interface IGatewayService
{

    public List<GatewayReader> selectReaderList(GatewayReader reader);
    public List<GatewayReader> selectReaderListAll(GatewayReader reader);
    public GatewayReader selectReader(String id);
    public List<GatewayReaderAntenna> selectReaderAntennaList(GatewayReaderAntenna readerAntenna);
    public GatewayReaderAntenna selectReaderAntenna(String id);
    public List<GatewayReaderPortAntenna> selectReaderPortAntennaList(GatewayReaderPortAntenna readerPortAntenna);
    public List<GatewayReaderPortAntenna> selectReaderPortAntennaListAll(GatewayReaderPortAntenna readerPortAntenna);
    public GatewayReaderPortAntenna selectReaderPortAntenna(String id);
    public List<GatewayStorageStore> selectStorageStoreList(GatewayStorageStore storageStore);
    public List<GatewayStorageStore> selectStorageStoreListAll(GatewayStorageStore storageStore);
    public GatewayStorageStore selectStorageStore(String id);
    public List<GatewayStorageRack> selectStorageRackList(GatewayStorageRack storageRack);
    public List<GatewayStorageRack> selectStorageRackListAll(GatewayStorageRack storageRack);
    public GatewayStorageRack selectStorageRack(String id);
    public List<GatewayStorageCell> selectStorageCellList(GatewayStorageCell storageCell);
    public GatewayStorageCell selectStorageCell(String id);

    public List<PageRfidData> selectRfidDataList(PageRfidData rfid);
    public int batchInsertRfidData(List<PageRfidData> list);
}
