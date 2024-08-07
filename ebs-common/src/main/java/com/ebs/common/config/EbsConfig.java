package com.ebs.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ebs")
public class EbsConfig
{

    private static String name;


    private static String version;


    private static String copyrightYear;


    private static boolean demoEnabled;


    private static String profile;


    private static boolean addressEnabled;

    public static String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        EbsConfig.name = name;
    }

    public static String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        EbsConfig.version = version;
    }

    public static String getCopyrightYear()
    {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear)
    {
        EbsConfig.copyrightYear = copyrightYear;
    }

    public static boolean isDemoEnabled()
    {
        return demoEnabled;
    }

    public void setDemoEnabled(boolean demoEnabled)
    {
        EbsConfig.demoEnabled = demoEnabled;
    }

    public static String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        EbsConfig.profile = profile;
    }

    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled)
    {
        EbsConfig.addressEnabled = addressEnabled;
    }


    public static String getImportPath()
    {
        return getProfile() + "/import";
    }


    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }


    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }


    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }
}
