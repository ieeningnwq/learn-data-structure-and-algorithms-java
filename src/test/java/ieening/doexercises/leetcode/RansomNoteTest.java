package ieening.doexercises.leetcode;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ieening.doexercises.leetcode.RansomNote;

public class RansomNoteTest {
    private RansomNote ransomNote;

    @BeforeEach
    public void setUpEach() {
        ransomNote = new RansomNote();
    }

    @Test
    public void testFirst() {
        String ransomNoteString = "fihjjjjei";
        String magazineString = "hjibagacbhadfaefdjaeaebgi";
        assertThat(false, equalTo(ransomNote.canConstruct(ransomNoteString, magazineString)));
    }

}
