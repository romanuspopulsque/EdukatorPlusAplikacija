package com.example.edukatorplus.DTO;

public class PrisustvoDTO {

    private Long id;
    private Long radionicaId;
    private Long polaznikId;
    private String status;

    // Getteri i setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRadionicaId() { return radionicaId; }
    public void setRadionicaId(Long radionicaId) { this.radionicaId = radionicaId; }

    public Long getPolaznikId() { return polaznikId; }
    public void setPolaznikId(Long polaznikId) { this.polaznikId = polaznikId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
