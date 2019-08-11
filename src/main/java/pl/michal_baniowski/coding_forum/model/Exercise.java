package pl.michal_baniowski.coding_forum.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class Exercise {
    private Long id;
    private String title;
    private String description;
    private String username;
    private Timestamp created;
    private Timestamp updated;


    public Exercise() {}

    public Exercise(Exercise exercise) {
        this.id = exercise.id;
        this.title = exercise.title;
        this.description = exercise.description;
        this.username = exercise.username;
        this.created = new Timestamp(exercise.created.getTime());
        this.updated = new Timestamp(exercise.updated.getTime());
    }

    public Exercise(long id, String title, String description, String username, Timestamp created, Timestamp updated) {
        this(title, description, username, created, updated);
        this.id = id;
    }

    public Exercise(String title, String description, String username, Timestamp created, Timestamp updated) {
        this.title = title;
        this.description = description;
        this.updated = updated;
        this.created = created;
        this.username = username;
    }

    public Exercise(String title, String description, String username) {
        this.title = title;
        this.description = description;
        this.username = username;
        this.created = Timestamp.valueOf(LocalDateTime.now());
        this.updated = Timestamp.valueOf(LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Exercise)) return false;
        Exercise exercise = (Exercise) o;
        return id == exercise.id &&
                title.equals(exercise.title) &&
                description.equals(exercise.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description);
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("Exercise{ ")
                .append("id = ")
                .append(id)
                .append(", title = ")
                .append(title)
                .append(", description = ")
                .append(description)
                .append(", author = ")
                .append(username)
                .append(", created = ")
                .append(created)
                .append(", updated = ")
                .append(updated)
                .append('}')
                .toString();
    }
}
