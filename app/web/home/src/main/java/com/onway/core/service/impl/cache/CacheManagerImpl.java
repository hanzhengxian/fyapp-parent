package com.onway.core.service.impl.cache;

import java.util.List;

import com.onway.core.service.cache.CacheManager;
import com.onway.platform.common.configration.DirectiveLogger;
import com.onway.platform.common.localcache.dal.dao.CacheManagerDAO;
import com.onway.platform.common.localcache.dal.dataobject.CacheManagerDO;
import com.onway.platform.common.localcache.enums.LocalCacheStatusEnum;
import com.onway.platform.common.utils.LogUtil;

public class CacheManagerImpl implements CacheManager, DirectiveLogger {

    private CacheManagerDAO cacheManagerDAO;

    public void setCacheManagerDAO(CacheManagerDAO cacheManagerDAO) {
        this.cacheManagerDAO = cacheManagerDAO;
    }

    @Override
    public void refresh(String cacheName) {
        LogUtil.info(cachelog, "开始尝试刷新本地缓存对象====" + cacheName);
        List<CacheManagerDO> loadByCacheName = cacheManagerDAO.loadByCacheName(cacheName);
        for (CacheManagerDO cacheManagerDO : loadByCacheName) {
            cacheManagerDAO.updateStatusById(cacheManagerDO.getId(),  LocalCacheStatusEnum.WAIT_FOR_REFRESH.getCode());
        }
        LogUtil.info(cachelog, "结束尝试刷新本地缓存对象====" + cacheName);
    }
}
