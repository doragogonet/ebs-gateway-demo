package com.ebs.system.service.impl;

import com.ebs.system.domain.*;
import com.ebs.system.mapper.GatewayMapper;
import com.ebs.system.service.IGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Gatewayサービス
 * 
 * @author ebs
 */
@Service
public class GatewayServiceImpl implements IGatewayService
{

    @Autowired
    private GatewayMapper gatewayMapper;

    @Override
    public List<GatewayReader> selectReaderList(GatewayReader reader)
    {
        return gatewayMapper.selectReaderList(reader);
    }
    @Override
    public List<GatewayReader> selectReaderListAll(GatewayReader reader)
    {
        return gatewayMapper.selectReaderListAll(reader);
    }
    @Override
    public GatewayReader selectReader(String id)
    {
        return gatewayMapper.selectReader(id);
    }

    @Override
    public List<GatewayReaderAntenna> selectReaderAntennaList(GatewayReaderAntenna readerAntenna)
    {
        return gatewayMapper.selectReaderAntennaList(readerAntenna);
    }
    @Override
    public GatewayReaderAntenna selectReaderAntenna(String id)
    {
        return gatewayMapper.selectReaderAntenna(id);
    }
    @Override
    public List<GatewayReaderPortAntenna> selectReaderPortAntennaList(GatewayReaderPortAntenna readerPortAntenna)
    {
        return gatewayMapper.selectReaderPortAntennaList(readerPortAntenna);
    }
    @Override
    public List<GatewayReaderPortAntenna> selectReaderPortAntennaListAll(GatewayReaderPortAntenna readerPortAntenna)
    {
        return gatewayMapper.selectReaderPortAntennaListAll(readerPortAntenna);
    }
    @Override
    public GatewayReaderPortAntenna selectReaderPortAntenna(String id)
    {
        return gatewayMapper.selectReaderPortAntenna(id);
    }
    @Override
    public List<GatewayStorageStore> selectStorageStoreList(GatewayStorageStore storageStore)
    {
        return gatewayMapper.selectStorageStoreList(storageStore);
    }
    @Override
    public List<GatewayStorageStore> selectStorageStoreListAll(GatewayStorageStore storageStore)
    {
        return gatewayMapper.selectStorageStoreListAll(storageStore);
    }
    @Override
    public GatewayStorageStore selectStorageStore(String id)
    {
        return gatewayMapper.selectStorageStore(id);
    }
    @Override
    public List<GatewayStorageRack> selectStorageRackList(GatewayStorageRack storageRack)
    {
        return gatewayMapper.selectStorageRackList(storageRack);
    }
    @Override
    public List<GatewayStorageRack> selectStorageRackListAll(GatewayStorageRack storageRack)
    {
        return gatewayMapper.selectStorageRackListAll(storageRack);
    }
    @Override
    public GatewayStorageRack selectStorageRack(String id)
    {
        return gatewayMapper.selectStorageRack(id);
    }
    @Override
    public List<GatewayStorageCell> selectStorageCellList(GatewayStorageCell storageCell)
    {
        return gatewayMapper.selectStorageCellList(storageCell);
    }
    @Override
    public GatewayStorageCell selectStorageCell(String id)
    {
        return gatewayMapper.selectStorageCell(id);
    }

    @Override
    public List<PageRfidData> selectRfidDataList(PageRfidData rfid) {
        return gatewayMapper.selectRfidDataList(rfid);
    }

    @Override
    public int batchInsertRfidData(List<PageRfidData> list) {
        return gatewayMapper.batchInsertRfidData(list);
    }
    @Override
    public int deleteRfidDataById(Long id) {
        return gatewayMapper.deleteRfidDataById(id);
    }
}
