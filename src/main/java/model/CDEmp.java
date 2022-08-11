package model;

//borrowed CD
public class CDEmp extends CD{
    private String nomAdherent;

    public String getNomAdherent() {
        return nomAdherent;
    }

    public CDEmp(int numCd, String nomAlbum, String nomInterprete, String nomEditeur, String nomAdherent) {
        super(numCd, nomAlbum, nomInterprete, nomEditeur);
        this.nomAdherent=nomAdherent;
    }

}
