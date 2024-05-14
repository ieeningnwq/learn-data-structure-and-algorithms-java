package ieening.datastructure;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ieening.datastructure.MyBipartite;
import com.ieening.datastructure.MyUndirectedGraph;

public class MyBipartiteTest {
    private MyUndirectedGraph undirectedGraph;
    private Scanner scanner;

    @BeforeEach
    public void setUpEach() throws FileNotFoundException {
        File file = new File("src\\main\\resources\\assets\\tinyG.txt");
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis));
        }

        undirectedGraph = new MyUndirectedGraph(scanner);
    }

    @Test
    public void testIsBipartite() {
        MyUndirectedGraph graph = new MyUndirectedGraph(5);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 0);

        MyBipartite bipartite = new MyBipartite(graph, true);
        assertThat(false, equalTo(bipartite.isBipartite()));
        bipartite = new MyBipartite(graph, false);
        assertThat(false, equalTo(bipartite.isBipartite()));

        graph = new MyUndirectedGraph(5);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        bipartite = new MyBipartite(graph, true);
        assertThat(true, equalTo(bipartite.isBipartite()));
        bipartite = new MyBipartite(graph, false);
        assertThat(true, equalTo(bipartite.isBipartite()));
    }

    @Test
    public void testOddCycle() {
        MyBipartite bipartite;
        bipartite = new MyBipartite(undirectedGraph, true);
        assertThat(new int[] { 5, 4, 3, 5 },
                equalTo(StreamSupport.stream(bipartite.oddCycle().spliterator(), false).toArray()));

        bipartite = new MyBipartite(undirectedGraph, false);
        assertThat(new int[] { 3, 5, 4, 3 },
                equalTo(StreamSupport.stream(bipartite.oddCycle().spliterator(), false).toArray()));

    }

    @Test
    public void testColor(){
        undirectedGraph = new MyUndirectedGraph(5);
        undirectedGraph.addEdge(0, 1);
        undirectedGraph.addEdge(1, 2);
        undirectedGraph.addEdge(2, 3);
        undirectedGraph.addEdge(0, 3);

        undirectedGraph.addEdge(3, 4);

        MyBipartite bipartite;

        bipartite = new MyBipartite(undirectedGraph, true);
        assertThat(bipartite.color(0),equalTo(bipartite.color(2)));
        assertThat(bipartite.color(0),not(equalTo(bipartite.color(1))));


        bipartite = new MyBipartite(undirectedGraph, false);
        assertThat(bipartite.color(0),equalTo(bipartite.color(2)));
        assertThat(bipartite.color(0),not(equalTo(bipartite.color(1))));
        
    }

}
