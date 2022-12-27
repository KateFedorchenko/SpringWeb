package kate.spring.crossword;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CrosswordController {

    /**
     *
     * @param s word that has letters and gaps (?), e.g. "a??l?" could be an "apple"
     * @return
     */
    @GetMapping("get-words")
    public List<String> getWords(@RequestParam String s){
        return null;

    }
}
