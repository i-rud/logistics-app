<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>IRC | Login</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico?" type="image/x-icon"/>

    <style>
        <%@include file="/pages/styles/style.css" %>
    </style>
</head>
<body class="text-center" style="font-family: 'Montserrat', sans-serif">
<img src="${pageContext.request.contextPath}/images/mainlogo.png" width="236px" height="70px"
     style="margin: 200px auto 20px;"/>
<!-- /login?error=true -->
<c:if test="${param.error == 'true'}">
    <div class="alert alert-danger" role="alert">
        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
        <span class="sr-only">Error:</span>
            ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].getMessage()}
    </div>
</c:if>
<div class="login-form">
    <form name='f' action="${pageContext.request.contextPath}/j_spring_security_check" method='POST'>
        <input type='text' class="form-control" style="height: 50px; border-radius: 5px 5px 0 0" name='username' value='' placeholder="Username">
        <input type='password' class="form-control" style="height: 50px; border-radius: 0 0 5px 5px" name='password' placeholder="Password"/>
        <br>
        <button class="btn btn-lg btn-primary btn-block" type="submit" style="background-color: #4460ef">Sign in</button>
    </form>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
