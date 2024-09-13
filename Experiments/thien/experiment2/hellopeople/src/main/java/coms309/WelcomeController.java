package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple Hello World Controller to display the string returned
 *
 * @author Vivek Bengre
 */

@RestController
class WelcomeController {
    @GetMapping("/")
    public String welcome() {return "Hello and welcome to COMS 309";}


    @GetMapping("/experiment1")
    public String welcome1() {return "This is hello world file";}

}
