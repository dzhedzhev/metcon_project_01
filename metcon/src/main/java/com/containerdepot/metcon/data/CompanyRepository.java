package com.containerdepot.metcon.data;

import com.containerdepot.metcon.model.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByNameEn(String company);
    boolean existsByNameEnOrVatNumberOrEmailOrPhoneNumber(String nameEn, String vat, String email, String phone);

    boolean existsByNameEn(String name);

    boolean existsByVatNumber(String vatNumber);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phone);
}
