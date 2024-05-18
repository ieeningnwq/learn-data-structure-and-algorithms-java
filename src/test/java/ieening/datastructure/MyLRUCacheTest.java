package ieening.datastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.ieening.datastructure.MyLRUCache;

public class MyLRUCacheTest {
    private MyLRUCache<Integer, Integer> myLRUCache;

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"operations\":[\"new\",\"put\",\"put\",\"get\",\"put\",\"get\",\"put\",\"get\",\"get\",\"get\"],\"parameters\":[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]],\"output\":[null, null, null, 1, null, null, null, null, 3, 4]}"
    })
    public void testPutAndGet(String parameterJsonString) {
        JSONObject jsonObj = new JSONObject(parameterJsonString);
        JSONArray operationsJsonArray = jsonObj.getJSONArray("operations");
        JSONArray parametersJsonArray = jsonObj.getJSONArray("parameters");
        JSONArray outputJsonArray = jsonObj.getJSONArray("output");
        for (int i = 0; i < operationsJsonArray.length(); i++) {
            String operation = operationsJsonArray.getString(i);
            JSONArray parameterJsonArray = parametersJsonArray.getJSONArray(i);
            Object expected = outputJsonArray.get(i);
            Object actual = null;
            if ("new".equals(operation)) {
                myLRUCache = new MyLRUCache<>(parameterJsonArray.getInt(0));
            } else if ("put".equals(operation)) {
                myLRUCache.put(parameterJsonArray.getInt(0), parameterJsonArray.getInt(1));
            } else if ("get".equals(operation)) {
                actual = myLRUCache.get(parameterJsonArray.getInt(0));
            }
            assertEquals(expected, actual);
        }
    }
}
