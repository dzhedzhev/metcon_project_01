package com.containerdepot.metcon.data;

import com.containerdepot.metcon.model.entities.Container;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContainerRepository extends JpaRepository<Container, Long> {
    Optional<Container> findByNumber(String number);
    Optional<Container> findByNumberAndReleasedNotNull(String number);
    List<Container> findByOrderByReceivedDesc();
    Page<Container> findByOrderByReceivedDesc(Pageable pageable);
    List<Container> findAllByOwnerId(long id);
    boolean existsByNumber(String number);
}
