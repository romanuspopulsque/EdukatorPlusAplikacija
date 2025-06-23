package ffos.rsimunovic.edukatorplus.DTO;

import ffos.rsimunovic.edukatorplus.model.PrisustvoStatus;

public record PrisustvoDTO(
    Long id,
    Long radionicaId,
    Long polaznikId,
    PrisustvoStatus status
) {}
