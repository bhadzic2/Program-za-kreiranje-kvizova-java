package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PitanjeTest {
    @Test
    public void testKonstruktor(){
        Pitanje pitanje = new Pitanje("Koji je podrazumijevani scope u Javi?", 2);
        assertAll(
                () -> assertEquals("Koji je podrazumijevani scope u Javi?",pitanje.getTekst()),
                () -> assertEquals(2, pitanje.getBrojPoena()),
                () -> assertTrue(pitanje.getOdgovori().isEmpty())
        );
    }

    @Test
    public void testSetteri(){
        Pitanje pitanje = new Pitanje("Koji je podrazumijevani scope u Javi?", 2);
        pitanje.setBrojPoena(2.5);
        pitanje.setTekst("Promijenjeni tekst");
        assertAll(
                () -> assertEquals("Promijenjeni tekst",pitanje.getTekst()),
                () -> assertEquals(2.5, pitanje.getBrojPoena()),
                () -> assertTrue(pitanje.getOdgovori().isEmpty())
        );
    }

    @Test
    public void testDodajOdgovor1(){
        Pitanje pitanje = new Pitanje("Koji je podrazumijevani scope u Javi?", 2);
        pitanje.dodajOdgovor("a", "package", true);
        pitanje.dodajOdgovor("b", "private", false);
        assertEquals(2, pitanje.getOdgovori().size());
    }

    @Test
    public void testDodajOdgovor2(){
        Pitanje pitanje = new Pitanje("Koji je podrazumijevani scope u Javi?", 2);
        pitanje.dodajOdgovor("a", "package", true);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->  pitanje.dodajOdgovor("a", "private", false));

        assertAll(
                () -> assertEquals("Id odgovora mora biti jedinstven", exception.getMessage()),
                () -> assertEquals(1, pitanje.getOdgovori().size())
        );
    }

    @Test
    public void testObrisiOdgovor1(){
        Pitanje pitanje = new Pitanje("Koji je podrazumijevani scope u Javi?", 2);
        pitanje.dodajOdgovor("a", "package", true);
        pitanje.obrisiOdgovor("a");
        assertTrue(pitanje.getOdgovori().isEmpty());
    }

    @Test
    public void testObrisiOdgovor2(){
        Pitanje pitanje = new Pitanje("Koji je podrazumijevani scope u Javi?", 2);
        pitanje.dodajOdgovor("a", "package", true);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->  pitanje.obrisiOdgovor("b"));

        assertAll(
                () -> assertEquals("Odgovor s ovim id-em ne postoji", exception.getMessage()),
                () -> assertEquals(1, pitanje.getOdgovori().size())
        );
    }

    @Test
    public void testDajListuOdgovora(){
        Pitanje pitanje = new Pitanje("Koji je podrazumijevani scope u Javi?", 2);
        pitanje.dodajOdgovor("a", "package", true);
        pitanje.dodajOdgovor("b", "private", false);
        List<Odgovor> odgovori = pitanje.dajListuOdgovora();
        assertAll(
                () -> assertEquals(2, odgovori.size()),
                () -> assertTrue(odgovori.contains(new Odgovor("package", true))),
                () -> assertTrue(odgovori.contains(new Odgovor("private", false))),
                () -> assertFalse(odgovori.contains(new Odgovor("package", false)))
        );
    }

    @Test
    public void testIzracunajPoene1(){
        Pitanje pitanje = new Pitanje("Koji je podrazumijevani scope u Javi?", 2);
        pitanje.dodajOdgovor("a", "package", true);
        pitanje.dodajOdgovor("b", "private", false);

        double poeni1 = pitanje.izracunajPoene(List.of("a"), SistemBodovanja.BINARNO);

        pitanje.setBrojPoena(3);
        double poeni2 = pitanje.izracunajPoene(List.of("a"), SistemBodovanja.BINARNO);

        assertAll(
                () -> assertEquals(2, poeni1),
                () -> assertEquals(3, poeni2)
        );
    }

    @Test
    public void testIzracunajPoene2(){
        Pitanje pitanje = new Pitanje("Koji je podrazumijevani scope u Javi?", 2);
        pitanje.dodajOdgovor("a", "package", true);
        pitanje.dodajOdgovor("b", "private", false);

        double poeni1 = pitanje.izracunajPoene(List.of("b"), SistemBodovanja.BINARNO);
        double poeni2 = pitanje.izracunajPoene(List.of("a"), SistemBodovanja.BINARNO);

        assertAll(
                () -> assertEquals(0, poeni1),
                () -> assertEquals(2, poeni2)
        );
    }

    @Test
    public void testIzracunajPoene3(){
        Pitanje pitanje = new Pitanje("Koji je podrazumijevani scope u Javi?", 2);
        pitanje.dodajOdgovor("a", "package", true);
        pitanje.dodajOdgovor("b", "private", false);

        double poeni = pitanje.izracunajPoene(List.of("a", "b"), SistemBodovanja.BINARNO);
        assertEquals(0, poeni);
    }

    @Test
    public void testIzracunajPoene4(){
        Pitanje pitanje = new Pitanje("Koja od boja se moze naci na semaforu?", 2);
        pitanje.dodajOdgovor("a", "zuta", true);
        pitanje.dodajOdgovor("b", "zelena", true);
        pitanje.dodajOdgovor("c", "crvena", true);

        double poeni1 = pitanje.izracunajPoene(List.of("a"), SistemBodovanja.BINARNO);
        double poeni2 = pitanje.izracunajPoene(List.of("a", "b"), SistemBodovanja.BINARNO);
        double poeni3 = pitanje.izracunajPoene(List.of("a", "b", "c"), SistemBodovanja.BINARNO);
        assertAll(
                () -> assertEquals(0, poeni1),
                () -> assertEquals(0, poeni2),
                () -> assertEquals(2, poeni3)
        );
    }

    @Test
    public void testIzracunajPoene5(){
        Pitanje pitanje = new Pitanje("Koja od boja se moze naci na semaforu?", 2);
        pitanje.dodajOdgovor("a", "zuta", true);
        pitanje.dodajOdgovor("b", "zelena", true);
        pitanje.dodajOdgovor("c", "crvena", true);

        double poeni1 = pitanje.izracunajPoene(List.of(), SistemBodovanja.BINARNO);
        assertEquals(0, poeni1);
    }

    @Test
    public void testIzracunajPoene6(){
        Pitanje pitanje = new Pitanje("Koja od boja se moze naci na semaforu?", 3);
        pitanje.dodajOdgovor("a", "zuta", true);
        pitanje.dodajOdgovor("b", "zelena", true);
        pitanje.dodajOdgovor("c", "crvena", true);

        double poeni1 = pitanje.izracunajPoene(List.of("a"), SistemBodovanja.PARCIJALNO);
        double poeni2 = pitanje.izracunajPoene(List.of("a", "b"), SistemBodovanja.PARCIJALNO);
        double poeni3 = pitanje.izracunajPoene(List.of("a", "b", "c"), SistemBodovanja.PARCIJALNO);
        assertAll(
                () -> assertEquals(1, poeni1),
                () -> assertEquals(2, poeni2),
                () -> assertEquals(3, poeni3)
        );
    }

    @Test
    public void testIzracunajPoene7(){
        Pitanje pitanje = new Pitanje("Koja od boja se moze naci na semaforu?", 4);
        pitanje.dodajOdgovor("a", "zuta", true);
        pitanje.dodajOdgovor("b", "zelena", true);
        pitanje.dodajOdgovor("c", "crvena", true);
        pitanje.dodajOdgovor("d", "plava", false);

        double poeni1 = pitanje.izracunajPoene(List.of("a"), SistemBodovanja.PARCIJALNO);
        double poeni2 = pitanje.izracunajPoene(List.of("a", "b"), SistemBodovanja.PARCIJALNO);
        double poeni3 = pitanje.izracunajPoene(List.of("a", "b", "d"), SistemBodovanja.PARCIJALNO);
        double poeni4 = pitanje.izracunajPoene(List.of("a", "d"), SistemBodovanja.PARCIJALNO);
        double poeni5 = pitanje.izracunajPoene(List.of("d"), SistemBodovanja.PARCIJALNO);
        assertAll(
                () -> assertEquals(1, poeni1),
                () -> assertEquals(2, poeni2),
                () -> assertEquals(0, poeni3),
                () -> assertEquals(0, poeni4),
                () -> assertEquals(0, poeni5)
        );
    }

    @Test
    public void testIzracunajPoene8(){
        Pitanje pitanje = new Pitanje("Koja od boja se moze naci na semaforu?", 4);
        pitanje.dodajOdgovor("a", "zuta", true);
        pitanje.dodajOdgovor("b", "zelena", true);
        pitanje.dodajOdgovor("c", "crvena", true);
        pitanje.dodajOdgovor("d", "plava", false);

        double poeni1 = pitanje.izracunajPoene(List.of(), SistemBodovanja.PARCIJALNO);
        double poeni2 = pitanje.izracunajPoene(List.of("a", "b", "c"), SistemBodovanja.PARCIJALNO);
        double poeni3 = pitanje.izracunajPoene(List.of("a", "b", "c", "d"), SistemBodovanja.PARCIJALNO);
        assertAll(
                () -> assertEquals(0, poeni1),
                () -> assertEquals(4, poeni2),
                () -> assertEquals(0, poeni3)
        );
    }

    @Test
    public void testIzracunajPoene9(){
        Pitanje pitanje = new Pitanje("Koja od boja se moze naci na semaforu?", 4);
        pitanje.dodajOdgovor("a", "zuta", true);
        pitanje.dodajOdgovor("b", "zelena", true);
        pitanje.dodajOdgovor("c", "crvena", true);
        pitanje.dodajOdgovor("d", "plava", false);

        double poeni1 = pitanje.izracunajPoene(List.of(), SistemBodovanja.PARCIJALNO_SA_NEGATIVNIM);
        double poeni2 = pitanje.izracunajPoene(List.of("a", "b", "c"), SistemBodovanja.PARCIJALNO_SA_NEGATIVNIM);
        double poeni3 = pitanje.izracunajPoene(List.of("a", "b", "c", "d"), SistemBodovanja.PARCIJALNO_SA_NEGATIVNIM);
        assertAll(
                () -> assertEquals(0, poeni1),
                () -> assertEquals(4, poeni2),
                () -> assertEquals(-2, poeni3)
        );
    }

    @Test
    public void testIzracunajPoene10(){
        Pitanje pitanje = new Pitanje("Koja od boja se moze naci na semaforu?", 4);
        pitanje.dodajOdgovor("a", "zuta", true);
        pitanje.dodajOdgovor("b", "zelena", true);
        pitanje.dodajOdgovor("c", "crvena", true);
        pitanje.dodajOdgovor("d", "plava", false);

        double poeni1 = pitanje.izracunajPoene(List.of("a"), SistemBodovanja.PARCIJALNO_SA_NEGATIVNIM);
        double poeni2 = pitanje.izracunajPoene(List.of("a", "b"), SistemBodovanja.PARCIJALNO_SA_NEGATIVNIM);
        double poeni3 = pitanje.izracunajPoene(List.of("a", "b", "d"), SistemBodovanja.PARCIJALNO_SA_NEGATIVNIM);
        double poeni4 = pitanje.izracunajPoene(List.of("d"), SistemBodovanja.PARCIJALNO_SA_NEGATIVNIM);
        double poeni5 = pitanje.izracunajPoene(List.of("a", "b", "c"), SistemBodovanja.PARCIJALNO_SA_NEGATIVNIM);
        assertAll(
                () -> assertEquals(1, poeni1),
                () -> assertEquals(2, poeni2),
                () -> assertEquals(-2, poeni3),
                () -> assertEquals(-2, poeni4),
                () -> assertEquals(4, poeni5)
        );
    }

    @Test
    public void provjeraValidnostiOdgovoraTest1(){
        Pitanje pitanje = new Pitanje("Koja od boja se moze naci na semaforu?", 4);
        pitanje.dodajOdgovor("a", "zuta", true);
        pitanje.dodajOdgovor("b", "zelena", true);
        pitanje.dodajOdgovor("c", "crvena", true);
        pitanje.dodajOdgovor("d", "plava", false);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->  pitanje.izracunajPoene(List.of("e"), SistemBodovanja.BINARNO));

        assertEquals("Odabran je nepostojeći odgovor", exception.getMessage());
    }

    @Test
    public void provjeraValidnostiOdgovoraTest2(){
        Pitanje pitanje = new Pitanje("Koja od boja se moze naci na semaforu?", 4);
        pitanje.dodajOdgovor("a", "zuta", true);
        pitanje.dodajOdgovor("b", "zelena", true);
        pitanje.dodajOdgovor("c", "crvena", true);
        pitanje.dodajOdgovor("d", "plava", false);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->  pitanje.izracunajPoene(List.of("a", "b", "a"), SistemBodovanja.BINARNO));

        assertEquals("Postoje duplikati među odabranim odgovorima", exception.getMessage());
    }

    @Test
    public void toStringTest(){
        Pitanje pitanje = new Pitanje("Koja od boja se moze naci na semaforu?", 4);
        pitanje.dodajOdgovor("a", "zuta", true);
        pitanje.dodajOdgovor("b", "zelena", true);
        pitanje.dodajOdgovor("c", "crvena", true);

        String rezultat = "Koja od boja se moze naci na semaforu?(4.0b)\n\ta: zuta\n\tb: zelena\n\tc: crvena";
        String izMetode = pitanje.toString();
        pitanje.dodajOdgovor("d", "plava", false);

        assertAll(
                () -> assertEquals(rezultat, izMetode),
                () -> assertEquals(rezultat+"\n\td: plava", pitanje.toString())
        );
    }
}