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
import com.ieening.datastructure.MyUndirectedGraph;

public class MyDepthFirstSearchTest {
    private MyUndirectedGraph undirectedGraph;
    private Scanner scanner;
    private MyDepthFirstSearch dfs;

    @BeforeEach
    public void setUpEach() throws FileNotFoundException {
        File file = new File("src\\main\\resources\\asserts\\depthFirstSearchG.txt");
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis));
        }

        undirectedGraph = new MyUndirectedGraph(scanner);

    }

    @Test
    public void testRecursiveDfs() {
        dfs = new MyDepthFirstSearch(undirectedGraph, 0, true);
        assertThat(true, equalTo(dfs.marked((int) (Math.random() * undirectedGraph.V()))));
    }

    @Test
    public void testReversalVertex() {
        dfs = new MyDepthFirstSearch(undirectedGraph, 0, false);
        assertThat(new int[] { 0, 5, 3, 2, 4, 1 }, equalTo(dfs.traversalVertexes()));
    }
}
