package top.sssd.ddns4j.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sssd
 * @careate 2023-10-08-16:30
 */
@RestController
public class TestController {

    @GetMapping("hello")
    public String hello(){
        return "hello world";
    }
}
