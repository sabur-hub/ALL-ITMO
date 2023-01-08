package DataBase;

import model.User;

import javax.ejb.*;
import javax.persistence.*;
import java.util.Base64;

@Stateful // ������ ��������� ��������������
public class UserDB {
    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
    private final EntityManager em = factory.createEntityManager();

    public User createUser(String login, String password) { //�������� ������������
        try {
            final User user = new User();
            final String hash = Hash.SHA(password);

            user.setLogin(login);
            //entity.setPassword(Base64.getEncoder().encodeToString((password).getBytes())); //����������� ������
            user.setPassword(hash);
            //user.setPassword(password);
            user.setLoginController("false");
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();

            return user;

        } catch (PersistenceException e) {
            return null;

        }
    }

    public boolean saveUser(User user) { //���������� �������� � ������������
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean checkPassword(String login, String password) { //�������� ������
        User user = findUser(login);

        //return user.getPassword().equals(new String(Base64.getDecoder().decode((password)))); //�������������
        return user.getPassword().equals(Hash.SHA(password));
    }

    public String ifExist(String login) { //����� ������������� ������
        try {
            User user = (User) em.createQuery(" from User u where u.login = :login")
                    .setParameter("login", login).getSingleResult();
            if (user == null)
                return "false1";
            else
                return "true";
        } catch (NoResultException e){ return "false2"; }
    }

    public User findUser(String login) {
        User user = (User) em.createQuery(" from User u where u.login = :login")
                .setParameter("login", login).getSingleResult();
        return user;
    }
}
