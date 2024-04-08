package ieening;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.ieening.MyDeque;
import com.ieening.MyResizingArrayDeque;

public class MyResizingArrayDequeTest {
    private MyDeque<Integer> myDeque;

    @BeforeEach
    public void setUpEach() {
        myDeque = new MyResizingArrayDeque<>();
    }

    // MARK:Deque Test
    @ParameterizedTest
    @ValueSource(strings = {
            "[1]", // 添加一个
            "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24]", // 添加多个，扩容
    })
    @DisplayName("测试队首入队函数enqueueFirst")
    public void testEnqueueFirst(String parameterJsonString) {
        JSONArray jsonArr = new JSONArray(parameterJsonString);
        int[] ints = jsonArr.toList().stream().mapToInt(x -> (int) x).toArray();
        int beforeSize = myDeque.size();
        assertThat(0, equalTo(myDeque.size()));
        assertThat(true, equalTo(myDeque.isEmpty()));
        for (int i : ints) {
            myDeque.enqueueFirst(i);
        }
        assertThat(ints.length + beforeSize, equalTo(myDeque.size()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[1]", // 添加一个
            "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24]", // 添加多个，扩容
    })
    @DisplayName("测试队首入队函数enqueueLast")
    public void testEnqueueLast(String parameterJsonString) {
        JSONArray jsonArr = new JSONArray(parameterJsonString);
        int[] ints = jsonArr.toList().stream().mapToInt(x -> (int) x).toArray();
        int beforeSize = myDeque.size();
        assertThat(0, equalTo(myDeque.size()));
        assertThat(true, equalTo(myDeque.isEmpty()));
        for (int i : ints) {
            myDeque.enqueueLast(i);
        }
        assertThat(ints.length + beforeSize, equalTo(myDeque.size()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[]",
            "[1]", // 添加一个
            "[1,2,3,4,5]", // 添加多个
    })
    @DisplayName("测试队首查看函数peekFirst")
    public void testPeekFirst(String parameterJsonString) {
        JSONArray jsonArr = new JSONArray(parameterJsonString);
        int[] ints = jsonArr.toList().stream().mapToInt(x -> (int) x).toArray();
        for (int i : ints) {
            myDeque.enqueueLast(i);
        }
        int beforeSize = myDeque.size();
        if (myDeque.isEmpty()) {
            NoSuchElementException assertThrowsException = assertThrows(NoSuchElementException.class, () -> {
                myDeque.peekFirst();
            });
            assertThat("Queue Underflow", equalTo(assertThrowsException.getMessage()));
        } else {
            assertThat(beforeSize, equalTo(myDeque.size()));
            assertThat(ints[0], equalTo(myDeque.peekFirst()));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[]",
            "[1]", // 添加一个
            "[1,2,3,4,5]", // 添加多个
    })
    @DisplayName("测试队首查看函数peekFirst")
    public void testPeekLast(String parameterJsonString) {
        JSONArray jsonArr = new JSONArray(parameterJsonString);
        int[] ints = jsonArr.toList().stream().mapToInt(x -> (int) x).toArray();
        for (int i : ints) {
            myDeque.enqueueLast(i);
        }
        int beforeSize = myDeque.size();
        if (myDeque.isEmpty()) {
            NoSuchElementException assertThrowsException = assertThrows(NoSuchElementException.class, () -> {
                myDeque.peekLast();
            });
            assertThat("Queue Underflow", equalTo(assertThrowsException.getMessage()));
        } else {
            assertThat(beforeSize, equalTo(myDeque.size()));
            assertThat(ints[ints.length - 1], equalTo(myDeque.peekLast()));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[1]", // 添加一个
            "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24]", // 添加多个，pop 后有缩减
    })
    @DisplayName("测试队首出队函数dequeueFirst")
    public void testDequeueFirst(String parameterJsonString) {
        JSONArray jsonArr = new JSONArray(parameterJsonString);
        int[] ints = jsonArr.toList().stream().mapToInt(x -> (int) x).toArray();
        for (int i : ints) {
            myDeque.enqueueLast(i);
        }
        int beforeSize;
        for (int i = 0; i < ints.length; i++) {
            beforeSize = myDeque.size();
            Integer dequeueInteger = myDeque.dequeueFirst();
            assertThat(dequeueInteger, equalTo(ints[i]));
            assertThat(beforeSize - 1, equalTo(myDeque.size()));
        }
        assertThat(true, equalTo(myDeque.isEmpty()));
        NoSuchElementException assertThrowsException = assertThrows(NoSuchElementException.class, () -> {
            myDeque.dequeueFirst();
        });
        assertThat("Queue Underflow", equalTo(assertThrowsException.getMessage()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[1]", // 添加一个
            "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24]", // 添加多个，pop 后有缩减
    })
    @DisplayName("测试队首出队函数dequeueLast")
    public void testDequeueLast(String parameterJsonString) {
        JSONArray jsonArr = new JSONArray(parameterJsonString);
        int[] ints = jsonArr.toList().stream().mapToInt(x -> (int) x).toArray();
        for (int i : ints) {
            myDeque.enqueueLast(i);
        }
        int beforeSize;
        for (int i = 0; i < ints.length; i++) {
            beforeSize = myDeque.size();
            Integer dequeueInteger = myDeque.dequeueLast();
            assertThat(dequeueInteger, equalTo(ints[ints.length - i - 1]));
            assertThat(beforeSize - 1, equalTo(myDeque.size()));
        }
        assertThat(true, equalTo(myDeque.isEmpty()));
        NoSuchElementException assertThrowsException = assertThrows(NoSuchElementException.class, () -> {
            myDeque.dequeueLast();
        });
        assertThat("Queue Underflow", equalTo(assertThrowsException.getMessage()));
    }

    // MARK:Queue Test

    @ParameterizedTest
    @ValueSource(strings = {
            "[1]", // 添加一个
            "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24]", // 添加多个，扩容
    })
    public void testEnqueue(String parameterJsonString) {
        JSONArray jsonArr = new JSONArray(parameterJsonString);
        int[] ints = jsonArr.toList().stream().mapToInt(x -> (int) x).toArray();
        int beforeSize = myDeque.size();
        assertThat(0, equalTo(myDeque.size()));
        assertThat(true, equalTo(myDeque.isEmpty()));
        for (int i : ints) {
            myDeque.enqueue(i);
        }
        assertThat(ints.length + beforeSize, equalTo(myDeque.size()));
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
            myDeque.enqueue(i);
        }
        int beforeSize = myDeque.size();
        if (myDeque.isEmpty()) {
            NoSuchElementException assertThrowsException = assertThrows(NoSuchElementException.class, () -> {
                myDeque.peek();
            });
            assertThat("Queue Underflow", equalTo(assertThrowsException.getMessage()));
        } else {
            assertThat(beforeSize, equalTo(myDeque.size()));
            assertThat(ints[0], equalTo(myDeque.peek()));
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
            myDeque.enqueue(i);
        }
        int beforeSize;
        for (int i = 0; i < ints.length; i++) {
            beforeSize = myDeque.size();
            Integer dequeueInteger = myDeque.dequeue();
            assertThat(dequeueInteger, equalTo(ints[i]));
            assertThat(beforeSize - 1, equalTo(myDeque.size()));
        }
        assertThat(true, equalTo(myDeque.isEmpty()));
        NoSuchElementException assertThrowsException = assertThrows(NoSuchElementException.class, () -> {
            myDeque.dequeue();
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
            myDeque.enqueue(i);
        }

        JSONArray operations = jObject.getJSONArray("operations");
        JSONArray operationValues = jObject.getJSONArray("values");
        assertThat(operations.length(), equalTo(operationValues.length()));

        for (int i = 0; i < operations.length(); i++) {
            if ("e".equals(operations.getString(i))) {
                myDeque.enqueue(operationValues.getInt(i));
            } else if ("d".equals(operations.getString(i))) {
                Integer value = myDeque.dequeue();
                assertThat(value, equalTo(operationValues.getInt(i)));
            }
        }
        assertThat(true, equalTo(myDeque.isEmpty()));
        NoSuchElementException assertThrowsException = assertThrows(NoSuchElementException.class, () -> {
            myDeque.dequeue();
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
            myDeque.enqueue(i);
        }
        // 迭代队列
        int[] iteratorArray = new int[myDeque.size()];
        int i = 0;
        for (int element : myDeque) {
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
            myDeque.enqueue(i);
        }

        myDeque.clear();

        assertThat(true, equalTo(myDeque.isEmpty()));

        myDeque.enqueue(1);
        myDeque.enqueue(2);
        assertThat(1, equalTo(myDeque.dequeue()));
        assertThat(2, equalTo(myDeque.dequeue()));

        NoSuchElementException assertThrowsException = assertThrows(NoSuchElementException.class, () -> {
            myDeque.dequeue();
        });
        assertThat("Queue Underflow", equalTo(assertThrowsException.getMessage()));
    }

}
