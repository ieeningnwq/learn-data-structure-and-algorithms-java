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
import com.ieening.datastructure.MyList;
import com.ieening.datastructure.MyUndirectedGraph;

public class MyDepthFirstPathsTest {
    private MyUndirectedGraph undirectedGraph;
    private Scanner scanner;
    private MyDepthFirstPaths mPaths;

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
    public void testHasPathTo() {
        mPaths = new MyDepthFirstPaths(undirectedGraph, 0);

        assertThat(true, equalTo(mPaths.hasPathTo((int) (Math.random() * undirectedGraph.V()))));
    }

    @Test
    public void testPathTo() {
        mPaths = new MyDepthFirstPaths(undirectedGraph, 0);

        MyList<Integer> myList = new MyArrayList<>();

        for (int vertex : mPaths.pathTo(2)) {
            myList.add(vertex);
        }
        assertThat(new int[] { 0, 5, 3, 2 }, equalTo(myList.toArray()));
    }
}
