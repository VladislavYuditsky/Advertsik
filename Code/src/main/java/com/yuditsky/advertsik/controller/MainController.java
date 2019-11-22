package com.yuditsky.advertsik.controller;

import com.yuditsky.advertsik.domain.Ad;
import com.yuditsky.advertsik.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private AdRepository adRepository;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        Iterable<Ad> ads = adRepository.findAll();
        model.put("ads", ads);

        return "main";
    }

    @PostMapping("/main")
    public String add(@RequestParam String title, @RequestParam String description, Map<String, Object> model) {
        Ad ad = new Ad(title, description);

        adRepository.save(ad);

        Iterable<Ad> ads = adRepository.findAll();
        model.put("ads", ads);

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Ad> ads;

        if (filter != null && !filter.isEmpty()) {
            ads = adRepository.findByTitle(filter);
        } else {
            ads = adRepository.findAll();
        }

        model.put("ads", ads);
        return "main";
    }

}
