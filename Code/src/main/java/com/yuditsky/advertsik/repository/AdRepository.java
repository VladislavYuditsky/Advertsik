package com.yuditsky.advertsik.repository;

import com.yuditsky.advertsik.bean.Ad;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdRepository extends CrudRepository<Ad, Integer> {
    //List<Ad> findAdByTitle(String title);
    List<Ad> findByTitle(String title);

}
