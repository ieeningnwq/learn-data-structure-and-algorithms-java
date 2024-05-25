package ieening.datastructure;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.ieening.datastructure.MyDigraph;
import com.ieening.datastructure.MyTopological;

public class MyTopologicalTest {
    private MyDigraph digraph;
    private MyTopological mTopological;

    @BeforeEach
    public void setUpEach() throws FileNotFoundException {
        File file = new File("src\\main\\resources\\assets\\tinyDAG.txt");

        try (Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file)));) {
            digraph = new MyDigraph(scanner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHasOrderRecursive() {
        mTopological = new MyTopological(digraph, true);
        assertTrue(mTopological.hasOrder());

        // 图有有向环
        digraph = new MyDigraph(5);
        digraph.addEdge(0, 1);
        digraph.addEdge(1, 2);
        digraph.addEdge(2, 3);
        digraph.addEdge(3, 1);
        digraph.addEdge(2, 4);
        mTopological = new MyTopological(digraph, true);
        assertFalse(mTopological.hasOrder());
    }

    @Test
    public void testOrderRecursive() {
        mTopological = new MyTopological(digraph, true);
        assertThat(new int[] { 8, 7, 2, 3, 0, 5, 1, 6, 9, 11, 10, 12, 4 },
                equalTo(StreamSupport.stream(mTopological.order().spliterator(), false).toArray()));
    }

    @Test
    public void testRankRecursive() {
        mTopological = new MyTopological(digraph, true);
        assertThat(0, equalTo(mTopological.rank(8)));
        assertThat(12, equalTo(mTopological.rank(4)));
        assertThat(4, equalTo(mTopological.rank(0)));
    }

    @Test
    public void testHasOrderNonRecursive() {
        mTopological = new MyTopological(digraph, true);
        assertTrue(mTopological.hasOrder());

        // 图有有向环
        digraph = new MyDigraph(5);
        digraph.addEdge(0, 1);
        digraph.addEdge(1, 2);
        digraph.addEdge(2, 3);
        digraph.addEdge(3, 1);
        digraph.addEdge(2, 4);
        mTopological = new MyTopological(digraph, false);
        assertFalse(mTopological.hasOrder());
    }

    @Test
    public void testOrderNonRecursive() {
        mTopological = new MyTopological(digraph, false);
        assertThat(new int[] { 2, 8, 3, 0, 7, 1, 5, 6, 4, 9, 10, 11, 12 },
                equalTo(StreamSupport.stream(mTopological.order().spliterator(), false).toArray()));
    }

    @Test
    public void testRankNonRecursive() {
        mTopological = new MyTopological(digraph, false);
        assertThat(1, equalTo(mTopological.rank(8)));
        assertThat(8, equalTo(mTopological.rank(4)));
        assertThat(12, equalTo(mTopological.rank(12)));
    }
}
