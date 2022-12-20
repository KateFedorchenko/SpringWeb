package kate.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController     // extends @Component
public class MyController {
    private Map<String, Integer> purchaseList = new HashMap<>();


    @GetMapping("/show-purchase-list")
    public String showPurchaseList() {
        if (purchaseList.size() == 0) {
            return "The list is empty";
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> item : purchaseList.entrySet()) {
            sb.append(item);
        }
        return sb.toString();
    }

    @GetMapping("/add-item")
    public String addItem(@RequestParam String name, @RequestParam Integer quantity) {
        purchaseList.put(name,quantity);
        return showPurchaseList();
    }



    @GetMapping("/hello-world")     // this handler is available via this URL (!not only URL actually)
    public String helloWorld() {
        return "HelloWorld";
    }

    @GetMapping("/get-data")
    public Map<String, String> getData() {
        return Map.of("foo", "bar", "gee", "bao", "lock", "see");
    }

    @GetMapping("/greetings")
    public String greetings(@RequestParam String name) {
        return "Hello " + name;
    }
}


// create Controller that will have 2 Handler: addToPurchaseList and showPurchaseList.
// !!!! Controller can be called from many Threads. Thread safe to be made!