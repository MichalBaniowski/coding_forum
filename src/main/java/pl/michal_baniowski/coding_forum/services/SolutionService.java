package pl.michal_baniowski.coding_forum.services;


import pl.michal_baniowski.coding_forum.dao.factoryDao.DaoFactory;
import pl.michal_baniowski.coding_forum.dao.interfacesDao.SolutionDao;
import pl.michal_baniowski.coding_forum.model.Solution;

import java.util.List;

public class SolutionService {
    private static SolutionService solutionService;
    private SolutionDao solutionDao;

    public synchronized static SolutionService getInstance(){
        if(solutionService == null){
            solutionService = new SolutionService();
        }
        return solutionService;
    }

    private SolutionService(){
        this.solutionDao = DaoFactory.getDaoFactory("MY_SQL").getSolutionDao();
    }

    public boolean createSolution(Solution solution) {
        Solution solutionSaved = solutionDao.create(solution);
        return solutionSaved.getId() != null;
    }

    public Solution getSolutionById(Long id) {
        return solutionDao.read(id);
    }

    public List<Solution> getSolutions() {
        return solutionDao.getAll();
    }

    public List<Solution> getSolutionsByUsername(String username) {
        return solutionDao.getAllByUsername(username);
    }

    public List<Solution> getSolutionByExercise(Long exerciseId) {
        return solutionDao.getAllByExercise(exerciseId);
    }

    public boolean updateSolution(Solution solution) {
        return solutionDao.update(solution);
    }

    public boolean deleteSolution (Long key) {
        return solutionDao.delete(key);
    }

}
