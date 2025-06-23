/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.edukatorplus.model;

/**
 *
 * @author ROMAN
 */
import jakarta.persistence.*;

@Entity
public class Prisustvo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "radionica_id")
    private Radionica radionica;

    @ManyToOne
    @JoinColumn(name = "polaznik_id")
    private Polaznik polaznik;

    private String status; // "prisutan", "odsutan", "opravdano"

    // Getteri i setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Radionica getRadionica() { return radionica; }
    public void setRadionica(Radionica radionica) { this.radionica = radionica; }

    public Polaznik getPolaznik() { return polaznik; }
    public void setPolaznik(Polaznik polaznik) { this.polaznik = polaznik; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
