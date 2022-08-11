package Dao;

import model.Livre;

//intreface for Books(livre in French) Data Access Object
public interface LivreDao {
    Livre findByNumeroLivre(int numeroLivre);
    void save(Livre adherent);
    void deleteByNumeroLivre(int numeroLivre);
}
