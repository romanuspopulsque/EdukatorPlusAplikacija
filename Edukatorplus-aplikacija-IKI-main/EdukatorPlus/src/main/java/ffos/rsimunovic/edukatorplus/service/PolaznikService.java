package ffos.rsimunovic.edukatorplus.service;

import ffos.rsimunovic.edukatorplus.DTO.PolaznikDTO;
import ffos.rsimunovic.edukatorplus.model.Polaznik;
import ffos.rsimunovic.edukatorplus.repository.PolaznikRepository;
import ffos.rsimunovic.edukatorplus.repository.PrisustvoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PolaznikService {

    @Autowired
    private PolaznikRepository polaznikRepository;

    @Autowired
    private PrisustvoRepository prisustvoRepository;

    // Mapiranje modela u DTO
    private PolaznikDTO toDTO(Polaznik p) {
        return new PolaznikDTO(
            p.getId(),
            p.getIme(),
            p.getPrezime(),
            p.getGodinaRodenja(),
            p.getEmail(),
            p.getTelefon()
        );
    }

    public List<PolaznikDTO> getAll() {
        return polaznikRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PolaznikDTO getById(Long id) {
        return polaznikRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public PolaznikDTO create(PolaznikDTO dto) {
        Polaznik p = new Polaznik();
        p.setIme(dto.ime());
        p.setPrezime(dto.prezime());
        p.setGodinaRodenja(dto.godinaRodenja());
        p.setEmail(dto.email());
        p.setTelefon(dto.telefon());
        Polaznik saved = polaznikRepository.save(p);
        return toDTO(saved);
    }

    public void delete(Long id) {
        Optional<Polaznik> opt = polaznikRepository.findById(id);
        opt.ifPresent(p -> {
            prisustvoRepository.deleteAll(p.getPrisustva());
            polaznikRepository.delete(p);
        });
    }
}
