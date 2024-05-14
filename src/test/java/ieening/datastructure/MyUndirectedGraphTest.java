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

import com.ieening.datastructure.MyUndirectedGraph;

public class MyUndirectedGraphTest {
    private MyUndirectedGraph undirectedGraph;
    private Scanner scanner;

    @BeforeEach
    public void setUpEach() throws FileNotFoundException {
        File file = new File("src\\main\\resources\\assets\\tinyGex2.txt");
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis));
        }
    }

    @Test
    public void testToString() {
        undirectedGraph = new MyUndirectedGraph(scanner);
        assertThat(
                "13 vertices, 13 edges \r\n0: 5 1 2 6 \r\n1: 0 \r\n2: 0 \r\n3: 4 5 \r\n4: 3 6 5 \r\n5: 0 4 3 \r\n6: 4 0 \r\n7: 8 \r\n8: 7 \r\n9: 12 10 11 \r\n10: 9 \r\n11: 12 9 \r\n12: 9 11 \r\n",
                equalTo(undirectedGraph.toString()));
    }

    @Test
    public void testV() {
        undirectedGraph = new MyUndirectedGraph(scanner);
        assertThat(13, equalTo(undirectedGraph.V()));
    }

    @Test
    public void testE() {
        undirectedGraph = new MyUndirectedGraph(scanner);
        assertThat(13, equalTo(undirectedGraph.E()));
    }

    @Test
    public void testDraw() {
        undirectedGraph = new MyUndirectedGraph(scanner);
        undirectedGraph.draw("src\\test\\java\\ieening\\output\\undirected_graph_tingGex2.jpg");
    }
}
