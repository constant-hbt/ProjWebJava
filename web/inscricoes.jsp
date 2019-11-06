<%-- 
    Document   : inscricoes
    Created on : 05/11/2019, 17:00:19
    Author     : henrique
--%>

<%@page import="model.Subeventos"%>
<%@page import="model.Eventos"%>
<%@page import="java.util.List"%>
<%@page import="controller.Inscricoes"%>
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
                    <div class="container"> <!--Alteração no Jumbotran -->
                        <% 
                            try{
                                Inscricoes DAO = new Inscricoes();
                                List<Eventos> eventos = DAO.listarEventos();
                                if(eventos.size() == 0){
                                    out.println("<h2>Não existem eventos disponíveis!</h2>");
                                }else{
                                    for(Eventos evento: eventos){
                        %>
                        
                        <div class="jumbotron jumbotron-fluid mt-5">
                            <h1 id="nomeEvento" class="display-4"><%= evento.getNome() %></h1>
                            <p>ID: <span id="idEvento<%= evento.getIdevento() %>"><%= evento.getIdevento() %></span></p>
                            <p id="descEvento" class="lead"><%= evento.getDescricao() %></p>
                            <p>
                                <span class="mx-3">Começa em: <%= evento.getDatainicio() %></span>
                                <span class="mx-3">Termina em: <%= evento.getDatafim() %></span>
                                <span class="mx-3">Inscrições começam em: <%= evento.getDatainicioinsc() %></span>
                                <span class="mx-3">Inscrições terminam em: <%= evento.getDatafiminsc() %></span>
                            </p>
                            <p><span>Local: <%= evento.getLocal() %></span></p>
                            <div class="dropdown">
                                <button id="botaoInscEvento<%= evento.getIdevento() %>" class="mx-auto btn btn-success">Inscrever-se</button>

                                <button id="botaoVerSubeve<%=evento.getIdevento() %>" class="btn btn-info" type="button" id="dropdownMenuButton" data-toggle="collapse" data-target="#subeventos<%= evento.getIdevento() %>">
                                    Ver sub-eventos
                                </button>
                                <div class="collapse navbar-collapse" id="subeventos<%=evento.getIdevento()%>">
                                    <ul class="navbar-nav ml-auto">
                                        <div class="container"> <!--Mexeu no li -->
                                            <% 
                                                List<Subeventos> subeventos = DAO.listarSubeventos(evento.getIdevento());
                                                if(subeventos.size() == 0){
                                                    out.println("<p>Não existem sub-eventos disponíveis para este evento!</p>");
                                                }else{
                                                    out.println("<p>Tem subeventos</p>");
                                                    for(Subeventos subevento: subeventos){
                                            %>
                                            <li>
                                                <div class="card my-4">
                                                    <div class="card-body">
                                                        <h4 class="card-title"> <%= subevento.getNome() %> </h4>
                                                        <p>ID: <span id="idSubevento<%= subevento.getIdsubevento() %>"><%= subevento.getIdsubevento() %></span></p>
                                                        <p class="card-text"><%= subevento.getDescricao()%></p>
                                                        <p class="card-text">
                                                            <span class="mx-3">Começa em: <%= subevento.getDatainicio() %> </span>
                                                            <span class="mx-3">Termina em: <%= subevento.getDatafim() %></span>
                                                            <span class="mx-3">Inscrições começam em: <%= subevento.getDatainicioinsc() %></span>
                                                            <span class="mx-3">Inscrições terminam em: <%= subevento.getDatafiminsc() %></span>
                                                        </p>
                                                        <% 
                                                            if((subevento.getQtdemin() == 1 && subevento.getQtdemax() == 1) || subevento.getQtdemaxequipes() == 0){
                                                        %>
                                                        <p class="card-text">Participação individual</p>
                                                        <%
                                                            }else if((subevento.getQtdemin() == 1 && subevento.getQtdemax() > 1) && subevento.getQtdemaxequipes() > 0){
                                                        %>
                                                        <p class="card-text">
                                                            <span class="mx-3">Participação individual e em equipes</span>
                                                            <span class="mx-3">Quantidade mínima de participantes em cada equipe: <%= subevento.getQtdemin() %></span>
                                                            <span class="mx-3">Quantidade máxima de participantes em cada equipe: <%= subevento.getQtdemax() %></span>
                                                            <span class="mx-3">Quantidade máxima de equipes: <%= subevento.getQtdemaxequipes() %></span>
                                                        </p>
                                                        <%      
                                                            }else{
                                                        %>
                                                        <p class="card-text">
                                                            <span class="mx-3">Participação somente em equipes</span>
                                                            <span class="mx-3">Quantidade mínima de participantes em cada equipe: <%= subevento.getQtdemin() %></span>
                                                            <span class="mx-3">Quantidade máxima de participantes em cada equipe: <%= subevento.getQtdemax() %></span>
                                                            <span class="mx-3">Quantidade máxima de equipes: <%= subevento.getQtdemaxequipes() %></span>
                                                        </p>
                                                        <%
                                                            }
                                                        %>
                                                        <p class="card-text">Local: sala<%= subevento.getSalas().getIdsala()%>, descrição: <%= subevento.getSalas().getDescricao() %></p>
                                                        <a class="btn btn-outline-primary" href="">Inscrever-se</a>
                                                    </div>
                                                </div>
                                            </li>
                                            <%
                                                    } 
                                                }
                                                out.println("Acabou o for de subeventos aqui");
                                            %>
                                        </div>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <%          
                                    }
                                }
                            }catch(Exception e){
                                System.out.println("Erro: " + e.getMessage());
                            }
                        %>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
