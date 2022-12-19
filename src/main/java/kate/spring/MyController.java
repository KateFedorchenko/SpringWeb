package kate.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController     // extends @Component
public class MyController {

    @GetMapping("/hello-world")     // this handler is available via this URL (!not only URL actually)
    public String helloWorld(){
        return "HelloWorld";
    }

    @GetMapping("/get-data")
    public Map<String,String> getData(){
        return Map.of("foo","bar","gee","bao","lock","see");
    }

    @GetMapping("/greetings")
    public String greetings(@RequestParam String name){
        return "Hello "+ name;
    }


}


// create Controller that will have 2 Handler: addToPurchaseList and showPurchaseList.
// !!!! Controller can be called from many Threads. Thread safe to be made!