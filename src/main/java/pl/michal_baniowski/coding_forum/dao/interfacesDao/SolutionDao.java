package pl.michal_baniowski.coding_forum.dao.interfacesDao;

import pl.michal_baniowski.coding_forum.model.Exercise;
import pl.michal_baniowski.coding_forum.model.Solution;
import pl.michal_baniowski.coding_forum.model.User;

import java.util.List;

public interface SolutionDao extends GenericDao<Solution, Long>  {
    List<Solution> getAllByUsername(String username);
    List<Solution> getAllByExercise(Long exerciseId);
}
