package task3;

public class Creature extends AbstractCreature {
    private final String planet;
    private final String type;
    private Boolean isVeritable;

    public Creature() {
        this.planet = "Альфа Центавра";
        this.type = "Мохнатое существо";
        this.isVeritable = false;
    }

    public Noun declineNoun(Noun noun) {
        if (!noun.getDeclinable()) {
            System.out.println("Ты не можешь склонять несклоняемое, " + this);
        }
        return noun;
    }

    @Override
    public String toString() {
        return this.type + " с планеты " + this.planet;
    }

    @Override
    public void becomeVeritable() {
        this.isVeritable = true;
    }

    public Boolean getVeritable() {
        return isVeritable;
    }
}
