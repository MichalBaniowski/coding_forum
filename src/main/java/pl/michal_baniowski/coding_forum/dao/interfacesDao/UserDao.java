package pl.michal_baniowski.coding_forum.dao.interfacesDao;

import pl.michal_baniowski.coding_forum.model.User;

import java.util.List;

public interface UserDao extends GenericDao<User, Long> {
    User getUserByUserName(String username);
    User getUserByEmail(String email);
    List<User> getAllByGroup(Long group_id);
}
