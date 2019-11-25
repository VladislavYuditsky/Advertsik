package com.yuditsky.advertsik.service;

import com.yuditsky.advertsik.bean.Ad;
import com.yuditsky.advertsik.bean.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AdService {
    Iterable<Ad> takeAds(String filter);

    void saveFile(Ad ad, MultipartFile file) throws IOException;

    void save(Ad ad);

    void updateAdd(User currentUser, Ad ad, String title, String description, MultipartFile file) throws IOException;

    void deleteAd(Ad ad);
}
