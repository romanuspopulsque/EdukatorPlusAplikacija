package com.example.edukatorplus.service;

import com.example.edukatorplus.DTO.PolaznikDTO;
import com.example.edukatorplus.DTO.PrisustvoDTO;
import com.example.edukatorplus.model.Prisustvo;
import com.example.edukatorplus.model.Polaznik;
import com.example.edukatorplus.model.Radionica;
import com.example.edukatorplus.repository.PrisustvoRepository;
import com.example.edukatorplus.repository.PolaznikRepository;
import com.example.edukatorplus.repository.RadionicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrisustvoService {

    @Autowired
    private PrisustvoRepository prisustvoRepository;

    @Autowired
    private PolaznikRepository polaznikRepository;

    @Autowired
    private RadionicaRepository radionicaRepository;

    public List<PrisustvoDTO> getAll() {
        return prisustvoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PrisustvoDTO create(PrisustvoDTO dto) {
        Optional<Radionica> r = radionicaRepository.findById(dto.getRadionicaId());
        Optional<Polaznik> p = polaznikRepository.findById(dto.getPolaznikId());

        if (r.isPresent() && p.isPresent()) {
            Prisustvo prisustvo = new Prisustvo();
            prisustvo.setRadionica(r.get());
            prisustvo.setPolaznik(p.get());
            prisustvo.setStatus(dto.getStatus());
            return toDTO(prisustvoRepository.save(prisustvo));
        }

        return null;
    }

    private PrisustvoDTO toDTO(Prisustvo prisustvo) {
        PrisustvoDTO dto = new PrisustvoDTO();
        dto.setId(prisustvo.getId());
        dto.setRadionicaId(prisustvo.getRadionica().getId());
        dto.setPolaznikId(prisustvo.getPolaznik().getId());
        dto.setStatus(prisustvo.getStatus());
        return dto;
    }
    
    public PrisustvoDTO evidentirajPrisustvo(Long radionicaId, Long polaznikId, String status) {
    Optional<Radionica> radionica = radionicaRepository.findById(radionicaId);
    Optional<Polaznik> polaznik = polaznikRepository.findById(polaznikId);

    if (radionica.isPresent() && polaznik.isPresent()) {
        Prisustvo prisustvo = new Prisustvo();
        prisustvo.setRadionica(radionica.get());
        prisustvo.setPolaznik(polaznik.get());
        prisustvo.setStatus(status);
        return toDTO(prisustvoRepository.save(prisustvo));
    }

    return null;
}
    
public List<PolaznikDTO> getPolazniciByRadionicaId(Long radionicaId) {
    return prisustvoRepository.findByRadionicaId(radionicaId).stream()
            .map(p -> {
                Polaznik polaznik = p.getPolaznik();
                PolaznikDTO dto = new PolaznikDTO();
                dto.setId(polaznik.getId());
                dto.setIme(polaznik.getIme());
                dto.setPrezime(polaznik.getPrezime());
                dto.setGodinaRodenja(polaznik.getGodinaRodenja());
                return dto;
            })
            .collect(Collectors.toList());
}

public List<PrisustvoDTO> getByRadionicaId(Long radionicaId) {
    return prisustvoRepository.findByRadionicaId(radionicaId).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
}



    
}
