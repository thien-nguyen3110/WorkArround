package coms309;

/**
 * Controller used to showcase what happens when an exception is thrown
 *
 * @author Vivek Bengre
 */

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
class ExceptionController {

    @RequestMapping(method = RequestMethod.GET, path = "/oops")
    public String triggerException() {
        throw new RuntimeException("Check to see what happens when an exception is thrown");
    }

    @GetMapping("/hayyo")
    public ResponseEntity<String> handleRequest(@RequestParam("name") String name) {
        if (!name.equals("Thien")) {
//            throw new IllegalArgumentException("You are not me");
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok("Hello " + name);
        }
    }

}





