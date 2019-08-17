<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="pl">
<head>
    <title>coding forum</title>
    <meta name="description" content="forum programistyczne"/>
    <meta name="keywords" content="coding, forum, programistyczne"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initioal-scale=1.0">


    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css"
          integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css">

</head>
<body>
<jsp:include page="../fragments/navbar.jsp"/>

<div class="container conteiner-exercise">
    <div class="bs-callout bs-callout-primary exercise">
        <h4 class="exercise-title">${exercise.title}</h4>
        <h6>
            <small>dodane przez: ${exercise.username}, dnia: ${exercise.created}</small>
        </h6>
        <p class="description-text">${exercise.description}</p>
        <c:if test="${not empty sessionScope.user}">
            <a href="/user/add-solution?exerciseId=${exercise.id}" class="btn btn-light">
                dodaj rozwiązanie
            </a>
            <c:if test="${sessionScope.user.username == exercise.username}">
                <a href="/user/edit-exercise?exerciseId=${exercise.id}" class="btn btn-light">
                    edytuj zadanie
                </a>
                <a href="/user/delete-exercise?exerciseId=${exercise.id}" class="btn btn-light">
                    usuń zadanie
                </a>
            </c:if>
        </c:if>
    </div>
    <div class="container">
        <c:forEach items="${solutions}" var="solution">
            <div class="row bs-callout bs-callout-info">
                <div class="col col-md-1 col-sm-2">
                    <a href="/user/vote?voteType=VOTE_UP&solutionId=${solution.id}&exerciseId=${solution.exercise.id}" class="btn btn-primary btn-success">
                        <div><i class="fas fa-angle-up"></i></div>
                        <div>
                            <small class="block">${solution.getCountUpVotes()}</small>
                        </div>
                    </a>
                    <div class="divider"></div>
                    <a href="/user/vote?voteType=VOTE_DOWN&solutionId=${solution.id}&exerciseId=${solution.exercise.id}" class="btn btn-primary btn-danger">
                        <div>
                            <small class="block">${solution.getCountDownVotes()}</small>
                        </div>
                        <div><i class="fas fa-angle-down"></i></div>
                    </a>
                </div>
                <div class="col col-md-11 col-sm-10">
                    <h6>
                        <small>dodane przez: ${solution.author}, dnia: ${solution.created}</small>
                    </h6>
                    <p>${solution.description}</p>
                    <p>
                        <c:if test="${not empty sessionScope.user}">
                            <button class="btn btn-light" type="button" data-toggle="collapse"
                                    data-target="#collapseCommentForm${solution.id}" aria-expanded="false" aria-controls="collapseExample">
                                skomentuj
                            </button>
                            <c:if test="${sessionScope.user.username == solution.author}">
                                <a class="btn btn-light" href="/user/edit-solution?solutionId=${solution.id}">
                                    edytuj
                                </a>
                                <a href="/user/delete-solution?solutionId=${solution.id}" class="btn btn-light">
                                    usuń rozwiązanie
                                </a>
                            </c:if>
                        </c:if>
                        <button class="btn btn-light" type="button" data-toggle="collapse"
                                data-target="#collapseComments${solution.id}" aria-expanded="false" aria-controls="collapseExample">
                            komentarze(${solution.comments.size()})
                        </button>
                    </p>
                    <div class="collapse" id="collapseComments${solution.id}">
                        <c:forEach var="comment" items="${solution.comments}">
                            <div class="bs-callout bs-callout-default">
                                <p>
                                    ${comment.description}
                                </p>
                                <h6>
                                    <small>dodane przez: ${comment.author}, dnia: ${comment.created}</small>
                                </h6>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="collapse" id="collapseCommentForm${solution.id}">
                        <div class="container log-container">
                            <div class="col-sm-12 col-md-10 centered">
                                <form method="post" action="/user/add-comment">
                                    <div class="form-group">
                                        <input name="solutionId" hidden value="${solution.id}"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="descriptionContent">Dodaj komentarz</label>
                                        <textarea name="description" class="form-control" id="descriptionContent" placeholder="treść" rows="6"></textarea>
                                    </div>
                                    <button type="submit" class="btn btn-primary btn-block">dodaj</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>

    </div>

</div>

<jsp:include page="../fragments/footer.jsp"/>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous">
</script>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous">
</script>
</body>

</html>

