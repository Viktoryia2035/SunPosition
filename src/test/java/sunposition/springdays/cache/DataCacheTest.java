package sunposition.springdays.cache;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
class DataCacheTest {

    private DataCache dataCache;

    @BeforeEach
    void setup() {
        dataCache = new DataCache();
    }

    @Test
    void putAndGet() {
        String key = "testKey";
        Object value = new Object();
        dataCache.put(key, value);
        assertEquals(value, dataCache.get(key));
    }

    @Test
    void remove() {
        String key = "testKey";
        Object value = new Object();
        dataCache.put(key, value);
        dataCache.remove(key);
        assertNull(dataCache.get(key));
    }

    @Test
    void putTwiceAndGet() {
        dataCache.put("key1", "value1");
        dataCache.put("key1", "value2");
        assertEquals("value2", dataCache.get("key1"));
    }

    @Test
    void clear() {
        String key1 = "testKey1";
        String key2 = "testKey2";
        Object value1 = new Object();
        Object value2 = new Object();
        dataCache.put(key1, value1);
        dataCache.put(key2, value2);
        dataCache.clear();
        assertNull(dataCache.get(key1));
        assertNull(dataCache.get(key2));
    }

    @Test
    void clearWhenSizeExceeds() throws NoSuchFieldException, IllegalAccessException {
        for (int i = 0; i < DataCache.MAX_SIZE + 1; i++) {
            dataCache.put("key" + i, new Object());
        }

        Field dataCacheMapField = DataCache.class.getDeclaredField("dataCacheMap");
        dataCacheMapField.setAccessible(true);
        Map<String, Object> dataCacheMap = dataCache.getDataCacheMap();
        assertEquals(DataCache.MAX_SIZE, dataCacheMap.size());
    }

    @Test
    void removeOldestEntry() {
        String key1 = "key1";
        String key2 = "key2";
        String key3 = "key3";
        Object value1 = new Object();
        Object value2 = new Object();
        Object value3 = new Object();
        dataCache.put(key1, value1);
        dataCache.put(key2, value2);
        dataCache.put(key3, value3);
        dataCache.removeOldestEntry();
        assertNull(dataCache.get(key1));
    }

    @Test
    void putAndGetWithNullValue() {
        String key = "testKey";
        dataCache.put(key, null);
        assertNull(dataCache.get(key));
    }

    @Test
    void cacheSizeLimit() {
        for (int i = 0; i < DataCache.MAX_SIZE + 1; i++) {
            dataCache.put("key" + i, new Object());
        }

        Map<String, Object> dataCacheMap = dataCache.getDataCacheMap();
        assertEquals(DataCache.MAX_SIZE, dataCacheMap.size());
    }

    @Test
    void setHashMap() {
        Map<String, Object> newMap = new ConcurrentHashMap<>();
        newMap.put("newKey", new Object());

        dataCache.setHashMap(newMap);

        Map<String, Object> dataCacheMap = dataCache.getDataCacheMap();
        assertEquals(1, dataCacheMap.size());
        assertNull(dataCache.get("testKey"));
        assertNotNull(dataCache.get("newKey"));
    }

    @Test
    void removeOldestEntryDirectly() {
        String key1 = "key1";
        String key2 = "key2";
        Object value1 = new Object();
        Object value2 = new Object();
        dataCache.put(key1, value1);
        dataCache.put(key2, value2);
        dataCache.removeOldestEntry();
        assertNull(dataCache.get(key1));
        assertNotNull(dataCache.get(key2));
    }
}
