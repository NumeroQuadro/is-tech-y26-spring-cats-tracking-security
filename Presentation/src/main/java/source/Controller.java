package source;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {
    // http://localhost:8080
    // http://numeroquadro.space:8080
    @GetMapping("")
    public String index() {
        return "index";
    }
}
