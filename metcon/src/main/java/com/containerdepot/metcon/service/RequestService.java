package com.containerdepot.metcon.service;

import com.containerdepot.metcon.model.entities.Request;
import com.containerdepot.metcon.service.dtos.imports.RequestAddDto;

import java.util.List;
import java.util.Optional;

public interface RequestService {
    boolean add(RequestAddDto data);

    List<Request> findAllContainersByIdDesc();

    Optional<Request> findRequestById(long id);

    void delete(Long id);

    boolean edit(Long id, RequestAddDto data);
}