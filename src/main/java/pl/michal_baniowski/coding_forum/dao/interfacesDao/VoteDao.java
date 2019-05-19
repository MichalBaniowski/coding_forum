package pl.michal_baniowski.coding_forum.dao.interfacesDao;

import pl.michal_baniowski.coding_forum.model.Vote;

public interface VoteDao extends GenericDao<Vote,  Long> {
    Vote getVoteByUserIdSolutionId(String username, long solutionId);
}
