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
import com.ieening.datastructure.MyList;
import com.ieening.datastructure.MyUndirectedGraph;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class MyBreadthFirstPathsTest {
    private MyUndirectedGraph undirectedGraph;
    private Scanner scanner;
    private MyBreadthFirstPaths myBreadthFirstPaths;

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
    public void testSourceHasPathTo() {
        myBreadthFirstPaths = new MyBreadthFirstPaths(undirectedGraph, 0);

        assertThat(true, equalTo(myBreadthFirstPaths.hasPathTo((int) (Math.random() * undirectedGraph.V()))));
    }

    @Test
    public void testSourcePathTo() {
        myBreadthFirstPaths = new MyBreadthFirstPaths(undirectedGraph, 0);

        MyList<Integer> myList = new MyArrayList<>();

        for (int vertex : myBreadthFirstPaths.pathTo(3)) {
            myList.add(vertex);
        }
        assertThat(new int[] { 0, 5, 3 }, equalTo(myList.toArray()));
    }

    @Test
    public void testSourcesHasPathTo() {
        MyList<Integer> mList = new MyArrayList<>();
        mList.add(0);
        mList.add(3);
        myBreadthFirstPaths = new MyBreadthFirstPaths(undirectedGraph, mList);

        assertThat(true, equalTo(myBreadthFirstPaths.hasPathTo((int) (Math.random() * undirectedGraph.V()))));
    }

    @Test
    public void testSourcesPathTo() {
        MyList<Integer> mList = new MyArrayList<>();
        mList.add(0);
        mList.add(3);
        myBreadthFirstPaths = new MyBreadthFirstPaths(undirectedGraph, mList);

        MyList<Integer> myList = new MyArrayList<>();
        for (int vertex : myBreadthFirstPaths.pathTo(4)) {
            myList.add(vertex);
        }
        assertThat(new int[] { 3, 4 }, equalTo(myList.toArray()));

        myList.clear();
        for (int vertex : myBreadthFirstPaths.pathTo(1)) {
            myList.add(vertex);
        }
        assertThat(new int[] { 0, 1 }, equalTo(myList.toArray()));
    }
}
