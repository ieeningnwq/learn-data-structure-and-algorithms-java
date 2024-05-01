package ieening;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import com.ieening.datastructure.MyArray;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItemInArray;

public class MyArrayTest {
    private Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 6, 9, 8, 7 };
    private MyArray<Integer> myArray;

    @BeforeEach
    public void setUpEach() {
        myArray = new MyArray<>(ints);
    }

    @Test
    @DisplayName("测试非空 MyArray randomAccess 方法")
    @RepeatedTest(value = 3, name = "{displayName} - repetition {currentRepetition}/{totalRepetitions}")
    public void testRandomAccessWithNotEmpty() {
        Integer randomAccess = myArray.randomAccess();
        assertThat(ints, hasItemInArray(randomAccess));
    }

    @Test
    @DisplayName("测试空 MyArray randomAccess 方法")
    public void testRandomAccessWithEmpty() {
        MyArray<Integer> emptyMyArray = new MyArray<>();
        RuntimeException assertThrowsException = assertThrows(RuntimeException.class, () -> {
            emptyMyArray.randomAccess();
        });
        assertThat("MyArray is empty", equalTo(assertThrowsException.getMessage()));
    }

    @Test
    @DisplayName("测试添加元素 add 方法")
    public void testAddLegal() {
        MyArray<Integer> anArray = new MyArray<>(4);
        int beforeSize = anArray.getSize();
        int element = 12, index = 0;
        anArray.add(element, index);
        assertThat(beforeSize + 1, is(equalTo(anArray.getSize())));
        assertThat(element, equalTo(anArray.get(index)));
    }

    @Test
    @DisplayName("测试删除元素 remove 方法")
    public void testRemoveLegal() {
        int beforeSize = myArray.getSize();
        int index = 3;
        assertThat(ints[index], equalTo(myArray.remove(index)));
        assertThat(beforeSize - 1, equalTo(myArray.getSize()));

    }

    @Test
    @DisplayName("测试替换元素 set 方法")
    public void testReplaceLegal() {
        int beforeSize = myArray.getSize();
        int element = 10, index = 3;
        int ingValue = myArray.get(index);
        Integer oldValuInteger = myArray.set(index, element);
        assertThat(ingValue, equalTo(oldValuInteger));
        assertThat(beforeSize, equalTo(myArray.getSize()));
    }

    @Test
    @DisplayName("测试迭代")
    public void testIterator() {
        Integer[] integers = new Integer[myArray.getSize()];
        int curIndex = 0;
        for (Integer integer : myArray) {
            integers[curIndex++] = integer;
        }
        assertThat(myArray.toArray(new Integer[myArray.getSize()]), equalTo(integers));
    }

}
