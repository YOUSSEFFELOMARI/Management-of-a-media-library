package Dao;

import model.CD;

//intreface for CD Data Access Object
public interface CDDao {
    CD findBynum(int numCd);
    void save(CD cd);
    void deleteByNumeroCd(int numCd);
}