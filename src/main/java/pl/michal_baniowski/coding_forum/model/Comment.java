package pl.michal_baniowski.coding_forum.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Comment {
    private long id;
    private long solutionId;
    private String username;
    private String description;
    private Timestamp date;

    public Comment() {}

    public Comment(Comment comment){
        this.id = comment.id;
        this.solutionId = comment.solutionId;
        this.username = comment.username;
        this.description = comment.description;
        this.date = new Timestamp(comment.date.getTime());
    }

    public Comment(long solutionId, String username, String description, Timestamp date) {
        this(solutionId, username, description);
        this.date = date;
    }

    public Comment(long solutionId, String username, String description) {
        this.solutionId = solutionId;
        this.username = username;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(long solutionId) {
        this.solutionId = solutionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return id == comment.id &&
                solutionId == comment.solutionId &&
                username == comment.username &&
                description.equals(comment.description) &&
                date.equals(comment.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, solutionId, username, description, date);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", solutionId=" + solutionId +
                ", username=" + username +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
