package ieening.datastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ieening.datastructure.MyDigraph;
import com.ieening.datastructure.MyStronglyConnectedComponent;

public class MyStronglyConnectedComponentTest {
    private MyDigraph digraph;
    private MyStronglyConnectedComponent scc;

    @BeforeEach
    public void setUpEach() throws FileNotFoundException {
        File file = new File("src\\main\\resources\\assets\\tinyDGSCC.txt");

        try (Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file)));) {
            digraph = new MyDigraph(scanner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MARK:Kosaraju
    @Test
    public void testStronglyConnectedKosaraju() {
        scc = new MyStronglyConnectedComponent(digraph, MyStronglyConnectedComponent.AlgorithmType.Kosaraju);
        assertFalse(scc.stronglyConnected(1, 2));
        assertFalse(scc.stronglyConnected(6, 1));
        assertTrue(scc.stronglyConnected(0, 2));
    }

    @Test
    public void testCountKosaraju() {
        scc = new MyStronglyConnectedComponent(digraph, MyStronglyConnectedComponent.AlgorithmType.Kosaraju);
        assertEquals(5, scc.count());

        File file = new File("src\\main\\resources\\assets\\tinyDAG.txt");

        try (Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file)));) {
            digraph = new MyDigraph(scanner);
        } catch (Exception e) {
            e.printStackTrace();
        }

        scc = new MyStronglyConnectedComponent(digraph, MyStronglyConnectedComponent.AlgorithmType.Kosaraju);
        assertEquals(digraph.V(), scc.count());
    }

    @Test
    public void testIdKosaraju() {
        scc = new MyStronglyConnectedComponent(digraph, MyStronglyConnectedComponent.AlgorithmType.Kosaraju);
        assertNotEquals(scc.id(1), scc.id(2));
        assertNotEquals(scc.id(6), scc.id(8));
        assertEquals(scc.id(10), scc.id(11));
    }
    // MARK:Tarjan

    @Test
    public void testStronglyConnectedTarjan() {
        scc = new MyStronglyConnectedComponent(digraph, MyStronglyConnectedComponent.AlgorithmType.Tarjan);
        assertFalse(scc.stronglyConnected(1, 2));
        assertFalse(scc.stronglyConnected(6, 1));
        assertTrue(scc.stronglyConnected(0, 2));
    }

    @Test
    public void testCountTarjan() {
        scc = new MyStronglyConnectedComponent(digraph, MyStronglyConnectedComponent.AlgorithmType.Tarjan);
        assertEquals(5, scc.count());

        File file = new File("src\\main\\resources\\assets\\tinyDAG.txt");

        try (Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file)));) {
            digraph = new MyDigraph(scanner);
        } catch (Exception e) {
            e.printStackTrace();
        }

        scc = new MyStronglyConnectedComponent(digraph, MyStronglyConnectedComponent.AlgorithmType.Tarjan);
        assertEquals(digraph.V(), scc.count());
    }

    @Test
    public void testIdTarjan() {
        scc = new MyStronglyConnectedComponent(digraph, MyStronglyConnectedComponent.AlgorithmType.Tarjan);
        assertNotEquals(scc.id(1), scc.id(2));
        assertNotEquals(scc.id(6), scc.id(8));
        assertEquals(scc.id(10), scc.id(11));
    }

    // MARK:Gabow

    @Test
    public void testStronglyConnectedGabow() {
        scc = new MyStronglyConnectedComponent(digraph, MyStronglyConnectedComponent.AlgorithmType.Gabow);
        assertFalse(scc.stronglyConnected(1, 2));
        assertFalse(scc.stronglyConnected(6, 1));
        assertTrue(scc.stronglyConnected(0, 2));
    }

    @Test
    public void testCountGabow() {
        scc = new MyStronglyConnectedComponent(digraph, MyStronglyConnectedComponent.AlgorithmType.Gabow);
        assertEquals(5, scc.count());

        File file = new File("src\\main\\resources\\assets\\tinyDAG.txt");

        try (Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file)));) {
            digraph = new MyDigraph(scanner);
        } catch (Exception e) {
            e.printStackTrace();
        }

        scc = new MyStronglyConnectedComponent(digraph, MyStronglyConnectedComponent.AlgorithmType.Gabow);
        assertEquals(digraph.V(), scc.count());
    }

    @Test
    public void testIdGabow() {
        scc = new MyStronglyConnectedComponent(digraph, MyStronglyConnectedComponent.AlgorithmType.Gabow);
        assertNotEquals(scc.id(1), scc.id(2));
        assertNotEquals(scc.id(6), scc.id(8));
        assertEquals(scc.id(10), scc.id(11));
    }
}
