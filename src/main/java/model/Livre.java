package model;

public class Livre {

    private String  numeroLivre;

    private String titre;

    private String auteurs;

    private String maisonEdition;

    private String nombrePages;

    private String prix;

    public Livre(String numeroLivre, String titre, String auteurs, String maisonEdition, String nombrePages, String prix) {
        this.numeroLivre = numeroLivre;
        this.titre = titre;
        this.auteurs = auteurs;
        this.maisonEdition = maisonEdition;
        this.nombrePages = nombrePages;
        this.prix = prix;
    }

    public  String  getNumeroLivre() {
        return numeroLivre;
    }

    public void setNumeroLivre(String numeroLivre) {
        this.numeroLivre = numeroLivre;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteurs() {
        return auteurs;
    }

    public void setAuteurs(String auteurs) {
        this.auteurs = auteurs;
    }

    public String getMaisonEdition() {
        return maisonEdition;
    }

    public void setMaisonEdition(String maisonEdition) {
        this.maisonEdition = maisonEdition;
    }

    public String getNombrePages() {
        return nombrePages;
    }

    public void setNombrePages(String nombrePages) {
        this.nombrePages = nombrePages;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Livre{" +
                "numeroLivre='" + numeroLivre + '\'' +
                ", titre='" + titre + '\'' +
                ", auteurs='" + auteurs + '\'' +
                ", maisonEdition='" + maisonEdition + '\'' +
                ", nombrePages=" + nombrePages +
                ", prix=" + prix +
                '}';
    }

}
