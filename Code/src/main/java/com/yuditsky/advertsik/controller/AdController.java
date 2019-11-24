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

    @PostMapping("/")
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

            model.addAttribute("ad", null);

            adService.save(ad);
        }
        Iterable<Ad> ads = adService.takeAds("");
        model.addAttribute("ads", ads);

        return "main";
    }

    @PostMapping("/user-ads/{user}")
    public String updateAd(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam("id") Ad ad,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file) throws IOException {
        adService.updateAdd(currentUser, ad, title, description, file);

        return "redirect:/user-ads/" + user;
    }

    @GetMapping("/user-ads/{user}")
    public String userAds(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Ad ad) { ///////
        Set<Ad> ads = user.getAds();
        model.addAttribute("ads", ads);

        model.addAttribute("ad", ad);

        model.addAttribute("isCurrentUser", currentUser.equals(user));

        return "userAds";
    }
}
