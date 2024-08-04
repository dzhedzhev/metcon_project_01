package com.containerdepot.metcon.service;

import com.containerdepot.metcon.model.entities.Request;
import com.containerdepot.metcon.service.dtos.imports.RequestAddDto;

import java.util.List;
import java.util.Optional;

public interface RequestService {
    void add(RequestAddDto data);

    List<Request> findAllContainersByIdDesc();

    Optional<Request> findRequestById(long id);

    void delete(Long id);

    void edit(Long id, RequestAddDto data);
}
