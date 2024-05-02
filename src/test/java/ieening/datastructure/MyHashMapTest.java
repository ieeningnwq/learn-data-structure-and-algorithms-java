package ieening.datastructure;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.ieening.datastructure.MyHashMap;
import com.ieening.datastructure.MyMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Set;

public class MyHashMapTest {
    private MyMap<String, String> myMap;

    @BeforeEach
    public void setUpEach() {
        myMap = new MyHashMap<>();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"name\":\"Linus Benedict Torvalds\",\"age\":\"55\",\"nationality\":\"The Republic of Finland\"}",
            "{\"f\":\"F\",\"G\":\"g\",\"H\":\"h\",\"I\":\"i\",\"J\":\"j\",\"K\":\"k\",\"L\":\"l\",\"M\":\"m\",\"N\":\"n\",\"O\":\"o\",\"P\":\"p\",\"Q\":\"q\",\"R\":\"r\",\"S\":\"s\",\"T\":\"t\",\"U\":\"u\",\"V\":\"v\",\"W\":\"w\",\"X\":\"x\",\"Y\":\"y\",\"Z\":\"z\",\"AAA\":\"aaa\",\"A\":\"a\",\"B\":\"b\",\"C\":\"c\",\"d\":\"D\",\"e\":\"E\"}" // 触发resize
    })
    public void testPutAndGet(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.keySet()) {
            myMap.put(key, jObject.getString(key));
            assertThat(jObject.getString(key), equalTo(myMap.get(key)));
        }
        assertThat(null, equalTo(myMap.get("myMapNoThisKey")));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"name\":\"Linus Benedict Torvalds\",\"age\":\"55\",\"nationality\":\"The Republic of Finland\"}"
    })
    public void testContainsKeyAndValues(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.keySet()) {
            myMap.put(key, jObject.getString(key));
            assertThat(true, equalTo(myMap.containsKey(key)));
            assertThat(true, equalTo(myMap.containsValue(jObject.getString(key))));
        }
        assertThat(false, equalTo(myMap.containsKey("myMapNoThisKey")));
        assertThat(false, equalTo(myMap.containsValue("myMapNoThisValue")));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"name\":\"Linus Benedict Torvalds\",\"age\":\"55\",\"nationality\":\"The Republic of Finland\"}"
    })
    public void testClear(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.keySet()) {
            myMap.put(key, jObject.getString(key));
        }
        assertThat(false, equalTo(myMap.isEmpty()));
        myMap.clear();
        assertThat(true, equalTo(myMap.isEmpty()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"name\":\"Linus Benedict Torvalds\",\"age\":\"55\",\"nationality\":\"The Republic of Finland\"}"
    })
    public void testRemove(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.keySet()) {
            myMap.put(key, jObject.getString(key));
            assertThat(true, equalTo(myMap.containsKey(key)));
            assertThat(true, equalTo(myMap.containsValue(jObject.getString(key))));
            String removeValue = myMap.remove(key);
            assertThat(false, equalTo(myMap.containsKey(key)));
            assertThat(false, equalTo(myMap.containsValue(removeValue)));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"name\":\"Linus Benedict Torvalds\",\"age\":\"55\",\"nationality\":\"The Republic of Finland\"}"
    })
    public void testKeySet(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.keySet()) {
            myMap.put(key, jObject.getString(key));
        }
        Object[] expectObjects = jObject.keySet().toArray();
        Arrays.sort(expectObjects);
        Object[] actualObjects = myMap.keySet().toArray();
        Arrays.sort(actualObjects);
        assertThat(expectObjects, equalTo(actualObjects));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"name\":\"Linus Benedict Torvalds\",\"age\":\"55\",\"nationality\":\"The Republic of Finland\"}"
    })
    public void testValues(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);

        Object[] expectObjects = new Object[jObject.length()];
        int index = 0;
        for (String key : jObject.keySet()) {
            expectObjects[index++] = jObject.getString(key);
            myMap.put(key, jObject.getString(key));
        }
        Arrays.sort(expectObjects);

        Object[] actualObjects = myMap.values().toArray();
        Arrays.sort(actualObjects);
        assertThat(expectObjects, equalTo(actualObjects));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"name\":\"Linus Benedict Torvalds\",\"age\":\"55\",\"nationality\":\"The Republic of Finland\"}"
    })
    public void testEntrySet(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);

        for (String key : jObject.keySet()) {
            myMap.put(key, jObject.getString(key));
        }
        Set<String> expectKeySet = jObject.keySet();
        for (MyMap.Entry<String, String> entry : myMap.entrySet()) {
            String expectValue = jObject.getString(entry.getKey());
            assertThat(expectValue, equalTo(entry.getValue()));
            expectKeySet.remove(entry.getKey());
        }
        assertThat(true, equalTo(expectKeySet.isEmpty()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"first\":{\"name\":\"Linus Benedict Torvalds\",\"age\":\"55\"},\"second\":{\"name\":\"Linus Benedict Torvalds\",\"nationality\":\"The Republic of Finland\"},\"after\":{\"name\":\"Linus Benedict Torvalds\",\"age\":\"55\",\"nationality\":\"The Republic of Finland\"}}",
    })
    public void testPutAll(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);

        JSONObject firstJsonObject = jObject.getJSONObject("first");
        MyHashMap<String, String> firstMap = new MyHashMap<>();
        for (String key : firstJsonObject.keySet()) {
            firstMap.put(key, firstJsonObject.getString(key));
        }

        JSONObject secondJsonObject = jObject.getJSONObject("second");
        MyHashMap<String, String> secondMap = new MyHashMap<>();
        for (String key : secondJsonObject.keySet()) {
            secondMap.put(key, secondJsonObject.getString(key));
        }

        JSONObject afterJsonObject = jObject.getJSONObject("after");
        MyHashMap<String, String> afterMap = new MyHashMap<>();
        for (String key : afterJsonObject.keySet()) {
            afterMap.put(key, afterJsonObject.getString(key));
        }

        myMap.putAll(firstMap);
        assertThat(firstMap, equalTo(myMap));

        myMap.putAll(secondMap);
        assertThat(afterMap, equalTo(myMap));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{}",
            "{\"name\":\"Linus Benedict Torvalds\",\"age\":\"55\",\"nationality\":\"The Republic of Finland\"}"
    })
    public void testComparisonAndHashing(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);

        for (String key : jObject.keySet()) {
            myMap.put(key, jObject.getString(key));
        }

        MyHashMap<String, String> equalHashMap = new MyHashMap<String, String>();
        for (String key : jObject.keySet()) {
            equalHashMap.put(key, jObject.getString(key));
        }

        assertThat(myMap, equalTo(equalHashMap));
        assertThat(myMap.hashCode(), equalTo(equalHashMap.hashCode()));

        MyHashMap<String, String> differentMap = new MyHashMap<String, String>();
        differentMap.put("differentMapKey", "differentMapKey");
        assertThat(false, equalTo(myMap.equals(differentMap)));
        assertThat(false, equalTo(differentMap.hashCode() == myMap.hashCode()));
    }
}
