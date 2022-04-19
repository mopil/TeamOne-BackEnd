package com.mjuteam2.TeamOne.util;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class SwaggerRedirector {
    @GetMapping("/api")
    public String redirect() {
        return "redirect:/swagger-ui/#";
    }
}
