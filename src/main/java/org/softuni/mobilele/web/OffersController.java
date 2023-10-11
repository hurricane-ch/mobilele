package org.softuni.mobilele.web;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offers")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class OffersController {

    @GetMapping("/all")
    public String all() {
        return "offers";
    }
}