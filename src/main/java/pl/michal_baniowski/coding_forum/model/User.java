package pl.michal_baniowski.coding_forum.model;

import java.util.Objects;

public class User {
    private long id;
    private String username;
    private String email;
    private String password;
    private boolean active;

    public User() {}

    public User(User user){
        this.id = user.id;
        this.username = user.username;
        this.email = user.email;
        this.password = user.password;
        this.active = user.active;
    }

    public User(long id, String username, String email, String password, int active) {
        this(username, email, password, active);
        this.id = id;
    }

    public User(String username, String email, String password, int active) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.active = active != 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
                active == user.active &&
                username.equals(user.username) &&
                email.equals(user.email) &&
                password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, active);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("User{ ")
                .append("id = ")
                .append(id)
                .append(", username = ")
                .append(username)
                .append(", email = ")
                .append(email)
                .append(", password = ")
                .append(password)
                .append(", active = ")
                .append(active)
                .append('}')
                .toString();
    }
}
