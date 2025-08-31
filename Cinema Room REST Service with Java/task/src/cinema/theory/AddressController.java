package cinema.theory;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequestMapping("/addresses")
@RestController
public class AddressController {
    private final Map<String, String> store = new ConcurrentHashMap<>();

    @PostMapping
    public void postAddress(@RequestParam String name, @RequestParam String address) {
        store.put(name, address);
    }

    @GetMapping
    public Map<String, String> getAddresses() {
        return store;
    }

    @DeleteMapping
    public void deleteAddress(@RequestParam String name) {
        store.remove(name);
    }

    @GetMapping("/{name}")
    public String getAddress(@PathVariable String name) {
        return store.get(name);
    }
}
