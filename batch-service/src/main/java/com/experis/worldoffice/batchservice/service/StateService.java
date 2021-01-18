package com.experis.worldoffice.batchservice.service;

import com.experis.worldoffice.batchservice.model.entity.Brand;
import com.experis.worldoffice.batchservice.model.entity.State;

public interface StateService {

    State findStateByName(String name);
}
