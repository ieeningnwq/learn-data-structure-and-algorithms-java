package ieening.datastructure;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.ieening.datastructure.MySingleCycleLinkedList;

public class MySingleCycleLinkedListTest {
    private MySingleCycleLinkedList<Integer> mCycleLinkedList;

    @BeforeEach
    public void setUpEach() {
        mCycleLinkedList = new MySingleCycleLinkedList<>();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"linkedList\":[]}", // 空链表
            "{\"linkedList\":[1,2,3,4,5,5,3,2]}" // 正常
    })
    public void testAddElementSizeIsEmpty(String parameterJsonString) {
        JSONObject jsonObject = new JSONObject(parameterJsonString);
        assertThat(0, equalTo(mCycleLinkedList.size()));
        assertTrue(mCycleLinkedList.isEmpty());
        int size = 0;
        for (Object element : jsonObject.getJSONArray("linkedList").toList()) {
            assertTrue(mCycleLinkedList.add((Integer) element));
            assertThat(++size, equalTo(mCycleLinkedList.size()));
            assertFalse(mCycleLinkedList.isEmpty());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"getIndex\":[-1,0,3,7,8],\"getResult\":[null,1,4,2,null]}" // 正常
    })
    public void testGetIndex(String parameterJsonString) {
        JSONObject jsonObject = new JSONObject(parameterJsonString);
        for (Object element : jsonObject.getJSONArray("linkedList").toList()) {
            assertTrue(mCycleLinkedList.add((Integer) element));
        }

        JSONArray indexes = jsonObject.getJSONArray("getIndex");
        JSONArray results = jsonObject.getJSONArray("getResult");

        for (int i = 0; i < indexes.length(); i++) {
            int index = indexes.getInt(i);
            try {
                int result = results.getInt(i);
                assertEquals(result, mCycleLinkedList.get(index));
            } catch (Exception e) {
                assertThrows(IndexOutOfBoundsException.class, () -> mCycleLinkedList.get(index));
            }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"linkedList\":[],\"insertIndex\":0,\"insertValue\":1,\"getIndex\":0,\"getResult\":1}", // 空链表
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"insertIndex\":-1,\"insertValue\":null,\"getIndex\":0,\"getResult\":1}",
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"insertIndex\":0,\"insertValue\":110,\"getIndex\":0,\"getResult\":110}",
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"insertIndex\":3,\"insertValue\":33,\"getIndex\":3,\"getResult\":33}",
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"insertIndex\":7,\"insertValue\":77,\"getIndex\":7,\"getResult\":77}",
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"insertIndex\":8,\"insertValue\":88,\"getIndex\":8,\"getResult\":88}",
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"insertIndex\":9,\"insertValue\":null,\"getIndex\":7,\"getResult\":2}",
    })
    public void testAddIndexElement(String parameterJsonString) {
        JSONObject jsonObject = new JSONObject(parameterJsonString);
        for (Object element : jsonObject.getJSONArray("linkedList").toList()) {
            assertTrue(mCycleLinkedList.add((Integer) element));
        }

        int insertIndex = jsonObject.getInt("insertIndex");
        int getIndex = jsonObject.getInt("getIndex");
        int getResult = jsonObject.getInt("getResult");

        try {
            int insertValue = jsonObject.getInt("insertValue");
            mCycleLinkedList.add(insertIndex, insertValue);
            assertEquals(getResult, mCycleLinkedList.get(getIndex));
        } catch (Exception e) {
            assertThrows(IndexOutOfBoundsException.class, () -> mCycleLinkedList.add(insertIndex, insertIndex));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"element\":3,\"index\":2}",
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"element\":1,\"index\":0}",
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"element\":2,\"index\":1}",
            "{\"linkedList\":[1,2,3,4,5,5,3,8],\"element\":8,\"index\":7}",
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"element\":0,\"index\":-1}",
    })
    public void testIndexOf(String parameterJsonString) {
        JSONObject jsonObject = new JSONObject(parameterJsonString);
        for (Object element : jsonObject.getJSONArray("linkedList").toList()) {
            assertTrue(mCycleLinkedList.add((Integer) element));
        }
        int element = jsonObject.getInt("element");
        int index = jsonObject.getInt("index");

        assertEquals(index, mCycleLinkedList.indexOf(element));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"removeIndex\":0,\"removeElement\":1,\"throw\":false,\"genIndex\":0,\"getResult\":2}",
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"removeIndex\":7,\"removeElement\":2,\"throw\":false,\"genIndex\":6,\"getResult\":3}",
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"removeIndex\":2,\"removeElement\":3,\"throw\":false,\"genIndex\":2,\"getResult\":4}",
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"removeIndex\":-1,\"throw\":true,\"genIndex\":0,\"getResult\":1}",
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"removeIndex\":8,\"throw\":true,\"genIndex\":7,\"getResult\":2}",
    })
    public void testRemoveIndex(String parameterJsonString) {
        JSONObject jsonObject = new JSONObject(parameterJsonString);
        for (Object element : jsonObject.getJSONArray("linkedList").toList()) {
            assertTrue(mCycleLinkedList.add((Integer) element));
        }
        if (jsonObject.getBoolean("throw"))
            assertThrows(IndexOutOfBoundsException.class,
                    () -> mCycleLinkedList.remove(jsonObject.getInt("removeIndex")));
        else
            assertEquals(jsonObject.getInt("removeElement"), mCycleLinkedList.remove(jsonObject.getInt("removeIndex")));

        int index = jsonObject.getInt("genIndex");
        int result = jsonObject.getInt("getResult");

        assertEquals(result, mCycleLinkedList.get(index));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"removeElement\":1,\"removed\":true,\"genIndex\":0,\"getResult\":2}",
            "{\"linkedList\":[1,2,3,4,5,5,3,2,6],\"removeElement\":6,\"removed\":true,\"genIndex\":7,\"getResult\":2}",
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"removeElement\":2,\"removed\":true,\"genIndex\":1,\"getResult\":3}",
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"removeElement\":6,\"removed\":false,\"genIndex\":0,\"getResult\":1}",
    })
    public void testRemoveObject(String parameterJsonString) {
        JSONObject jsonObject = new JSONObject(parameterJsonString);
        for (Object element : jsonObject.getJSONArray("linkedList").toList()) {
            assertTrue(mCycleLinkedList.add((Integer) element));
        }

        assertEquals(jsonObject.getBoolean("removed"), mCycleLinkedList.remove(jsonObject.get("removeElement")));

        int index = jsonObject.getInt("genIndex");
        int result = jsonObject.getInt("getResult");

        assertEquals(result, mCycleLinkedList.get(index));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"index\":0,\"element\":110,\"throw\":false,\"genIndex\":0,\"getResult\":110}",
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"index\":2,\"element\":110,\"throw\":false,\"genIndex\":2,\"getResult\":110}",
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"index\":7,\"element\":110,\"throw\":false,\"genIndex\":7,\"getResult\":110}",
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"index\":-1,\"element\":110,\"throw\":true,\"genIndex\":0,\"getResult\":1}",
            "{\"linkedList\":[1,2,3,4,5,5,3,2],\"index\":8,\"element\":110,\"throw\":true,\"genIndex\":7,\"getResult\":2}",
    })
    public void testSetIndexElement(String parameterJsonString) {
        JSONObject jsonObject = new JSONObject(parameterJsonString);
        for (Object element : jsonObject.getJSONArray("linkedList").toList()) {
            assertTrue(mCycleLinkedList.add((Integer) element));
        }
        if (jsonObject.getBoolean("throw"))
            assertThrows(IndexOutOfBoundsException.class,
                    () -> mCycleLinkedList.set(jsonObject.getInt("index"), jsonObject.getInt("element")));
        else {
            assertEquals(mCycleLinkedList.get(jsonObject.getInt("index")),
                    mCycleLinkedList.set(jsonObject.getInt("index"), jsonObject.getInt("element")));
        }

        int index = jsonObject.getInt("genIndex");
        int result = jsonObject.getInt("getResult");

        assertEquals(result, mCycleLinkedList.get(index));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"linkedList\":[1,2,3,4,5,5,3,2]}",
    })
    public void testClear(String parameterJsonString) {
        JSONObject jsonObject = new JSONObject(parameterJsonString);
        for (Object element : jsonObject.getJSONArray("linkedList").toList()) {
            assertTrue(mCycleLinkedList.add((Integer) element));
        }

        mCycleLinkedList.clear();
        assertTrue(mCycleLinkedList.isEmpty());
        assertEquals(0, mCycleLinkedList.size());
    }
}
