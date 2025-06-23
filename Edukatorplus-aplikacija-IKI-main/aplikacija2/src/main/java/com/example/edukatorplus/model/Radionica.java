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
import java.time.LocalDate;
import java.util.List;

@Entity
public class Radionica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;

    private String opis;

    private LocalDate datum;

    private int trajanje; // u satima

    @OneToMany(mappedBy = "radionica", cascade = CascadeType.ALL)
    private List<Prisustvo> prisustva;

    // Getteri i setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }

    public String getOpis() { return opis; }
    public void setOpis(String opis) { this.opis = opis; }

    public LocalDate getDatum() { return datum; }
    public void setDatum(LocalDate datum) { this.datum = datum; }

    public int getTrajanje() { return trajanje; }
    public void setTrajanje(int trajanje) { this.trajanje = trajanje; }

    public List<Prisustvo> getPrisustva() { return prisustva; }
    public void setPrisustva(List<Prisustvo> prisustva) { this.prisustva = prisustva; }
}
