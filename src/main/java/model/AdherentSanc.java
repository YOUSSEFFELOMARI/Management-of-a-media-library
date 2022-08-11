package model;

//adherent sactionated
public class AdherentSanc {
    private int id;
    private String name;
    private String lastname;
    private String adresse;
    private int date_difference;
    private double montant;


    public AdherentSanc(int id, String name, String lastname, String adresse, int date_difference, double montant) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.adresse = adresse;
        this.date_difference = date_difference;
        this.montant = montant;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getDate_difference() {
        return date_difference;
    }

    public double getMontant() {
        return montant;
    }
}
