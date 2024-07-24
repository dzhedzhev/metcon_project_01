package com.containerdepot.metcon.data;

import com.containerdepot.metcon.model.entities.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContainerRepository extends JpaRepository<Container, Long> {
    Optional<Container> findByNumber(String number);
    List<Container> findByOrderByReceivedDesc();
    List<Container> findAllByOwnerId(long id);
}
