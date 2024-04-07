package sunposition.springdays.cache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class DataCacheTest {

    private DataCache dataCache;

    @Before
    public void setup() {
        dataCache = new DataCache();
    }

    @Test
    public void testPutAndGet() {
        String key = "testKey";
        Object value = new Object();
        dataCache.put(key, value);
        assertEquals(value, dataCache.get(key));
    }

    @Test
    public void testRemove() {
        String key = "testKey";
        Object value = new Object();
        dataCache.put(key, value);
        dataCache.remove(key);
        assertNull(dataCache.get(key));
    }

    @Test
    public void testClear() {
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
    public void testRemoveOldestEntry() {
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
    public void testPutAndGetWithNullValue() {
        String key = "testKey";
        dataCache.put(key, null);
        assertNull(dataCache.get(key));
    }

    @Test
    public void testCacheSizeLimit() {
        for (int i = 0; i < DataCache.MAX_SIZE + 1; i++) {
            dataCache.put("key" + i, new Object());
        }
        assertEquals(DataCache.MAX_SIZE, dataCache.dataCacheMap.size());
    }

    @Test
    public void testSetHashMap() {
        Map<String, Object> newMap = new ConcurrentHashMap<>();
        newMap.put("newKey", new Object());
        dataCache.setHashMap(newMap);
        assertEquals(1, dataCache.dataCacheMap.size());
        assertNull(dataCache.get("testKey"));
        assertNotNull(dataCache.get("newKey"));
    }

    @Test
    public void testRemoveOldestEntryDirectly() {
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
