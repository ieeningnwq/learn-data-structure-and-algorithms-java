package ieening.datastructure;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.ieening.datastructure.MyPriorityQueue;

public class MyPriorityQueueTest {
    private MyPriorityQueue<Integer> myPq;

    @BeforeEach
    public void setUpEach() {
        myPq = new MyPriorityQueue<>();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"init\":[8,7,6,5,4,3,2,1],\"expect\":[1,4,2,5,8,3,6,7]}"
    })
    public void testCreateFromArray(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        Object[] initElements = jObject.getJSONArray("init").toList().toArray();
        Object[] expectElements = jObject.getJSONArray("expect").toList().toArray();
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>(initElements);

        assertThat(expectElements, equalTo(pq.toArray()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"init\":[4,6,9,10,8,13,17,12,14,21,16,3],\"expect\":[3,6,4,10,8,9,17,12,14,21,16,13]}"
    })
    public void testEnqueue(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        int size = 0;
        for (Object integer : jObject.getJSONArray("init").toList()) {
            myPq.enqueue((Integer) integer);
            assertThat(++size, equalTo(myPq.size()));
        }

        assertThat(jObject.getJSONArray("expect").toList().toArray(), equalTo(myPq.toArray()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"init\":[4,6,9,10,8,13,17,12,14,21,16,3],\"expect\":3}",
            "{\"init\":[8,7,6,5,4,3,2,1],\"expect\":1}",
            "{\"init\":[],\"expect\":null}",
    })
    public void testPeek(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (Object integer : jObject.getJSONArray("init").toList()) {
            myPq.enqueue((Integer) integer);
        }
        try {
            assertThat(jObject.getInt("expect"), equalTo(myPq.peek()));
        } catch (Exception e) {
            assertThat(null, equalTo(myPq.peek()));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"init\":[4,6,9,10,8,13,17,12,14,21,16,3],\"expect\":[3,4,6,8,9,10,12,13,14,16,17,21]}",
            "{\"init\":[8,7,6,5,4,3,2,1],\"expect\":[1,2,3,4,5,6,7,8]}",
    })
    public void testDequeue(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (Object integer : jObject.getJSONArray("init").toList()) {
            myPq.enqueue((Integer) integer);
        }
        Object[] expect = jObject.getJSONArray("expect").toList().toArray();
        int size = myPq.size();
        for (int i = 0; i < jObject.getJSONArray("init").toList().size(); i++) {
            assertThat(expect[i], equalTo(myPq.dequeue()));
            assertThat(--size, equalTo(myPq.size()));
        }
        assertThat(null, equalTo(myPq.dequeue()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"init\":[4,6,9,10,8,13,17,12,14,21,16,3],\"expect\":[3,4,6,8,9,10,12,13,14,16,17,21]}",
            "{\"init\":[8,7,6,5,4,3,2,1],\"expect\":[1,2,3,4,5,6,7,8]}",
    })
    public void testIterator(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (Object integer : jObject.getJSONArray("init").toList()) {
            myPq.enqueue((Integer) integer);
        }
        Object[] expect = jObject.getJSONArray("expect").toList().toArray();
        Iterator<Integer> pqIterator = myPq.iterator();
        int index=0;
        while (pqIterator.hasNext()) {
            assertThat(expect[index++], equalTo(pqIterator.next()));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"init\":[4,6,9,10,8,13,17,12,14,21,16,3],\"remove\":[17,9,5],\"expect\":[true,true,false],\"last\":[3,4,6,8,10,12,13,14,16,21]}",
    })
    public void testRemove(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (Object integer : jObject.getJSONArray("init").toList()) {
            myPq.enqueue((Integer) integer);
        }
        List<Object> removeList = jObject.getJSONArray("remove").toList();
        List<Object> expectList = jObject.getJSONArray("expect").toList();
        List<Object> lastList = jObject.getJSONArray("last").toList();
        for (int i = 0; i < removeList.size(); i++) {
            assertThat(expectList.get(i), equalTo(myPq.remove(removeList.get(i))));
        }
        Iterator<Integer> pqIterator = myPq.iterator();
        int index=0;
        while (pqIterator.hasNext()) {
            assertThat(lastList.get(index++), equalTo(pqIterator.next()));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[8,7,6,5,4,3,2,1]"
    })
    public void testDraw(String parameterJsonString) {
        JSONArray jsonArray = new JSONArray(parameterJsonString);

        for (Object integer : jsonArray.toList())
            myPq.enqueue((Integer) integer);

        myPq.draw("src\\test\\java\\ieening\\output\\priority_queue.png");
    }

}
