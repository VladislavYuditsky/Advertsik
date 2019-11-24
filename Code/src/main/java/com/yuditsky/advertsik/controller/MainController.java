package com.yuditsky.advertsik.controller;

import com.yuditsky.advertsik.domain.Ad;
import com.yuditsky.advertsik.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    private AdService adService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Ad> ads = adService.takeAds(filter);
        model.addAttribute("ads", ads);

        model.addAttribute("filter", filter);

        return "main";
    }
}
