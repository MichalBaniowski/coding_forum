package pl.michal_baniowski.coding_forum.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class Solution {
    private Long id;
    private Exercise exercise;
    private String author;
    private Timestamp created;
    private Timestamp updated;
    private String description;
    private Set<Vote> votes;
    private List<Comment> comments;

    public Solution() {}

    public Solution(Solution solution){
        this.id = solution.id;
        this.exercise = new Exercise(solution.exercise);
        this.author = solution.author;
        this.created = new Timestamp(solution.created.getTime());
        this.updated = new Timestamp(solution.updated.getTime());
        this.description = solution.description;
        this.votes = solution.votes;
        this.comments = solution.comments;
    }

    public Solution(Exercise exercise, String author, Timestamp created, Timestamp updated, String description, Set<Vote> votes, List<Comment> comments) {
        this.exercise = exercise;
        this.author = author;
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.votes = votes;
        this.comments = comments;
    }

    public Solution(Exercise exercise, String author, String description) {
        this.exercise = exercise;
        this.author = author;
        this.created = Timestamp.valueOf(LocalDateTime.now());
        this.updated = Timestamp.valueOf(LocalDateTime.now());
        this.description = description;
        this.votes = new HashSet<>();
        this.comments = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public long getCountUpVotes() {
        return votes.stream()
                .filter(vote -> vote.getVoteType().equals(VoteType.VOTE_UP))
                .count();
    }

    public long getCountDownVotes() {
        return votes.stream()
                .filter(vote -> vote.getVoteType().equals(VoteType.VOTE_DOWN))
                .count();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Solution)) return false;
        Solution solution = (Solution) o;
        return getId() == solution.getId() &&
                getExercise().equals(solution.getExercise()) &&
                getAuthor().equals(solution.getAuthor()) &&
                getCreated().equals(solution.getCreated()) &&
                getUpdated().equals(solution.getUpdated()) &&
                getDescription().equals(solution.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getExercise(), getAuthor(), getCreated(), getUpdated(), getDescription(), getVotes());
    }

    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
                ", exercise= " + exercise +
                ", author= " + author +
                ", created= " + created +
                ", updated= " + updated +
                ", description= '" + description + '\'' +
                '}';
    }
}
