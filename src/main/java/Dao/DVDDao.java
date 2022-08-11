package Dao;

import model.DVD;

//intreface for DVD Data Access Object
public interface DVDDao {
    DVD findBynum(int numDvd);
    void save(DVD dvd);
    void deleteByNumeroDvd(int numDvd);
}