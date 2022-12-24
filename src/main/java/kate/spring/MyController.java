package kate.spring;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController     // extends @Component
public class MyController {
    private final Map<String, Integer> purchaseList = new ConcurrentHashMap<>();    // one of the best

    @GetMapping("/show-purchase-list")
    public String showPurchaseList() {
        if (purchaseList.isEmpty()) {       // size to be counted needs to take several blocks; isEmpty -> no blocks
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
        purchaseList.put(name, quantity);
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

    @GetMapping("/get-my-data")
    public MyData getMyData() {
        return new MyData(1, "str", new int[]{1, 2, 3}, 65555555555L);
    }

    @PutMapping("/consume-my-data")
    public void consumeMyData(@RequestBody MyData myData) {
        System.out.println(Arrays.toString(myData.getNums()));

    }
}


// 1) describe a class PurchaseList that has the following properties: author, item, quantity, maxPrice (maybe more than 1 class!)
// 2) several Handlers: addItem(name, quantity, maxPrice, author), getItemListByAuthor(author), removeItemFromList(name, quantity)
// !!! PUT, POST, GET -> think over
// 3) loadPurchaseList(for all authors!)