package xyz.wongs.drunkard.chat.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("rest")
@RestController
public class WebController {

    /**
     * test
     */
    @GetMapping("test")
    public Map<String, Object> gs() {
        HashMap<String, Object> map = new HashMap<String, Object>() {
            {
                put("name", "test");
                put("age", 20);
            }
        };
        return map;
    }

    /**
     * test
     */
    @GetMapping("ind")
    public String wf() {
        return "ind";
    }
}
