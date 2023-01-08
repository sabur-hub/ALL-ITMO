package servlets;

import model.Model;
import model.Point;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class AreaCheckServlet extends HttpServlet {
    public Model model;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");

        HttpSession session = req.getSession();
        if(session.getAttribute("model") == null) {
            model = new Model();
        } else {
            model = (Model)session.getAttribute("model");
        }

        try {
            if (!(tryToParse(req.getParameter("x")) && (tryToParse(req.getParameter("Xgr"))))) {
                if (tryToParse(req.getParameter("x"))) {

                    checkButton(req,resp);
                } else if (tryToParse(req.getParameter("Xgr"))) {

                    checkGraphic(req,resp);
                } else {
                    createErrorPage(resp, "Unable to fund X");
                }

            } else {
                createErrorPage(resp, "Error page!");
            }

        } catch (Exception e) {
            PrintWriter writer = resp.getWriter();
            writer.write("An error occurs in AreaCheck: " + e.toString());
            writer.close();
        }
    }


    public void checkButton(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        double scale = Math.pow(10, 4);
        String res = "";
        Double x = Math.ceil(Double.parseDouble(req.getParameter("x")) * scale) / scale;
        Double y = Math.ceil(Double.parseDouble(req.getParameter("Y")) * scale) / scale;
        Double r = Math.ceil(Double.parseDouble(req.getParameter("R")) * scale) / scale;

        if(((x==-3) || (x==-2) || (x==-1) || (x==0) || (x==1) || (x==2) || (x==3) || (x==4) || (x==5)) && (y>=-5) &&
                (y<=3) && (r>=1) && (r<=4)){
            if(ifInZone(x,y,r)){
                res = "True";
                model.setPoint(new Point(x,y,r,true));

            }else {
                res = "False";
                model.setPoint(new Point(x,y,r,false));
            }
            drawTable(resp,x.toString(),y.toString(),r.toString(),res);
        }else {
          createErrorPage(resp,"Error page!");
        }

        HttpSession session = req.getSession();
        session.setAttribute("model", model);
    }

    public void checkGraphic(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        double scale = Math.pow(10, 4);
        String res = "";
        Double x = Math.ceil(Double.parseDouble(req.getParameter("Xgr")) * scale) / scale;
        Double y = Math.ceil(Double.parseDouble(req.getParameter("Y")) * scale) / scale;
        Double r = Math.ceil(Double.parseDouble(req.getParameter("R")) * scale) / scale;

        if((r>=1)&&(r<=4)){
            if(ifInZone(x,y,r)){
                res = "True";
                model.setPoint(new Point(x,y,r,true));

            }else {
                res = "False";
                model.setPoint(new Point(x,y,r,false));
            }
            drawTable(resp,x.toString(),y.toString(),r.toString(),res);

        }else {
            createErrorPage(resp,"Error page!");
        }
    }

    public void drawTable(HttpServletResponse resp, String x, String y, String r, String result) throws IOException {

        PrintWriter writer = resp.getWriter();
        String answer = "<html>\n" +
                "  <head>\n" +
                "   <meta charset=\"utf-8\" /> " +
                "   <title>Result</title>" +
                "   <style>\n" +
                ".header-text{\n" +
                "\t\t\t\tcolor: black;\n" +
                "\t\t\t\tfont-size: 20px;\n" +
                "\t\t\t}" +
                "table {\n" +
                "\t\t\t\tfont-size: 14px;\n" +
                "\t\t\t\tmargin: auto;\n" +
                "\t\t\t\ttext-align: center;\n" +
                "\t\t\t\tborder-collapse: collapse;\n" +
                "\t\t\t\tborder-top: 5px ridge #F1692F;\n" +
                "\t\t\t\tborder-bottom: 5px ridge #F1692F;\n" +
                "\t\t\t\tborder-right: 5px ridge #F1692F;\n" +
                "\t\t\t\tborder-left: 5px ridge #F1692F;\n" +
                "\t\t\t}" +
                "th {\n" +
                "\t\t\t\tfont-size: 13px;\n" +
                "\t\t\t\tfont-weight: normal;\n" +
                "\t\t\t\tbackground: #FFBB97;\n" +
                "\t\t\t\tborder-right: 1px ridge #F1692F;\n" +
                "\t\t\t\tborder-left: 1px ridge #F1692F;\n" +
                "\t\t\t\tcolor: black;\n" +
                "\t\t\t\tpadding: 8px;\n" +
                "\t\t\t}" +
                "td {\n" +
                "\t\t\t\tbackground: #FFBB97;\n" +
                "\t\t\t\tborder-right: 1px ridge #F1692F;\n" +
                "\t\t\t\tborder-left: 1px ridge #F1692F;\n" +
                "\t\t\t\tcolor: black;\n" +
                "\t\t\t\tpadding: 8px;\n" +
                "\t\t\t}" +
                "\t\t</style>" +
                "\t</head>" +
                "\t<body bgcolor=\"#931C14\">\n" +
                "        <table class=\"table\">\n" +
                "            <thead>\n" +
                "                <tr>\n" +
                "                    <th colspan=\"2\" class=\"table\">\n" +
                "                        <div class=\"header-text\"> Checking result: </div>\n" +
                "                    </th>\n" +
                "                </tr>\n" +
                "            </thead>\n" +
                "            <tbody>\n" +
                "                <tr>\n" +
                "                    <td class=\"table\">\n" +
                "                        <div id=\"label_x\">X = </div>\n" +
                "                    </td>\n" +
                "                    <td class=\"table\">\n" +
                "                        <div id = \\\"error1\\\">" + x + "</div>\t\t\t\t\t\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "\n" +
                "                <tr>\n" +
                "                    <td class=\"table\">\n" +
                "                        <div id=\"label_y\">Y = </div>\n" +
                "                    </td>\n" +
                "                    <td class=\"table\">\n" +
                "                       <div id = \\\"error2\\\">" + y + "</div>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "\n" +
                "                <tr>\n" +
                "                    <td class=\"table\">\n" +
                "                        <div id=\"label_r\">R = </div>\n" +
                "                    </td>\n" +
                "                    <td class=\"table\">\n" +
                "                        <div id = \\\"error3\\\">" + r + "</div>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "\n" +
                "                <tr>\n" +
                "                    <td class=\"table\">\n" +
                "                        <div id=\"label_r\">Result: </div>\n" +
                "                    </td>\n" +
                "                    <td class=\"table\">\n" +
                "                        <div id = \\\"error4\\\">" + result + "</div>\n" +
                "                    </td>\n" +
                "                </tr>" +
                "\n" +
                "                <tr>\n" +
                "                    <td class=\"table\">\n" +
                "                        <a href = \"http://localhost:8080/WebLab2_war_exploded\"> BACK </a>\n" +
                "                    </td>\n" +
                "\t\t\t\t\t<td class=\"table\">                       \n" +
                "                    </td>\n" +
                "                </tr>              \n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </body>\n" +
                "</html>";
        writer.write(answer);
        writer.close();
    }


    public void createErrorPage(HttpServletResponse resp, String text) throws IOException {
        PrintWriter writer = resp.getWriter();
        String answer = "<html>\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\" /> " +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/error.css\">" +
                "  </head>" +
                "<body>" +
                "<div id = \"error\">Error " + text + "</div>" +
                "<a href = \"http://localhost:8080/WebLab2_war_exploded\"> BACK </a>" +
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


    public boolean ifInZone(Double x, Double y, Double r){
        boolean res = false;
        if ((x >= 0) && (y <= 0) && (x <= r) && (y >= (-r)) ||
                (x >= 0) && (y >= 0) && (x*x + y*y <= r*r) ||
                (x <=0) && (y >= 0) && (y <= x + r/2)){
            res = true;
        }
        return res;
    }
}
