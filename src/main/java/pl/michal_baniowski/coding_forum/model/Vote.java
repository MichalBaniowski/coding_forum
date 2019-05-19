package pl.michal_baniowski.coding_forum.model;

import java.util.Objects;

public class Vote {
    private long id;
    private long solutionId;
    private String username;
    private VoteType voteType;

    public Vote() {}

    public Vote(Vote vote){
        this.id = vote.id;
        this.solutionId = vote.solutionId;
        this.username = vote.username;
        this.voteType = vote.voteType;
    }

    public Vote(long solutionId, String username, VoteType voteType) {
        this.solutionId = solutionId;
        this.username = username;
        this.voteType = voteType;
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

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote)) return false;
        Vote vote = (Vote) o;
        return id == vote.id &&
                solutionId == vote.solutionId &&
                username.equals(vote.username) &&
                voteType == vote.voteType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, solutionId, username, voteType);
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", solutionId=" + solutionId +
                ", username='" + username + '\'' +
                ", voteType=" + voteType +
                '}';
    }
}
