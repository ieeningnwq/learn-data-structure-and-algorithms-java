package ieening.datastructure;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ieening.datastructure.MyArrayList;
import com.ieening.datastructure.MyBreadthFirstPaths;
import com.ieening.datastructure.MyDigraph;
import com.ieening.datastructure.MyList;
import com.ieening.datastructure.MyUndirectedGraph;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class MyBreadthFirstPathsTest {
    private MyUndirectedGraph undirectedGraph;
    private MyDigraph digraph;
    private MyBreadthFirstPaths mPaths;

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
    public void testSourceHasPathToUndirectedGraph() {
        mPaths = new MyBreadthFirstPaths(undirectedGraph, 0);

        assertThat(true, equalTo(mPaths.hasPathTo((int) (Math.random() * undirectedGraph.V()))));
    }

    @Test
    public void testSourcePathToUndirectedGraph() {
        mPaths = new MyBreadthFirstPaths(undirectedGraph, 0);

        MyList<Integer> myList = new MyArrayList<>();

        for (int vertex : mPaths.pathTo(3)) {
            myList.add(vertex);
        }
        assertThat(new int[] { 0, 5, 3 }, equalTo(myList.toArray()));
    }

    @Test
    public void testSourcesHasPathToUndirectedGraph() {
        MyList<Integer> mList = new MyArrayList<>();
        mList.add(0);
        mList.add(3);
        mPaths = new MyBreadthFirstPaths(undirectedGraph, mList);

        assertThat(true, equalTo(mPaths.hasPathTo((int) (Math.random() * undirectedGraph.V()))));
    }

    @Test
    public void testSourcesPathToUndirectedGraph() {
        MyList<Integer> mList = new MyArrayList<>();
        mList.add(0);
        mList.add(3);
        mPaths = new MyBreadthFirstPaths(undirectedGraph, mList);

        MyList<Integer> myList = new MyArrayList<>();
        for (int vertex : mPaths.pathTo(4)) {
            myList.add(vertex);
        }
        assertThat(new int[] { 3, 4 }, equalTo(myList.toArray()));

        myList.clear();
        for (int vertex : mPaths.pathTo(1)) {
            myList.add(vertex);
        }
        assertThat(new int[] { 0, 1 }, equalTo(myList.toArray()));
    }

    @Test
    public void testSourceHasPathToMyDigraph() {
        mPaths = new MyBreadthFirstPaths(digraph, 0);

        assertThat(false, equalTo(mPaths.hasPathTo(11)));
        assertThat(true, equalTo(mPaths.hasPathTo(2)));
    }

    @Test
    public void testSourcePathToMyDigraph() {
        mPaths = new MyBreadthFirstPaths(digraph, 0);

        MyList<Integer> myList = new MyArrayList<>();

        for (int vertex : mPaths.pathTo(2)) {
            myList.add(vertex);
        }
        assertThat(new int[] { 0, 5, 4, 2 }, equalTo(myList.toArray()));
    }

    @Test
    public void testSourcesHasPathToMyDigraph() {
        MyList<Integer> mList = new MyArrayList<>();
        mList.add(0);
        mList.add(7);
        mPaths = new MyBreadthFirstPaths(digraph, mList);

        assertThat(true, equalTo(mPaths.hasPathTo(11)));
    }

    @Test
    public void testSourcesPathToMyDigraph() {
        MyList<Integer> mList = new MyArrayList<>();
        mList.add(0);
        mList.add(7);
        mPaths = new MyBreadthFirstPaths(digraph, mList);

        MyList<Integer> myList = new MyArrayList<>();
        for (int vertex : mPaths.pathTo(11)) {
            myList.add(vertex);
        }
        assertThat(new int[] { 7, 9, 11 }, equalTo(myList.toArray()));

        myList.clear();
        for (int vertex : mPaths.pathTo(1)) {
            myList.add(vertex);
        }
        assertThat(new int[] { 0, 1 }, equalTo(myList.toArray()));
    }
}
