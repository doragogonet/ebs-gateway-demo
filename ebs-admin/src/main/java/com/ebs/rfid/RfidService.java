package com.ebs.rfid;

import com.ebs.common.constant.Constants;
import com.ebs.common.constant.ShiroConstants;
import com.ebs.common.core.domain.entity.SysUser;
import com.ebs.common.exception.user.UserPasswordNotMatchException;
import com.ebs.common.exception.user.UserPasswordRetryLimitExceedException;
import com.ebs.common.utils.MessageUtils;
import com.ebs.framework.manager.AsyncManager;
import com.ebs.framework.manager.factory.AsyncFactory;
import com.ebs.rfid.gateway.*;
import com.ebs.rfid.gateway.ProcessHandle;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;

/**
 * EBSのGatewaySDKを呼び出す
 * 
 * @author ebs
 */
@Configuration
public class RfidService
{
    @Value("${redis.host}")
    String host;
    @Value("${redis.port}")
    int port;
    @Value("${redis.password}")
    String password;
    private RfidGatewayDriver gatewayDriver = null;

    public RfidService() throws Exception {
        this.gatewayDriver = new RfidGatewayDriver(this.host, this.port, this.password);
    }

    public ProcessHandle startInventory(String json, RfidDataListener listener) throws Exception {
        return this.gatewayDriver.startInventory(json,listener);
    }

    public boolean stopInventory() throws Exception {
        return this.gatewayDriver.stopInventory();
    }
    public TagInfo tagRead(String json) throws Exception {
        return this.gatewayDriver.tagRead(json);
    }

    public boolean tagLock(String json) throws Exception {
        return this.gatewayDriver.tagLock(json);
    }

    public boolean tagUnlock(String json) throws Exception {
        return this.gatewayDriver.tagUnlock(json);
    }

    public boolean tagWrite(String json) throws Exception {
        return this.gatewayDriver.tagWrite(json);
    }

    public String epcEncode(String json) throws Exception {
        return this.gatewayDriver.epcEncode(json);
    }

    public String epcDecode(String json) throws Exception {
        return this.gatewayDriver.epcDecode(json);
    }

    public List<RfidData> getrfidDatas(int length) {
        return this.gatewayDriver.getRfidDatas(length);
    }
    public void destory() {
        this.gatewayDriver.destrory();
    }
}
