package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OdgovorTest {
    @Test
    public void testKonstruktor1(){
        Odgovor odgovor = new Odgovor("Tacan odgovor", true);
        assertAll(
                () -> assertEquals("Tacan odgovor", odgovor.getTekstOdgovora()),
                () -> assertTrue(odgovor.isTacno())
        );
    }

    @Test
    public void testKonstruktor2(){
        Odgovor odgovor = new Odgovor("Netacan odgovor", false);
        assertAll(
                () -> assertEquals("Netacan odgovor", odgovor.getTekstOdgovora()),
                () -> assertFalse(odgovor.isTacno())
        );
    }

    @Test
    public void testSetteri(){
        Odgovor odgovor = new Odgovor("Tacan odgovor", true);
        odgovor.setTacno(false);
        odgovor.setTekstOdgovora("Ovaj odgovor nije tacan");
        assertAll(
                () -> assertEquals("Ovaj odgovor nije tacan", odgovor.getTekstOdgovora()),
                () -> assertFalse(odgovor.isTacno())
        );
    }
}