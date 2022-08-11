package Dao;

import model.Adherent;

//intreface for Adherent Data Access Object
public interface AdherentDao {
    Adherent findById(int id);
    void save(Adherent adherent);
    void deleteById(int id);
}