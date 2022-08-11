package model;

//borrowed DVD
public class DVDEmp extends DVD{

    private String nomAdherent;

    public DVDEmp(int numDvd, String nomFilm, String nomDocumentaire, String nomRealisateur, String nomActeur, String nomEditeurdvd, String nomProducteur, String nomAdherent) {
        super(numDvd, nomFilm, nomDocumentaire, nomRealisateur, nomActeur, nomEditeurdvd, nomProducteur);
        this.nomAdherent=nomAdherent;
    }

    public String getNomAdherent() {
        return nomAdherent;
    }
}
