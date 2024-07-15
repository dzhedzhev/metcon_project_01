package com.containerdepot.metcon.data;

import com.containerdepot.metcon.model.entities.Request;
import com.containerdepot.metcon.model.enums.RequestEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
    boolean existsByTypeAndContainerNumber(RequestEnum type, String containerNumber);
}
