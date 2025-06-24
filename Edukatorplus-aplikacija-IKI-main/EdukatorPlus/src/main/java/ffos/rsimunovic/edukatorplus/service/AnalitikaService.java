package ffos.rsimunovic.edukatorplus.service;

import ffos.rsimunovic.edukatorplus.DTO.PolaznikDTO;
import ffos.rsimunovic.edukatorplus.DTO.RadionicaDTO;
import ffos.rsimunovic.edukatorplus.model.Polaznik;
import ffos.rsimunovic.edukatorplus.model.Prisustvo;
import ffos.rsimunovic.edukatorplus.model.PrisustvoStatus;
import ffos.rsimunovic.edukatorplus.model.Radionica;
import ffos.rsimunovic.edukatorplus.repository.PolaznikRepository;
import ffos.rsimunovic.edukatorplus.repository.PrisustvoRepository;
import ffos.rsimunovic.edukatorplus.repository.RadionicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalitikaService {

    @Autowired
    private PrisustvoRepository prisustvoRepository;

    @Autowired
    private RadionicaRepository radionicaRepository;

    @Autowired
    private PolaznikRepository polaznikRepository;

    public List<RadionicaDTO> getRadioniceByPolaznik(Long polaznikId) {
        return prisustvoRepository.findByPolaznikId(polaznikId).stream()
                .map(p -> p.getRadionica())
                .distinct()
                .map(r -> new RadionicaDTO(
                        r.getId(),
                        r.getNaziv(),
                        r.getOpis(),
                        r.getDatum(),
                        r.getTrajanje()
                ))
                .collect(Collectors.toList());
    }

    public Map<String, Long> getStatistikaPolaznika(Long polaznikId) {
        List<Prisustvo> prisustva = prisustvoRepository.findByPolaznikId(polaznikId);
        long prisutni = prisustva.stream().filter(p -> p.getStatus() == PrisustvoStatus.PRISUTAN).count();
        long odsutni = prisustva.stream().filter(p -> p.getStatus() == PrisustvoStatus.ODSUTAN).count();
        return Map.of("prisutni", prisutni, "odsutni", odsutni);
    }

    public List<RadionicaDTO> getRadioniceBezPolaznika() {
        return radionicaRepository.findAll().stream()
                .filter(r -> r.getPrisustva() == null || r.getPrisustva().isEmpty())
                .map(r -> new RadionicaDTO(
                        r.getId(),
                        r.getNaziv(),
                        r.getOpis(),
                        r.getDatum(),
                        r.getTrajanje()
                ))
                .collect(Collectors.toList());
    }

    public List<PolaznikDTO> getPolazniciBezPrisustva() {
        return polaznikRepository.findAll().stream()
                .filter(p -> p.getPrisustva() == null || p.getPrisustva().isEmpty())
                .map(p -> new PolaznikDTO(
                        p.getId(),
                        p.getIme(),
                        p.getPrezime(),
                        p.getGodinaRodenja(),
                        p.getEmail(),
                        p.getTelefon()
                ))
                .collect(Collectors.toList());
    }
} 