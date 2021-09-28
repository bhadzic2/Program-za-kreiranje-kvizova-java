package ba.unsa.etf.rpr;

public enum SistemBodovanja {
    BINARNO("binarno"),
    PARCIJALNO("parcijalno"),
    PARCIJALNO_SA_NEGATIVNIM("parcijalno sa negativnim bodovima");
    private final String opis;
    SistemBodovanja(String opis){
        this.opis=opis;
    }

    @Override
    public String toString() {
        return opis;
    }
}
