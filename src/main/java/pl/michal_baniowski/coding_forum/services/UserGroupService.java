package pl.michal_baniowski.coding_forum.services;

public class UserGroupService {

    private static UserGroupService userGroupService;
    public synchronized static UserGroupService getInstance(){
        if(userGroupService == null){
            userGroupService = new UserGroupService();
        }
        return userGroupService;
    }

    private UserGroupService(){}

}
