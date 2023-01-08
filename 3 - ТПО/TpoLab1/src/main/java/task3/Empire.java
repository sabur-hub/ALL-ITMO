package task3;

public class Empire {
    private int countOfHardening;
    private final Ship ship;
    private final Man man;
    private final Woman woman;
    private final Creature creature;

    public Empire(Ship ship, Man man, Woman woman, Creature creature) {
        this.countOfHardening = 0;
        this.ship = ship;
        this.man = man;
        this.woman = woman;
        this.creature = creature;
    }

    public String doHarden() {
        if (ship.getGoalReached() && man.getBraveHeart() && man.getVeritable() && woman.getBraveHeart()
                && woman.getVeritable() && creature.getVeritable()) {
            this.countOfHardening++;
            return "Империя готова к закаливанию";
        } else return "Империя не готова к закаливанию";
    }

    public int getCountOfHardening() {
        return countOfHardening;
    }
}
