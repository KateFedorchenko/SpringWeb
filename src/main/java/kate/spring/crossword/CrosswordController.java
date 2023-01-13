package kate.spring.crossword;

import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

@RestController
public class CrosswordController {
    private File file = ResourceUtils.getFile("classpath:DBWords.txt");
    private List<String> wordsFromDB;
    private Map<Integer, List<String>> wordsMapWithLengthsAsKeys = new HashMap<>();

    public CrosswordController() throws IOException {
        generateWordList(file);
        generateMapWithWordLengthsAsKeys();
    }

    /**
     * @param str word that has letters and gaps (?), e.g. "a??l?" could be an "apple"
     * @return
     */
    @GetMapping("get-words")
    public List<String> getWords(@RequestBody String str) throws IOException {
        if (str == null) {
            throw new RuntimeException("No word was given.");
        }
        if (!checkWordLength(str) || !checkAstericsPresence(str)) {
            throw new RuntimeException("The word format is not allowed!");
        }
        return matchedWordList(str);
    }

    private boolean check(String pattern, String word) {
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) != word.charAt(i) && pattern.charAt(i) != '*') {
                return false;
            }
        }
        return true;
    }

    private void generateMapWithWordLengthsAsKeys() {
        for (String word : wordsFromDB) {
            int wordLength = word.length();
            List<String> strings = wordsMapWithLengthsAsKeys.get(wordLength);

            if (wordsMapWithLengthsAsKeys.containsKey(wordLength)) {
                strings.add(word);
                wordsMapWithLengthsAsKeys.put(wordLength, strings);
            } else {
                wordsMapWithLengthsAsKeys.computeIfAbsent(wordLength, k -> new ArrayList<>()).add(word);  // ?
            }
        }
    }

    private List<String> matchedWordList(String pattern) {
        List<String> matchedWordList = new ArrayList<>();
        int wordLength = pattern.length();
        List<String> strings = wordsMapWithLengthsAsKeys.get(wordLength);
        for (String word : strings) {
            if (check(pattern, word)) {
                matchedWordList.add(word);
            }
        }
        return matchedWordList;
    }


    private boolean checkAstericsPresence(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '*') {
                return true;
            }
        }
        return false;
    }

    private boolean checkWordLength(String str) throws IOException {
        int max = wordsFromDB.stream()
                .mapToInt(String::length)
                .max()
                .getAsInt();

        if (str.length() > max) {
            return false;
        } else {
            return true;
        }
    }

    private List<String> generateWordList(File file) throws IOException {
        try {
            wordsFromDB = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            return wordsFromDB;
        } catch (IOException e) {
            throw new IOException("Failed!");
        }
    }

}

// 2) separate search for different word lengths
//