<%--
  Encaminha a raiz da aplicacao para a landing page.
--%>
<%
    request.getRequestDispatcher("/WEB-INF/jsp/landing.jsp").forward(request, response);
%>
