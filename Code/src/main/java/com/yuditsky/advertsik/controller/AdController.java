package com.yuditsky.advertsik.controller;

import com.yuditsky.advertsik.domain.Ad;
import com.yuditsky.advertsik.domain.User;
import com.yuditsky.advertsik.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Controller
public class AdController {
    @Autowired
    private AdService adService;

    @GetMapping("/new-ad")
    public String newAd(){
        return "newAd";
    }

    @PostMapping("/new-ad")
    public String addAd(
            @AuthenticationPrincipal User user,
            @Valid Ad ad,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file) throws IOException {
        ad.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtil.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);

            model.addAttribute("ad", ad);
        } else {
            adService.saveFile(ad, file);

            adService.save(ad);

            return "redirect:/";
        }

        return "newAd";
    }

    @GetMapping("/ad-editor")
    public String edit(@RequestParam Ad ad, Model model) {
        model.addAttribute("ad", ad);

        return "adEditor";
    }

    @PostMapping("/ad-editor")
    public String updateAd(
            @AuthenticationPrincipal User currentUser,
            @Valid Ad ad,
            BindingResult bindingResult,
            Model model,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtil.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);

            model.addAttribute("ad", ad);
        } else {
            adService.updateAdd(currentUser, ad, title, description, file);

            User user = ad.getAuthor();
            return "redirect:/user-ads/" + user.getId();
        }
        return "adEditor";
    }

    @GetMapping("/user-ads/{user}")
    public String userAds(@PathVariable User user, Model model) {
        Set<Ad> ads = user.getAds();
        model.addAttribute("ads", ads);

        return "userAds";
    }
}
