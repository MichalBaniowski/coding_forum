package pl.michal_baniowski.coding_forum.dao.factoryDao;

import pl.michal_baniowski.coding_forum.dao.interfacesDao.*;
import pl.michal_baniowski.coding_forum.exception.NoSuchDbTypeException;

public abstract class DaoFactory {
    public abstract UserDao getUserDao();
    public abstract UserGroupDao getUserGroupDao();
    public abstract ExerciseDao getExerciseDao();
    public abstract SolutionDao getSolutionDao();
    public abstract CommentDao getCommentDao();
    public abstract VoteDao getVoteDao();
    public abstract ActivationCodeDao getActivationCodeDao();

    public static DaoFactory getDaoFactory(int dbId) throws NoSuchDbTypeException {
        for(DbType db : DbType.values()){
            if(db.getId() == dbId){
                return db.getDaoFactory();
            }
        }
        throw new NoSuchDbTypeException("dao factory not found");
    }

    public static DaoFactory getDaoFactory(String dbType) throws NoSuchDbTypeException {
        DbType type = DbType.valueOf(dbType.toUpperCase());
        if(type != null){
            return type.getDaoFactory();
        }
        throw new NoSuchDbTypeException("dao factory not found");
    }

    private enum DbType{
        MY_SQL(1, new MysqlFactoryDao());
        private int id;
        private DaoFactory daoFactory;
        DbType(int id, DaoFactory daoFactory){
            this.id = id;
            this.daoFactory = daoFactory;
        }

        public DaoFactory getDaoFactory() {
            return daoFactory;
        }

        public int getId() {
            return id;
        }
    }
}
