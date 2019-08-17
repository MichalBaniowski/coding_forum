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
    <div class="page-header">
        <h3>Twoje Komentarze</h3>
    </div>
    <div class="container">
        <c:forEach items="${comments}" var="comment">
            <div class="row bs-callout bs-callout-info">
                <div class="col col-md-11 col-sm-10">
                    <h6>
                        <small>dodane przez: ${comment.author}, dnia: ${comment.created}</small>
                    </h6>
                    <p>${comment.description}</p>
                    <p>
                        <a href="/user/delete-comment?commentId=${comment.id}" class="btn btn-light">
                            usuń komentarz
                        </a>
                        <a href="/user/exercise?id=${comment.solutionId}" class="btn btn-light">
                            idź do zadania
                        </a>
                    </p>
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
