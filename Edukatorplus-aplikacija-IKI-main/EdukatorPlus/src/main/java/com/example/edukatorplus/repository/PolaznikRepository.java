package com.example.edukatorplus.repository;

import com.example.edukatorplus.model.Polaznik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolaznikRepository extends JpaRepository<Polaznik, Long> {
    // možeš dodati custom metode ako bude trebalo
}
