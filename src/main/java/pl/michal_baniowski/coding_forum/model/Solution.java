package pl.michal_baniowski.coding_forum.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Solution {
    private long id;
    private Exercise exercise;
    private String username;
    private Timestamp created;
    private Timestamp updated;
    private String description;
    private int upVote;
    private int downVote;

    public Solution() {}

    public Solution(Solution solution){
        this.id = solution.id;
        this.exercise = new Exercise(solution.exercise);
        this.username = solution.username;
        this.created = new Timestamp(solution.created.getTime());
        this.updated = new Timestamp(solution.updated.getTime());
        this.description = solution.description;
        this.upVote = solution.upVote;
        this.downVote = solution.downVote;
    }

    public Solution(Exercise exercise, String username, Timestamp created, Timestamp updated, String description, int upVote, int downVote) {
        this.exercise = exercise;
        this.username = username;
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.upVote = upVote;
        this.downVote = downVote;
    }

    public Solution(Exercise exercise, String username, String description, int upVote, int downVote) {
        this.exercise = exercise;
        this.username = username;
        this.description = description;
        this.upVote = upVote;
        this.downVote = downVote;
    }

    public long getId() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUpVote() {
        return upVote;
    }

    public void setUpVote(int upVote) {
        this.upVote = upVote;
    }

    public int getDownVote() {
        return downVote;
    }

    public void setDownVote(int downVote) {
        this.downVote = downVote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Solution)) return false;
        Solution solution = (Solution) o;
        return getId() == solution.getId() &&
                getUpVote() == solution.getUpVote() &&
                getDownVote() == solution.getDownVote() &&
                getExercise().equals(solution.getExercise()) &&
                getUsername().equals(solution.getUsername()) &&
                getCreated().equals(solution.getCreated()) &&
                getUpdated().equals(solution.getUpdated()) &&
                getDescription().equals(solution.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getExercise(), getUsername(), getCreated(), getUpdated(), getDescription(), getUpVote(), getDownVote());
    }

    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
                ", exercise=" + exercise +
                ", username=" + username +
                ", created=" + created +
                ", updated=" + updated +
                ", description='" + description + '\'' +
                ", upVote=" + upVote +
                ", downVote=" + downVote +
                '}';
    }
}
