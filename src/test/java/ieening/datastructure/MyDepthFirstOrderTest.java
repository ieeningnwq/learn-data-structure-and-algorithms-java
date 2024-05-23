package ieening.datastructure;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ieening.datastructure.MyDepthFirstOrder;
import com.ieening.datastructure.MyDigraph;

public class MyDepthFirstOrderTest {
    private MyDigraph digraph;
    private MyDepthFirstOrder mDepthFirstOrder;

    @BeforeEach
    public void setUpEach() throws FileNotFoundException {
        File file = new File("src\\main\\resources\\assets\\tinyDAG.txt");

        try (Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file)));) {
            digraph = new MyDigraph(scanner);
            digraph.draw("src\\test\\java\\ieening\\output\\tinyDAG.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPreInt() {
        mDepthFirstOrder = new MyDepthFirstOrder(digraph);
        assertEquals(0, mDepthFirstOrder.pre(0));
        assertEquals(12, mDepthFirstOrder.pre(8));
    }

    @Test
    public void testPostInt() {
        mDepthFirstOrder = new MyDepthFirstOrder(digraph);
        assertEquals(0, mDepthFirstOrder.post(4));
        assertEquals(12, mDepthFirstOrder.post(8));
    }

    @Test
    public void testPre() {
        mDepthFirstOrder = new MyDepthFirstOrder(digraph);
        assertThat(new int[] { 0, 6, 4, 9, 12, 10, 11, 1, 5, 2, 3, 7, 8 },
                equalTo(StreamSupport.stream(mDepthFirstOrder.pre().spliterator(), false).toArray()));

    }

    @Test
    public void testPost() {
        mDepthFirstOrder = new MyDepthFirstOrder(digraph);
        assertThat(new int[] { 4, 12, 10, 11, 9, 6, 1, 5, 0, 3, 2, 7, 8 },
                equalTo(StreamSupport.stream(mDepthFirstOrder.post().spliterator(), false).toArray()));
    }

    @Test
    public void testReversePost() {
        mDepthFirstOrder = new MyDepthFirstOrder(digraph);
        assertThat(new int[] { 8, 7, 2, 3, 0, 5, 1, 6, 9, 11, 10, 12, 4 },
                equalTo(StreamSupport.stream(mDepthFirstOrder.reversePost().spliterator(), false).toArray()));
    }
}
