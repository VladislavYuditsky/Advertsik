package com.yuditsky.advertsik.service.impl;

import com.yuditsky.advertsik.bean.Ad;
import com.yuditsky.advertsik.bean.User;
import com.yuditsky.advertsik.repository.AdRepository;
import com.yuditsky.advertsik.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class AdServiceImpl implements AdService {
    @Autowired
    private AdRepository adRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public Iterable<Ad> takeAds(String filter) {
        List<Ad> ads;
        if (filter != null && !filter.isEmpty()) {
            ads = adRepository.findByTitle(filter);
        } else {
            ads = (List<Ad>) adRepository.findAll();
        }
        Collections.reverse(ads);

        return ads;
    }

    @Override
    public void saveFile(Ad ad, MultipartFile file) throws IOException {
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

    @Override
    public void save(Ad ad) {
        adRepository.save(ad);
    }

    @Override
    public void updateAdd(User currentUser,
                          Ad ad,
                          String title,
                          String description,
                          MultipartFile file) throws IOException {
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

    @Override
    public void deleteAd(Ad ad) {
        adRepository.deleteById(ad.getId());
    }
}
