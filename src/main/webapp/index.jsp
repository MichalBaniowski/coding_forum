<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="pl">
<head>
    <title>coding forum</title>
    <meta name="description" content="forum programistyczne"/>
    <meta name="keywords" content="coding, forum, programistyczne"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initioal-scale=1.0">


    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css">

</head>
<body>
<jsp:include page="WEB-INF/fragments/navbar.jsp"/>

<div class="container conteiner-exercise">
    <div class="bs-callout bs-callout-primary exercise">
        <h4 class="exercise-title"><a href="exercise.jsp">ćwiczenie nr 1</a></h4>
        <h6><small>dodane przez: urzytkownik 2, dnia: 02.luty.2019</small></h6>
        <p class="description-text-short">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque mattis nisl a ipsum dictum porta. Praesent facilisis diam quis fringilla semper. Duis pharetra sapien nulla, a malesuada arcu luctus vel. Maecenas eu dolor urna.
            In sollicitudin tempor ligula, id blandit urna condimentum quis. Quisque venenatis arcu quam, vitae pretium felis placerat posuere. Etiam efficitur nisi ut dolor vehicula, ac posuere leo egestas.
            In quis ipsum eu felis iaculis imperdiet et sit amet massa. In consectetur dui tellus, eu vulputate lorem condimentum sed. Etiam pretium et massa eget pellentesque. Phasellus luctus mauris non arcu gravida, vel finibus
            libero ullamcorper. Ut nec sapien libero. Sed eu arcu augue. Ut ac vestibulum nunc, nec cursus metus.</p>
        <h6><small>ilość rozwiązań</small></h6>
    </div>

</div>

<jsp:include page="WEB-INF/fragments/footer.jsp"/>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous">
</script>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous">
</script>
</body>

</html>
