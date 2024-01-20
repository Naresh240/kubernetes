package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {
    
    @RequestMapping("/")
    public String welcome() {
        return "Welcome to Devops Course at NareshLabs..!!!";
    }

    @RequestMapping("/hello")
    public String hello() {
        return "Hello Everyone, welcome to our training on AWS and Devops..!!!";
    }
}
