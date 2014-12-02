<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="header.jsp"></c:import>

        <script>
            alert('Permissão Negada!!!');
            window.location='SessionServlet?action=logout';
        </script>

    <c:import url="footer.jsp"></c:import>
