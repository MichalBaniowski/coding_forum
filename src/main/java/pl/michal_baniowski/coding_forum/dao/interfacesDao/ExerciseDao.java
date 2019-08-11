package pl.michal_baniowski.coding_forum.dao.interfacesDao;

import pl.michal_baniowski.coding_forum.model.Exercise;

import java.util.List;

public interface ExerciseDao extends GenericDao<Exercise, Long> {
    List<Exercise> findAllByUsername(String username);
}
