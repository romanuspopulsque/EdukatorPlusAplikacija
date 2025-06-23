package ffos.rsimunovic.edukatorplus.DTO;

import java.time.LocalDate;

public record RadionicaDTO(
    Long id,
    String naziv,
    String opis,
    LocalDate datum,
    int trajanje
) {}
