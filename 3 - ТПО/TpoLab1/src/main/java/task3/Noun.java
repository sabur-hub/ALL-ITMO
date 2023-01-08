package task3;

public class Noun {
    private Boolean isDeclinable;

    public Noun() {
        this.isDeclinable = false;
    }

    public Boolean getDeclinable() {
        return isDeclinable;
    }

    public void setInDeclinable() {
        this.isDeclinable = false;
    }

    public void doDeclinable() {
        this.isDeclinable = true;
    }
}
