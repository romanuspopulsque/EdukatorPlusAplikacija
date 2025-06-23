package com.example.edukatorplus.service;

import com.example.edukatorplus.DTO.PolaznikDTO;
import com.example.edukatorplus.DTO.RadionicaDTO;
import com.example.edukatorplus.model.Polaznik;
import com.example.edukatorplus.model.Prisustvo;
import com.example.edukatorplus.model.Radionica;
import com.example.edukatorplus.repository.PrisustvoRepository;
import com.example.edukatorplus.repository.RadionicaRepository;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RadionicaService {

    @Autowired
    private RadionicaRepository radionicaRepository;

    @Autowired
    private PrisustvoRepository prisustvoRepository; // ⬅️ ovo je falilo

    public List<RadionicaDTO> getAll() {
        return radionicaRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public RadionicaDTO getById(Long id) {
        Optional<Radionica> opt = radionicaRepository.findById(id);
        return opt.map(this::toDTO).orElse(null);
    }

    public RadionicaDTO create(RadionicaDTO dto) {
        Radionica r = new Radionica();
        r.setNaziv(dto.getNaziv());
        r.setOpis(dto.getOpis());
        r.setDatum(dto.getDatum());
        r.setTrajanje(dto.getTrajanje());
        return toDTO(radionicaRepository.save(r));
    }

    public void delete(Long id) {
        radionicaRepository.deleteById(id);
    }

    private RadionicaDTO toDTO(Radionica r) {
        RadionicaDTO dto = new RadionicaDTO();
        dto.setId(r.getId());
        dto.setNaziv(r.getNaziv());
        dto.setOpis(r.getOpis());
        dto.setDatum(r.getDatum());
        dto.setTrajanje(r.getTrajanje());
        return dto;
    }

    public Map<String, Long> getStatistika(Long radionicaId) {
        List<Prisustvo> prisustva = prisustvoRepository.findByRadionicaId(radionicaId); // ⬅️ ovdje ispravno korištenje
        long prisutni = prisustva.stream().filter(p -> "da".equalsIgnoreCase(p.getStatus())).count();
        long odsutni = prisustva.size() - prisutni;
        return Map.of("prisutni", prisutni, "odsutni", odsutni);
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
    
    public List<RadionicaDTO> getNadolazece() {
    LocalDate danas = LocalDate.now();
    return radionicaRepository.findByDatumAfter(danas)
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
}
    public List<RadionicaDTO> pretraziPoDatumu(LocalDate od, LocalDate doDatuma) {
    return radionicaRepository.findByDatumBetween(od, doDatuma).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
}


}
