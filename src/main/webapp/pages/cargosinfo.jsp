<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: ivanrud
  Date: 29.04.2021
  Time: 21:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>IRC | Cargos</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico?" type="image/x-icon"/>

    <style>
        <%@include file="/pages/styles/style.css" %>

    </style>
</head>
<body style="font-family: 'Montserrat',sans-serif">

<div class="head">
    <a href="home">
        <img src="${pageContext.request.contextPath}/images/mainlogo.png" width="118px" height="35px"
             style="margin-top: 17px; margin-left: 75px"/>
    </a>
    <div style="display: inline-block; float: right; margin-right: 40px; margin-top: 16px;">
        <div style="display: inline-block">
            <sec:authentication var="user" property="principal"/>

            <sec:authorize access="isAuthenticated()">
                <label style="font-weight: normal">Welcome, </label>
                <label style="color: #4460ef; font-weight: bold">${user.username}</label>!
            </sec:authorize>
        </div>
        <div style="display: inline-block; margin-left: 10px;">
            <a href="logout">
                <button type="submit" class="btn btn-outline-primary">Logout</button>
            </a>
        </div>
    </div>
</div>

<div class="wrapper">
    <div class="menu">
        <a href="routes">
            <button class="menu-button">
                📍&nbsp;&nbsp;&nbsp;Routes
            </button>
        </a>
        <br>
        <br>
        <br>
        <button class="menu-button-clicked">📦&nbsp;&nbsp;&nbsp;Orders</button>
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
    </div>
    <div class="content">
        <div style="width: 800px; margin: 50px auto auto;">
            <h2 style="text-align: center; margin-bottom: 30px">📦</h2>

            <button class="btn btn-primary" data-toggle="modal" data-target="#addModal"
                    style="background-color: #4460ef; font-weight: bold; width: 800px">+
            </button>

            <div id="addModal" class="modal fade" style="z-index: 1300 !important;">
                <div class="modal-dialog">
                    <div class="modal-content" style="width: 600px; padding: 20px">

                        <h2 style="margin-bottom: 30px">Adding a new cargo</h2>

                        <form:form action="cargos/add" method="post" modelAttribute="newCargo">
                            <div class="form-group row">
                                <h2 for="titleInput" class="col-sm-2 col-form-label">Title: </h2>
                                <div class="col-sm-10">
                                    <form:input path="title" type="text" class="form-control" id="titleInput"
                                                placeholder="Title"/>
                                </div>
                            </div>
                            <div class="form-group row">
                                <h2 for="massInput" class="col-sm-2 col-form-label">Mass (.kg): </h2>
                                <div class="col-sm-10">
                                    <form:input type="hidden" path="status" value="PREPARED"/>
                                    <form:input path="mass" type="number" class="form-control" id="massInput"
                                                placeholder="Mass (.kg)"/>
                                </div>
                            </div>
                            <div class="form-group row">
                                <h2 for="depInput" class="col-sm-2 col-form-label">From: </h2>
                                <div class="col-sm-10">
                                    <form:select path="departure" type="text" class="form-control" id="depInput">
                                        <c:forEach items="${cities}" var="city">
                                            <form:option value="${city.title}">${city.title}</form:option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <h2 for="depInput" class="col-sm-2 col-form-label">To: </h2>
                                <div class="col-sm-10">
                                    <form:select path="destination" type="text" class="form-control" id="depInput">
                                        <c:forEach items="${cities}" var="city">
                                            <form:option value="${city.title}">${city.title}</form:option>
                                        </c:forEach>
                                    </form:select>
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

            <table class="table" style="margin: 10px auto auto; font-family: 'Montserrat',sans-serif">
                <thead style="background-color: #2b2d42; color: white">
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">TITLE</th>
                    <th scope="col">MASS (kg.)</th>
                    <th scope="col">FROM</th>
                    <th scope="col">TO</th>
                    <th scope="col">STATUS</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${cargoslist}" var="cargo">
                    <tr style="z-index: 1500 !important;">
                        <th scope="row" style="color: #4460ef">${cargo.id}</th>
                        <td>${cargo.title}</td>
                        <td>${cargo.mass}</td>
                        <td>${cargo.departure}</td>
                        <td>${cargo.destination}</td>
                        <td>${cargo.status.getStatus()}</td>
                        <td><a href="cargos/delete?id=${cargo.id}" style="color: #f07167;">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                 class="bi bi-x-square-fill" viewBox="0 0 16 16">
                                <path d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm3.354 4.646L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 1 1 .708-.708z"></path>
                            </svg>
                        </a></td>
                        <td><a href="" data-toggle="modal" data-target="#${cargo.idAsString()}" style="color: #4460ef">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                 class="bi bi-pencil-square" viewBox="0 0 16 16">
                                <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"></path>
                                <path fill-rule="evenodd"
                                      d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"></path>
                            </svg>
                        </a>

                            <div id="${cargo.idAsString()}" class="modal fade" style="z-index: 1300 !important;">
                                <div class="modal-dialog">
                                    <div class="modal-content" style="width: 600px; padding: 20px">

                                        <h2 style="margin-bottom: 30px">Editing cargo information</h2>

                                        <form:form action="cargos/edit" method="post" modelAttribute="newCargo">
                                            <div class="form-group row">
                                                <h2 for="titleInput" class="col-sm-2 col-form-label">Title: </h2>
                                                <div class="col-sm-10">
                                                    <form:input type="hidden" path="id" value="${cargo.id}"/>
                                                    <form:input path="title" type="text" class="form-control"
                                                                id="titleInput" value="${cargo.title}"/>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <h2 for="massInput" class="col-sm-2 col-form-label">Mass (.kg): </h2>
                                                <div class="col-sm-10">
                                                    <form:input path="mass" type="number" class="form-control"
                                                                id="massInput"
                                                                value="${cargo.mass}"/>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <h2 for="depInput" class="col-sm-2 col-form-label">From: </h2>
                                                <div class="col-sm-10">
                                                    <form:select path="departure" type="text" class="form-control"
                                                                 id="depInput" selected="${cargo.departure}">
                                                        <form:option
                                                                value="${cargo.departure}">${cargo.departure}</form:option>
                                                        <c:forEach items="${cities}" var="city">
                                                            <form:option
                                                                    value="${city.title}">${city.title}</form:option>
                                                        </c:forEach>
                                                    </form:select>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <h2 for="desInput" class="col-sm-2 col-form-label">To: </h2>
                                                <div class="col-sm-10">
                                                    <form:select path="destination" type="text" class="form-control"
                                                                 id="desInput"
                                                                 selected="${cargo.destination}">
                                                        <form:option
                                                                value="${cargo.destination}">${cargo.destination}</form:option>
                                                        <c:forEach items="${cities}" var="city">
                                                            <form:option value="${city.title}">${city.title}</form:option>
                                                        </c:forEach>
                                                    </form:select>
                                                </div>
                                            </div>
                                            <fieldset class="form-group">
                                                <div class="row">
                                                    <h2 class="col-form-label col-sm-2 pt-0">Status: </h2>
                                                    <div class="col-sm-10">
                                                        <div class="form-check">
                                                            <form:radiobutton path="status" value="PREPARED"
                                                                              class="form-check-input" name="gridRadios"
                                                                              id="gridRadios1" checked="checked"/>
                                                            <label class="form-check-label" for="gridRadios1">
                                                                Prepared for dispatch
                                                            </label>
                                                        </div>
                                                        <div class="form-check">
                                                            <form:radiobutton path="status" value="DELIEVERED"
                                                                              class="form-check-input" name="gridRadios"
                                                                              id="gridRadios3"/>
                                                            <label class="form-check-label" for="gridRadios3">
                                                                Delievered
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

                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
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
</div>
</body>
</html>
