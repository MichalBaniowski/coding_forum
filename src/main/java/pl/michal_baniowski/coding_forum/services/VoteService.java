package pl.michal_baniowski.coding_forum.services;

import pl.michal_baniowski.coding_forum.dao.factoryDao.DaoFactory;
import pl.michal_baniowski.coding_forum.dao.interfacesDao.VoteDao;
import pl.michal_baniowski.coding_forum.exception.QueryException;
import pl.michal_baniowski.coding_forum.model.Vote;

public class VoteService {
    private static VoteService voteService;
    private VoteDao voteDao;
    public static VoteService getInstance(){
        if(voteService == null){
            voteService = new VoteService();
        }
        return voteService;
    }
    private VoteService(){
        this.voteDao = DaoFactory.getDaoFactory("MY_SQL").getVoteDao();
    }

    public void addVote(Vote vote) {
        try{
            Vote voteFromDb = voteDao.getVoteByUsernameSolutionId(vote.getUsername(), vote.getSolutionId());
            voteFromDb.setVoteType(vote.getVoteType());
            voteDao.update(voteFromDb);
        } catch (QueryException e) {
            voteDao.create(vote);
        }
    }

    public boolean deleteVote(Long voteId) {
        return voteDao.delete(voteId);
    }
}
