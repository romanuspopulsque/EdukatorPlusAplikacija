/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.edukatorplus.service;

/**
 *
 * @author ROMAN
 */
import com.example.edukatorplus.DTO.PolaznikDTO;
import com.example.edukatorplus.model.Polaznik;
import com.example.edukatorplus.repository.PolaznikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PolaznikService {

    @Autowired
    private PolaznikRepository polaznikRepository;

    public List<PolaznikDTO> getAll() {
        return polaznikRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PolaznikDTO getById(Long id) {
        Optional<Polaznik> opt = polaznikRepository.findById(id);
        return opt.map(this::toDTO).orElse(null);
    }

    public PolaznikDTO create(PolaznikDTO dto) {
        Polaznik p = new Polaznik();
        p.setIme(dto.getIme());
        p.setPrezime(dto.getPrezime());
        p.setGodinaRodenja(dto.getGodinaRodenja());
        return toDTO(polaznikRepository.save(p));
    }

    public void delete(Long id) {
        polaznikRepository.deleteById(id);
    }

    private PolaznikDTO toDTO(Polaznik p) {
        PolaznikDTO dto = new PolaznikDTO();
        dto.setId(p.getId());
        dto.setIme(p.getIme());
        dto.setPrezime(p.getPrezime());
        dto.setGodinaRodenja(p.getGodinaRodenja());
        return dto;
    }
}

