package ieening.datastructure;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.HashSet;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.ieening.datastructure.MyArrayList;
import com.ieening.datastructure.MyLinkedList;

public class MyLinkedListTest {
    private MyLinkedList<Integer> myLinkedList;

    @BeforeEach
    public void setUpEach() {
        myLinkedList = new MyLinkedList<>();
    }

    @ParameterizedTest
    @DisplayName("测试列表 add(Object)")
    @ValueSource(ints = { 1, 2, 3, 4, 5 })
    public void testAdd(int addedInteger) {
        int beforeSize = myLinkedList.size();
        assertThat(true, equalTo(myLinkedList.add(addedInteger)));
        assertThat(beforeSize + 1, equalTo(myLinkedList.size()));
    }

    @ParameterizedTest
    @DisplayName("测试列表 remove(int)")
    @ValueSource(strings = {
            "{\"oldValue\":1,\"index\":0,\"before\":[1],\"head\":null,\"tail\":null,\"after\":[]}}", // 只有一个元素
            "{\"oldValue\":1,\"index\":0,\"before\":[1,2,3],\"head\":2,\"tail\":3,\"after\":[2,3]}", // 多个元素，删除头节点
            "{\"oldValue\":3,\"index\":2,\"before\":[1,2,3],\"head\":1,\"tail\":2,\"after\":[1,2]}", // 多个元素，删除尾节点
            "{\"oldValue\":3,\"index\":2,\"before\":[1,2,3,4],\"head\":1,\"tail\":4,\"after\":[1,2,4]}", // 多个元素，删除中间节点
    })
    public void testRemoveIndex(String parameterJsonString) {
        JSONObject jsonObj = new JSONObject(parameterJsonString);
        int oldValue = jsonObj.getInt("oldValue");
        int index = jsonObj.getInt("index");
        int[] before = jsonObj.getJSONArray("before").toList().stream().mapToInt(x -> (int) x).toArray();
        Object head = jsonObj.get("head");
        Object tail = jsonObj.get("tail");
        int[] after = jsonObj.getJSONArray("after").toList().stream().mapToInt(x -> (int) x).toArray();

        for (int i : before) {
            myLinkedList.add(i);
        }
        if (index < myLinkedList.size() && index >= 0) {
            assertThat(oldValue, equalTo(myLinkedList.remove(index)));
            if (head == JSONObject.NULL && tail == JSONObject.NULL) {
                assertThat(0, equalTo(myLinkedList.size()));
            } else {
                assertThat(tail, equalTo(myLinkedList.get(myLinkedList.size() - 1)));
                assertThat(head, equalTo(myLinkedList.get(0)));
            }
            assertThat(after, equalTo(myLinkedList.toArray()));
        } else {
            assertThrows(IndexOutOfBoundsException.class, () -> {
                myLinkedList.remove(index);
            });
        }
    }

    @ParameterizedTest
    @DisplayName("测试列表 remove(Object)")
    @ValueSource(ints = { -1, 2 })
    public void testRemoveObject(Object value) {
        myLinkedList.add(1);
        myLinkedList.add(2);
        myLinkedList.add(3);
        int beforeSize = myLinkedList.size();
        boolean contains = myLinkedList.contains(value);
        assertThat(contains, equalTo(myLinkedList.remove(value)));
        int afterSize = myLinkedList.size();
        if (contains) {
            assertThat(beforeSize-1, equalTo(afterSize));
        }else{
            assertThat(beforeSize, equalTo(afterSize));
        }
        
    }

    @ParameterizedTest
    @DisplayName("测试列表 indexOf(Object)")
    @ValueSource(ints = { -1, 2 })
    public void testIndexOfObject(Object value) {
        myLinkedList.add(1);
        myLinkedList.add(2);
        myLinkedList.add(3);
        int actual = -1;
        if (value.equals(2)) {
            actual = 1;
        }
        assertThat(actual, equalTo(myLinkedList.indexOf(value)));
    }

    @ParameterizedTest
    @DisplayName("测试列表 lastIndexOf(Object)")
    @ValueSource(ints = { -1, 3 })
    public void testLastIndexOfObject(Object value) {
        myLinkedList.add(1);
        myLinkedList.add(2);
        myLinkedList.add(3);
        int actual = -1;
        if (value.equals(3)) {
            actual = 2;
        }
        assertThat(actual, equalTo(myLinkedList.lastIndexOf(value)));
    }

    @Test
    @DisplayName("测试列表 testGetAndSet")
    public void testGetAndSet() {
        myLinkedList.add(1);
        myLinkedList.add(2);
        myLinkedList.add(3);

        int element = 5;
        int index = 1;
        int oldValue = myLinkedList.get(index);
        assertThat(oldValue, equalTo(myLinkedList.set(index, element)));
        assertThat(element, equalTo(myLinkedList.get(index)));
    }

    @ParameterizedTest
    @CsvSource(value = { "1,3", "0,4", "-1,5", "6,5" })
    public void testAddIntE(int index, int value) {
        myLinkedList.add(1);
        myLinkedList.add(2);
        myLinkedList.add(3);
        if (index <= myLinkedList.size() && index >= 0) {
            int beforeSize = myLinkedList.size();
            myLinkedList.add(index, value);
            assertThat(beforeSize + 1, equalTo(myLinkedList.size()));
            assertThat(value, equalTo(myLinkedList.get(index)));
        } else {
            assertThrows(IndexOutOfBoundsException.class, () -> {
                myLinkedList.add(index, value);
            });
        }
    }

    @Test
    public void testIterator() {
        int[] ints = { 1, 2, 3 };
        for (int i : ints) {
            myLinkedList.add(i);
        }
        int[] intsArray = new int[myLinkedList.size()];
        int index = 0;
        for (Integer integer : myLinkedList) {
            intsArray[index++] = integer;
        }
        assertThat(ints, equalTo(intsArray));
    }

    @Test
    public void testToArray() {
        int[] ints = { 1, 2, 3 };
        for (int i : ints) {
            myLinkedList.add(i);
        }
        assertThat(ints, equalTo(myLinkedList.toArray()));
        assertThat(Object[].class, equalTo(myLinkedList.toArray().getClass()));
    }

    @Test
    public void testToArrayT() {
        Integer[] ints = { 1, 2, 3 };
        for (int i : ints) {
            myLinkedList.add(i);
        }
        assertThat(ints, equalTo(myLinkedList.toArray(new Integer[myLinkedList.size()])));
        assertThat(ints.getClass(), equalTo(myLinkedList.toArray(new Integer[myLinkedList.size()]).getClass()));
    }

    @Test
    public void testAddAllMyList() {
        Integer[] ints = { 1, 2, 3 };
        Integer[] added = { 4, 5, 0, -1 };
        Integer[] expected = new Integer[ints.length + added.length];
        System.arraycopy(ints, 0, expected, 0, ints.length);
        System.arraycopy(added, 0, expected, ints.length, added.length);

        for (Integer integer : ints) {
            myLinkedList.add(integer);
        }
        MyArrayList<Integer> addedMyArrayList = new MyArrayList<>();
        for (Integer integer : added) {
            addedMyArrayList.add(integer);
        }
        myLinkedList.addAll(addedMyArrayList);
        assertThat(expected, equalTo(myLinkedList.toArray()));
    }

    @Test
    public void testRemoveAll() {
        Integer[] ints = { 34, 12, 1, 2, 3, 129, 121 };
        HashSet<Integer> hSet = new HashSet<>(Arrays.asList(ints));

        for (int i : ints) {
            myLinkedList.add(i);
        }

        Integer[] reomveElements = { 10, 5, 1, 6, 3, 4 };
        HashSet<Integer> rSet = new HashSet<>(Arrays.asList(reomveElements));

        MyArrayList<Integer> l = new MyArrayList<Integer>();

        for (Integer integer : reomveElements) {
            l.add(integer);
        }
        assertThat(true, equalTo(myLinkedList.removeAll(l)));

        HashSet<Integer> result = new HashSet<>(hSet);
        result.retainAll(rSet);

        hSet.removeAll(result);
        Object[] expectArray = hSet.toArray();
        Arrays.sort(expectArray);
        Object[] actualArray = myLinkedList.toArray();
        Arrays.sort(actualArray);
        assertThat(expectArray, equalTo(actualArray));
    }

    @ParameterizedTest
    @ValueSource(strings = { "1,1,2,3", "0,1,2,4" })
    public void testContainsAll(String value) {
        Integer[] ints = { 34, 12, 1, 2, 3, 129, 121 };

        for (int i : ints) {
            myLinkedList.add(i);
        }

        MyArrayList<Integer> containsArrayList = new MyArrayList<>();
        int[] valueArray = Arrays.stream(value.split(",")).mapToInt(Integer::parseInt).toArray();
        boolean flag = valueArray[0] == 1;
        for (int i = 1; i < valueArray.length; i++) {
            containsArrayList.add(valueArray[i]);
        }
        assertThat(flag, equalTo(myLinkedList.containsAll(containsArrayList)));
    }

    @ParameterizedTest
    @ValueSource(strings = { "1,1,2,3", "0,2,3,4,5,6" })
    public void testEquals(String value) {
        Integer[] ints = { 1, 2, 3 };

        for (int i : ints) {
            myLinkedList.add(i);
        }

        MyLinkedList<Integer> equalsArrayList = new MyLinkedList<>();
        int[] valueArray = Arrays.stream(value.split(",")).mapToInt(Integer::parseInt).toArray();
        boolean flag = valueArray[0] == 1;
        for (int i = 1; i < valueArray.length; i++) {
            equalsArrayList.add(valueArray[i]);
        }
        assertThat(flag, equalTo(myLinkedList.equals(equalsArrayList)));
    }

    @ParameterizedTest
    @ValueSource(strings = { "1,1,2,3", "0,2,3,4,5,6" })
    public void testHashCode(String value) {
        Integer[] ints = { 1, 2, 3 };

        for (int i : ints) {
            myLinkedList.add(i);
        }

        MyLinkedList<Integer> equalsArrayList = new MyLinkedList<>();
        int[] valueArray = Arrays.stream(value.split(",")).mapToInt(Integer::parseInt).toArray();
        boolean flag = valueArray[0] == 1;
        for (int i = 1; i < valueArray.length; i++) {
            equalsArrayList.add(valueArray[i]);
        }
        assertThat(flag, equalTo(myLinkedList.hashCode() == equalsArrayList.hashCode()));
    }

    @ParameterizedTest
    @ValueSource(strings = { "1|1|2|3|MyLinkedList [[1, 2, 3], size=3]",
            "0|2|3|4|5|6|MyArrayList [elementData=[1, 2, 3], size=3]" })
    public void testToString(String value) {
        String[] splitArray = value.split("\\|");
        int[] valueArray = Arrays.stream(Arrays.copyOf(splitArray, splitArray.length - 1)).mapToInt(Integer::parseInt)
                .toArray();
        boolean flag = valueArray[0] == 1;
        String result = splitArray[splitArray.length - 1];
        for (int i = 1; i < valueArray.length; i++) {
            myLinkedList.add(valueArray[i]);
        }
        assertThat(flag, equalTo(result.equals(myLinkedList.toString())));
    }

}
