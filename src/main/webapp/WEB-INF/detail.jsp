<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create a Task</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="<c:url value='/' />">Inicio</a>
        <ul class="navbar-nav mr-auto">
          <li class="nav-item">
            <a class="nav-link" href="<c:url value='/home' />">Regresar</a>
          </li>
        </ul>
    </nav>
    <div class="container">
        <div class="row justify-content-center">
          <div class="col-sm-4">
            <div class="card mt-5">
              <div class="card-body">
                <h1>${song.title}</h1>
                <h4>(Started by ${user.name})</h4>
                <p><strong>Genere:</strong>${song.genere}</p>
                <p><strong>Lyrics:</strong>${song.lyrics}</p>
              </div>
              <a href="/songs/${song.id}/edit">contribuir</a>
            </div>
          </div>
        </div>
    </div>
</body>
</html>