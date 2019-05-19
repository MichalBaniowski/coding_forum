package pl.michal_baniowski.coding_forum.model;

import java.util.Objects;

public class Exercise {
    private long id;
    private String title;
    private String description;

    public Exercise() {}

    public Exercise(Exercise exercise) {
        this.id = exercise.id;
        this.title = exercise.title;
        this.description = exercise.description;
    }

    public Exercise(long id, String title, String description) {
        this(title, description);
        this.id = id;
    }

    public Exercise(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public long getId() {
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
                .append('}')
                .toString();
    }
}
