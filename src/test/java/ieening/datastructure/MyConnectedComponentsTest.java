package ieening.datastructure;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import com.ieening.datastructure.MyConnectedComponents;
import com.ieening.datastructure.MyUndirectedGraph;

public class MyConnectedComponentsTest {
    private MyUndirectedGraph undirectedGraph;
    private Scanner scanner;
    private MyConnectedComponents mComponents;

    @BeforeEach
    public void setUpEach() throws FileNotFoundException {
        File file = new File("src\\main\\resources\\asserts\\tinyG.txt");
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis));
        }

        undirectedGraph = new MyUndirectedGraph(scanner);
    }

    @Test
    public void testUndirectedGraphCount() {
        mComponents = new MyConnectedComponents(undirectedGraph);

        assertThat(3, equalTo(mComponents.count()));
    }

    @Test
    public void testUndirectedGraphId() {
        mComponents = new MyConnectedComponents(undirectedGraph);

        assertThat(mComponents.id(0), equalTo(mComponents.id(3)));
        assertThat(mComponents.id(0), not(equalTo(mComponents.id(7))));
    }

    @Test
    public void testUndirectedGraphSize() {
        mComponents = new MyConnectedComponents(undirectedGraph);

        assertThat(2, equalTo(mComponents.size(8)));
        assertThat(4, equalTo(mComponents.size(9)));
        assertThat(7, equalTo(mComponents.size(3)));

    }

    @Test
    public void testUndirectedGraphConnected() {
        mComponents = new MyConnectedComponents(undirectedGraph);

        assertThat(true, equalTo(mComponents.connected(0, 0)));
        assertThat(true, equalTo(mComponents.connected(3, 2)));
        assertThat(false, equalTo(mComponents.connected(7, 12)));
    }

}
