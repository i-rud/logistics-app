<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: ivanrud
  Date: 29.04.2021
  Time: 21:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>IRC | Homepage</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico?" type="image/x-icon"/>
    <style>
        <%@include file="/pages/styles/style.css" %>
    </style>
</head>
<body style="font-family: 'Montserrat',sans-serif; font-weight: bolder">
<sec:authentication var="user" property="principal"/>
<sec:authorize access="hasAuthority('DRIVER') and isAuthenticated()">
    <c:forEach items="${drivers}" var="driver">
        <c:if test="${driver.email.equals(user.username)}">
            <c:set var="driverinfo" value="${driver}" scope="session"/>
        </c:if>
    </c:forEach>
</sec:authorize>
<div class="head">
    <img src="${pageContext.request.contextPath}/images/mainlogo.png" width="118px" height="35px"
         style="margin-top: 17px; margin-left: 75px"/>
    <div style="display: inline-block; float: right; margin-right: 40px; margin-top: 16px;">
        <div style="display: inline-block">
            <sec:authorize access="isAuthenticated()">
                <label style="font-weight: normal">Welcome, </label>
                <label style="color: #4460ef;">${user.username}</label>!
            </sec:authorize>
        </div>

        <sec:authorize access="hasAuthority('DRIVER') and isAuthenticated()">
            <div style="display: inline-block; margin-left: 10px;">
                <a href="" data-toggle="modal" data-target="#edit">
                    <button type="button" class="btn btn-warning">Edit</button>
                </a>
            </div>
        </sec:authorize>
        <div style="display: inline-block; margin-left: 10px;">
            <a href="logout">
                <button type="submit" class="btn btn-outline-primary">Logout</button>
            </a>
        </div>
    </div>
</div>

<div id="edit" class="modal fade" style="z-index: 1300 !important;">
    <div class="modal-dialog">
        <div class="modal-content" style="padding: 20px; width: 600px">
            <form:form action="drivers/edit" method="post" modelAttribute="newDriver">
                <div class="form-group row">
                    <h2 for="firstNameInput" class="col-sm-2 col-form-label">First
                        name: </h2>
                    <div class="col-sm-10">
                        <form:input type="hidden" path="id" value="${driverinfo.id}"/>
                        <form:input type="hidden" path="email" value="${driverinfo.email}"/>
                        <form:input type="hidden" path="city" value="${driverinfo.city}"/>
                        <form:input type="hidden" path="hoursWorked" value="${driverinfo.hoursWorked}"/>
                        <form:input path="firstName" type="text" class="form-control"
                                    id="firstNameInput"
                                    value="${driverinfo.firstName}"/>
                    </div>
                </div>
                <div class="form-group row">
                    <h2 for="secondNameInput" class="col-sm-2 col-form-label">Second
                        name: </h2>
                    <div class="col-sm-10">
                        <form:input path="secondName" type="text" class="form-control"
                                    id="secondNameInput"
                                    value="${driverinfo.secondName}"/>
                    </div>
                </div>
                <div class="form-group row">
                    <h2 for="telegramInput" class="col-sm-2 col-form-label">Telegram (without @): </h2>
                    <div class="col-sm-10">
                        <form:input path="telegram" type="text" class="form-control"
                                    id="telegramInput"
                                    value="${driverinfo.telegram}"/>
                    </div>
                </div>
                <div class="form-group row">
                    <h2 for="firstNameInput" class="col-sm-2 col-form-label">Password: </h2>
                    <div class="col-sm-10">
                        <form:input path="password" type="password" class="form-control"
                                    id="firstNameInput"
                                    value="${driverinfo.password}"/>
                    </div>
                </div>
                <fieldset class="form-group">
                    <div class="row">
                        <h2 class="col-form-label col-sm-2 pt-0">Status: </h2>
                        <div class="col-sm-10">
                            <form:input type="hidden" path="status" value="${driverinfo.status}"/>
                            <div class="form-check">
                                <form:radiobutton path="status" value="ON_SHIFT"
                                                  class="form-check-input"
                                                  name="gridRadios" id="gridRadios2"/>
                                <label class="form-check-label" for="gridRadios2">
                                    On shift
                                </label>
                            </div>
                            <div class="form-check">
                                <form:radiobutton path="status" value="DRIVING"
                                                  class="form-check-input"
                                                  name="gridRadios" id="gridRadios3"/>
                                <label class="form-check-label" for="gridRadios3">
                                    Driving
                                </label>
                            </div>
                        </div>
                    </div>
                </fieldset>
                <div class="form-group row">
                    <div class="col-sm-10">
                        <button type="submit" class="btn btn-primary" style="width: 560px">
                            Edit
                        </button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>

<div class="wrapper">
    <div class="menu" style="padding-top: 10px">
        <sec:authorize access="hasAuthority('DRIVER')">
            <div class="card bg-light mb-3"
                 style="font-family: 'JetBrains Mono'; max-width: 17rem; margin: auto; text-align: left">
                <div class="card-header" style="font-weight: bold">Driver information</div>
                <div class="card-body">
                    <p class="card-text">
                        Name: <label
                            style="font-weight: bold; color: #2a9d8f">${driverinfo.firstName} ${driverinfo.secondName}</label>
                        <br>
                        Telegram: <label
                            style="font-weight: bold; color: #2a9d8f">${driverinfo.telegram}</label>
                        <br>
                        Email: <label style="font-weight: bold; color: #2a9d8f">${driverinfo.email}</label>
                        <br>
                        Hours worked: <label
                            style="font-weight: bold; color: #2a9d8f">${driverinfo.hoursWorked}</label>
                        <br>
                        Current city: <label style="font-weight: bold; color: #2a9d8f">${driverinfo.city}</label>
                        <br>
                        Status: <label style="font-weight: bold; color: #2a9d8f">${driverinfo.status.status}</label>
                    </p>
                </div>
            </div>
            <a href="routes">
                <button class="menu-button">
                    📍&nbsp;&nbsp;&nbsp;Routes
                </button>
            </a>
        </sec:authorize>
        <sec:authorize access="hasAuthority('MANAGER')">
            <a href="routes">
                <button class="menu-button" style="margin-top: 100px">
                    📍&nbsp;&nbsp;&nbsp;Routes
                </button>
            </a>
        </sec:authorize>
        <br>
        <br>
        <br>
        <sec:authorize access="hasAuthority('MANAGER') and isAuthenticated()">
            <a href=" cargos
            ">
                <button class="menu-button">
                    📦&nbsp;&nbsp;&nbsp;Orders
                </button>
            </a>
            <br>
            <a href="trucks">
                <button class="menu-button">
                    🚚&nbsp;&nbsp;&nbsp;Trucks
                </button>
            </a>
            <br>
            <a href="drivers">
                <button class="menu-button">
                    👦🏻&nbsp;&nbsp;&nbsp;Drivers
                </button>
            </a>
            <br>
            <a href="cities">
                <button class="menu-button">
                    🏙️&nbsp;&nbsp;&nbsp;Cities
                </button>
            </a>
        </sec:authorize>
    </div>
    <div class="content">

        <div class="card-deck" style="font-family: 'Helvetica Neue';width: 1100px; margin: 100px auto auto;">
            <div class="card">
                <img class="card-img-top" src="${pageContext.request.contextPath}/images/truck.gif"
                     alt="Card image cap">
                <div class="card-body">
                    <h5 class="card-title" style="font-weight: bold">Trucks 🚚</h5>
                    <p class="card-text" style="font-weight: normal">Our company has a lot of modern and powerful
                        trucks.
                        They are really fast! You will not have time to blink, as your order will be delievered.</p>
                </div>
            </div>
            <div class="card">
                <img class="card-img-top" src="${pageContext.request.contextPath}/images/map.gif" alt="Card image cap">
                <div class="card-body">
                    <h5 class="card-title" style="font-weight: bold">Delivery range 🗺️</h5>
                    <p class="card-text" style="font-weight: normal">Current delivery range is extremely wide! We open a
                        lot of new
                        delivery points every week! Very soon we will be delivering orders not only in Germany but
                        all over the world!</p>
                </div>
            </div>
            <div class="card">
                <img class="card-img-top" src="${pageContext.request.contextPath}/images/driver.gif"
                     alt="Card image cap">
                <div class="card-body">
                    <h5 class="card-title" style="font-weight: bold">Drivers 👦🏻</h5>
                    <p class="card-text" style="font-weight: normal">With no doubt our drivers are the best specialists.
                        We employ only high-experienced drivers and we post a lot of new vacancies
                        every week. Nobody will remain without orders!</p>
                </div>
            </div>
        </div>

    </div>
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
