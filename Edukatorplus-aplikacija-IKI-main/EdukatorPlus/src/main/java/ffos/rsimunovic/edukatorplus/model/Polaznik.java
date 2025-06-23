/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffos.rsimunovic.edukatorplus.model;

/**
 *
 * @author ROMAN
 */
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Polaznik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ime;

    private String prezime;

    private int godinaRodenja;

    private String email;

    private String telefon;

    @OneToMany(mappedBy = "polaznik", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prisustvo> prisustva;

    // Getteri i setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }

    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }

    public int getGodinaRodenja() { return godinaRodenja; }
    public void setGodinaRodenja(int godinaRodenja) { this.godinaRodenja = godinaRodenja; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    public List<Prisustvo> getPrisustva() { return prisustva; }
    public void setPrisustva(List<Prisustvo> prisustva) { this.prisustva = prisustva; }
}