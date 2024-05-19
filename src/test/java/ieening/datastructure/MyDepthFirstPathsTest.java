package ieening.datastructure;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import com.ieening.datastructure.MyArrayList;
import com.ieening.datastructure.MyDepthFirstPaths;
import com.ieening.datastructure.MyDigraph;
import com.ieening.datastructure.MyList;
import com.ieening.datastructure.MyUndirectedGraph;

public class MyDepthFirstPathsTest {
    private MyUndirectedGraph undirectedGraph;
    private MyDigraph digraph;
    private MyDepthFirstPaths mPaths;

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
    public void testHasPathToUndirectedGraph() {
        mPaths = new MyDepthFirstPaths(undirectedGraph, 0);

        assertThat(true, equalTo(mPaths.hasPathTo((int) (Math.random() * undirectedGraph.V()))));
    }

    @Test
    public void testPathToUndirectedGraph() {
        mPaths = new MyDepthFirstPaths(undirectedGraph, 0);

        MyList<Integer> myList = new MyArrayList<>();

        for (int vertex : mPaths.pathTo(2)) {
            myList.add(vertex);
        }
        assertThat(new int[] { 0, 5, 3, 2 }, equalTo(myList.toArray()));
    }

    @Test
    public void testHasPathToDigraph() {
        mPaths = new MyDepthFirstPaths(digraph, 0);

        assertThat(true, equalTo(mPaths.hasPathTo(2)));
        assertThat(false, equalTo(mPaths.hasPathTo(7)));
    }

    @Test
    public void testPathToDigraph() {
        mPaths = new MyDepthFirstPaths(digraph, 0);

        MyList<Integer> myList = new MyArrayList<>();

        for (int vertex : mPaths.pathTo(2)) {
            myList.add(vertex);
        }
        assertThat(new int[] { 0, 5, 4, 2 }, equalTo(myList.toArray()));
    }
}
