package model;

public class Adherent {
    private int id;
    private String name;
    private String lastName;

    private String adresse;

    public Adherent(int id, String name, String lastName, String adresse) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.adresse = adresse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return "Adherent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}
