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
        <h4 class="exercise-title"><a href="#">ćwiczenie nr 1</a></h4>
        <h6><small>dodane przez: urzytkownik 2, dnia: 02.luty.2019</small></h6>
        <p class="description-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque mattis nisl a ipsum dictum porta. Praesent facilisis diam quis fringilla semper. Duis pharetra sapien nulla, a malesuada arcu luctus vel. Maecenas eu dolor urna.
            In sollicitudin tempor ligula, id blandit urna condimentum quis. Quisque venenatis arcu quam, vitae pretium felis placerat posuere. Etiam efficitur nisi ut dolor vehicula, ac posuere leo egestas.
            In quis ipsum eu felis iaculis imperdiet et sit amet massa. In consectetur dui tellus, eu vulputate lorem condimentum sed. Etiam pretium et massa eget pellentesque. Phasellus luctus mauris non arcu gravida, vel finibus
            libero ullamcorper. Ut nec sapien libero. Sed eu arcu augue. Ut ac vestibulum nunc, nec cursus metus.</p>
    </div>
    <div class="container">
        <div class="row bs-callout bs-callout-info">
            <div class="col col-md-1 col-sm-2">
                <a href="#" class="btn btn-primary btn-success">
                    <div><i class="fas fa-angle-up"></i></div>
                    <div><small class="block">12</small></div></a>
                <div class="divider"></div>
                <a href="#" class="btn btn-primary btn-danger">
                    <div><small class="block">24</small></div>
                    <div><i class="fas fa-angle-down"></i></div>
                </a>
            </div>
            <div class="col col-md-11 col-sm-10">
                <h6><small>dodane przez: dupa, dnia: 23 czerwca 2019</small></h6>
                <p>Praesent feugiat ipsum a leo tincidunt, ac venenatis ipsum euismod. Morbi at dolor pulvinar, vulputate arcu vel, venenatis nibh. Donec mollis imperdiet lobortis. Sed et tortor rutrum, vulputate est et, viverra libero.
                    Maecenas tristique purus ac quam placerat, eget dapibus leo bibendum. Nullam interdum lacus ipsum, nec ultrices mauris pellentesque sed. Aliquam interdum vitae magna id ultrices. Donec a tortor et eros iaculis lacinia.
                    Cras fringilla ultrices neque, nec egestas magna sollicitudin suscipit. Cras turpis augue, pharetra quis suscipit a, ornare mattis ante. In sit amet faucibus turpis. Vivamus a ipsum in libero luctus porttitor sit amet
                    id nulla. Nunc sapien tellus, vestibulum non ligula sit amet, ornare mollis magna. Curabitur in sapien lacus.</p>
                <p>
                    <a class="btn btn-light" href="#">
                        skomentuj
                    </a>
                    <button class="btn btn-light" type="button" data-toggle="collapse" data-target="#collapseComments" aria-expanded="false" aria-controls="collapseExample">
                        komentarze(1)
                    </button>
                </p>
                <div class="collapse" id="collapseComments">
                    <div class="bs-callout bs-callout-default">
                        <p>
                            Praesent feugiat ipsum a leo tincidunt, ac venenatis ipsum euismod. Morbi at dolor pulvinar, vulputate arcu vel, venenatis nibh. Donec mollis imperdiet lobortis. Sed et tortor rutrum, vulputate est et, viverra libero.
                            Maecenas tristique purus ac quam placerat, eget dapibus leo bibendum. Nullam interdum lacus ipsum, nec ultrices mauris pellentesque sed. Aliquam interdum vitae magna id ultrices. Donec a tortor et eros iaculis lacinia.
                            Cras fringilla ultrices neque, nec eges
                        </p>
                    </div>
                </div>
            </div>
        </div>
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

