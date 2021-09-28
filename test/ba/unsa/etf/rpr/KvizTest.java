package ba.unsa.etf.rpr;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class KvizTest {
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
        pitanja.get(0).setBrojPoena(3);
        pitanja.get(1).setBrojPoena(4);
    }

    @Test
    public void testKonstruktor1(){
        kviz = new Kviz("Moj kviz", SistemBodovanja.PARCIJALNO);
        assertAll(
                () -> assertEquals("Moj kviz", kviz.getNaziv()),
                () -> assertEquals(SistemBodovanja.PARCIJALNO, kviz.getSistemBodovanja()),
                () -> assertTrue(kviz.getPitanja().isEmpty())
        );
    }

    @Test
    public void testKonstruktor2(){
        assertEquals(2, kviz.getPitanja().size());
    }

    @Test
    public void testDodajPitanje1(){
        Pitanje pitanje3 = new Pitanje("Koliko je 2+2?", 1.5);
        kviz.dodajPitanje(pitanje3);
        assertEquals(3, kviz.getPitanja().size());
    }

    @Test
    public void testDodajPitanje2(){
        Pitanje pitanje3 = new Pitanje("Koliko je 2+2?", 1.5);
        kviz.dodajPitanje(pitanje3);
        Pitanje pitanje4 = new Pitanje("kolikO je 2+2?", 1.5);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->  kviz.dodajPitanje(pitanje4));

        assertAll(
                () -> assertEquals("Ne možete dodati pitanje sa tekstom koji već postoji",
                        exception.getMessage()),
                () -> assertEquals(3, kviz.getPitanja().size())
        );
    }

    @Test
    public void testDodajPitanje3(){
        Pitanje pitanje3 = new Pitanje("Koliko je 2+2?", 1.5);
        kviz.dodajPitanje(pitanje3);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->  kviz.dodajPitanje(pitanje3));

        assertAll(
                () -> assertEquals("Ne možete dodati pitanje sa tekstom koji već postoji",
                        exception.getMessage()),
                () -> assertEquals(3, kviz.getPitanja().size())
        );
    }

    @Test
    public void testSetteri(){
        kviz.setSistemBodovanja(SistemBodovanja.PARCIJALNO);
        kviz.setNaziv("Moj kviz");
        assertAll(
                () -> assertEquals(SistemBodovanja.PARCIJALNO, kviz.getSistemBodovanja()),
                () -> assertEquals("Moj kviz", kviz.getNaziv())
        );
    }

    @Test
    public void testToString(){
        //prikaz cijelog kviza uz oznacene tacne odgovore
        String tacno = "Kviz \"Kviz opsteg znanja\" boduje se binarno. Pitanja:\n" +
                "1. Koja od boja se moze naci na semaforu?(3.0b)\n" +
                "\ta: zuta(T)\n" +
                "\tb: zelena(T)\n" +
                "\tc: crvena(T)\n" +
                "\td: plava\n" +
                "\n" +
                "2. Koji mjeseci imaju 28 dana?(4.0b)\n" +
                "\ta: Januar(T)\n" +
                "\tb: Februar(T)\n" +
                "\tc: Novembar(T)";

        String izMetode = kviz.toString();
        kviz.setSistemBodovanja(SistemBodovanja.PARCIJALNO_SA_NEGATIVNIM);
        assertAll(
                () -> assertEquals(tacno, izMetode),
                () -> assertEquals(tacno.replace("binarno", "parcijalno sa negativnim bodovima"),
                        kviz.toString())
        );
    }

    @Test
    public void testPredajKviz1(){
        kviz.setSistemBodovanja(SistemBodovanja.PARCIJALNO);
        pitanja.get(1).setBrojPoena(6);

        Map<Pitanje, ArrayList<String>> zaokruzeniOdgovori = new HashMap<>();
        zaokruzeniOdgovori.put(pitanja.get(0), new ArrayList<>(List.of("a", "b"))); //1.5
        zaokruzeniOdgovori.put(pitanja.get(1), new ArrayList<>(List.of("c"))); //2
        RezultatKviza rezultatKviza = kviz.predajKviz(zaokruzeniOdgovori);

        assertAll(
                () -> assertEquals(3.5, rezultatKviza.getTotal()),
                () -> assertEquals(1.5, rezultatKviza.getBodovi().get(pitanja.get(0))),
                () -> assertEquals(2, rezultatKviza.getBodovi().get(pitanja.get(1)))
        );
    }

    @Test
    public void testPredajKviz2(){
        kviz.setSistemBodovanja(SistemBodovanja.PARCIJALNO);

        Map<Pitanje, ArrayList<String>> zaokruzeniOdgovori = new HashMap<>();
        zaokruzeniOdgovori.put(pitanja.get(0), new ArrayList<>(List.of("a", "b"))); //1.5
        RezultatKviza rezultatKviza = kviz.predajKviz(zaokruzeniOdgovori);

        assertAll(
                () -> assertEquals(1.5, rezultatKviza.getTotal()),
                () -> assertEquals(1.5, rezultatKviza.getBodovi().get(pitanja.get(0))),
                () -> assertEquals(0, rezultatKviza.getBodovi().get(pitanja.get(1)))
        );
    }

    @Test
    public void testPredajKviz3(){
        kviz.setSistemBodovanja(SistemBodovanja.PARCIJALNO);

        Map<Pitanje, ArrayList<String>> zaokruzeniOdgovori = new HashMap<>();
        zaokruzeniOdgovori.put(pitanja.get(0), new ArrayList<>());
        RezultatKviza rezultatKviza = kviz.predajKviz(zaokruzeniOdgovori);

        assertAll(
                () -> assertEquals(0, rezultatKviza.getTotal()),
                () -> assertEquals(0, rezultatKviza.getBodovi().get(pitanja.get(0))),
                () -> assertEquals(0, rezultatKviza.getBodovi().get(pitanja.get(1)))
        );
    }

    @Test
    public void testPredajKviz4(){
        kviz.setSistemBodovanja(SistemBodovanja.PARCIJALNO_SA_NEGATIVNIM);

        Map<Pitanje, ArrayList<String>> zaokruzeniOdgovori = new HashMap<>();
        zaokruzeniOdgovori.put(pitanja.get(0), new ArrayList<>(List.of("a", "c", "d"))); //-1.5
        zaokruzeniOdgovori.put(pitanja.get(1), new ArrayList<>(List.of("a", "b", "c"))); //4
        RezultatKviza rezultatKviza = kviz.predajKviz(zaokruzeniOdgovori);

        assertAll(
                () -> assertEquals(2.5, rezultatKviza.getTotal()),
                () -> assertEquals(-1.5, rezultatKviza.getBodovi().get(pitanja.get(0))),
                () -> assertEquals(4, rezultatKviza.getBodovi().get(pitanja.get(1)))
        );
    }

    @Test
    public void testPredajKviz5(){
        Map<Pitanje, ArrayList<String>> zaokruzeniOdgovori = new HashMap<>();
        zaokruzeniOdgovori.put(pitanja.get(0), new ArrayList<>(List.of("a", "b", "c")));
        zaokruzeniOdgovori.put(pitanja.get(1), new ArrayList<>(List.of("a", "b")));
        RezultatKviza rezultatKviza = kviz.predajKviz(zaokruzeniOdgovori);

        assertAll(
                () -> assertEquals(3, rezultatKviza.getTotal()),
                () -> assertEquals(3, rezultatKviza.getBodovi().get(pitanja.get(0))),
                () -> assertEquals(0, rezultatKviza.getBodovi().get(pitanja.get(1)))
        );
    }

}