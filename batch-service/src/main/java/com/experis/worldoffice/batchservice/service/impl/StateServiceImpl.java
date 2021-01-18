package com.experis.worldoffice.batchservice.service.impl;

import com.experis.worldoffice.batchservice.model.entity.State;
import com.experis.worldoffice.batchservice.repository.StateRepository;
import com.experis.worldoffice.batchservice.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateServiceImpl implements StateService {

    @Autowired
    private StateRepository stateRepository;


    @Override
    public State findStateByName(String name) {
        return stateRepository.findByNameIgnoreCase(name).orElse(null);
    }
}
