package com.experis.worldoffice.batchservice.service;

import com.experis.worldoffice.batchservice.model.entity.Brand;

public interface BrandService {

    Brand findBrandByName(String name);
}
