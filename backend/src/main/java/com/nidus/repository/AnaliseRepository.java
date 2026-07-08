package com.nidus.repository;

import com.nidus.model.Analise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AnaliseRepository extends JpaRepository<Analise, UUID> {
    Page<Analise> findAllByOrderByCriadoEmDesc(Pageable pageable);
}
