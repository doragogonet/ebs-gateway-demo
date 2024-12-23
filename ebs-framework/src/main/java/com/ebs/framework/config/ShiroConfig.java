package com.ebs.framework.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.Filter;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ebs.common.constant.Constants;
import com.ebs.common.utils.StringUtils;
import com.ebs.common.utils.security.CipherUtils;
import com.ebs.common.utils.spring.SpringUtils;
import com.ebs.framework.shiro.realm.UserRealm;
import com.ebs.framework.shiro.session.OnlineSessionDAO;
import com.ebs.framework.shiro.session.OnlineSessionFactory;
import com.ebs.framework.shiro.web.CustomShiroFilterFactoryBean;
import com.ebs.framework.shiro.web.filter.LogoutFilter;
import com.ebs.framework.shiro.web.filter.captcha.CaptchaValidateFilter;
import com.ebs.framework.shiro.web.filter.kickout.KickoutSessionFilter;
import com.ebs.framework.shiro.web.filter.online.OnlineSessionFilter;
import com.ebs.framework.shiro.web.filter.sync.SyncOnlineSessionFilter;
import com.ebs.framework.shiro.web.session.OnlineWebSessionManager;
import com.ebs.framework.shiro.web.session.SpringSessionValidationScheduler;
import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;


@Configuration
public class ShiroConfig
{
   
    @Value("${shiro.session.expireTime}")
    private int expireTime;

  
    @Value("${shiro.session.validationInterval}")
    private int validationInterval;

    @Value("${shiro.session.maxSession}")
    private int maxSession;

 
    @Value("${shiro.session.kickoutAfter}")
    private boolean kickoutAfter;

   
    @Value("${shiro.user.captchaEnabled}")
    private boolean captchaEnabled;

    
    @Value("${shiro.user.captchaType}")
    private String captchaType;

  
    @Value("${shiro.cookie.domain}")
    private String domain;

   
    @Value("${shiro.cookie.path}")
    private String path;

  
    @Value("${shiro.cookie.httpOnly}")
    private boolean httpOnly;

 
    @Value("${shiro.cookie.maxAge}")
    private int maxAge;

 
    @Value("${shiro.cookie.cipherKey}")
    private String cipherKey;

 
    @Value("${shiro.user.loginUrl}")
    private String loginUrl;

   
    @Value("${shiro.user.unauthorizedUrl}")
    private String unauthorizedUrl;

  
    @Value("${shiro.rememberMe.enabled: false}")
    private boolean rememberMe;

  
    @Bean
    public EhCacheManager getEhCacheManager()
    {
        net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.getCacheManager("ruoyi");
        EhCacheManager em = new EhCacheManager();
        if (StringUtils.isNull(cacheManager))
        {
            em.setCacheManager(new net.sf.ehcache.CacheManager(getCacheManagerConfigFileInputStream()));
            return em;
        }
        else
        {
            em.setCacheManager(cacheManager);
            return em;
        }
    }

   
    protected InputStream getCacheManagerConfigFileInputStream()
    {
        String configFile = "classpath:ehcache/ehcache-shiro.xml";
        InputStream inputStream = null;
        try
        {
            inputStream = ResourceUtils.getInputStreamForPath(configFile);
            byte[] b = IOUtils.toByteArray(inputStream);
            InputStream in = new ByteArrayInputStream(b);
            return in;
        }
        catch (IOException e)
        {
            throw new ConfigurationException(
                    "Unable to obtain input stream for cacheManagerConfigFile [" + configFile + "]", e);
        }
        finally
        {
            IOUtils.closeQuietly(inputStream);
        }
    }

    
    @Bean
    public UserRealm userRealm(EhCacheManager cacheManager)
    {
        UserRealm userRealm = new UserRealm();
        userRealm.setAuthorizationCacheName(Constants.SYS_AUTH_CACHE);
        userRealm.setCacheManager(cacheManager);
        return userRealm;
    }

   
    @Bean
    public OnlineSessionDAO sessionDAO()
    {
        OnlineSessionDAO sessionDAO = new OnlineSessionDAO();
        return sessionDAO;
    }

   
    @Bean
    public OnlineSessionFactory sessionFactory()
    {
        OnlineSessionFactory sessionFactory = new OnlineSessionFactory();
        return sessionFactory;
    }

   
    @Bean
    public OnlineWebSessionManager sessionManager()
    {
        OnlineWebSessionManager manager = new OnlineWebSessionManager();
       
        manager.setCacheManager(getEhCacheManager());
       
        manager.setDeleteInvalidSessions(true);
       
        manager.setGlobalSessionTimeout(expireTime * 60 * 1000);
        
        manager.setSessionIdUrlRewritingEnabled(false);
      
        manager.setSessionValidationScheduler(SpringUtils.getBean(SpringSessionValidationScheduler.class));
       
        manager.setSessionValidationSchedulerEnabled(true);
        
        manager.setSessionDAO(sessionDAO());
      
        manager.setSessionFactory(sessionFactory());
        return manager;
    }

 
    @Bean
    public SecurityManager securityManager(UserRealm userRealm)
    {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        
        securityManager.setRealm(userRealm);
       
        securityManager.setRememberMeManager(rememberMe ? rememberMeManager() : null);
       
        securityManager.setCacheManager(getEhCacheManager());
      
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

 
    public LogoutFilter logoutFilter()
    {
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setLoginUrl(loginUrl);
        return logoutFilter;
    }

  
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager)
    {
        CustomShiroFilterFactoryBean shiroFilterFactoryBean = new CustomShiroFilterFactoryBean();
      
        shiroFilterFactoryBean.setSecurityManager(securityManager);
     
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
     
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
     
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
      
        filterChainDefinitionMap.put("/favicon.ico**", "anon");
        filterChainDefinitionMap.put("/ebs.png**", "anon");
        filterChainDefinitionMap.put("/html/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/docs/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/ajax/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/ebs/**", "anon");
        filterChainDefinitionMap.put("/assets/**", "anon");
        filterChainDefinitionMap.put("/captcha/captchaImage**", "anon");
        filterChainDefinitionMap.put("/ws/**", "anon");
      
        filterChainDefinitionMap.put("/logout", "logout");
      
        filterChainDefinitionMap.put("/login", "anon,captchaValidate");
      
        filterChainDefinitionMap.put("/register", "anon,captchaValidate");
       
        // filterChainDefinitionMap.putAll(SpringUtils.getBean(IMenuService.class).selectPermsAll());

        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
        filters.put("onlineSession", onlineSessionFilter());
        filters.put("syncOnlineSession", syncOnlineSessionFilter());
        filters.put("captchaValidate", captchaValidateFilter());
        filters.put("kickout", kickoutSessionFilter());
       
        filters.put("logout", logoutFilter());
        shiroFilterFactoryBean.setFilters(filters);

       
        filterChainDefinitionMap.put("/**", "user,kickout,onlineSession,syncOnlineSession");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

   
    public OnlineSessionFilter onlineSessionFilter()
    {
        OnlineSessionFilter onlineSessionFilter = new OnlineSessionFilter();
        onlineSessionFilter.setLoginUrl(loginUrl);
        onlineSessionFilter.setOnlineSessionDAO(sessionDAO());
        return onlineSessionFilter;
    }

  
    public SyncOnlineSessionFilter syncOnlineSessionFilter()
    {
        SyncOnlineSessionFilter syncOnlineSessionFilter = new SyncOnlineSessionFilter();
        syncOnlineSessionFilter.setOnlineSessionDAO(sessionDAO());
        return syncOnlineSessionFilter;
    }

 
    public CaptchaValidateFilter captchaValidateFilter()
    {
        CaptchaValidateFilter captchaValidateFilter = new CaptchaValidateFilter();
        captchaValidateFilter.setCaptchaEnabled(captchaEnabled);
        captchaValidateFilter.setCaptchaType(captchaType);
        return captchaValidateFilter;
    }

    public SimpleCookie rememberMeCookie()
    {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setHttpOnly(httpOnly);
        cookie.setMaxAge(maxAge * 24 * 60 * 60);
        return cookie;
    }

  
    public CookieRememberMeManager rememberMeManager()
    {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        if (StringUtils.isNotEmpty(cipherKey))
        {
            cookieRememberMeManager.setCipherKey(Base64.decode(cipherKey));
        }
        else
        {
            cookieRememberMeManager.setCipherKey(CipherUtils.generateNewKey(128, "AES").getEncoded());
        }
        return cookieRememberMeManager;
    }

   
    public KickoutSessionFilter kickoutSessionFilter()
    {
        KickoutSessionFilter kickoutSessionFilter = new KickoutSessionFilter();
        kickoutSessionFilter.setCacheManager(getEhCacheManager());
        kickoutSessionFilter.setSessionManager(sessionManager());
       
        kickoutSessionFilter.setMaxSession(maxSession);
        
        kickoutSessionFilter.setKickoutAfter(kickoutAfter);
    
        kickoutSessionFilter.setKickoutUrl("/login?kickout=1");
        return kickoutSessionFilter;
    }

    
    @Bean
    public ShiroDialect shiroDialect()
    {
        return new ShiroDialect();
    }

  
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier("securityManager") SecurityManager securityManager)
    {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
