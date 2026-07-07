package com.nidus.repository;

import com.nidus.model.Analise;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface AnaliseRepository extends JpaRepository<Analise, UUID> {
    List<Analise> findAllByOrderByCriadoEmDesc();
}
