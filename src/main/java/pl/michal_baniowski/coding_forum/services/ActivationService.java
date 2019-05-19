package pl.michal_baniowski.coding_forum.services;

import org.apache.commons.lang3.RandomStringUtils;
import pl.michal_baniowski.coding_forum.dao.factoryDao.DaoFactory;
import pl.michal_baniowski.coding_forum.exception.NotFoundException;
import pl.michal_baniowski.coding_forum.model.ActivationCode;

public class ActivationService {
    private static ActivationService activationService;
    private DaoFactory daoFactory;

    public static ActivationService getInstance() {
        if (activationService == null) {
            activationService = new ActivationService();
        }
        return activationService;
    }

    public ActivationService() {
        daoFactory = DaoFactory.getDaoFactory("MY_SQL");
    }

    public String getActivationLink(long id) {
        String code = generateActivationCode();

        if(readCodeFromDb(id) != null) {
            updateCode(id, code);
        }else {
            saveCodeToDb(id, code);
        }
        //eventually domain address
        return String.format("http://192.168.0.12:8080/account-activation?id=%d&code=%s", id, code);
    }

    public boolean checkActivationCode(long userId, String candidate) {
        ActivationCode codeObj = readCodeFromDb(userId);
        if (codeObj != null){
            return codeObj.getCode().equals(candidate);
        }
        return false;
    }

    private ActivationCode readCodeFromDb(long id) {
        try{
            return daoFactory.getActivationCodeDao().read(id);
        } catch (NotFoundException e) {
        }
        return null;
    }

    private void saveCodeToDb(long id, String code){
        daoFactory.getActivationCodeDao().create(new ActivationCode(id, code));
    }

    private void deleteCodeFromDb(long id) {
        daoFactory.getActivationCodeDao().delete(id);
    }

    private void updateCode(long id, String code) {
        daoFactory.getActivationCodeDao().update(new ActivationCode(id, code));
    }

    private String generateActivationCode() {
        int length = 20;
        return RandomStringUtils.random(length, true, false);
    }
}
