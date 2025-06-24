package ffos.rsimunovic.edukatorplus.service;

import ffos.rsimunovic.edukatorplus.DTO.PolaznikDTO;
import ffos.rsimunovic.edukatorplus.DTO.PrisustvoDTO;
import ffos.rsimunovic.edukatorplus.model.Polaznik;
import ffos.rsimunovic.edukatorplus.model.Prisustvo;
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
        return toDTO(polaznikRepository.save(p));
    }

    public PolaznikDTO update(Long id, PolaznikDTO dto) {
        Optional<Polaznik> opt = polaznikRepository.findById(id);
        if (opt.isPresent()) {
            Polaznik p = opt.get();
            p.setIme(dto.ime());
            p.setPrezime(dto.prezime());
            p.setGodinaRodenja(dto.godinaRodenja());
            p.setEmail(dto.email());
            p.setTelefon(dto.telefon());
            return toDTO(polaznikRepository.save(p));
        }
        return null;
    }

    public void delete(Long id) {
        Optional<Polaznik> opt = polaznikRepository.findById(id);
        opt.ifPresent(p -> {
            prisustvoRepository.deleteAll(p.getPrisustva());
            polaznikRepository.delete(p);
        });
    }

    public List<PolaznikDTO> pretraziPoImenuIPrezimenu(String ime, String prezime) {
        return polaznikRepository.findAll().stream()
                .filter(p -> (ime == null || p.getIme().toLowerCase().contains(ime.toLowerCase())) &&
                             (prezime == null || p.getPrezime().toLowerCase().contains(prezime.toLowerCase())))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PrisustvoDTO> getPrisustvaZaPolaznika(Long id) {
        return polaznikRepository.findById(id)
                .map(polaznik -> polaznik.getPrisustva().stream()
                        .map(p -> new PrisustvoDTO(
                                p.getId(),
                                p.getRadionica().getId(),
                                p.getPolaznik().getId(),
                                p.getStatus()
                        ))
                        .collect(Collectors.toList())
                ).orElse(List.of());
    }
}
