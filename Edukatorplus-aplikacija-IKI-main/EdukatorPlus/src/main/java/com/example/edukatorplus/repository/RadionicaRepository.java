package com.example.edukatorplus.repository;

import com.example.edukatorplus.model.Radionica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;


@Repository
public interface RadionicaRepository extends JpaRepository<Radionica, Long> {
    List<Radionica> findByDatumAfter(LocalDate datum);
    List<Radionica> findByDatumBetween(LocalDate start, LocalDate end);

}
