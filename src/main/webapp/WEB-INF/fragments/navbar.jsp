<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="index.jsp">Coding Forum</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <c:choose>
                <c:when test="${empty sessionScope.user}">
                    <li class="nav-item">
                        <a class="nav-link" href="/login">Zaloguj</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/register">Zarejestruj</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item">
                        <a class="nav-link" href="/logout">Wyloguj</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Menu
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="#">Dodaj</a>
                            <a class="dropdown-item" href="#">Twoje Grupy</a>
                            <a class="dropdown-item" href="#">Twoje Rozwiązania</a>
                            <a class="dropdown-item" href="#">Twoje Pytania</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="/edit-user">Edytuj dane</a>
                        </div>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
        <div class="d-flex  nav-item">
            <div class="text-light mr-3"><i class="fas fa-user icon-user"></i></div>
            <c:choose>
                <c:when test="${empty sessionScope.user}">
                    <h5 class="text-light mr-3">Gość</h5>
                </c:when>
                <c:otherwise>
                    <h5 class="text-light mr-3">${sessionScope.user.username}</h5>
                </c:otherwise>
            </c:choose>
        </div>
        <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="search" placeholder="Wyszukaj zadania" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Szukaj</button>
        </form>
    </div>
</nav>
