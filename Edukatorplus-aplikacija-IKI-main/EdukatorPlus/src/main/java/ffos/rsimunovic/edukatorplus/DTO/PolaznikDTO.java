package ffos.rsimunovic.edukatorplus.DTO;

public record PolaznikDTO(
    Long id,
    String ime,
    String prezime,
    int godinaRodenja,
    String email,
    String telefon
) {}
