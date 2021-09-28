package ba.unsa.etf.rpr;

import java.util.HashMap;
import java.util.Map;

public class RezultatKviza {
    private Kviz kviz;
    private double total;
    private Map<Pitanje,Double> bodovi;
    public RezultatKviza(Kviz kviz){
        this.kviz=kviz;
        total=0;
        bodovi=new HashMap<>();
    }

    public Kviz getKviz() {
        return kviz;
    }

    public double getTotal() {
        return total;
    }

    public Map<Pitanje, Double> getBodovi() {
        return bodovi;
    }

    public void setKviz(Kviz kviz) {
        this.kviz = kviz;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setBodovi(Map<Pitanje, Double> bodovi) {
        this.bodovi = bodovi;
    }

    @Override
    public String toString() {
        String s;
        s="Na kvizu \""+kviz.getNaziv()+"\" ostvarili ste ukupno ";
        s=s+(total+" poena. Raspored po pitanjima:");
        for(Map.Entry<Pitanje, Double> b:bodovi.entrySet()){
            s=s+("\n"+b.getKey().getTekst()+" - "+b.getValue().toString()+"b");
        }
        return s;
    }
}
