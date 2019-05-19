package pl.michal_baniowski.coding_forum.model;

public class ActivationCode {
    private long id;
    private String code;

    public ActivationCode(long id, String code) {
        this.id = id;
        this.code = code;
    }

    public ActivationCode() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
