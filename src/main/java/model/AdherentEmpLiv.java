package model;

//adherent borowwed books
public class AdherentEmpLiv extends Adherent{

    private String titre;

    public String getTitre() {
        return titre;
    }

    public AdherentEmpLiv(int id, String name, String lastName, String adresse, String titre) {
        super(id, name, lastName, adresse);
        this.titre=titre;
    }
}
