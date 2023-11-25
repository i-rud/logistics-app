<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ivanrud
  Date: 06.06.2021
  Time: 12:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>IRC | Map</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico?" type="image/x-icon"/>
    <style>
        <%@include file="/pages/styles/style.css" %>
    </style>

    <script>
        function displayRoute(origin, destination, service, display) {
            service.route(
                {
                    origin: origin,
                    destination: destination,
                    waypoints: [
                        <c:forEach items="${cities}" var="city">
                        {location: "${city}"},
                        </c:forEach>
                    ],
                    travelMode: google.maps.TravelMode.DRIVING,
                    avoidTolls: true,
                },
                (result, status) => {
                    if (status === "OK" && result) {
                        display.setDirections(result);
                    } else {
                        alert("Could not display directions due to: " + status);
                    }
                }
            );
        }

        function initMap() {
            const map = new google.maps.Map(document.getElementById("map"), {
                zoom: 4,
                center: {lat: 51.51, lng: 9.92}, // Germany.
            });
            const directionsService = new google.maps.DirectionsService();
            const directionsRenderer = new google.maps.DirectionsRenderer({
                draggable: true,
                map,
                panel: document.getElementById("right-panel"),
            });
            directionsRenderer.addListener("directions_changed", () => {
                computeTotalDistance(directionsRenderer.getDirections());
            });
            displayRoute(
                "${from}",
                "${to}",
                directionsService,
                directionsRenderer
            );
        }
    </script>
</head>
<body>

<div class="head">
    <button class="btn btn-primary" type="submit" onclick="window.history.back()"
            style="margin-left: 20px; margin-top: 15px">Back
    </button>

    <button class="btn btn-outline-primary" href="" data-toggle="modal"
            data-target="#infoModal" style="display: inline-block; margin-top: 15px; margin-left: 5px">
        Order info
    </button>
    <a href="home">
        <img src="${pageContext.request.contextPath}/images/mainlogo.png" width="118px" height="35px"
             style="margin-top: 17px; margin-left: 475px"/>
    </a>
</div>


<div id="infoModal" class="modal fade" style="z-index: 1300 !important;">
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


<div id="map"></div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>

<script
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDYcL-6U1p0x7s1Z77r7j2iWnytpMpwXIo&callback=initMap&libraries=&v=weekly"
        async
></script>

</body>
</html>
