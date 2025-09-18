package org.bookstore.bookstore.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TestController {

    @ResponseBody
    @GetMapping("/test")
    public String testController() {
        return "testController";
    }
}
