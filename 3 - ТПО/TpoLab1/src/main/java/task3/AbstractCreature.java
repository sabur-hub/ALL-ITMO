package task3;

import java.util.Objects;

public abstract class AbstractCreature implements CreatureAble {
    public String race;
    public String name;

    public AbstractCreature() {
    }

    public void setRace(String planet) {
        if (Objects.equals(planet, "Земля")) this.race = "Человек";
        else if (Objects.equals(planet, "Альфа Центавра")) this.race = "Мохнатое существо";
    }

    public String getName() {
        return name;
    }

    public String getRace() {
        return race;
    }
}
