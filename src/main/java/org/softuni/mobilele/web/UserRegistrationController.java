package org.softuni.mobilele.web;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.softuni.mobilele.model.dto.CreateOfferDTO;
import org.softuni.mobilele.model.dto.ReCaptchaResponseDTO;
import org.softuni.mobilele.model.dto.UserRegistrationDTO;
import org.softuni.mobilele.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@RequestMapping("/users")
@Controller
public class UserRegistrationController {
    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register() {
        return "auth-register";
    }

    @PostMapping("/register")
    public String register(UserRegistrationDTO userRegistrationDTO) {
                           //                           @RequestParam("g-recaptcha-response") String reCaptchaResponse) {

//        boolean isBot = !reCaptchaService
//                .verify(reCaptchaResponse)
//                .map(ReCaptchaResponseDTO::isSuccess)
//                .orElse(false);
//
//        if (isBot) {
//            return "redirect:/";
//        }

        userService.registerUser(userRegistrationDTO);

        return "redirect:/";
    }
}