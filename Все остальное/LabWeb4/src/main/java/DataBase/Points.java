package DataBase;

import model.Point;
import model.User;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.StringJoiner;

@Stateless //��� ������ ������ ��� ���������� �������
public class Points {
    @EJB
    private PointDB pointDB;
    @EJB
    private Auth auth;

    public String addPoint(String token, String x, String y, String r) { //�������� ����� �����
        User current_user = auth.getUserByToken(token);
        try {
            double xd = Double.parseDouble(x);
            double yd = Double.parseDouble(y);
            double rd = Double.parseDouble(r);
            if (xd <= 4 && xd >= -4 && yd <= 5 && yd >= -5 && rd <= 4 && rd > -4) {
                pointDB.createPoint(xd, yd, rd, current_user);
            }
        }catch (NumberFormatException e){
            return "Bad format";
        }
        return getPoints(token);
    }

    public String getPoints(String token) { //������� ���� ����� � ������� json
        User current_user = auth.getUserByToken(token);

        List<Point> points = pointDB.findPoint(current_user);
        StringJoiner joiner = new StringJoiner(",");
        for (Point point : points) {
            String stringBuilder = "{\"x\":\"" +
                    point.getX().toString() +
                    "\", \"y\":\"" +
                    point.getY().toString() +
                    "\", \"r\":\"" +
                    point.getR().toString() +
                    "\", \"result\":\"" +
                    point.isResult() +
                    "\"}";
            joiner.add(stringBuilder);
        }

        return "["+joiner.toString()+"]";
    }

}
