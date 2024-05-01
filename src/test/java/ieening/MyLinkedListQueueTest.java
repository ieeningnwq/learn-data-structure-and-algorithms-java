package ieening;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.ieening.datastructure.MyLinkedListQueue;
import com.ieening.datastructure.MyQueue;

public class MyLinkedListQueueTest {
    private MyQueue<Integer> myQueue;

    @BeforeEach
    public void setUpEach() {
        myQueue = new MyLinkedListQueue<>();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[1]", // 添加一个
            "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24]", // 添加多个，扩容
    })
    public void testEnqueue(String parameterJsonString) {
        JSONArray jsonArr = new JSONArray(parameterJsonString);
        int[] ints = jsonArr.toList().stream().mapToInt(x -> (int) x).toArray();
        int beforeSize = myQueue.size();
        assertThat(0, equalTo(myQueue.size()));
        assertThat(true, equalTo(myQueue.isEmpty()));
        for (int i : ints) {
            myQueue.enqueue(i);
        }
        assertThat(ints.length + beforeSize, equalTo(myQueue.size()));
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
            myQueue.enqueue(i);
        }
        int beforeSize = myQueue.size();
        if (myQueue.isEmpty()) {
            NoSuchElementException assertThrowsException = assertThrows(NoSuchElementException.class, () -> {
                myQueue.peek();
            });
            assertThat("Queue Underflow", equalTo(assertThrowsException.getMessage()));
        } else {
            assertThat(beforeSize, equalTo(myQueue.size()));
            assertThat(ints[0], equalTo(myQueue.peek()));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[1]", // 添加一个
            "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24]", // 添加多个，pop 后有缩减
    })
    public void testDequeue(String parameterJsonString) {
        JSONArray jsonArr = new JSONArray(parameterJsonString);
        int[] ints = jsonArr.toList().stream().mapToInt(x -> (int) x).toArray();
        for (int i : ints) {
            myQueue.enqueue(i);
        }
        int beforeSize;
        for (int i = 0; i < ints.length; i++) {
            beforeSize = myQueue.size();
            Integer dequeueInteger = myQueue.dequeue();
            assertThat(dequeueInteger, equalTo(ints[i]));
            assertThat(beforeSize - 1, equalTo(myQueue.size()));
        }
        assertThat(true, equalTo(myQueue.isEmpty()));
        NoSuchElementException assertThrowsException = assertThrows(NoSuchElementException.class, () -> {
            myQueue.dequeue();
        });
        assertThat("Queue Underflow", equalTo(assertThrowsException.getMessage()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"queue\":[1,2,3,4,5,6,7],\"operations\":[\"d\",\"d\",\"d\",\"d\",\"e\",\"e\",\"e\",\"e\",\"e\",\"e\",\"d\",\"d\",\"d\",\"d\",\"d\",\"d\",\"d\",\"d\",\"d\"],\"values\":[1,2,3,4,8,9,10,11,12,13,5,6,7,8,9,10,11,12,13]}", // 添加一个
    })
    public void testEnqueueAndDequeue(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        JSONArray queueValues = jObject.getJSONArray("queue");
        int[] queueIntValues = queueValues.toList().stream().mapToInt(x -> (int) x).toArray();
        for (int i : queueIntValues) {
            myQueue.enqueue(i);
        }

        JSONArray operations = jObject.getJSONArray("operations");
        JSONArray operationValues = jObject.getJSONArray("values");
        assertThat(operations.length(), equalTo(operationValues.length()));

        for (int i = 0; i < operations.length(); i++) {
            if ("e".equals(operations.getString(i))) {
                myQueue.enqueue(operationValues.getInt(i));
            } else if ("d".equals(operations.getString(i))) {
                Integer value = myQueue.dequeue();
                assertThat(value, equalTo(operationValues.getInt(i)));
            }
        }
        assertThat(true, equalTo(myQueue.isEmpty()));
        NoSuchElementException assertThrowsException = assertThrows(NoSuchElementException.class, () -> {
            myQueue.dequeue();
        });
        assertThat("Queue Underflow", equalTo(assertThrowsException.getMessage()));
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
            myQueue.enqueue(i);
        }
        // 迭代队列
        int[] iteratorArray = new int[myQueue.size()];
        int i = 0;
        for (int element : myQueue) {
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
            myQueue.enqueue(i);
        }

        myQueue.clear();

        assertThat(true, equalTo(myQueue.isEmpty()));

        myQueue.enqueue(1);
        myQueue.enqueue(2);
        assertThat(1, equalTo(myQueue.dequeue()));
        assertThat(2, equalTo(myQueue.dequeue()));

        NoSuchElementException assertThrowsException = assertThrows(NoSuchElementException.class, () -> {
            myQueue.dequeue();
        });
        assertThat("Queue Underflow", equalTo(assertThrowsException.getMessage()));
    }
}
