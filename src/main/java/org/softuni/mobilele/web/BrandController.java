package org.softuni.mobilele.web;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class BrandController {
    @GetMapping("/brands")
    public String allBrands() {
        return "brands";
    }

}