package pl.michal_baniowski.coding_forum.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class Comment {
    private Long id;
    private long solutionId;
    private String author;
    private String description;
    private Timestamp created;

    public Comment() {}

    public Comment(Comment comment){
        this.id = comment.id;
        this.solutionId = comment.solutionId;
        this.author = comment.author;
        this.description = comment.description;
        this.created = new Timestamp(comment.created.getTime());
    }

    public Comment(long solutionId, String author, String description, Timestamp created) {
        this(solutionId, author, description);
        this.created = created;
    }

    public Comment(long solutionId, String author, String description) {
        this.solutionId = solutionId;
        this.author = author;
        this.description = description;
        this.created = Timestamp.valueOf(LocalDateTime.now());
    }

    public Long getId() {
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return id == comment.id &&
                solutionId == comment.solutionId &&
                author == comment.author &&
                description.equals(comment.description) &&
                created.equals(comment.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, solutionId, author, description, created);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", solutionId=" + solutionId +
                ", author=" + author +
                ", description='" + description + '\'' +
                ", created=" + created +
                '}';
    }
}
