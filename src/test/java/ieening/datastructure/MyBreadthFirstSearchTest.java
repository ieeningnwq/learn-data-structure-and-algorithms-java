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

import com.ieening.datastructure.MyBreadthFirstSearch;
import com.ieening.datastructure.MyDigraph;
import com.ieening.datastructure.MyUndirectedGraph;

public class MyBreadthFirstSearchTest {
    private MyUndirectedGraph undirectedGraph;
    private MyDigraph digraph;
    private MyBreadthFirstSearch bfs;

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
        bfs = new MyBreadthFirstSearch(undirectedGraph, 0);
        assertThat(true, equalTo(bfs.marked((int) (Math.random() * undirectedGraph.V()))));
    }

    @Test
    public void testReversalVertexUndirectedGraph() {
        bfs = new MyBreadthFirstSearch(undirectedGraph, 0);
        assertThat(new int[] { 0, 5, 1, 2, 3, 4 }, equalTo(bfs.traversalVertexes()));
    }

    @Test
    public void testRecursiveDfsDigraph() {
        bfs = new MyBreadthFirstSearch(digraph, 0);
        assertThat(true, equalTo(bfs.marked(2)));
    }

    @Test
    public void testReversalVertexDigraph() {
        bfs = new MyBreadthFirstSearch(undirectedGraph, 0);
        assertThat(new int[] { 0, 5, 1, 2, 3, 4 }, equalTo(bfs.traversalVertexes()));
    }
}
