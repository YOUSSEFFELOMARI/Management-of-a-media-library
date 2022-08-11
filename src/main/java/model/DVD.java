package model;

public class DVD {
    private int numDvd;
    private String nomFilm;
    private String nomDocumentaire;
    private String nomRealisateur;
    private String nomActeur;
    private String nomEditeurdvd;
    private String nomProducteur;

    public DVD(int numDvd, String nomFilm, String nomDocumentaire, String nomRealisateur, String nomActeur, String nomEditeurdvd, String nomProducteur) {
        this.numDvd = numDvd;
        this.nomFilm = nomFilm;
        this.nomDocumentaire = nomDocumentaire;
        this.nomRealisateur = nomRealisateur;
        this.nomActeur = nomActeur;
        this.nomEditeurdvd = nomEditeurdvd;
        this.nomProducteur = nomProducteur;
    }

    public int getNumDvd() {
        return numDvd;
    }

    public void setNumDvd(int numDvd) {
        this.numDvd = numDvd;
    }

    public String getNomFilm() {
        return nomFilm;
    }

    public void setNomFilm(String nomFilm) {
        this.nomFilm = nomFilm;
    }

    public String getNomDocumentaire() {
        return nomDocumentaire;
    }

    public void setNomDocumentaire(String nomDocumentaire) {
        this.nomDocumentaire = nomDocumentaire;
    }

    public String getNomRealisateur() {
        return nomRealisateur;
    }

    public void setNomRealisateur(String nomRealisateur) {
        this.nomRealisateur = nomRealisateur;
    }

    public String getNomActeur() {
        return nomActeur;
    }

    public void setNomActeur(String nomActeur) {
        this.nomActeur = nomActeur;
    }

    public String getNomEditeurdvd() {
        return nomEditeurdvd;
    }

    public void setNomEditeurdvd(String nomEditeurdvd) {
        this.nomEditeurdvd = nomEditeurdvd;
    }

    public String getNomProducteur() {
        return nomProducteur;
    }

    public void setNomProducteur(String nomProducteur) {
        this.nomProducteur = nomProducteur;
    }

    @Override
    public String toString() {
        return "DVD{" +
                "numDvd=" + numDvd +
                ", nomFilm='" + nomFilm + '\'' +
                ", nomDocumentaire='" + nomDocumentaire + '\'' +
                ", nomRealisateur='" + nomRealisateur + '\'' +
                ", nomActeur='" + nomActeur + '\'' +
                ", nomEditeurdvd='" + nomEditeurdvd + '\'' +
                ", nomProducteur='" + nomProducteur + '\'' +
                '}';
    }
}
