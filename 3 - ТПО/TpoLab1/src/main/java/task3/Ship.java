package task3;

public class Ship {
    private String type;
    private int size;
    private Boolean goalReached;

    public Ship(String type, Integer size) {
        this.size = size;
        this.type = type;
        this.goalReached = false;
    }

    public void findSomething(Boolean glory, Boolean adventure) {
        if (glory && adventure) {
            this.goalReached = true;
        }
    }

    public Boolean getGoalReached() {
        return goalReached;
    }

    @Override
    public String toString() {
        return type + " корабль размером " + size;
    }
}
