package com.techbox.productservice.repository;

import com.techbox.productservice.model.entity.State;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StateRepository extends PagingAndSortingRepository<State,Long> {

}
