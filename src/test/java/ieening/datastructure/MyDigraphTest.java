package ieening.datastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.StreamSupport;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.ieening.datastructure.MyDigraph;

public class MyDigraphTest {
    private MyDigraph digraph;
    private Scanner scanner;

    @BeforeEach
    public void setUpEach() throws FileNotFoundException {
        File file = new File("src\\main\\resources\\assets\\tinyDG.txt");
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis));
        }
        digraph = new MyDigraph(scanner);
    }

    @Test
    public void testToString() {
        assertThat(
                "13 vertices, 22 edges\r\n0: 1 5 \r\n1: \r\n2: 3 0 \r\n3: 2 5 \r\n4: 2 3 \r\n5: 4 \r\n6: 0 8 4 9 \r\n7: 9 6 \r\n8: 6 \r\n9: 10 11 \r\n10: 12 \r\n11: 12 4 \r\n12: 9 \r\n",
                equalTo(digraph.toString()));
    }

    @Test
    public void testDraw() {
        digraph.draw("src\\test\\java\\ieening\\output\\tinyDG.jpg");
    }

    @Test
    public void testV() {
        assertEquals(13, digraph.V());
    }

    @Test
    public void testE() {
        assertEquals(22, digraph.E());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"vertex\":0,\"adj\":[1,5]}",
            "{\"vertex\":1,\"adj\":[]}",
            "{\"vertex\":20,\"adj\":null}",
            "{\"vertex\":-1,\"adj\":null}",
    })
    public void testAdj(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        int vertex = jObject.getInt("vertex");
        if (jObject.get("adj") == JSONObject.NULL) {
            assertThrows(IllegalArgumentException.class, () -> digraph.adj(vertex));
        } else {
            assertThat(jObject.getJSONArray("adj").toList().toArray(),
                    equalTo(StreamSupport.stream(digraph.adj(vertex).spliterator(), false).toArray()));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"vertex\":0,\"inDegree\":2}",
            "{\"vertex\":7,\"inDegree\":0}",
            "{\"vertex\":1,\"inDegree\":1}",
    })
    public void testInDegree(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        int vertex = jObject.getInt("vertex");
        assertEquals(jObject.getInt("inDegree"), digraph.inDegree(vertex));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"vertex\":1,\"outDegree\":0}",
            "{\"vertex\":7,\"outDegree\":2}",
            "{\"vertex\":0,\"outDegree\":2}",
    })
    public void testOutDegree(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        int vertex = jObject.getInt("vertex");
        assertEquals(jObject.getInt("outDegree"), digraph.outDegree(vertex));
    }
    @Test
    public void testReverse(){
        MyDigraph reverse = digraph.reverse();
        assertThat(
                "13 vertices, 22 edges\r\n0: 2 6 \r\n1: 0 \r\n2: 3 4 \r\n3: 2 4 \r\n4: 5 6 11 \r\n5: 0 3 \r\n6: 7 8 \r\n7: \r\n8: 6 \r\n9: 6 7 12 \r\n10: 9 \r\n11: 9 \r\n12: 10 11 \r\n",
                equalTo(reverse.toString()));
        reverse.draw("src\\test\\java\\ieening\\output\\tinyDGReverse.jpg");
    }
}
