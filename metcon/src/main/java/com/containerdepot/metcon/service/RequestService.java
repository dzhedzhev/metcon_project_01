package com.containerdepot.metcon.service;

import com.containerdepot.metcon.model.entities.Request;
import com.containerdepot.metcon.service.dtos.imports.RequestAddDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

import java.util.List;
import java.util.Optional;

public interface RequestService {
    void add(RequestAddDto data, String username);
    List<Request> findAllRequestsByIdDesc();
    PagedModel<Request> findAllRequestsByIdDesc(Pageable pageable, int pageNumber);
    Optional<Request> findRequestById(long id);
    void delete(Long id);
    void edit(Long id, RequestAddDto data);
}
