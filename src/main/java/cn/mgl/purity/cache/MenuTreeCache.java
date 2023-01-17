package cn.mgl.purity.cache;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 菜单缓存
 */
@Component
public class MenuTreeCache {
    private final Map<String, Object> cache = new ConcurrentHashMap<>();

    public Object getCache(String key) {
        return cache.get(key);
    }

    public void setCache(String key, Object value) {
        cache.put(key, value);
    }
}
