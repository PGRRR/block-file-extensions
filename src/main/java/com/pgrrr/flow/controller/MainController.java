package com.pgrrr.flow.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    /**
     * Thymeleaf 통한 메인 페이지 접속
     *
     * @return 메인 페이지
     */
    @GetMapping
    public String loadMainPage() {
        return "index";
    }
}
