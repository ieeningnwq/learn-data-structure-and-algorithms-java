package ieening.datastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.StreamSupport;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.ieening.datastructure.MyEdgeWeightedDigraph;

public class MyEdgeWeightedDigraphTest {
    MyEdgeWeightedDigraph graph;

    @BeforeEach
    public void setUpEach() {
        File file = new File("src\\main\\resources\\assets\\tinyEWD.txt");
        try (Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file)))) {
            graph = new MyEdgeWeightedDigraph(scanner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDraw() {
        graph.draw("src\\test\\java\\ieening\\output\\tinyEWD.jpg");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"vertex\":0,\"degree\":1}",
            "{\"vertex\":7,\"degree\":3}",
            "{\"vertex\":-1,\"degree\":null}",
            "{\"vertex\":8,\"degree\":null}",
    })
    public void testInDegree(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        int vertex = jObject.getInt("vertex");
        try {
            int degree = jObject.getInt("degree");
            assertEquals(degree, graph.inDegree(vertex));
        } catch (Exception e) {
            assertThrows(IllegalArgumentException.class, () -> graph.inDegree(vertex));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"vertex\":0,\"degree\":2}",
            "{\"vertex\":7,\"degree\":2}",
            "{\"vertex\":-1,\"degree\":null}",
            "{\"vertex\":8,\"degree\":null}",
    })
    public void testOutDegree(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        int vertex = jObject.getInt("vertex");
        try {
            int degree = jObject.getInt("degree");
            assertEquals(degree, graph.outDegree(vertex));
        } catch (Exception e) {
            assertThrows(IllegalArgumentException.class, () -> graph.outDegree(vertex));
        }

    }

    @Test
    public void testV() {
        assertEquals(8, graph.V());
    }

    @Test
    public void testE() {
        assertEquals(15, graph.E());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"vertex\":7,\"adj\":[5,3]}",
            "{\"vertex\":0,\"adj\":[4,2]}",
            "{\"vertex\":-1,\"adj\":null}",
            "{\"vertex\":8,\"adj\":null}",
    })
    public void testAdj(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        int vertex = jObject.getInt("vertex");
        try {
            JSONArray adj = jObject.getJSONArray("adj");
            assertThat(adj.toList().toArray(),
                    equalTo(StreamSupport.stream(graph.adj(vertex).spliterator(), false)
                            .mapToInt((MyEdgeWeightedDigraph.MyDirectedEdge x) -> x.to()).toArray()));
        } catch (Exception e) {
            assertThrows(IllegalArgumentException.class, () -> graph.adj(vertex));
        }
    }

    @Test
    public void testEdges() {
        assertThat(
                "[0->4  0.38, 0->2  0.26, 1->3  0.29, 2->7  0.34, 3->6  0.52, 4->5  0.35, 4->7  0.37, 5->4  0.35, 5->7  0.28, 5->1  0.32, 6->2  0.40, 6->0  0.58, 6->4  0.93, 7->5  0.28, 7->3  0.39]",
                equalTo(Arrays
                        .toString(StreamSupport.stream(graph.edges().spliterator(), false).toArray())));
    }

    @Test
    public void testToString() {
        System.out.println(graph.toString());
    }
}
