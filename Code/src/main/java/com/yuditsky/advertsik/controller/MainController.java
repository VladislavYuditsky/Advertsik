package com.yuditsky.advertsik.controller;

import com.yuditsky.advertsik.domain.Ad;
import com.yuditsky.advertsik.domain.User;
import com.yuditsky.advertsik.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Controller
public class MainController {
    @Autowired
    private AdRepository adRepository;

    @Value("${upload.path}")
    private String uploadPath;

    //@GetMapping("/")
    //public String greeting(Map<String, Object> model) {
    //    return "greeting";
    //}

    @GetMapping("/")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Ad> ads;

        if (filter != null && !filter.isEmpty()) {
            ads = adRepository.findByTitle(filter);
        } else {
            ads = adRepository.findAll();
        }

        model.addAttribute("ads", ads);
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/")
    public String add(
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
            saveFile(ad, file);

            model.addAttribute("ad", null);

            adRepository.save(ad);
        }

        Iterable<Ad> ads = adRepository.findAll();
        model.addAttribute("ads", ads);

        return "main";
    }

    private void saveFile(@Valid Ad ad, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            ad.setFilename(resultFilename);
        }
    }

    @GetMapping("/user-ads/{user}")
    public String userAds(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Ad ad) {
        Set<Ad> ads = user.getAds();

        model.addAttribute("ads", ads);
        model.addAttribute("ad", ad);
        model.addAttribute("isCurrentUser", currentUser.equals(user));

        return "userAds";
    }

    @PostMapping("/user-ads/{user}")
    public String updateAd(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam("id") Ad ad,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if(ad.getAuthor().equals(currentUser)){
            if(!StringUtils.isEmpty(title)){
                ad.setTitle(title);
            }

            if(!StringUtils.isEmpty(description)){
                ad.setDescription(description);
            }

            saveFile(ad, file);

            adRepository.save(ad);
        }
        return "redirect:/user-ads/" + user;
    }
}
