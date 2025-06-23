package com.example.edukatorplus.service;

import com.example.edukatorplus.model.Polaznik;
import com.example.edukatorplus.model.Radionica;
import com.example.edukatorplus.model.Prisustvo;
import com.example.edukatorplus.repository.PolaznikRepository;
import com.example.edukatorplus.repository.RadionicaRepository;
import com.example.edukatorplus.repository.PrisustvoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import net.datafaker.Faker;

@Service
public class DevDataService {

    @Autowired
    private PolaznikRepository polaznikRepo;

    @Autowired
    private RadionicaRepository radionicaRepo;

    @Autowired
    private PrisustvoRepository prisustvoRepo;
    
    @Autowired
    private RadionicaRepository radionicaRepository;


    private final Faker faker = new Faker(new Locale("hr"));
    private final Random rand = new Random();

    private final String[] hrvatskiNazivi = {   
        "Uvod u programiranje", "Osnove prve pomoći", "Radionica kreativnog pisanja",
        "Digitalni marketing za početnike", "Učenje kroz igru", "Zdrav život i prehrana",
        "Rad s mladima", "Medijska pismenost", "Edukacija o mentalnom zdravlju"
    };

    private final String[] hrvatskiOpisi = {
        "Radionica usmjerena na osnovne principe programiranja za sve uzraste.",
        "Učimo kako reagirati u hitnim situacijama i spasiti živote.",
        "Kreativno izražavanje kroz pisanu riječ i književne forme.",
        "Kako koristiti društvene mreže i oglašavanje za promociju.",
        "Igra kao alat u obrazovanju djece i mladih.",
        "Savjeti za zdravu prehranu i život bez stresa.",
        "Kako kvalitetno raditi s djecom i mladima u udrugama.",
        "Razumijevanje medijskih poruka i kritičko razmišljanje.",
        "Važnost mentalnog zdravlja i alati za očuvanje istog."
    };

    public void generirajPolaznike(int n) {
        for (int i = 0; i < n; i++) {
            Polaznik p = new Polaznik();
            p.setIme(faker.name().firstName());
            p.setPrezime(faker.name().lastName());
            p.setGodinaRodenja(1980 + rand.nextInt(25));
            polaznikRepo.save(p);
        }
    }

public void generirajRadionice(int broj) {
    List<String> nazivi = List.of(
            "Uvod u volonterstvo", "Digitalne vještine za mlade",
            "Psihološko osnaživanje", "Zeleni aktivizam", "Medijska pismenost",
            "Radionica kreativnog pisanja", "Osnove financijske pismenosti",
            "Mentalno zdravlje mladih", "Rodna ravnopravnost", "Kritičko razmišljanje i dijalog"
    );

    List<String> opisi = List.of(
            "Radionica osnažuje sudionike za aktivno volontiranje u zajednici.",
            "Uči osnovne digitalne vještine kroz praktičan rad i suradnju.",
            "Fokus na osobni rast, samopouzdanje i emocionalnu otpornost.",
            "Podizanje svijesti o klimatskim promjenama i održivim praksama.",
            "Analiziramo medije i razvijamo kritički pristup informacijama.",
            "Sudionici razvijaju kreativno izražavanje i komunikacijske vještine.",
            "Osnove upravljanja osobnim financijama kroz praktične zadatke.",
            "Kako prepoznati stres i unaprijediti mentalno zdravlje.",
            "Razumijevanje rodnih uloga i borba protiv stereotipa.",
            "Razvijamo sposobnosti argumentacije i konstruktivne rasprave."
    );

    for (int i = 0; i < broj; i++) {
        Radionica r = new Radionica();
        int index = i % nazivi.size(); // koristi se kružno ako je broj > 10
        r.setNaziv(nazivi.get(index));
        r.setOpis(opisi.get(index));
        r.setDatum(LocalDate.now().plusDays(i)); // svaka nova je na drugi dan
        r.setTrajanje(2 + (i % 3)); // trajanje 2–4 sata
        radionicaRepository.save(r);
    }
}

    public void generirajPrisustva(int n) {
        List<Polaznik> polaznici = polaznikRepo.findAll();
        List<Radionica> radionice = radionicaRepo.findAll();

        for (Polaznik p : polaznici) {
            for (Radionica r : radionice) {
                if (rand.nextBoolean()) {
                    Prisustvo prisustvo = new Prisustvo();
                    prisustvo.setPolaznik(p);
                    prisustvo.setRadionica(r);
                    prisustvo.setStatus(rand.nextBoolean() ? "da" : "ne");
                    prisustvoRepo.save(prisustvo);
                }
            }
        }
    }

public void generirajSve(int brojPolaznika, int brojRadionica, int brojPrisustava) {
    generirajPolaznike(brojPolaznika);
    generirajRadionice(brojRadionica);
    generirajPrisustva(brojPrisustava);
}
}
