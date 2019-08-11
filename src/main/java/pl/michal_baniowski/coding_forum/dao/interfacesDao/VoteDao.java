package pl.michal_baniowski.coding_forum.dao.interfacesDao;

import pl.michal_baniowski.coding_forum.model.Vote;

import java.util.List;


public interface VoteDao extends GenericDao<Vote,  Long> {
    Vote getVoteByUsernameSolutionId(String username, long solutionId);
    List<Vote> getSolutionVotes(Long solutionId);
}
