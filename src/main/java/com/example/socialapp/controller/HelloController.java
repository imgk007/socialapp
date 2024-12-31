package com.example.socialapp.controller;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloController {

    private MessageSource messageSource;
    public HelloController(MessageSource messageSource){
        this.messageSource=messageSource;
    }

    @GetMapping("/hello")
    public String helloWorld () {
        return "Hi, it works GK!!!!!";
    }

    //internationalization-> change of language using header Accept-Language
    @GetMapping("/helloi18n")
    public String helloWorldInternationalization () {
        Locale locale= LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message",null,"Default Message",locale);
    }

}
