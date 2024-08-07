package com.ebs.common.utils;

import java.util.List;
import org.springframework.stereotype.Component;
import com.ebs.common.constant.Constants;
import com.ebs.common.core.domain.entity.SysDictData;


@Component
public class DictUtils
{
 
    public static final String SEPARATOR = ",";


    public static void setDictCache(String key, List<SysDictData> dictDatas)
    {
        CacheUtils.put(getCacheName(), getCacheKey(key), dictDatas);
    }


    public static List<SysDictData> getDictCache(String key)
    {
        Object cacheObj = CacheUtils.get(getCacheName(), getCacheKey(key));
        if (StringUtils.isNotNull(cacheObj))
        {
            return StringUtils.cast(cacheObj);
        }
        return null;
    }


    public static String getDictLabel(String dictType, String dictValue)
    {
        return getDictLabel(dictType, dictValue, SEPARATOR);
    }


    public static String getDictValue(String dictType, String dictLabel)
    {
        return getDictValue(dictType, dictLabel, SEPARATOR);
    }


    public static String getDictLabel(String dictType, String dictValue, String separator)
    {
        StringBuilder propertyString = new StringBuilder();
        List<SysDictData> datas = getDictCache(dictType);

        if (StringUtils.containsAny(dictValue, separator) && StringUtils.isNotEmpty(datas))
        {
            for (SysDictData dict : datas)
            {
                for (String value : dictValue.split(separator))
                {
                    if (value.equals(dict.getDictValue()))
                    {
                        propertyString.append(dict.getDictLabel()).append(separator);
                        break;
                    }
                }
            }
        }
        else
        {
            for (SysDictData dict : datas)
            {
                if (dictValue.equals(dict.getDictValue()))
                {
                    return dict.getDictLabel();
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }


    public static String getDictValue(String dictType, String dictLabel, String separator)
    {
        StringBuilder propertyString = new StringBuilder();
        List<SysDictData> datas = getDictCache(dictType);

        if (StringUtils.containsAny(dictLabel, separator) && StringUtils.isNotEmpty(datas))
        {
            for (SysDictData dict : datas)
            {
                for (String label : dictLabel.split(separator))
                {
                    if (label.equals(dict.getDictLabel()))
                    {
                        propertyString.append(dict.getDictValue()).append(separator);
                        break;
                    }
                }
            }
        }
        else
        {
            for (SysDictData dict : datas)
            {
                if (dictLabel.equals(dict.getDictLabel()))
                {
                    return dict.getDictValue();
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

  
    public static void removeDictCache(String key)
    {
        CacheUtils.remove(getCacheName(), getCacheKey(key));
    }

  
    public static void clearDictCache()
    {
        CacheUtils.removeAll(getCacheName());
    }


    public static String getCacheName()
    {
        return Constants.SYS_DICT_CACHE;
    }


    public static String getCacheKey(String configKey)
    {
        return Constants.SYS_DICT_KEY + configKey;
    }
}
