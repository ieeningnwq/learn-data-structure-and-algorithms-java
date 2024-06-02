package ieening.datastructure;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

import com.ieening.datastructure.MyEdgeWeightedGraph;

public class MyEdgeWeightedGraphTest {
    MyEdgeWeightedGraph mEdgeWeightGraph;

    @BeforeEach
    public void setUpEach() {
        File file = new File("src\\main\\resources\\assets\\tinyEWG.txt");
        try (Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file)))) {
            mEdgeWeightGraph = new MyEdgeWeightedGraph(scanner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDraw() {
        mEdgeWeightGraph.draw("src\\test\\java\\ieening\\output\\tinyEWG.jpg");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"vertex\":7,\"degree\":5}",
            "{\"vertex\":3,\"degree\":3}",
            "{\"vertex\":-1,\"degree\":null}",
            "{\"vertex\":8,\"degree\":null}",

    })
    public void testDegree(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        int vertex = jObject.getInt("vertex");
        try {
            int degree = jObject.getInt("degree");
            assertEquals(degree, mEdgeWeightGraph.degree(vertex));
        } catch (Exception e) {
            assertThrows(IllegalArgumentException.class, () -> mEdgeWeightGraph.degree(vertex));
        }
    }

    @Test
    public void testV() {
        assertEquals(8, mEdgeWeightGraph.V());
    }

    @Test
    public void testE() {
        assertEquals(16, mEdgeWeightGraph.E());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"vertex\":7,\"adj\":[4,5,0,1,2]}",
            "{\"vertex\":3,\"adj\":[2,1,6]}",
            "{\"vertex\":-1,\"adj\":null}",
            "{\"vertex\":8,\"adj\":null}",
    })
    public void testAdj(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        int vertex = jObject.getInt("vertex");
        try {
            JSONArray adj = jObject.getJSONArray("adj");
            assertThat(adj.toList().toArray(),
                    equalTo(StreamSupport.stream(mEdgeWeightGraph.adj(vertex).spliterator(), false)
                            .mapToInt((MyEdgeWeightedGraph.MyEdge x) -> x.other(vertex)).toArray()));
        } catch (Exception e) {
            assertThrows(IllegalArgumentException.class, () -> mEdgeWeightGraph.adj(vertex));
        }
    }

    @Test
    public void testEdges() {
        assertThat(
                "[0-7 0.16000, 0-4 0.38000, 0-2 0.26000, 6-0 0.58000, 1-5 0.32000, 1-7 0.19000, 1-2 0.36000, 1-3 0.29000, 2-3 0.17000, 2-7 0.34000, 6-2 0.40000, 3-6 0.52000, 4-5 0.35000, 4-7 0.37000, 6-4 0.93000, 5-7 0.28000]",
                equalTo(Arrays
                        .toString(StreamSupport.stream(mEdgeWeightGraph.edges().spliterator(), false).toArray())));
    }

    @Test
    public void testToString() {
        System.out.println(mEdgeWeightGraph.toString());
    }
}
