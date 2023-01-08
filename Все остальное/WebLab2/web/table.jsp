<%@ page import="model.Point" %>
<%@ page import="model.Model" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% if (!Model.points.isEmpty()) {  Point dot = Model.points.get(Model.points.size()-1); %>
<circle id="point2" r="3" cx="<%= dot.getX() %>" cy="<%= dot.getY() %>" fill=#6A1515 stroke="white" visibility="visible"></circle>
<% } %>

<% if (!Model.points.isEmpty() && Model.points.size() > 1) {  Point dot = Model.points.get(Model.points.size()-2); %>
<circle id="point2" r="3" cx="<%= dot.getX() %>" cy="<%= dot.getY() %>" fill=#8E1D1D stroke="white" visibility="visible"></circle>
<% } %>

<% if (!Model.points.isEmpty() && Model.points.size() > 2) {  Point dot = Model.points.get(Model.points.size()-3); %>
<circle id="point2" r="3" cx="<%= dot.getX() %>" cy="<%= dot.getY() %>" fill=#B12626 stroke="white" visibility="visible"></circle>
<% } %>

<% if (!Model.points.isEmpty() && Model.points.size() > 3) {  Point dot = Model.points.get(Model.points.size()-4); %>
<circle id="point2" r="3" cx="<%= dot.getX() %>" cy="<%= dot.getY() %>" fill=#E53535 stroke="white" visibility="visible"></circle>
<% } %>