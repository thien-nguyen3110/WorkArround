package org.springframework.samples.petclinic.system;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Welcome Spring boot 3 ";
    }
    @GetMapping("/id")
    public String welcome1() {
        return "Provide university id:  " + ;
    }
}
