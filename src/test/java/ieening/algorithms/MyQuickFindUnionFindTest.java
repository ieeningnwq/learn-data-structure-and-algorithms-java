package ieening.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ieening.algorithms.MyQuickFindUnionFind;

public class MyQuickFindUnionFindTest {
    private MyQuickFindUnionFind uf;

    @BeforeEach
    public void setUpEach() throws FileNotFoundException {
        File file = new File("src\\main\\resources\\assets\\tinyUF.txt");
        try (Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file)));) {
            uf = new MyQuickFindUnionFind(scanner);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCount() {
        assertEquals(2, uf.count());
    }

    @Test
    public void testConnected() {
        assertFalse(uf.connected(4, 1));
        assertTrue(uf.connected(2, 1));
    }

    @Test
    public void testUnion() {
        assertFalse(uf.connected(6, 9));
        uf.union(5, 4);
        assertEquals(1, uf.count());
        assertTrue(uf.connected(6, 9));
    }

}
