package com.ebs.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.ebs.common.config.EbsConfig;
import com.ebs.common.constant.Constants;
import com.ebs.common.utils.http.HttpUtils;


public class AddressUtils
{
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    public static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip)
    {
        if (IpUtils.internalIp(ip))
        {
            return "内部IP";
        }
        if (EbsConfig.isAddressEnabled())
        {
            try
            {
                String rspStr = HttpUtils.sendGet(IP_URL, "ip=" + ip + "&json=true", Constants.GBK);
                if (StringUtils.isEmpty(rspStr))
                {
                    log.error("地理的位置取得異常 {}", ip);
                    return UNKNOWN;
                }
                JSONObject obj = JSONObject.parseObject(rspStr);
                String region = obj.getString("pro");
                String city = obj.getString("city");
                return String.format("%s %s", region, city);
            }
            catch (Exception e)
            {
                log.error("地理的位置取得異常 {}", e);
            }
        }
        return UNKNOWN;
    }
}
