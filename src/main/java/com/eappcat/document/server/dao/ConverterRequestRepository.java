package com.eappcat.document.server.dao;

import com.eappcat.document.server.entity.ConverterRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConverterRequestRepository extends JpaRepository<ConverterRequest,Long> {
    Optional<ConverterRequest> findByRequestNo(String requstNo);
}
