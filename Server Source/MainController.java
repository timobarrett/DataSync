package fivetwentysix.ware.com.dataSync;

/**
 * Created by tim on 1/29/2017.
*/
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "Test Code by fivetwentysix.ware " +
                "<a href='http://news.google.com/'>actotracker</a> :)";
    }

}
