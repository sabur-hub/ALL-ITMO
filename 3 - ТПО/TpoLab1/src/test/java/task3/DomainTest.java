package task3;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DomainTest {
    static Empire empire;
    static Ship ship;
    static Man man;
    static Woman woman;
    static Creature creature;
    static Noun noun;

    @BeforeAll
    public static void createAll() {
        man = new Man("Какой-то тестовый чувак");
        woman = new Woman("Какая-то тестовая чувиха");
        creature = new Creature();
        noun = new Noun();
        ship = new Ship("Мощный космический", 10);
        empire = new Empire(ship, man, woman, creature);
    }


    @Test
    public void toStringCheck() {
        assertEquals(ship.toString(), "Мощный космический корабль размером 10");
        assertEquals(creature.toString(), "Мохнатое существо с планеты Альфа Центавра");
        assertEquals(man.toString(), "Человек по имени 'Какой-то тестовый чувак', пол - мужской");
        assertEquals(woman.toString(), "Человек по имени 'Какая-то тестовая чувиха', пол - женский");
    }

    @Test
    public void braveHeartCheck() {
        assertEquals(man.getBraveHeart(), false);
        man.becomeBraveHeart();
        assertEquals(man.getBraveHeart(), true);

        assertEquals(woman.getBraveHeart(), false);
        woman.becomeBraveHeart();
        assertEquals(woman.getBraveHeart(), true);
    }

    @Test
    public void raceCheck(){
        assertNull(man.getRace());
        assertNull(woman.getRace());
        assertNull(creature.getRace());

        man.setRace("Земля");
        woman.setRace("Земля");
        creature.setRace("Альфа Центавра");

        assertEquals(man.getRace(), "Человек");
        assertEquals(woman.getRace(), "Человек");
        assertEquals(creature.getRace(), "Мохнатое существо");
    }

    @Test
    public void veritableCheck() {
        assertEquals(man.getVeritable(), false);
        man.becomeVeritable();
        assertEquals(man.getVeritable(), true);

        assertEquals(woman.getVeritable(), false);
        woman.becomeVeritable();
        assertEquals(woman.getVeritable(), true);

        assertEquals(creature.getVeritable(), false);
        creature.becomeVeritable();
        assertEquals(creature.getVeritable(), true);
    }

    @Test
    public void heroicAndChallengeCheck() {
        assertEquals(man.challengeUnknown(), "Ты можешь это сделать");
        assertEquals(man.doHeroicAction(), "Ты можешь это сделать");

        assertEquals(woman.challengeUnknown(), "Ты можешь это сделать");
        assertEquals(woman.doHeroicAction(), "Ты можешь это сделать");
    }

    @Test
    public void nounDeclineCheck() {
        assertEquals(noun.getDeclinable(), false);
        noun = man.declineNoun(noun);
        assertEquals(noun.getDeclinable(), true);

        noun.setInDeclinable();

        assertEquals(noun.getDeclinable(), false);
        noun = woman.declineNoun(noun);
        assertEquals(noun.getDeclinable(), true);

        noun.setInDeclinable();

        assertEquals(noun.getDeclinable(), false);
        noun = creature.declineNoun(noun);
        assertEquals(noun.getDeclinable(), false);
    }

    @AfterAll
    @Test
    public static void empireHardenCheck(){
        assertEquals(empire.doHarden(), "Империя не готова к закаливанию");

        ship.findSomething(true,true);

        assertEquals(empire.doHarden(), "Империя готова к закаливанию");
        assertEquals(empire.getCountOfHardening(), 1);
    }
}