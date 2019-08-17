package pl.michal_baniowski.coding_forum.services;

import pl.michal_baniowski.coding_forum.dao.factoryDao.DaoFactory;
import pl.michal_baniowski.coding_forum.dao.interfacesDao.ExerciseDao;
import pl.michal_baniowski.coding_forum.model.Exercise;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class ExerciseService {
    private static ExerciseService exerciseService;
    private ExerciseDao exerciseDao;

    public synchronized static ExerciseService getInstance(){
        if (exerciseService == null){
            exerciseService = new ExerciseService();
        }
        return exerciseService;
    }

    private ExerciseService(){
        this.exerciseDao = DaoFactory.getDaoFactory("MY_SQL").getExerciseDao();
    }

    public boolean createExercise(Exercise exercise) {
        Exercise exerciseResult = exerciseDao.create(exercise);
        return exerciseResult.getId() != null;
    }

    public Exercise getExerciseById(Long key) {
        return exerciseDao.read(key);
    }

    public boolean updateExercise(Exercise exercise) {
        exercise.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        return exerciseDao.update(exercise);
    }

    public boolean deleteExercise(Long key) {
        return exerciseDao.delete(key);
    }

    public List<Exercise> getAllExercises() {
        return exerciseDao.getAll();
    }

    public List<Exercise> getAllByUsername(String username) {
        return exerciseDao.findAllByUsername(username);
    }
}
