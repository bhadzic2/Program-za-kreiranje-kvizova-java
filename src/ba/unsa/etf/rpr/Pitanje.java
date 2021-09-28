package ba.unsa.etf.rpr;

import java.util.*;

public class Pitanje {
    private String tekst;
    private double brojPoena;
    private Map<String,Odgovor> odgovori;
    public Pitanje(String tekst, double brojPoena){
        this.tekst=tekst;
        this.brojPoena=brojPoena;
        odgovori=new HashMap<>();
    }

    public String getTekst() {
        return tekst;
    }

    public double getBrojPoena() {
        return brojPoena;
    }

    public Map<String, Odgovor> getOdgovori() {
        return odgovori;
    }

    public void setOdgovori(Map<String,Odgovor> odgovori) {
        this.odgovori = odgovori;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public void setBrojPoena(double brojPoena) {
        this.brojPoena = brojPoena;
    }

    public void dodajOdgovor(String id, String tekst, boolean tacno)throws IllegalArgumentException{
        if(odgovori.containsKey(id)) throw new IllegalArgumentException("Id odgovora mora biti jedinstven");
        odgovori.put(id,new Odgovor(tekst, tacno));
    }
    public void obrisiOdgovor(String id)throws IllegalArgumentException{
        if(!odgovori.containsKey(id)) throw new IllegalArgumentException("Odgovor s ovim id-em ne postoji");
        odgovori.remove(id);
    }
    public List<Odgovor> dajListuOdgovora(){
        return new ArrayList<>(odgovori.values());
    }

    @Override
    public String toString() {
        String s=tekst+"("+brojPoena+"b)";
        for(Map.Entry<String, Odgovor> odg:odgovori.entrySet()){
            s=s+("\n\t"+odg.getKey()+": "+odg.getValue().toString());
        }
        return s;
    }
    public double izracunajPoene(List<String> idevi, SistemBodovanja sistemBodovanja)throws IllegalArgumentException {
        List<String> stari = new ArrayList<>(odgovori.keySet());
        for (String s : idevi) {
            if (!stari.contains(s)) throw new IllegalArgumentException("Odabran je nepostojeći odgovor");
        }
        Set<String> set = new HashSet<>(idevi);
        if (set.size() != idevi.size())
            throw new IllegalArgumentException("Postoje duplikati među odabranim odgovorima");
        if (sistemBodovanja == SistemBodovanja.BINARNO) {
            for (Map.Entry<String, Odgovor> odg : odgovori.entrySet()) {
                if ((odg.getValue().isTacno() && !idevi.contains(odg.getKey())) || (!odg.getValue().isTacno() && idevi.contains(odg.getKey()))) return 0.;
            }
            return brojPoena;
        } else {
            int gTacnih = 0, gNetacnih = 0, gUkupnih = 0;
            int lTacnih = 0, lNetacnih = 0, lUkupnih = 0;
            for (Map.Entry<String, Odgovor> odg : odgovori.entrySet()) {
                if (odg.getValue().isTacno()) {
                    gTacnih++;
                    if (idevi.contains(odg.getKey())) {
                        lTacnih++;
                    }
                } else gNetacnih++;
            }
            gUkupnih = gTacnih + gNetacnih;
            lUkupnih = idevi.size();
            lNetacnih = lUkupnih - lTacnih;
            if (sistemBodovanja == SistemBodovanja.PARCIJALNO) {
                if (lNetacnih != 0 || lTacnih == 0) return 0.;
                else if (lTacnih == gTacnih) return brojPoena;
                else {
                    return ((brojPoena / gUkupnih) * lTacnih);
                }
            }
            if (sistemBodovanja == SistemBodovanja.PARCIJALNO_SA_NEGATIVNIM) {
                if(lTacnih==gTacnih && lNetacnih==0) return brojPoena;
                else if(lNetacnih!=0){
                    return -(brojPoena/2.);
                }
                else{
                    return ((brojPoena / gUkupnih) * lTacnih);
                }
            }
        }
        return 0.;
    }

    @Override
    public boolean equals(Object obj) {
        Pitanje p=(Pitanje) obj;
        return (tekst.equalsIgnoreCase(p.getTekst()) && brojPoena==p.getBrojPoena() && odgovori.equals(p.getOdgovori()));
    }
}
