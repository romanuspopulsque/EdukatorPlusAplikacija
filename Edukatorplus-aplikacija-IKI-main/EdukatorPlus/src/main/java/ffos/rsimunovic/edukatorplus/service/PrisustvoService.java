package ffos.rsimunovic.edukatorplus.service;

import ffos.rsimunovic.edukatorplus.DTO.PolaznikDTO;
import ffos.rsimunovic.edukatorplus.DTO.PrisustvoDTO;
import ffos.rsimunovic.edukatorplus.model.Prisustvo;
import ffos.rsimunovic.edukatorplus.model.Polaznik;
import ffos.rsimunovic.edukatorplus.model.PrisustvoStatus;
import ffos.rsimunovic.edukatorplus.model.Radionica;
import ffos.rsimunovic.edukatorplus.repository.PrisustvoRepository;
import ffos.rsimunovic.edukatorplus.repository.PolaznikRepository;
import ffos.rsimunovic.edukatorplus.repository.RadionicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public PrisustvoDTO getById(Long id) {
        return prisustvoRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public PrisustvoDTO create(PrisustvoDTO dto) {
        Optional<Radionica> radionicaOpt = radionicaRepository.findById(dto.radionicaId());
        Optional<Polaznik> polaznikOpt = polaznikRepository.findById(dto.polaznikId());

        if (radionicaOpt.isPresent() && polaznikOpt.isPresent()) {
            Prisustvo prisustvo = new Prisustvo();
            prisustvo.setRadionica(radionicaOpt.get());
            prisustvo.setPolaznik(polaznikOpt.get());
            prisustvo.setStatus(dto.status());
            Prisustvo saved = prisustvoRepository.save(prisustvo);
            return toDTO(saved);
        }
        return null;
    }

    private PrisustvoDTO toDTO(Prisustvo prisustvo) {
        return new PrisustvoDTO(
            prisustvo.getId(),
            prisustvo.getRadionica().getId(),
            prisustvo.getPolaznik().getId(),
            prisustvo.getStatus()
        );
    }

public List<PolaznikDTO> getPolazniciByRadionicaId(Long radionicaId) {
    return prisustvoRepository.findByRadionicaId(radionicaId).stream()
        .map(p -> {
            Polaznik polaznik = p.getPolaznik();
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

    public List<PrisustvoDTO> getByRadionicaId(Long radionicaId) {
        return prisustvoRepository.findByRadionicaId(radionicaId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
public PrisustvoDTO evidentirajPrisustvo(Long radionicaId, Long polaznikId, String statusStr) {
    Optional<Radionica> radionicaOpt = radionicaRepository.findById(radionicaId);
    Optional<Polaznik> polaznikOpt = polaznikRepository.findById(polaznikId);

    if (radionicaOpt.isEmpty() || polaznikOpt.isEmpty()) {
        return null; // možeš baciti i exception ako želiš
    }

    PrisustvoStatus status;
    try {
        status = PrisustvoStatus.valueOf(statusStr.toUpperCase());
    } catch (IllegalArgumentException e) {
        // Status nije validan enum, možeš baciti exception ili vratiti null
        return null;
    }

    Prisustvo prisustvo = new Prisustvo();
    prisustvo.setRadionica(radionicaOpt.get());
    prisustvo.setPolaznik(polaznikOpt.get());
    prisustvo.setStatus(status);

    Prisustvo saved = prisustvoRepository.save(prisustvo);
    return toDTO(saved);
}

public PrisustvoDTO update(Long id, PrisustvoDTO dto) {
    Optional<Prisustvo> opt = prisustvoRepository.findById(id);
    if (opt.isPresent()) {
        Prisustvo p = opt.get();
        // Pretpostavljam da možeš updateati samo status, ali možeš dodati i ostala polja ako trebaš
        p.setStatus(dto.status());
        Prisustvo saved = prisustvoRepository.save(p);
        return toDTO(saved);
    }
    return null;
}

public boolean delete(Long id) {
    Optional<Prisustvo> opt = prisustvoRepository.findById(id);
    if (opt.isPresent()) {
        prisustvoRepository.delete(opt.get());
        return true;
    }
    return false;
}

public List<PrisustvoDTO> getByStatus(String statusStr) {
    PrisustvoStatus status;
    try {
        status = PrisustvoStatus.valueOf(statusStr.toUpperCase());
    } catch (IllegalArgumentException e) {
        return List.of(); // ili baci exception, ovisno o logici
    }

    return prisustvoRepository.findByStatus(status).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
}

}
