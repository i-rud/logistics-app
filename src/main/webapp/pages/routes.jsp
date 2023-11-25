<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: ivanrud
  Date: 05.05.2021
  Time: 13:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>IRC | Routes</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico?" type="image/x-icon"/>

    <style>
        <%@include file="/pages/styles/style.css" %>

    </style>

</head>
<body style="font-family: 'Montserrat',sans-serif;">

<sec:authentication var="user" property="principal"/>
<sec:authorize var="role" access="hasAuthority('MANAGER')"/>

<sec:authentication var="user" property="principal"/>
<sec:authorize access="hasAuthority('DRIVER') and isAuthenticated()">
    <c:forEach items="${drivers}" var="driver">
        <c:if test="${driver.email.equals(user.username)}">
            <c:set var="driverinfo" value="${driver}" scope="session"/>
        </c:if>
    </c:forEach>
</sec:authorize>
<div class="head">
    <a href="home">
        <img src="${pageContext.request.contextPath}/images/mainlogo.png" width="118px" height="35px"
             style="margin-top: 17px; margin-left: 75px"/>
    </a>
    <div style="display: inline-block; float: right; margin-right: 40px; margin-top: 16px;">
        <div style="display: inline-block">
            <sec:authorize access="isAuthenticated()">
                <label style="font-weight: normal">Welcome, </label>
                <label style="font-weight: bold; color: #4460ef;">${user.username}</label>!
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
            <button class="menu-button-clicked">
                📍&nbsp;&nbsp;&nbsp;Routes
            </button>
        </sec:authorize>

        <sec:authorize access="hasAuthority('MANAGER')">
            <button class="menu-button-clicked" style="margin-top: 100px">
                📍&nbsp;&nbsp;&nbsp;Routes
            </button>
        </sec:authorize>
        <br>
        <br>
        <br>
        <sec:authorize access="hasAuthority('MANAGER') and isAuthenticated()">
            <a href="cargos">
                <button class="menu-button">📦&nbsp;&nbsp;&nbsp;Orders</button>
            </a>
            <br>
            <a href="trucks">
                <button class="menu-button">🚚&nbsp;&nbsp;&nbsp;Trucks</button>
            </a>
            <a href="drivers">
                <button class="menu-button">
                    👦🏻&nbsp;&nbsp;&nbsp;Drivers
                </button>
            </a>
            <a href="cities">
                <button class="menu-button">
                    🏙️&nbsp;&nbsp;&nbsp;Cities
                </button>
            </a>
        </sec:authorize>
    </div>
    <div class="content">
        <div style="width: 700px; margin: 50px auto auto;">
            <h2 style="text-align: center; margin-bottom: 30px">📍</h2>

            <sec:authorize access="hasAuthority('MANAGER') and isAuthenticated()">
                <button class="btn btn-primary" data-toggle="modal" data-target="#addModal"
                        style="background-color: #4460ef; font-weight: bold; width: 700px;">+
                </button>
            </sec:authorize>

            <div id="addModal" class="modal fade" style="z-index: 1300 !important;">
                <div class="modal-dialog">
                    <div class="modal-content" style="padding: 20px; width: 600px">

                        <h2 style="margin-bottom: 30px">Adding a new route</h2>

                        <form:form action="routes/add" method="post" modelAttribute="newOrder">
                            <div class="form-group row">
                                <h2 for="depInput" class="col-sm-2 col-form-label">Choose orders: </h2>
                                <div class="col-sm-10">
                                    <form:input path="status" type="hidden" value="PREPARING"/>
                                    <form:select path="cargosId" type="text" multiple="multiple"
                                                 class="form-control"
                                                 id="depInput"
                                                 required="required">
                                        <c:forEach items="${cargos}" var="cargo">
                                            <form:option value="${cargo.id}">
                                                <td>${cargo.title}</td>
                                                <td> (${cargo.mass}kg, from</td>
                                                <td>${cargo.departure} to</td>
                                                <td>${cargo.destination})</td>
                                            </form:option>
                                        </c:forEach>
                                    </form:select>
                                    </tbody>
                                    </table>
                                </div>
                            </div>

                            <div class="form-group row">
                                <div class="col-sm-10">
                                    <button type="submit" class="btn btn-primary" style="width: 560px">Add</button>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>

        <c:forEach items="${orders}" var="order">
            <c:if test="${role || (order.has(user.username) && !order.status.status.equals('Preparing'))}">
                <div class="plate">
                    <p style="display: inline-block; color: #4460ef; font-weight: bold">Order#${order.id}</p>
                    <p style="display: inline-block; color: #353852; font-weight: bold">
                        [${order.status.getStatus()}]</p>
                    <a style="display: inline-block; margin-left: 5px;" href="" data-toggle="modal"
                       data-target="#infoModal${order.id}">
                        ℹ️
                    </a>
                    <div style="display: inline-block; float: right">
                        <c:if test="${order.truck == null && order.status != 'CLOSED'}">
                            <a href="" data-toggle="modal" data-target="#truckModal${order.id}"
                               style="color: #4460ef; margin-left: 20px">
                                🚚
                            </a>
                        </c:if>
                        <c:if test="${order.truck != null && order.drivers.size() < order.requiredDrivers}">
                            <a href="" data-toggle="modal" data-target="#driverModal${order.id}"
                               style="color: #4460ef;margin-left: 20px">
                                👦🏻
                            </a>
                        </c:if>
                        <c:if test="${order.status == 'READY'}">
                            <a href="routes/start?id=${order.id}"
                               style="color: #4460ef; margin-left: 20px; font-weight: bold">
                                Start
                            </a>
                        </c:if>
                        <c:if test="${order.status == 'IN_PROGRESS'}">
                            <a href="routes/complete?id=${order.id}"
                               style="color: #4460ef; margin-left: 20px;">
                                ✅
                            </a>
                        </c:if>
                        <sec:authorize access="hasAuthority('MANAGER') and isAuthenticated()">
                            <a href="routes/delete?id=${order.id}"
                               style="color: #f07167; display: inline-block;margin-left: 20px">
                                ❌
                            </a>
                        </sec:authorize>
                    </div>

                    <div id="infoModal${order.id}" class="modal fade" style="z-index: 1300 !important;">
                        <div class="modal-dialog">
                            <div class="modal-content" style="padding: 20px; width: 600px">

                                <h2 style="margin-bottom: 30px">Order information</h2>
                                <div class="form-group row">
                                    <h2 for="depInput" class="col-sm-2 col-form-label">Order id: </h2>
                                    <div class="col-sm-10">
                                        <label class="form-control"
                                               style="width: 450px; float: right;">${order.id}</label>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <h2 for="depInput" class="col-sm-2 col-form-label">Order status: </h2>
                                    <div class="col-sm-10">
                                        <label class="form-control"
                                               style="width: 450px; float: right;">${order.status.getStatus()}</label>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <h2 for="depInput" class="col-sm-2 col-form-label">Trip time: </h2>
                                    <div class="col-sm-10">
                                        <label class="form-control"
                                               style="width: 450px; float: right;">${order.tripTime}h</label>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <h2 for="depInput" class="col-sm-2 col-form-label">Total distance: </h2>
                                    <div class="col-sm-10">
                                        <label class="form-control"
                                               style="width: 450px; float: right;">${order.distance}km</label>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <h2 for="pathInput" class="col-sm-2 col-form-label">Route: </h2>
                                    <div class="col-sm-10">
                                        <label class="form-control"
                                               style="width: 450px; float: right;">${order.path}</label>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <h2 for="depInput" class="col-sm-2 col-form-label">Display route: </h2>
                                    <div class="col-sm-10">
                                    <a class="btn btn-outline-primary" href="routes/display?id=${order.id}"
                                       style="display: inline-block; margin-left: 11px; margin-top: 7px">
                                        Display route
                                    </a>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <h2 for="depInput" class="col-sm-2 col-form-label">Total weight: </h2>
                                    <div class="col-sm-10">
                                        <label class="form-control"
                                               style="width: 450px; float: right;">${order.totalWeight}kg</label>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <h2 for="depInput" class="col-sm-2 col-form-label">Departure: </h2>
                                    <div class="col-sm-10">
                                        <label class="form-control"
                                               style="width: 450px; float: right;"> ${order.departure}</label>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <h2 for="depInput" class="col-sm-2 col-form-label">Destination: </h2>
                                    <div class="col-sm-10">
                                        <label class="form-control"
                                               style="width: 450px; float: right;">${order.destination}</label>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <h2 for="depInput" class="col-sm-2 col-form-label">Cargos: </h2>
                                    <div class="col-sm-10">
                                        <table class="table"
                                               style="margin: 10px auto auto; font-family: 'Open Sans'">
                                            <thead style="background-color: #2b2d42; color: white">
                                            <tr>
                                                <th scope="col">ID</th>
                                                <th scope="col">TITLE</th>
                                                <th scope="col">MASS (kg.)</th>
                                                <th scope="col">FROM</th>
                                                <th scope="col">TO</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${order.cargos}" var="cargo">
                                                <tr style="z-index: 1500 !important;">
                                                    <th scope="row" style="color: #4460ef">${cargo.id}</th>
                                                    <td>${cargo.title}</td>
                                                    <td>${cargo.mass}</td>
                                                    <td>${cargo.departure}</td>
                                                    <td>${cargo.destination}</td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <h2 for="depInput" class="col-sm-2 col-form-label">Truck: </h2>
                                    <div class="col-sm-10">
                                        <label class="form-control"
                                               style="width: 450px; float: right;">${order.truck.plate}</label>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <h2 for="depInput" class="col-sm-2 col-form-label">Drivers: </h2>
                                    <div class="col-sm-10">
                                        <table class="table"
                                               style="margin: 10px auto auto; font-family: 'Open Sans'">
                                            <thead style="background-color: #2b2d42; color: white">
                                            <tr>
                                                <th scope="col">ID</th>
                                                <th scope="col">FIRST NAME</th>
                                                <th scope="col">SECOND NAME</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${order.drivers}" var="driver">
                                                <tr style="z-index: 1500 !important;">
                                                    <th scope="row" style="color: #4460ef">${driver.id}</th>
                                                    <td>${driver.firstName}</td>
                                                    <td>${driver.secondName}</td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="truckModal${order.id}" class="modal fade" style="z-index: 1300 !important;">
                        <div class="modal-dialog">
                            <div class="modal-content" style="padding: 20px; width: 600px">

                                <h2 style="margin-bottom: 30px">Add a truck</h2>

                                <form:form action="routes/truck" method="post" modelAttribute="newOrder">
                                    <div class="form-group row">
                                        <h2 for="depInput" class="col-sm-2 col-form-label">Choose truck: </h2>
                                        <div class="col-sm-10">
                                            <div class="form-check">
                                                <form:input path="id" value="${order.id}" type="hidden"/>
                                                <c:forEach items="${trucks}" var="truck">
                                                    <c:if test="${truck.city == order.departure && truck.capacity >= order.totalWeight && truck.status == 'FREE' && truck.condition == 'OK'}">
                                                        <div class="form-check">
                                                            <form:radiobutton path="truckInWork" value="${truck.id}"
                                                                              class="form-check-input"
                                                                              name="gridRadios"
                                                                              id="gridRadios1" checked="checked"/>
                                                            <label class="form-check-label" for="gridRadios1">
                                                                    ${truck.plate}
                                                            </label>
                                                        </div>
                                                    </c:if>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <div class="col-sm-10">
                                            <button type="submit" class="btn btn-primary" style="width: 560px">Add
                                            </button>
                                        </div>
                                    </div>
                                </form:form>
                            </div>
                        </div>
                    </div>

                    <div id="driverModal${order.id}" class="modal fade" style="z-index: 1300 !important;">
                        <div class="modal-dialog">
                            <div class="modal-content" style="padding: 20px; width: 600px">

                                <h2 style="margin-bottom: 30px">Adding drivers</h2>

                                <label style="opacity: 0.7;">
                                    Please choose <label
                                        style="color: #4460ef; font-weight: bold">${order.getRequiredDrivers() - order.drivers.size()}</label>
                                    drivers or you will not able to start the order.
                                </label>

                                <form:form action="routes/drivers" method="post" modelAttribute="newOrder">
                                    <div class="form-group row">
                                        <h2 for="driverInput" class="col-sm-2 col-form-label">Choose drivers: </h2>
                                        <div class="col-sm-10">
                                            <div class="form-check">
                                                <form:input path="id" value="${order.id}" type="hidden"/>
                                                <form:select path="driversId" type="text" multiple="multiple"
                                                             class="form-control"
                                                             id="depInput" required="required">
                                                    <c:forEach items="${drivers}" var="driver">
                                                        <c:if test="${driver.city.equals(order.departure) && (driver.hoursWorked + order.tripTime) <= 176}">
                                                            <form:option value="${driver.id}">
                                                                <td>[#${driver.id}]</td>
                                                                <td> ${driver.firstName}</td>
                                                                <td> ${driver.secondName}</td>
                                                            </form:option>
                                                        </c:if>
                                                    </c:forEach>
                                                </form:select>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <div class="col-sm-10">
                                            <button type="submit" class="btn btn-primary" style="width: 560px">Add
                                            </button>
                                        </div>
                                    </div>

                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
        </c:forEach>
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
                        <form:input path="password" type="text" class="form-control"
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

<script
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDYcL-6U1p0x7s1Z77r7j2iWnytpMpwXIo&callback=initMap&libraries=&v=weekly"
        async
></script>
</body>
</html>
