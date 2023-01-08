package servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");

        try {
            if (tryToParse(req.getParameter("Y")) &&
                    tryToParse(req.getParameter("R"))) {

                getServletContext().getRequestDispatcher("/areaCheckServlet").forward(req, resp);

            } else {
                    getServletContext().getRequestDispatcher("/mainPage.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            PrintWriter writer = resp.getWriter();
            writer.write("An error occurs in Controller: " + e.toString());
            writer.close();
        }
    }


    public void createErrorPage(HttpServletResponse resp, String Y, String R) throws IOException {
        PrintWriter writer = resp.getWriter();
        String answer = "<html>\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\" /> " +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/error.css\">" +
                "  </head>" +
                "<body>" +
                "<div id = \"error\">Error " + Y + " " + R + "</div>" +
                "<a href = \"http://localhost:8080/WebLab2_war_exploded\">Go back</a>" +
                "</body></html>";
        writer.write(answer);
        writer.close();
    }


    private boolean tryToParse(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException | NullPointerException ex) {
            return false;
        }
    }
}


