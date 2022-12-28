package kate.spring.crossword;

import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@RestController
public class CrosswordController {
    private File file;
    private static List<String> lines;

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
            throw new RemoteException("The word format is not allowed!");
        }


        return null;

    }

    private List<String> getMatchedList(String s) {
        List<String> newList = new ArrayList<>();
        for (String line : lines) {
            if (s.length() == line.length()) {
                newList.add(line);
            }
        }
        return newList;
    }

    private void compileRegex(){

    }


    private boolean checkAstericsPresence(String str) {
        char[] chars = str.toCharArray();
        for (char c : chars) {
            if (c == '*') {
                return true;
            }
        }
        return false;
    }

    private boolean checkWordLength(String str) throws IOException {
        file = ResourceUtils.getFile("classpath:DBWords.txt");
        generateWordList(file);
        int max = lines.stream()
                .map(String::length)
                .max(Integer::compare)
                .get();
        if (str.length() > max) {
            return false;
        } else {
            return true;
        }
    }

    private static List<String> generateWordList(File file) throws IOException {
        try {
            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            return lines;
        } catch (IOException e) {
            throw new IOException("Failed!");
        }
    }

}
