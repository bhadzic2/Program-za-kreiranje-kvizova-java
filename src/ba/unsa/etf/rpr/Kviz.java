package ba.unsa.etf.rpr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Kviz {
    private String naziv;
    private ArrayList<Pitanje> pitanja;
    private SistemBodovanja sistemBodovanja;
    public Kviz(String naziv, SistemBodovanja sistemBodovanja){
        this.naziv=naziv;
        this.sistemBodovanja=sistemBodovanja;
        pitanja=new ArrayList<>();
    }
    public String getNaziv() {
        return naziv;
    }

    public ArrayList<Pitanje> getPitanja() {
        return pitanja;
    }

    public SistemBodovanja getSistemBodovanja() {
        return sistemBodovanja;
    }
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setPitanja(ArrayList<Pitanje> pitanja) {
        this.pitanja = pitanja;
    }
    public void setSistemBodovanja(SistemBodovanja sistemBodovanja){
        this.sistemBodovanja=sistemBodovanja;
    }
    public void dodajPitanje(Pitanje pitanje) throws IllegalArgumentException {
        for (Pitanje value : pitanja) {
            if (value.equals(pitanje)) {
                throw new IllegalArgumentException("Ne možete dodati pitanje sa tekstom koji već postoji");
            }
        }
        pitanja.add(pitanje);
    }

    @Override
    public String toString() {
        String s="Kviz "+"\""+naziv+"\""+" boduje se "+sistemBodovanja+". "+"Pitanja:";
        int i=1;
        for(Pitanje pit: pitanja){
            s=s+("\n"+i+". "+pit.getTekst()+"("+pit.getBrojPoena()+"b)");
            for(Map.Entry<String,Odgovor> o:pit.getOdgovori().entrySet()){
                s=s+("\n\t"+o.getKey().toString()+": "+o.getValue().toString());
                if(o.getValue().isTacno()) s=s+"(T)";
            }
            if(i!=pitanja.size()) s=s+"\n";
            i++;
        }
        return s;
    }
    public RezultatKviza predajKviz(Map<Pitanje, ArrayList<String>> odg){

        RezultatKviza rez=new RezultatKviza(this);
        Map<Pitanje, Double> b=new HashMap<>();
        double total=0.;
        for(Pitanje p:pitanja){
            Double x2=0.0;
            for(Map.Entry<Pitanje,ArrayList<String>> z: odg.entrySet()){
                if(z.getKey().equals(p)){ //ako ima tog pitanja
                    x2=p.izracunajPoene(z.getValue(),sistemBodovanja);
                    break;
                }
            }
            b.put(p,x2);
            total=total+x2;
        }
        rez.setBodovi(b);
        rez.setTotal(total);
        return rez;
    }
}
