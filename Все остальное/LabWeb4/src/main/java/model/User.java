package model;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity //�������� ������� ������������� �� � ������� �������
@Table(name = User.Name)
public class User {
    static final String Name = "USERS";

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private Long id;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy="owner")
    private List<Point> myPoints;

    @Column(name = "access_token") //������������ ��������������� �����
    private String accessToken;

    @Column(name = "login_controller") //������������ ��������������� �����
    private String loginController;

    public void setLoginController(String loginController) {
        this.loginController = loginController;
    }
    public String getLoginController() {
        return loginController;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Point> getMyEntries() {
        return myPoints;
    }

    public void setMyEntries(List<Point> myEntries) {
        this.myPoints = myEntries;
    }
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String access_token) {
        this.accessToken = access_token;
    }

    public void generateAccessToken(){
        this.setAccessToken(UUID.randomUUID().toString());
    }


}
