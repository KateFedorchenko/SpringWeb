package kate.spring.crossword;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CrosswordControllerTest {
    private List<String> list = List.of("apple", "body", "tag", "sun", "apricot");

    @Test
    public void test() {
        String s = "**ple";
        assertEquals(findWord(s), "apple");
    }

    private String findWord(String pattern) {
        for (String word : list) {
            if (check(pattern, word)) {
                return word;
            }
        }
        return null;
    }

    private boolean check(String pattern, String word) {
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) != word.charAt(i) && pattern.charAt(i) != '*') {
                return false;
            }
        }
        return true;
    }


}

// adapt the code
//regex101.com // dynamic regex not good