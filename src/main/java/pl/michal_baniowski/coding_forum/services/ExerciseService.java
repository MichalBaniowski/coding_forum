package pl.michal_baniowski.coding_forum.services;

public class ExerciseService {
    private static ExerciseService exerciseService;
    public synchronized static ExerciseService getInstance(){
        if (exerciseService == null){
            exerciseService = new ExerciseService();
        }
        return exerciseService;
    }

    private ExerciseService(){}


}
