package model;

//adherent borowwed DVD
public class AdherentEmpDVD extends Adherent{

    private String nomFilm;

    public AdherentEmpDVD(int id, String name, String lastName, String adresse,String nomFilm) {
        super(id, name, lastName, adresse);
        this.nomFilm=nomFilm;
    }

    public String getNomFilm() {
        return nomFilm;
    }
}
