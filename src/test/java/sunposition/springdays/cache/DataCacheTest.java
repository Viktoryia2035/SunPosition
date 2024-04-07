package sunposition.springdays.cache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class DataCacheTest {

    @Mock
    private Map<String, Object> hashMap;

    @InjectMocks
    private DataCache dataCache;

    @Before
    public void setup() {
        Map<String, Object> realHashMap = new HashMap<>();
        hashMap = Mockito.spy(realHashMap);
        dataCache.setHashMap(hashMap);
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
}
