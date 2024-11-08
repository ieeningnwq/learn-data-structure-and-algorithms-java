package ieening.datastructure;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ieening.datastructure.MyDepthFirstSearch;
import com.ieening.datastructure.MyDigraph;
import com.ieening.datastructure.MyUndirectedGraph;

public class MyDepthFirstSearchTest {
    private MyUndirectedGraph undirectedGraph;
    private MyDigraph digraph;
    private MyDepthFirstSearch dfs;

    @BeforeEach
    public void setUpEach() throws FileNotFoundException {
        File file = new File("src\\main\\resources\\assets\\depthFirstSearchG.txt");
        try (Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file)))) {
            undirectedGraph = new MyUndirectedGraph(scanner);
        } catch (Exception e) {
            e.printStackTrace();
        }

        file = new File("src\\main\\resources\\assets\\tinyDG.txt");
        try (Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file)))) {
            digraph = new MyDigraph(scanner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRecursiveDfsUndirectedGraph() {
        dfs = new MyDepthFirstSearch(undirectedGraph, 0, true);
        assertThat(true, equalTo(dfs.marked((int) (Math.random() * undirectedGraph.V()))));
    }

    @Test
    public void testReversalVertexUndirectedGraph() {
        dfs = new MyDepthFirstSearch(undirectedGraph, 0, false);
        assertThat(new int[] { 0, 5, 3, 2, 4, 1 }, equalTo(dfs.traversalVertexes()));
    }

    @Test
    public void testRecursiveDfsDigraph() {
        dfs = new MyDepthFirstSearch(digraph, 0, true);
        assertThat(true, equalTo(dfs.marked((int) (Math.random() * dfs.traversalVertexes().length))));
    }

    @Test
    public void testReversalVertexDigraph() {
        dfs = new MyDepthFirstSearch(digraph, 0, false);
        assertThat(new int[] { 0, 1, 5, 4, 2, 3 }, equalTo(dfs.traversalVertexes()));
    }
}
