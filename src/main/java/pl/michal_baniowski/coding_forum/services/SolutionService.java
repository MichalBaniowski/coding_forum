package pl.michal_baniowski.coding_forum.services;


public class SolutionService {
    private static SolutionService solutionService;
    public synchronized static SolutionService getInstance(){
        if(solutionService == null){
            solutionService = new SolutionService();
        }
        return solutionService;
    }

    private SolutionService(){}



}
