package pl.michal_baniowski.coding_forum.dao.interfacesDao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao <T, PK extends Serializable> {
    T create(T newObject);
    T read(PK primaryKey);
    boolean update(T updateObject);
    boolean delete(PK key);
    List<T> getAll();
}
