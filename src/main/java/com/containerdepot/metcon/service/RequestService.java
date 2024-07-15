package com.containerdepot.metcon.service;

import com.containerdepot.metcon.service.dtos.RequestAddDto;

public interface RequestService {
    boolean add(RequestAddDto data);
}
