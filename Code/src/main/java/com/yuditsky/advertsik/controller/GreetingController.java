package com.yuditsky.advertsik.controller;

import com.yuditsky.advertsik.bean.Ad;
import com.yuditsky.advertsik.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    private AdRepository adRepository;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<Ad> ads = adRepository.findAll();
        model.put("ads", ads);

        return "main";
    }

    @PostMapping
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
