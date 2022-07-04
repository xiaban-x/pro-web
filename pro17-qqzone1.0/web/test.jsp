<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JavaWeb</title>
</head>
<body>
<div style="font-family:monospace; text-align: center; font-size: 20px; margin-top: 200px">
    ████████╗&nbsp██████╗&nbsp███╗&nbsp&nbsp&nbsp███╗&nbsp██████╗&nbsp█████╗&nbsp████████╗<br>
    ╚══██╔══╝██╔═══██╗████╗&nbsp████║██╔════╝██╔══██╗╚══██╔══╝<br>
    &nbsp&nbsp&nbsp██║&nbsp&nbsp&nbsp██║&nbsp&nbsp&nbsp██║██╔████╔██║██║&nbsp&nbsp&nbsp&nbsp&nbsp███████║&nbsp&nbsp&nbsp██║&nbsp&nbsp&nbsp<br>
    &nbsp&nbsp&nbsp██║&nbsp&nbsp&nbsp██║&nbsp&nbsp&nbsp██║██║╚██╔╝██║██║&nbsp&nbsp&nbsp&nbsp&nbsp██╔══██║&nbsp&nbsp&nbsp██║&nbsp&nbsp&nbsp<br>
    &nbsp&nbsp&nbsp██║&nbsp&nbsp&nbsp╚██████╔╝██║&nbsp╚═╝&nbsp██║╚██████╗██║&nbsp&nbsp██║&nbsp&nbsp&nbsp██║&nbsp&nbsp&nbsp<br>
    &nbsp&nbsp&nbsp╚═╝&nbsp&nbsp&nbsp&nbsp╚═════╝&nbsp╚═╝&nbsp&nbsp&nbsp&nbsp&nbsp╚═╝&nbsp╚═════╝╚═╝&nbsp&nbsp╚═╝&nbsp&nbsp&nbsp╚═╝&nbsp&nbsp&nbsp<br>
</div>

<h1 style="text-align: center; font-size: 60px; margin-top: 50px">
    <%= application.getServerInfo() %>
    <%
        System.out.println("Tomcat运行成功");
        System.out.println(application.getServerInfo());
    %>
</h1>
</body>
</html>
