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

import com.ieening.datastructure.MyCycleDetection;
import com.ieening.datastructure.MyUndirectedGraph;

public class MyCycleDetectionTest {
    private MyUndirectedGraph undirectedGraph;
    private Scanner scanner;

    private MyCycleDetection mCycleDetection;

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
    public void testHasCycle(){
        mCycleDetection = new MyCycleDetection(undirectedGraph);
        assertThat(true, equalTo(mCycleDetection.hasCycle()));
    }

    @Test
    public void testCycle(){
        mCycleDetection = new MyCycleDetection(undirectedGraph);
        mCycleDetection.cycle();
        for (Integer v : mCycleDetection.cycle()) {
            System.out.println(v);
        }
    }


}
