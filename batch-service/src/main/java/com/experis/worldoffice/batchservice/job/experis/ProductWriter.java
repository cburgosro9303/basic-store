package com.experis.worldoffice.batchservice.job.experis;

import com.experis.worldoffice.batchservice.model.entity.Product;
import com.experis.worldoffice.batchservice.repository.ProductRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ProductWriter implements ItemWriter<Product> {

    @Autowired
    private ProductRepository respository;

    @Override
    @Transactional
    @Modifying(flushAutomatically = true)
    public void write(List<? extends Product> items) throws Exception {
        respository.saveAll(items);
    }


}