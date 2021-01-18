package com.experis.worldoffice.batchservice.service.impl;

import com.experis.worldoffice.batchservice.model.entity.Brand;
import com.experis.worldoffice.batchservice.repository.BrandRepository;
import com.experis.worldoffice.batchservice.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;


    @Override
    public Brand findBrandByName(String name) {
        return brandRepository.findByNameIgnoreCase(name).orElse(null);
    }
}
