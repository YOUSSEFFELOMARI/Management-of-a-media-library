package model;

//adherent borowwed CD
public class AdherentEmpCD extends Adherent{
    private String nomAlbum;

    public AdherentEmpCD(int id, String name, String lastName, String adresse, String nomAlbum) {
        super(id, name, lastName, adresse);
        this.nomAlbum=nomAlbum;
    }

    public String getNomAlbum() {
        return nomAlbum;
    }
}
