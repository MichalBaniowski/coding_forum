package pl.michal_baniowski.coding_forum.dao.factoryDao;

import pl.michal_baniowski.coding_forum.dao.*;
import pl.michal_baniowski.coding_forum.dao.interfacesDao.*;

public class MysqlFactoryDao extends DaoFactory {
    @Override
    public UserDao getUserDao() {
        return UserDaoImpl.getInstance();
    }

    @Override
    public UserGroupDao getUserGroupDao() {
        return UserGroupDaoImpl.getInstance();
    }

    @Override
    public ExerciseDao getExerciseDao() {
        return ExerciseDaoImpl.getInstance();
    }

    @Override
    public SolutionDao getSolutionDao() {
        return SolutionDaoImpl.getInstance();
    }

    @Override
    public CommentDao getCommentDao() {
        return CommentDaoImpl.getInstance();
    }

    @Override
    public VoteDao getVoteDao() {
        return VoteDaoImpl.getInstance();
    }

    @Override
    public ActivationCodeDao getActivationCodeDao() {
        return ActivationCodeDaoImpl.getInstance();
    }
}
