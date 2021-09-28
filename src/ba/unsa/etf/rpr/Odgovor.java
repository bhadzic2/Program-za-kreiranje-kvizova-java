package ba.unsa.etf.rpr;

public class Odgovor {
    private String tekstOdgovora;
    private boolean tacno;
    public Odgovor(String tekstOdgovora, boolean tacno) {
        this.tekstOdgovora = tekstOdgovora;
        this.tacno = tacno;
    }

    public String getTekstOdgovora() {
        return tekstOdgovora;
    }
    public boolean isTacno(){
        return tacno;
    }

    public void setTekstOdgovora(String tekstOdgovora) {
        this.tekstOdgovora = tekstOdgovora;
    }

    public void setTacno(boolean tacno) {
        this.tacno = tacno;
    }

    @Override
    public boolean equals(Object obj) {
        Odgovor odg=(Odgovor) obj;
        return(tacno==odg.isTacno() && tekstOdgovora.equals(odg.getTekstOdgovora()));
    }

    @Override
    public String toString() {
        return tekstOdgovora;
    }
}
