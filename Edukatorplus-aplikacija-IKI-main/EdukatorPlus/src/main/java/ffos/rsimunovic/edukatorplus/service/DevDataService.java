package ffos.rsimunovic.edukatorplus.service;

import ffos.rsimunovic.edukatorplus.model.Polaznik;
import ffos.rsimunovic.edukatorplus.model.Radionica;
import ffos.rsimunovic.edukatorplus.model.Prisustvo;
import ffos.rsimunovic.edukatorplus.model.PrisustvoStatus;
import ffos.rsimunovic.edukatorplus.repository.PolaznikRepository;
import ffos.rsimunovic.edukatorplus.repository.RadionicaRepository;
import ffos.rsimunovic.edukatorplus.repository.PrisustvoRepository;
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
        for (int i = 0; i < broj; i++) {
            Radionica r = new Radionica();
            int index = i % hrvatskiNazivi.length;
            r.setNaziv(hrvatskiNazivi[index]);
            r.setOpis(hrvatskiOpisi[index]);
            r.setDatum(LocalDate.now().plusDays(i)); // svaka nova je na drugi dan
            r.setTrajanje(2 + (i % 3)); // trajanje 2–4 sata
            radionicaRepo.save(r);
        }
    }

    /**
     * Generira nasumična prisustva za postojeće polaznike i radionice.
     * Ne koristi parametar n jer se generira za sve polaznike i radionice.
     */
    public void generirajPrisustva() {
        List<Polaznik> polaznici = polaznikRepo.findAll();
        List<Radionica> radionice = radionicaRepo.findAll();

        PrisustvoStatus[] statusi = PrisustvoStatus.values();

        for (Polaznik p : polaznici) {
            for (Radionica r : radionice) {
                if (rand.nextBoolean()) { // 50% šanse da će prisustvo biti kreirano
                    Prisustvo prisustvo = new Prisustvo();
                    prisustvo.setPolaznik(p);
                    prisustvo.setRadionica(r);
                    PrisustvoStatus randomStatus = statusi[rand.nextInt(statusi.length)];
                    prisustvo.setStatus(randomStatus);
                    prisustvoRepo.save(prisustvo);
                }
            }
        }
    }

    /**
     * Pokreće generiranje svih podataka - polaznika, radionica i prisustava.
     */
    public void generirajSve(int brojPolaznika, int brojRadionica) {
        generirajPolaznike(brojPolaznika);
        generirajRadionice(brojRadionica);
        generirajPrisustva();
    }
}
