package com.yuditsky.advertsik.controller;

import com.yuditsky.advertsik.domain.User;
import com.yuditsky.advertsik.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("confirmationPassword") String confirmationPassword,
            @Valid User user,
            BindingResult bindingResult,
            Model model) {
        boolean isConfirmEmpty = StringUtils.isEmpty(confirmationPassword);
        if (isConfirmEmpty) {
            model.addAttribute("confirmationPasswordError", "Confirmation password can't be empty");
        }

        if (user.getPassword() != null && !user.getPassword().equals(confirmationPassword)) {
            model.addAttribute("passwordError", "Password are different!");
        }

        if (isConfirmEmpty || bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtil.getErrors(bindingResult);
            model.mergeAttributes(errors);

            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");

            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code) {
        userService.activateUser(code);

        return "login";
    }
}
