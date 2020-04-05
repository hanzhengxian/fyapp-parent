package com.onway.core.service.cache;

public interface CacheManager{

    /**
     * 刷新
     * LocalCacheNameEnum
     * @param cacheName
     */
    void refresh(String cacheName);
}
