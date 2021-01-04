package hu.pazsitz.webflux_sse.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE, name = "/hello")
    public String hello() {
        return "hello";
    }
}
