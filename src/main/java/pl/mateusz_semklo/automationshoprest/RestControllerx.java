package pl.mateusz_semklo.automationshoprest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllerx {
    @GetMapping("/alfa")
    public String alfa(){
        return "alfa";
    }
    @GetMapping("/beta")
    public String beta(){
        return "beta";
    }
    @GetMapping("/")
    public String home(){
        return "home";
    }
}
