package com.experis.worldoffice.batchservice.job.experis;

import com.experis.worldoffice.batchservice.model.entity.Product;
import com.experis.worldoffice.batchservice.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductWriter implements ItemWriter<Product> {

    @Autowired
    private ProductRepository repository;

    private static final Logger LOG = LogManager.getLogger(ProductWriter.class);

    @Override
    public void write(List<? extends Product> items) throws Exception {
        LOG.info("Writing {} registers",items.size());
        repository.saveAll(items);
    }


}