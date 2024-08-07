package com.ebs.framework.manager;

import com.ebs.framework.shiro.web.session.SpringSessionValidationScheduler;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PreDestroy;


@Component
public class ShutdownManager
{
    private static final Logger logger = LoggerFactory.getLogger("sys-user");

    @Autowired(required = false)
    private SpringSessionValidationScheduler springSessionValidationScheduler;

    @Autowired(required = false)
    private EhCacheManager ehCacheManager;

    @PreDestroy
    public void destroy()
    {
        shutdownSpringSessionValidationScheduler();
        shutdownAsyncManager();
        shutdownEhCacheManager();
    }


    private void shutdownSpringSessionValidationScheduler()
    {
        if (springSessionValidationScheduler != null && springSessionValidationScheduler.isEnabled())
        {
            try
            {
                logger.info("====セッション検証タスクを閉じる====");
                springSessionValidationScheduler.disableSessionValidation();
            }
            catch (Exception e)
            {
                logger.error(e.getMessage(), e);
            }
        }
    }


    private void shutdownAsyncManager()
    {
        try
        {
            logger.info("====バックグラウンドタスクスレッドプールを閉じる====");
            AsyncManager.me().shutdown();
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }

    private void shutdownEhCacheManager()
    {
        try
        {
            logger.info("====キャッシュを閉じる====");
            if (ehCacheManager != null)
            {
                CacheManager cacheManager = ehCacheManager.getCacheManager();
                cacheManager.shutdown();
            }
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }
}
