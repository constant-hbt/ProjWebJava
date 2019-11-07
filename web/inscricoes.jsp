<%-- 
    Document   : inscricoes
    Created on : 05/11/2019, 17:00:19
    Author     : henrique
--%>

<%@page import="java.util.ArrayList"%>
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

        <script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        
        <script>
            $('document').ready(function(){
                $(".insc-evento").click(function() {
                    var inscEvento = $(this).data("idevento");
                    $.ajax({
                        url: "InscEvento",
                        type: "POST",
                        data: {
                            idUsuario : 1,
                            idEvento : inscEvento
                        },
                        success: function(responseText){
                            $("#modal_titulo").text(responseText);
                            document.getElementById("modal_titulo_div").class = "modal-header text-success";
                            $('#modalInscricao').modal('show');
                        },
                        statusCode: {
                            400: function(responseText){
                                $("#modal_titulo").text(responseText);
                                document.getElementById("#modal_titulo_div").class = "modal-header text-danger";
                                $('#modalInscricao').modal('show');
                            }
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            $("#modal_titulo").text("Erro ao se inscrever!");
                            document.getElementById("#modal_titulo_div").class = "modal-header text-danger";
                            $('#modalInscricao').modal('show');
                        }
                    });
                });
                
                $(".insc-sub").click(function() {
                    var inscSubeve = $(this).data("idsubevento");
                    alert(inscSubeve);
                });  
            });
        </script>
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
                    <div class="container">
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
                            <div class="row">
                                <div class="col-md-3">Começa em: <%= evento.getDatainicio() %></div>
                                <div class="col-md-3">Termina em: <%= evento.getDatafim() %></div>
                                <div class="col-md-3">Inscrições começam em: <%= evento.getDatainicioinsc() %></div>
                                <div class="col-md-3">Inscrições terminam em: <%= evento.getDatafiminsc() %></div>
                            </div>
                            <p><span>Local: <%= evento.getLocal() %></span></p>
                            <div class="dropdown">
                                <button id="botaoInscEvento<%= evento.getIdevento() %>" data-idevento="<%= evento.getIdevento() %>" class="mx-auto btn btn-success insc-evento">Inscrever-se</button>

                                <button id="botaoVerSubeve<%=evento.getIdevento() %>" class="btn btn-info" type="button" id="dropdownMenuButton" data-toggle="collapse" data-target="#subeventos<%= evento.getIdevento() %>">
                                    Ver sub-eventos
                                </button>
                                <div class="collapse navbar-collapse" id="subeventos<%=evento.getIdevento()%>">
                                    <div class="container">
                                        <ul class="navbar-nav ml-auto">
                                            <% 
                                                List<Subeventos> subeventos = DAO.listarSubeventos(evento.getIdevento());
                                                if(subeventos.size() == 0){
                                                    out.println("<p>Não existem sub-eventos disponíveis para este evento!</p>");
                                                }else{
                                                    for(Subeventos subevento: subeventos){
                                            %>
                                            <li>
                                                <div class="card my-4">
                                                    <div class="card-body">
                                                        <h4 class="card-title"> <%= subevento.getNome() %> </h4>
                                                        <p>ID: <span id="idSubevento<%= subevento.getIdsubevento() %>"><%= subevento.getIdsubevento() %></span></p>
                                                        <p class="card-text"><%= subevento.getDescricao()%></p>
                                                        <div class="card-text row">
                                                            
                                                            <div class="col-md-3">Começa em: <%= subevento.getDatainicio() %> </div>
                                                            <div class="col-md-3">Termina em: <%= subevento.getDatafim() %></div>
                                                            <div class="col-md-3">Inscrições começam em: <%= subevento.getDatainicioinsc() %></div>
                                                            <div class="col-md-3">Inscrições terminam em: <%= subevento.getDatafiminsc() %></div>
                                                        </div>
                                                        <% 
                                                            if((subevento.getQtdemin() == 1 && subevento.getQtdemax() == 1) || subevento.getQtdemaxequipes() == 0){
                                                        %>
                                                        <p class="card-text">Participação individual</p>
                                                        <%
                                                            }else if((subevento.getQtdemin() == 1 && subevento.getQtdemax() > 1) && subevento.getQtdemaxequipes() > 0){
                                                        %>
                                                        <div class="card-text row">
                                                            <div class="col-md-3">Participação individual e em equipes</div>
                                                            <div class="col-md-3">Quantidade mínima de participantes em cada equipe: <%= subevento.getQtdemin() %></div>
                                                            <div class="col-md-3">Quantidade máxima de participantes em cada equipe: <%= subevento.getQtdemax() %></div>
                                                            <div class="col-md-3">Quantidade máxima de equipes: <%= subevento.getQtdemaxequipes() %></div>
                                                        </div>
                                                        <%      
                                                            }else{
                                                        %>
                                                        <div class="card-text row">
                                                            <div class="col-md-3">Participação somente em equipes</div>
                                                            <div class="col-md-3">Quantidade mínima de participantes em cada equipe: <%= subevento.getQtdemin() %></div>
                                                            <div class="col-md-3">Quantidade máxima de participantes em cada equipe: <%= subevento.getQtdemax() %></div>
                                                            <div class="col-md-3">Quantidade máxima de equipes: <%= subevento.getQtdemaxequipes() %></div>
                                                        </p>
                                                        <%
                                                            }
                                                        %>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <p class="card-text">Local: sala<%= subevento.getSalas().getIdsala()%>, descrição: <%= subevento.getSalas().getDescricao() %></p>
                                                            </div>
                                                            <div class="col-md-2">
                                                                <button class="btn btn-outline-primary insc-sub" id="inscSub<%=subevento.getIdsubevento() %>" data-idsubevento="<%=subevento.getIdsubevento() %>" href="">Inscrever-se</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </li>
                                            <%
                                                    } 
                                                }
                                            %>
                                        </ul>
                                    </div>
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
                    </div><!--Fechamento do container-->
                </div>
            </div>
            
            <!-- Modal -->
            <div class="modal fade" id="modalInscricao" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div id="modal_titulo_div" class="modal-header text-success">
                            <h5 class="modal-title" id="modal_titulo">Inscrição realizada com sucesso</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body" id="modal_conteudo">

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" id="modal_btn" data-dismiss="modal">Voltar</button>
                        </div>
                    </div>
                </div>
            </div> <!-- Fim do modal -->
        </div>
    </body>
</html>
