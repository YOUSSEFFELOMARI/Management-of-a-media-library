package model;

public class CD {
    private int numCd;
    private String nomAlbum;
    private String nomInterprete;
    private String nomEditeur;

    public CD(int numCd, String nomAlbum, String nomInterprete, String nomEditeur) {
        this.numCd = numCd;
        this.nomAlbum = nomAlbum;
        this.nomInterprete = nomInterprete;
        this.nomEditeur = nomEditeur;
    }

    public int getNumCd() {
        return numCd;
    }

    public void setNumCd(int numCd) {
        this.numCd = numCd;
    }

    public String getNomAlbum() {
        return nomAlbum;
    }

    public void setNomAlbum(String nomAlbum) {
        this.nomAlbum = nomAlbum;
    }

    public String getNomInterprete() {
        return nomInterprete;
    }

    public void setNomInterprete(String nomInterprete) {
        this.nomInterprete = nomInterprete;
    }

    public String getNomEditeur() {
        return nomEditeur;
    }

    public void setNomEditeur(String nomEditeur) {
        this.nomEditeur = nomEditeur;
    }

    @Override
    public String toString() {
        return "CD{" +
                "numCd=" + numCd +
                ", nomAlbum='" + nomAlbum + '\'' +
                ", nomInterprete='" + nomInterprete + '\'' +
                ", nomEditeur='" + nomEditeur + '\'' +
                '}';
    }
}
