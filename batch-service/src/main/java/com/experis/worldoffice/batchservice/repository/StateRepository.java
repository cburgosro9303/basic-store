package com.experis.worldoffice.batchservice.repository;

import com.experis.worldoffice.batchservice.model.entity.State;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface StateRepository extends PagingAndSortingRepository<State,Long> {

    Optional<State> findByNameIgnoreCase(String name);
}
