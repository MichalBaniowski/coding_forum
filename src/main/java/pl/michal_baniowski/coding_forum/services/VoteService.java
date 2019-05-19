package pl.michal_baniowski.coding_forum.services;

public class VoteService {
    private static VoteService voteService;
    public static VoteService getInstance(){
        if(voteService == null){
            voteService = new VoteService();
        }
        return voteService;
    }
    private VoteService(){}
}
