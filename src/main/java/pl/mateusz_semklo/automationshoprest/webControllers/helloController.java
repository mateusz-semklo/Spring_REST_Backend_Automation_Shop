package pl.mateusz_semklo.automationshoprest.webControllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class helloController {
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String index(){
        return "index";
    }
}
