package sunposition.springdays.cache;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DataCache {
    Map<String, Object> dataCacheMap = new ConcurrentHashMap<>();
    protected static final int MAX_SIZE = 100;

    public void put(final String key, final Object value) {
        dataCacheMap.put(key, value);
        if (dataCacheMap.size() > MAX_SIZE) {
            removeOldestEntry();
        }
    }

    public Object get(final String key) {
        return dataCacheMap.get(key);
    }

    public void remove(final String key) {
        dataCacheMap.remove(key);
    }

    public void clear() {
        dataCacheMap.clear();
    }

    public void setHashMap(final Map<String, Object> hashMap) {
        this.dataCacheMap = hashMap;
    }

    void removeOldestEntry() {
        if (!dataCacheMap.isEmpty()) {
            String oldestKey = dataCacheMap.keySet().iterator().next();
            dataCacheMap.remove(oldestKey);
        }
    }

}
