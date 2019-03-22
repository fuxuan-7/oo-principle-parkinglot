package cc.oobootcamp.principle;

import cc.oobootcamp.principle.testThefrequencyOfWords.MyUtils;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class testThefrequencyOfWordsTest {
    @Test
    public void should_return_fileString_when_given_a_wordstxt_file_valid() {
        String fileString = MyUtils.readFile("D://words.txt");
        assertThat(fileString).isEqualTo("the day is sunny the the the sunny is is");
    }

    /**
     * given  "he"
     * when  统计单词个数
     * then 返回 he  1
     */
    @Test
    public void should_return_he_1_when_given_he_valid() {
        String frequencyOfWords = MyUtils.getThefrequencyOfWords("the day is sunny the the the sunny is is");
        assertThat(frequencyOfWords).isEqualTo("the 4\nis 3\nsunny 2\nday 1\n");
    }

    @Test
    public void test() {
        String fileString = MyUtils.readFile("D://words.txt");
        assertThat(fileString).isEqualTo("the day is sunny the the the sunny is is");
    }


}
