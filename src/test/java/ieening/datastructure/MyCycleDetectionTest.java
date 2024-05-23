package ieening.datastructure;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ieening.datastructure.MyCycleDetection;
import com.ieening.datastructure.MyDigraph;
import com.ieening.datastructure.MyQueue;
import com.ieening.datastructure.MyResizingArrayDeque;
import com.ieening.datastructure.MyUndirectedGraph;

public class MyCycleDetectionTest {
    private MyUndirectedGraph undirectedGraph;
    private MyDigraph digraph;
    private MyCycleDetection mCycleDetection;

    @BeforeEach
    public void setUpEach() throws FileNotFoundException {
        File file = new File("src\\main\\resources\\assets\\depthFirstSearchG.txt");
        try (Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file)));) {
            undirectedGraph = new MyUndirectedGraph(scanner);
        } catch (Exception e) {
            e.printStackTrace();
        }
        file = new File("src\\main\\resources\\assets\\tinyDG.txt");
        try (Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file)));) {
            digraph = new MyDigraph(scanner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHasCycleUndirectedGraph() {
        mCycleDetection = new MyCycleDetection(undirectedGraph);
        assertThat(true, equalTo(mCycleDetection.hasCycle()));

        undirectedGraph = new MyUndirectedGraph(4);
        undirectedGraph.addEdge(0, 1);
        undirectedGraph.addEdge(1, 3);
        undirectedGraph.addEdge(0, 2);
        mCycleDetection = new MyCycleDetection(undirectedGraph);
        assertThat(false, equalTo(mCycleDetection.hasCycle()));
    }

    @Test
    public void testCycleUndirectedGraph() {
        mCycleDetection = new MyCycleDetection(undirectedGraph);
        assertThat(new int[] { 4, 3, 2, 4 },
                equalTo(StreamSupport.stream(mCycleDetection.cycle().spliterator(), false).toArray()));

        undirectedGraph = new MyUndirectedGraph(4);
        undirectedGraph.addEdge(0, 1);
        undirectedGraph.addEdge(1, 3);
        undirectedGraph.addEdge(0, 2);
        mCycleDetection = new MyCycleDetection(undirectedGraph);
        assertThat(null, equalTo(mCycleDetection.cycle()));
    }

    @Test
    public void testHasCycleDigraph() {
        mCycleDetection = new MyCycleDetection(digraph);
        assertThat(true, equalTo(mCycleDetection.hasCycle()));

        digraph = new MyDigraph(4);
        digraph.addEdge(0, 1);
        digraph.addEdge(1, 3);
        digraph.addEdge(0, 2);
        mCycleDetection = new MyCycleDetection(digraph);
        assertThat(false, equalTo(mCycleDetection.hasCycle()));
    }

    @Test
    public void testCycleDigraph() {
        mCycleDetection = new MyCycleDetection(digraph);
        assertThat(new int[] { 3, 2, 3 },
                equalTo(StreamSupport.stream(mCycleDetection.cycle().spliterator(), false).toArray()));

        digraph = new MyDigraph(4);
        digraph.addEdge(0, 1);
        digraph.addEdge(1, 3);
        digraph.addEdge(0, 2);
        mCycleDetection = new MyCycleDetection(digraph);
        assertThat(null, equalTo(mCycleDetection.cycle()));
    }

    @Test
    public void testHasCycleDigraphQueue() {
        MyQueue<Integer> queue = new MyResizingArrayDeque<>();
        mCycleDetection = new MyCycleDetection(digraph, queue);
        assertThat(true, equalTo(mCycleDetection.hasCycle()));

        digraph = new MyDigraph(6);
        digraph.addEdge(0, 1);
        digraph.addEdge(0, 2);
        digraph.addEdge(1, 3);
        digraph.addEdge(2, 3);
        digraph.addEdge(3, 4);
        digraph.addEdge(4, 5);
        digraph.addEdge(5, 2);

        mCycleDetection = new MyCycleDetection(digraph, queue);
        assertThat(true, equalTo(mCycleDetection.hasCycle()));

        digraph = new MyDigraph(6);
        digraph.addEdge(0, 1);
        digraph.addEdge(0, 5);
        digraph.addEdge(1, 3);
        digraph.addEdge(1, 2);
        digraph.addEdge(1, 5);
        digraph.addEdge(2, 3);
        digraph.addEdge(2, 5);
        digraph.addEdge(3, 4);
        digraph.addEdge(4, 2);


        mCycleDetection = new MyCycleDetection(digraph, queue);
        assertThat(true, equalTo(mCycleDetection.hasCycle()));
    }
}
