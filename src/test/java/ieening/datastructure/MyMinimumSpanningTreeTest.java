package ieening.datastructure;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ieening.datastructure.MyEdgeWeightedGraph;
import com.ieening.datastructure.MyMinimumSpanningTree;

public class MyMinimumSpanningTreeTest {
    private MyEdgeWeightedGraph graph;
    private MyMinimumSpanningTree mst;

    @BeforeEach
    public void setUpEach() throws FileNotFoundException {
        File file = new File("src\\main\\resources\\assets\\tinyEWG.txt");

        try (Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file)));) {
            graph = new MyEdgeWeightedGraph(scanner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEdgesLazyPrim() {
        mst = new MyMinimumSpanningTree(graph, MyMinimumSpanningTree.AlgorithmType.LazyPrim);
        assertThat("[0-7 0.16000, 1-7 0.19000, 0-2 0.26000, 2-3 0.17000, 5-7 0.28000, 4-5 0.35000, 6-2 0.40000]",
                equalTo(Arrays.toString(StreamSupport.stream(mst.edges().spliterator(), false).toArray())));
    }

    @Test
    public void testWeightLazyPrim(){
        mst = new MyMinimumSpanningTree(graph, MyMinimumSpanningTree.AlgorithmType.LazyPrim);
        assertEquals(1.81, mst.weight());
    }
}