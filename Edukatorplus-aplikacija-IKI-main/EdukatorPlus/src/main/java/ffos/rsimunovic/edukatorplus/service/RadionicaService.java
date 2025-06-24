package ffos.rsimunovic.edukatorplus.service;

import ffos.rsimunovic.edukatorplus.DTO.PolaznikDTO;
import ffos.rsimunovic.edukatorplus.DTO.RadionicaDTO;
import ffos.rsimunovic.edukatorplus.model.Polaznik;
import ffos.rsimunovic.edukatorplus.model.Prisustvo;
import ffos.rsimunovic.edukatorplus.model.PrisustvoStatus;
import ffos.rsimunovic.edukatorplus.model.Radionica;
import ffos.rsimunovic.edukatorplus.repository.PrisustvoRepository;
import ffos.rsimunovic.edukatorplus.repository.RadionicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RadionicaService {

    @Autowired
    private RadionicaRepository radionicaRepository;

    @Autowired
    private PrisustvoRepository prisustvoRepository;

    public List<RadionicaDTO> getAll() {
        return radionicaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public RadionicaDTO getById(Long id) {
        return radionicaRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public RadionicaDTO create(RadionicaDTO dto) {
        Radionica r = new Radionica();
        r.setNaziv(dto.naziv());
        r.setOpis(dto.opis());
        r.setDatum(dto.datum());
        r.setTrajanje(dto.trajanje());
        Radionica saved = radionicaRepository.save(r);
        return toDTO(saved);
    }

    public void delete(Long id) {
        Optional<Radionica> opt = radionicaRepository.findById(id);
        if (opt.isPresent()) {
            Radionica r = opt.get();
            prisustvoRepository.deleteAll(r.getPrisustva()); // prvo brišemo prisustva da ne ostanu orphani
            radionicaRepository.delete(r);
        }
    }

    private RadionicaDTO toDTO(Radionica r) {
        return new RadionicaDTO(
                r.getId(),
                r.getNaziv(),
                r.getOpis(),
                r.getDatum(),
                r.getTrajanje()
        );
    }

    public Map<String, Long> getStatistika(Long radionicaId) {
        List<Prisustvo> prisustva = prisustvoRepository.findByRadionicaId(radionicaId);
        if (prisustva == null || prisustva.isEmpty()) {
            return Map.of("prisutni", 0L, "odsutni", 0L);
        }
        long prisutni = prisustva.stream()
                .filter(p -> p.getStatus() == PrisustvoStatus.PRISUTAN)
                .count();
        long odsutni = prisustva.size() - prisutni;
        return Map.of("prisutni", prisutni, "odsutni", odsutni);
    }

    public List<PolaznikDTO> getPolazniciByRadionicaId(Long radionicaId) {
        return prisustvoRepository.findByRadionicaId(radionicaId).stream()
                .map(prisustvo -> {
                    Polaznik polaznik = prisustvo.getPolaznik();
                    return new PolaznikDTO(
                            polaznik.getId(),
                            polaznik.getIme(),
                            polaznik.getPrezime(),
                            polaznik.getGodinaRodenja(),
                            polaznik.getEmail(),
                            polaznik.getTelefon()
                    );
                })
                .collect(Collectors.toList());
    }

    public List<RadionicaDTO> getNadolazece() {
        LocalDate danas = LocalDate.now();
        return radionicaRepository.findByDatumAfter(danas).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<RadionicaDTO> pretraziPoDatumu(LocalDate od, LocalDate doDatuma) {
        return radionicaRepository.findByDatumBetween(od, doDatuma).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public RadionicaDTO update(Long id, RadionicaDTO dto) {
    Optional<Radionica> opt = radionicaRepository.findById(id);
    if (opt.isPresent()) {
        Radionica r = opt.get();
        r.setNaziv(dto.naziv());
        r.setOpis(dto.opis());
        r.setDatum(dto.datum());
        r.setTrajanje(dto.trajanje());
        Radionica saved = radionicaRepository.save(r);
        return toDTO(saved);
    }
    return null;
}

}
