package pl.michal_baniowski.coding_forum.model;

import java.util.Objects;

public class UserGroup {
    private long id;
    private String name;
    private String description;

    public UserGroup() {}

    public UserGroup(UserGroup userGroup) {
        this.id = userGroup.id;
        this.name = userGroup.name;
        this.description = userGroup.description;
    }

    public UserGroup(long id, String name, String description){
        this(name, description);
        this.id = id;
    }

    public UserGroup(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserGroup)) return false;
        UserGroup userGroup = (UserGroup) o;
        return id == userGroup.id &&
                name.equals(userGroup.name) &&
                description.equals(userGroup.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
