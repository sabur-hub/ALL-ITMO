package task3;

public class Woman extends AbstractCreature {
    private final String gender;
    private final String name;
    private Boolean isVeritable;
    private Boolean braveHeart;

    public Woman(String name) {
        this.name = name;
        this.gender = "женский";
        this.isVeritable = false;
        this.braveHeart = false;
    }

    public void becomeBraveHeart() {
        this.braveHeart = true;
    }

    @Override
    public void becomeVeritable() {
        this.isVeritable = true;
    }

    public String challengeUnknown() {
        return "Ты можешь это сделать";
    }

    public String doHeroicAction() {
        return "Ты можешь это сделать";
    }

    public Noun declineNoun(Noun noun) {
        if (!noun.getDeclinable()) {
            noun.doDeclinable();
            System.out.println("Склоняешь несклоняемое, ты крут");
        }
        return noun;
    }

    public Boolean getBraveHeart() {
        return braveHeart;
    }

    public Boolean getVeritable() {
        return isVeritable;
    }

    @Override
    public String toString() {
        return "Человек по имени '" + name + "', пол - " + gender;
    }
}
