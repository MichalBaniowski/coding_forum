package pl.michal_baniowski.coding_forum.services;

import pl.michal_baniowski.coding_forum.dao.factoryDao.DaoFactory;
import pl.michal_baniowski.coding_forum.exception.UpdateFailException;
import pl.michal_baniowski.coding_forum.model.User;
import pl.michal_baniowski.coding_forum.services.message.EmailService;

import javax.servlet.http.HttpServletRequest;

public class UserService {
    private static UserService userService;
    private DaoFactory daoFactory;
    public synchronized static UserService getInstance(){
        if (userService == null){
            userService = new UserService();
        }
        return userService;
    }

    private UserService(){
        daoFactory = DaoFactory.getDaoFactory("MY_SQL");
    }

    public boolean addUser(HttpServletRequest request){
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setActive(false);
        try {
            user = daoFactory.getUserDao().create(user);
        }catch (UpdateFailException e){
            return false;
        }
        sendEmailWhithActivationCode(user.getEmail(), user.getId());
        return true;
    }

    private void sendEmailWhithActivationCode(String addressee, long userId) {
        String activationLink = ActivationService.getInstance().getActivationLink(userId);
        String message = String.format("Witaj, oto link aktywacyjny:  %s", activationLink);
        EmailService.getInstance().sendMessage(message, addressee, "link aktywacyjny ");
    }

    public String activateUser(long id, String code) {
        User user = daoFactory.getUserDao().read(id);
        String message = "nie udało się aktywować konta";
        if (ActivationService.getInstance().checkActivationCode(id, code)) {
            user.setActive(true);
            if (daoFactory.getUserDao().update(user)) {
                daoFactory.getActivationCodeDao().delete(id);
                message = "twoje konto jest aktywne";
            }
        }
        return message;
    }

    public User getUserByUserName(String userName){
        return daoFactory.getUserDao().getUserByUserName(userName);
    }

    public boolean deleteUser(long id){
        return daoFactory.getUserDao().delete(id);
    }

    public boolean editUser(User user) {
        return daoFactory.getUserDao().update(user);
    }


}
