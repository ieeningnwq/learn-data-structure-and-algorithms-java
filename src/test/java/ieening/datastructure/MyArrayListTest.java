package ieening.datastructure;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.ieening.datastructure.MyArrayList;

public class MyArrayListTest {
    private MyArrayList<Integer> myArrayList;

    @BeforeEach
    public void setUpEach() {
        myArrayList = new MyArrayList<>(3);
    }

    @ParameterizedTest
    @DisplayName("测试列表 add(Object)")
    @ValueSource(ints = { 1, 2, 3, 4, 5 })
    public void testAdd(int addedInteger) {
        int beforeSize = myArrayList.size();
        assertThat(true, equalTo(myArrayList.add(addedInteger)));
        assertThat(beforeSize + 1, equalTo(myArrayList.size()));
    }

    @ParameterizedTest
    @DisplayName("测试列表 remove(int)")
    @ValueSource(ints = { -1, 2, 4 })
    public void testRemoveIndex(int index) {
        myArrayList.add(1);
        myArrayList.add(2);
        myArrayList.add(3);
        if (index < myArrayList.size() && index >= 0) {
            Integer e = myArrayList.get(index);
            assertThat(e, equalTo(myArrayList.remove(index)));
        } else {
            assertThrows(IndexOutOfBoundsException.class, () -> {
                myArrayList.remove(index);
            });
        }
    }

    @ParameterizedTest
    @DisplayName("测试列表 remove(Object)")
    @ValueSource(ints = { -1, 2 })
    public void testRemoveObject(Object value) {
        myArrayList.add(1);
        myArrayList.add(2);
        myArrayList.add(3);
        assertThat(myArrayList.contains(value), equalTo(myArrayList.remove(value)));
    }

    @ParameterizedTest
    @DisplayName("测试列表 indexOf(Object)")
    @ValueSource(ints = { -1, 2 })
    public void testIndexOfObject(Object value) {
        myArrayList.add(1);
        myArrayList.add(2);
        myArrayList.add(3);
        int actual = -1;
        if (value.equals(2)) {
            actual = 1;
        }
        assertThat(actual, equalTo(myArrayList.indexOf(value)));
    }

    @ParameterizedTest
    @DisplayName("测试列表 lastIndexOf(Object)")
    @ValueSource(ints = { -1, 3 })
    public void testLastIndexOfObject(Object value) {
        myArrayList.add(1);
        myArrayList.add(2);
        myArrayList.add(3);
        int actual = -1;
        if (value.equals(3)) {
            actual = 2;
        }
        assertThat(actual, equalTo(myArrayList.lastIndexOf(value)));
    }

    @Test
    public void testGetAndSet() {
        myArrayList.add(1);
        myArrayList.add(2);
        myArrayList.add(3);

        int element = 5;
        int index = 1;
        int oldValue = myArrayList.get(index);
        assertThat(oldValue, equalTo(myArrayList.set(index, element)));
        assertThat(element, equalTo(myArrayList.get(index)));
    }

    @ParameterizedTest
    @CsvSource(value = { "1,3", "0,4", "-1,5", "6,5" })
    public void testAddIntE(int index, int value) {
        myArrayList.add(1);
        myArrayList.add(2);
        myArrayList.add(3);
        if (index <= myArrayList.size() && index >= 0) {
            int beforeSize = myArrayList.size();
            myArrayList.add(index, value);
            assertThat(beforeSize + 1, equalTo(myArrayList.size()));
            assertThat(value, equalTo(myArrayList.get(index)));
        } else {
            assertThrows(IndexOutOfBoundsException.class, () -> {
                myArrayList.add(index, value);
            });
        }
    }

    @Test
    public void testIterator() {
        int[] ints = { 1, 2, 3 };
        for (int i : ints) {
            myArrayList.add(i);
        }
        int[] intsArray = new int[myArrayList.size()];
        int index = 0;
        for (Integer integer : myArrayList) {
            intsArray[index++] = integer;
        }
        assertThat(ints, equalTo(intsArray));
    }

    @Test
    public void testToArray() {
        int[] ints = { 1, 2, 3 };
        for (int i : ints) {
            myArrayList.add(i);
        }
        assertThat(ints, equalTo(myArrayList.toArray()));
        assertThat(Object[].class, equalTo(myArrayList.toArray().getClass()));
    }

    @Test
    public void testToArrayT() {
        Integer[] ints = { 1, 2, 3 };
        for (int i : ints) {
            myArrayList.add(i);
        }
        assertThat(ints, equalTo(myArrayList.toArray(new Integer[myArrayList.size()])));
        assertThat(ints.getClass(), equalTo(myArrayList.toArray(new Integer[myArrayList.size()]).getClass()));
    }

    @Test
    public void testAddAllMyList() {
        Integer[] ints = { 1, 2, 3 };
        Integer[] added = { 4, 5, 0, -1 };
        Integer[] expected = new Integer[ints.length + added.length];
        System.arraycopy(ints, 0, expected, 0, ints.length);
        System.arraycopy(added, 0, expected, ints.length, added.length);

        for (Integer integer : ints) {
            myArrayList.add(integer);
        }
        MyArrayList<Integer> addedMyArrayList = new MyArrayList<>();
        for (Integer integer : added) {
            addedMyArrayList.add(integer);
        }
        myArrayList.addAll(addedMyArrayList);
        assertThat(expected, equalTo(myArrayList.toArray()));
    }

    @Test
    public void testRemoveAll() {
        Integer[] ints = { 34, 12, 1, 2, 3, 129, 121 };
        HashSet<Integer> hSet = new HashSet<>(Arrays.asList(ints));

        for (int i : ints) {
            myArrayList.add(i);
        }

        Integer[] reomveElements = { 10, 5, 1, 6, 3, 4 };
        HashSet<Integer> rSet = new HashSet<>(Arrays.asList(reomveElements));

        MyArrayList<Integer> l = new MyArrayList<Integer>();

        for (Integer integer : reomveElements) {
            l.add(integer);
        }
        assertThat(true, equalTo(myArrayList.removeAll(l)));

        HashSet<Integer> result = new HashSet<>(hSet);
        result.retainAll(rSet);

        hSet.removeAll(result);
        Object[] expectArray = hSet.toArray();
        Arrays.sort(expectArray);
        Object[] actualArray = myArrayList.toArray();
        Arrays.sort(actualArray);
        assertThat(expectArray, equalTo(actualArray));
    }

    @ParameterizedTest
    @ValueSource(strings = { "1,1,2,3", "0,1,2,4" })
    public void testContainsAll(String value) {
        Integer[] ints = { 34, 12, 1, 2, 3, 129, 121 };

        for (int i : ints) {
            myArrayList.add(i);
        }

        MyArrayList<Integer> containsArrayList = new MyArrayList<>();
        int[] valueArray = Arrays.stream(value.split(",")).mapToInt(Integer::parseInt).toArray();
        boolean flag = valueArray[0] == 1;
        for (int i = 1; i < valueArray.length; i++) {
            containsArrayList.add(valueArray[i]);
        }
        assertThat(flag, equalTo(myArrayList.containsAll(containsArrayList)));
    }

    @ParameterizedTest
    @ValueSource(strings = { "1,1,2,3", "0,2,3,4,5,6" })
    public void testEquals(String value) {
        Integer[] ints = { 1, 2, 3 };

        for (int i : ints) {
            myArrayList.add(i);
        }

        MyArrayList<Integer> equalsArrayList = new MyArrayList<>();
        int[] valueArray = Arrays.stream(value.split(",")).mapToInt(Integer::parseInt).toArray();
        boolean flag = valueArray[0] == 1;
        for (int i = 1; i < valueArray.length; i++) {
            equalsArrayList.add(valueArray[i]);
        }
        assertThat(flag, equalTo(myArrayList.equals(equalsArrayList)));
    }

    @ParameterizedTest
    @ValueSource(strings = { "1,1,2,3", "0,2,3,4,5,6" })
    public void testHashCode(String value) {
        Integer[] ints = { 1, 2, 3 };

        for (int i : ints) {
            myArrayList.add(i);
        }

        MyArrayList<Integer> equalsArrayList = new MyArrayList<>();
        int[] valueArray = Arrays.stream(value.split(",")).mapToInt(Integer::parseInt).toArray();
        boolean flag = valueArray[0] == 1;
        for (int i = 1; i < valueArray.length; i++) {
            equalsArrayList.add(valueArray[i]);
        }
        assertThat(flag, equalTo(myArrayList.hashCode() == equalsArrayList.hashCode()));
    }

    @ParameterizedTest
    @ValueSource(strings = { "1|1|2|3|MyArrayList [elementData=[1, 2, 3], size=3]",
            "0|2|3|4|5|6|MyArrayList [elementData=[1, 2, 3], size=3]" })
    public void testToString(String value) {
        MyArrayList<Integer> equalsArrayList = new MyArrayList<>();
        String[] splitArray = value.split("\\|");
        int[] valueArray = Arrays.stream(Arrays.copyOf(splitArray, splitArray.length - 1)).mapToInt(Integer::parseInt)
                .toArray();
        boolean flag = valueArray[0] == 1;
        String result = splitArray[splitArray.length - 1];
        for (int i = 1; i < valueArray.length; i++) {
            equalsArrayList.add(valueArray[i]);
        }
        assertThat(flag, equalTo(result.equals(equalsArrayList.toString())));
    }

}
