package ieening.datastructure;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.ieening.datastructure.MyHashSet;
import com.ieening.datastructure.MySet;

public class MyHashSetTest {
    private MySet<String> mSet;

    @BeforeEach
    public void setUpEach() {
        mSet = new MyHashSet<>();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[\"name\",\"age\",\"nationality\"]",
            "[\"f\",\"F\",\"G\",\"g\",\"H\",\"h\",\"I\",\"i\",\"J\",\"j\",\"K\",\"k\",\"L\",\"l\",\"M\",\"m\",\"N\",\"n\",\"O\",\"o\",\"P\",\"p\",\"Q\",\"q\",\"R\",\"r\",\"S\",\"s\",\"T\",\"t\",\"U\",\"u\",\"V\",\"v\",\"W\",\"w\",\"X\",\"x\",\"Y\",\"y\",\"Z\",\"z\",\"AAA\",\"aaa\",\"A\",\"a\",\"B\",\"b\",\"C\",\"c\",\"d\",\"D\",\"e\",\"E\"]" // 触发resize
    })
    public void testAddContainsSizeIsEmpty(String parameterJsonString) {
        assertThat(true, equalTo(mSet.isEmpty()));
        assertThat(0, equalTo(mSet.size()));

        JSONArray jsonArray = new JSONArray(parameterJsonString);
        int size = 0;
        for (Object key : jsonArray) {
            assertThat(false, equalTo(mSet.contains(key)));
            mSet.add((String) key);
            assertThat(++size, equalTo(mSet.size()));
            assertThat(true, equalTo(mSet.contains(key)));
        }
        assertThat(false, equalTo(mSet.isEmpty()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"mySet\":[\"name\",\"age\",\"nationality\"],\"remove\":[\"name\",\"grade\"],\"result\":[true,false]}",
            "{\"mySet\":[],\"remove\":[\"name\"],\"result\":[false]}",
    })
    public void testRemove(String parameterJsonString) {
        JSONObject jsonObject = new JSONObject(parameterJsonString);
        for (Object value : jsonObject.getJSONArray("mySet")) {
            mSet.add((String) value);
        }

        for (int i = 0; i < jsonObject.getJSONArray("remove").length(); i++) {
            assertThat(jsonObject.getJSONArray("result").getBoolean(i),
                    equalTo(mSet.remove(jsonObject.getJSONArray("remove").getString(i))));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"mySet\":[\"name\",\"age\",\"nationality\"],\"other\":[\"name\",\"grade\"],\"result\":false}",
            "{\"mySet\":[\"name\",\"age\",\"nationality\"],\"other\":[\"name\",\"age\",\"nationality\"],\"result\":true}",
            "{\"mySet\":[\"name\",\"age\",\"nationality\"],\"other\":[\"name\",\"age\"],\"result\":true}",

    })
    public void testContainsAll(String parameterJsonString) {
        JSONObject jsonObject = new JSONObject(parameterJsonString);
        for (Object value : jsonObject.getJSONArray("mySet")) {
            mSet.add((String) value);
        }

        MySet<String> otherSet = new MyHashSet<>();
        for (Object value : jsonObject.getJSONArray("other")) {
            otherSet.add((String) value);
        }

        assertThat(jsonObject.getBoolean("result"), equalTo(mSet.containsAll(otherSet)));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"mySet\":[\"name\",\"age\",\"nationality\"],\"other\":[\"name\",\"grade\"],\"result\":[\"name\",\"age\",\"nationality\",\"grade\"]}",
            "{\"mySet\":[],\"other\":[\"name\",\"age\",\"nationality\"],\"result\":[\"name\",\"age\",\"nationality\"]}",

    })
    public void testAddAll(String parameterJsonString) {
        JSONObject jsonObject = new JSONObject(parameterJsonString);
        for (Object value : jsonObject.getJSONArray("mySet")) {
            mSet.add((String) value);
        }

        MySet<String> otherSet = new MyHashSet<>();
        for (Object value : jsonObject.getJSONArray("other")) {
            otherSet.add((String) value);
        }

        MySet<String> resultSet = new MyHashSet<>();
        for (Object value : jsonObject.getJSONArray("result")) {
            resultSet.add((String) value);
        }
        mSet.addAll(otherSet);
        assertThat(resultSet, equalTo(mSet));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"mySet\":[\"name\",\"age\",\"nationality\"],\"other\":[\"name\",\"grade\"],\"result\":[\"age\",\"nationality\"]}",
            "{\"mySet\":[\"name\",\"age\",\"nationality\"],\"other\":[\"id\",\"grade\",\"score\"],\"result\":[\"name\",\"age\",\"nationality\"]}",
    })
    public void testRemoveAll(String parameterJsonString) {
        JSONObject jsonObject = new JSONObject(parameterJsonString);
        for (Object value : jsonObject.getJSONArray("mySet")) {
            mSet.add((String) value);
        }

        MySet<String> otherSet = new MyHashSet<>();
        for (Object value : jsonObject.getJSONArray("other")) {
            otherSet.add((String) value);
        }

        MySet<String> resultSet = new MyHashSet<>();
        for (Object value : jsonObject.getJSONArray("result")) {
            resultSet.add((String) value);
        }
        mSet.removeAll(otherSet);
        assertThat(resultSet, equalTo(mSet));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"mySet\":[\"name\",\"grade\"],\"other\":[\"name\",\"age\",\"nationality\"],\"result\":[\"name\"]}",
            "{\"mySet\":[\"name\",\"age\",\"nationality\"],\"other\":[\"id\",\"grade\",\"score\"],\"result\":[]}",
    })
    public void testRetainAll(String parameterJsonString) {
        JSONObject jsonObject = new JSONObject(parameterJsonString);
        for (Object value : jsonObject.getJSONArray("mySet")) {
            mSet.add((String) value);
        }

        MySet<String> otherSet = new MyHashSet<>();
        for (Object value : jsonObject.getJSONArray("other")) {
            otherSet.add((String) value);
        }

        MySet<String> resultSet = new MyHashSet<>();
        for (Object value : jsonObject.getJSONArray("result")) {
            resultSet.add((String) value);
        }
        mSet.retainAll(otherSet);
        assertThat(resultSet, equalTo(mSet));
    }
}
