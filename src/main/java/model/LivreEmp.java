package model;

//borrowed Book
public class LivreEmp extends Livre{
    private String nomAdherent;
    public LivreEmp(String numeroLivre, String titre, String auteurs, String maisonEdition, String nombrePages, String prix, String nameadh) {
        super(numeroLivre, titre, auteurs, maisonEdition, nombrePages, prix);
        this.nomAdherent=nameadh;
    }

    public String getNameadh() {
        return nomAdherent;
    }
}
