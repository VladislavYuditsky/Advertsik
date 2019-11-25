package com.yuditsky.advertsik.service;

import com.yuditsky.advertsik.domain.Ad;
import com.yuditsky.advertsik.domain.User;
import com.yuditsky.advertsik.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class AdService {
    @Autowired
    private AdRepository adRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public Iterable<Ad> takeAds(@RequestParam(required = false, defaultValue = "") String filter) {
        List<Ad> ads;
        if (filter != null && !filter.isEmpty()) {
            ads = adRepository.findByTitle(filter);
        } else {
            ads = (List<Ad>) adRepository.findAll();
        }
        Collections.reverse(ads);

        return ads;
    }

    public void saveFile(@Valid Ad ad, @RequestParam("file") MultipartFile file) throws IOException {
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

    public void save(@Valid Ad ad) {
        adRepository.save(ad);
    }

    public void updateAdd(@AuthenticationPrincipal User currentUser,
                          @RequestParam("id") Ad ad,
                          @RequestParam("title") String title,
                          @RequestParam("description") String description,
                          @RequestParam("file") MultipartFile file) throws IOException {
        if (ad.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(title)) {
                ad.setTitle(title);
            }

            if (!StringUtils.isEmpty(description)) {
                ad.setDescription(description);
            }

            saveFile(ad, file);

            save(ad);
        }
    }

    public void deleteAd(Ad ad) {
        adRepository.deleteById(ad.getId());
    }
}
