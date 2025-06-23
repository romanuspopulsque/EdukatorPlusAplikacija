package com.example.edukatorplus.repository;

import com.example.edukatorplus.model.Prisustvo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrisustvoRepository extends JpaRepository<Prisustvo, Long> {
    List<Prisustvo> findByPolaznikId(Long polaznikId);
    List<Prisustvo> findByRadionicaId(Long radionicaId);
    List<Prisustvo> findByRadionicaIdAndStatus(Long radionicaId, String status);
    Long countByRadionicaId(Long radionicaId);

}
