package ieening.datastructure;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.ieening.datastructure.MyResizingArrayStack;

public class MyResizingArrayStackTest {
    private MyResizingArrayStack<Integer> myStack;

    @BeforeEach
    public void setUpEach() {
        myStack = new MyResizingArrayStack<>();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[1]", // 添加一个
            "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]", // 添加多个，扩容
    })
    public void testPush(String parameterJsonString) {
        JSONArray jsonArr = new JSONArray(parameterJsonString);
        int[] ints = jsonArr.toList().stream().mapToInt(x -> (int) x).toArray();
        int beforeSize = myStack.size();
        assertThat(0, equalTo(myStack.size()));
        for (int i : ints) {
            myStack.push(i);
        }
        assertThat(ints.length + beforeSize, equalTo(myStack.size()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[]",
            "[1]", // 添加一个
            "[1,2,3,4,5]", // 添加多个
    })
    public void testPeek(String parameterJsonString) {
        JSONArray jsonArr = new JSONArray(parameterJsonString);
        int[] ints = jsonArr.toList().stream().mapToInt(x -> (int) x).toArray();
        for (int i : ints) {
            myStack.push(i);
        }
        if (myStack.isEmpty()) {
            NoSuchElementException assertThrowsException = assertThrows(NoSuchElementException.class, () -> {
                myStack.peek();
            });
            assertThat("Stack Underflow", equalTo(assertThrowsException.getMessage()));
        } else {
            assertThat(ints[ints.length - 1], equalTo(myStack.peek()));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[1]", // 添加一个
            "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]", // 添加多个，pop 后有缩减
    })
    public void testPop(String parameterJsonString) {
        JSONArray jsonArr = new JSONArray(parameterJsonString);
        int[] ints = jsonArr.toList().stream().mapToInt(x -> (int) x).toArray();
        for (int i : ints) {
            myStack.push(i);
        }
        int beforeSize;
        for (int i = ints.length - 1; i >= 0; i--) {
            beforeSize = myStack.size();
            Integer popInteger = myStack.pop();
            assertThat(popInteger, equalTo(ints[i]));
            assertThat(beforeSize - 1, equalTo(myStack.size()));
        }
        assertThat(true, equalTo(myStack.isEmpty()));
        NoSuchElementException assertThrowsException = assertThrows(NoSuchElementException.class, () -> {
            myStack.pop();
        });
        assertThat("Stack Underflow", equalTo(assertThrowsException.getMessage()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[1]", // 添加一个
            "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]", // 添加多个
    })
    public void testIterator(String parameterJsonString) {
        JSONArray jsonArr = new JSONArray(parameterJsonString);
        int[] ints = jsonArr.toList().stream().mapToInt(x -> (int) x).toArray();
        for (int i : ints) {
            myStack.push(i);
        }
        // 数组逆转
        int temp = 0;
        for (int i = 0; i < ints.length >> 1; i++) {
            temp = ints[i];
            ints[i] = ints[ints.length - i - 1];
            ints[ints.length - i - 1] = temp;
        }
        // 迭代栈
        int[] iteratorArray = new int[myStack.size()];
        int i = 0;
        for (int element : myStack) {
            iteratorArray[i++] = element;
        }
        // 断言
        assertThat(ints, equalTo(iteratorArray));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[1]", // 添加一个
            "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]", // 添加多个
    })
    public void testClear(String parameterJsonString) {
        JSONArray jsonArr = new JSONArray(parameterJsonString);
        int[] ints = jsonArr.toList().stream().mapToInt(x -> (int) x).toArray();
        for (int i : ints) {
            myStack.push(i);
        }
        myStack.clear();
        assertThat(true, equalTo(myStack.isEmpty()));
    }
}
