<%-- 
    Document   : inscricoes
    Created on : 05/11/2019, 17:00:19
    Author     : henrique
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Inscrições</title>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2">

                </div>

                <div class="col-md-10">
                    <div class="alert alert-danger mt-3" role="alert">
                        <p>Você pode se inscrever em apenas um evento, então faça a sua escolha!</p>
                        <p>Lembrando que pode se inscrever nos eventos que acontecem dentro do evento principal,
                            desde que esteja disponível, para isso basta ver os sub-eventos e observar na sua descrição, pode participar ou
                            criar uma equipe, participando em grupos!
                        </p>
                    </div>

                    <div class="jumbotron jumbotron-fluid mt-5">
                        <div class="container">
                            <h1 id="nomeEvento" class="display-4">Nome do evento</h1>
                            <p id="descEvento" class="lead">Descrição do evento! Lorem ipsum dolor sit amet consectetur adipisicing elit. Numquam similique saepe, sint maxime, quibusdam fuga velit explicabo sed quos odit laudantium deserunt placeat tempore non exercitationem eaque deleniti. Magnam, consequatur?</p>
                            <p id="dataEvento">Data:</p>
                            <div class="dropdown">
                                <button id="botaoInsc" class="mx-auto btn btn-success">Inscrever-se</button>

                                <button id="botaoSubev" class="btn btn-info" type="button" id="dropdownMenuButton" data-toggle="collapse" data-target="#subeventos">
                                    Ver sub-eventos
                                </button>
                                <div class="collapse navbar-collapse" id="subeventos">
                                    <ul class="navbar-nav ml-auto">
                                        <div class="container">
                                            <div class="card my-4">
                                                <div class="card-body">
                                                    <h4 id="nomeSubeve" class="card-title">Título do cartão</h4>
                                                    <p id="descSubeve" class="card-text">Descrição do subevento: Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce sapien dui, feugiat et lorem quis, fringilla maximus nisl. Vivamus sed est pulvinar, aliquet purus ut, dictum elit.</p>
                                                    <p id="dataSubeve">Data: </p>
                                                    <a class="btn btn-outline-primary" href="">Inscrever-se</a>
                                                </div>
                                            </div>
                                        </div>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
