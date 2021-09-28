package ba.unsa.etf.rpr;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RezultatKvizaTest {
    private static Kviz kviz;
    private static List<Pitanje> pitanja;

    @BeforeAll
    static void setUp(){
        Pitanje pitanje1 = new Pitanje("Koja od boja se moze naci na semaforu?", 3);
        pitanje1.dodajOdgovor("a", "zuta", true);
        pitanje1.dodajOdgovor("b", "zelena", true);
        pitanje1.dodajOdgovor("c", "crvena", true);
        pitanje1.dodajOdgovor("d", "plava", false);

        Pitanje pitanje2 = new Pitanje("Koji mjeseci imaju 28 dana?", 4);
        pitanje2.dodajOdgovor("a", "Januar", true);
        pitanje2.dodajOdgovor("b", "Februar", true);
        pitanje2.dodajOdgovor("c", "Novembar", true);

        pitanja = new ArrayList<>();
        pitanja.add(pitanje1);
        pitanja.add(pitanje2);
    }

    @BeforeEach
    void setUpTest(){
        kviz = new Kviz("Kviz opsteg znanja", SistemBodovanja.BINARNO);
        pitanja.forEach(pitanje -> kviz.dodajPitanje(pitanje));
    }

    @Test
    public void testKonstruktor(){
        RezultatKviza rezultatKviza = new RezultatKviza(kviz);
        assertAll(
                () -> assertEquals(kviz, rezultatKviza.getKviz()),
                () -> assertEquals(0, rezultatKviza.getTotal())
        );
    }

    @Test
    public void testRezultatkviza(){
        Map<Pitanje, ArrayList<String>> zaokruzeniOdgovori = new HashMap<>();
        zaokruzeniOdgovori.put(pitanja.get(0), new ArrayList<>(List.of("a", "c", "b"))); //3
        zaokruzeniOdgovori.put(pitanja.get(1), new ArrayList<>(List.of("c"))); //0
        RezultatKviza rezultatKviza = kviz.predajKviz(zaokruzeniOdgovori);
        assertAll(
                () -> assertEquals(3, rezultatKviza.getTotal()),
                () -> assertEquals(2, rezultatKviza.getBodovi().size()),
                () -> assertTrue(rezultatKviza.getBodovi().containsKey(pitanja.get(0))),
                () -> assertEquals(3, rezultatKviza.getBodovi().get(pitanja.get(0))),
                () -> assertEquals(0, rezultatKviza.getBodovi().get(pitanja.get(1)))
        );
    }

    @Test
    public void testToString(){
        Map<Pitanje, ArrayList<String>> zaokruzeniOdgovori = new HashMap<>();
        zaokruzeniOdgovori.put(pitanja.get(0), new ArrayList<>(List.of("a", "c", "b"))); //3
        zaokruzeniOdgovori.put(pitanja.get(1), new ArrayList<>(List.of("c"))); //0
        RezultatKviza rezultatKviza = kviz.predajKviz(zaokruzeniOdgovori);

        String rezultat1 = "Na kvizu \"Kviz opsteg znanja\" ostvarili ste ukupno 3.0 poena. Raspored po pitanjima:\n" +
                "Koja od boja se moze naci na semaforu? - 3.0b\n" +
                "Koji mjeseci imaju 28 dana? - 0.0b";

        String rezultat2 = "Na kvizu \"Kviz opsteg znanja\" ostvarili ste ukupno 3.0 poena. Raspored po pitanjima:\n" +
                "Koji mjeseci imaju 28 dana? - 0.0b\n" +
                "Koja od boja se moze naci na semaforu? - 3.0b";

        List<String> moguciTacni = new ArrayList<>(List.of(rezultat1, rezultat2));
        assertTrue(moguciTacni.contains(rezultatKviza.toString()));
    }

}