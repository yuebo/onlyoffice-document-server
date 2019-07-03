package com.eappcat.document.server.dao;

import com.eappcat.document.server.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface DocumentRepository extends JpaRepository<Document,Long> {
    Optional<Document> findByDocId(String docId);
}
