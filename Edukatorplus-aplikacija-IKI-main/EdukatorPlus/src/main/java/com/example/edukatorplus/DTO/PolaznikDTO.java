package com.example.edukatorplus.DTO;

public class PolaznikDTO {

    private Long id;
    private String ime;
    private String prezime;
    private int godinaRodenja;

    // Getteri i setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }

    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }

    public int getGodinaRodenja() { return godinaRodenja; }
    public void setGodinaRodenja(int godinaRodenja) { this.godinaRodenja = godinaRodenja; }
}
