<%-- 
    Document   : cadastro
    Created on : 03/11/2019, 15:14:22
    Author     : henrique
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Cadastro</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="css/style.css">

        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
        <script src="js/verificacao.js"></script>

    </head>

    <body>
        <div class="row">

            <div class="col-md-3">

            </div>

            <div class="col-md-6" id="cadastro">

                <form action="Cadastro" method="post" name="frmCadastro" id="frmCadastro" class="formCadastro" onsubmit="return cadastrar()">

                    <h1>Cadastre-se</h1>

                    <div class="container">

                        <div class="row mt-5">
                            <div class="col-12">
                                <div class="form-group">
                                    <label for="nome" class="control-label">Nome</label>
                                    <input type="text" name="nome" id="nome" class="form-control" placeholder="Ex: Fulano de Tal" required="required">
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                <label for="cpf" class="control-label">CPF</label>
                                <input type="text" name="cpf" id="cpf" class="form-control" placeholder="Ex: 123.456.789-10" data-mask="999.999.999-99" required="required">
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="rg" class="control-label">RG</label>
                                    <input type="text" name="rg" id="rg" class="form-control" placeholder="Ex: 12.345.678-9" required="required">
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="email" class="control-label">Email</label>
                                    <input type="email" name="email" id="email" class="form-control" placeholder="Ex: fulanodetal@gmail.com" required="required">
                                </div>
                            </div>

                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="telefone" class="control-label">Telefone</label>
                                    <input type="tel" name="telefone" id="telefone" class="form-control phone-ddd-mask" placeholder="Ex: (11) 99999-9999" >
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label for="dataNasc" class="control-label">Data de nascimento</label>
                                    <input type="text" name="dataNasc" id="dataNasc"  class="form-control date-mask" placeholder="Ex: 01/01/2000" required="required">
                                </div>
                            </div>
                        </div>

                        <h2>Endereço</h2>

                        <div class="row">
                            <div class="col-md-8">
                                <div class="form-group">
                                    <label for="rua" class="control-label">Rua</label>
                                    <input type="text" name="rua" id="rua"  class="form-control" placeholder="Nome da rua" required="required" >
                                </div>
                            </div>

                            <div class="col-md-4">
                                <div class="form-group">
                                    <label for="numero" class="control-label">Numero</label>
                                    <input type="text" name="numero" id="numero"  class="form-control" placeholder="Ex.: 0000" required="required" >
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="bairro" class="control-label">Bairro</label>
                                    <input type="text" name="bairro" id="bairro"  class="form-control" placeholder="Nome do bairro" required="required" >
                                </div>
                            </div>

                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="cidade" class="control-label">Cidade</label>
                                    <input type="text" name="cidade" id="cidade"  class="form-control" placeholder="Ex: Votuporanga" required="required" >
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label for="cep" class="control-label">CEP</label>
                                    <input type="text" name="cep" id="cep"  class="form-control" placeholder="Ex: 15500-000" required="required" >
                                </div>
                            </div>

                            <div class="col-md-4 form-group">
                                <label for="estado" class="control-label">Estado</label>
                                <select id="estado" class="form-control" required="required">
                                    <option value="">Selecione</option>
                                    <option value="AC">Acre</option>
                                    <option value="AL">Alagoas</option>
                                    <option value="AP">Amapá</option>
                                    <option value="AM">Amazonas</option>
                                    <option value="BA">Bahia</option>
                                    <option value="CE">Ceará</option>
                                    <option value="DF">Distrito Federal</option>
                                    <option value="ES">Espirito Santo</option>
                                    <option value="GO">Goiás</option>
                                    <option value="MA">Maranhão</option>
                                    <option value="MS">Mato Grosso do Sul</option>
                                    <option value="MT">Mato Grosso</option>
                                    <option value="MG">Minas Gerais</option>
                                    <option value="PA">Pará</option>
                                    <option value="PB">Paraíba</option>
                                    <option value="PR">Paraná</option>
                                    <option value="PE">Pernambuco</option>
                                    <option value="PI">Piauí</option>
                                    <option value="RJ">Rio de Janeiro</option>
                                    <option value="RN">Rio Grande do Norte</option>
                                    <option value="RS">Rio Grande do Sul</option>
                                    <option value="RO">Rondônia</option>
                                    <option value="RR">Roraima</option>
                                    <option value="SC">Santa Catarina</option>
                                    <option value="SP">São Paulo</option>
                                    <option value="SE">Sergipe</option>
                                    <option value="TO">Tocantins</option>
                                </select>
                            </div>
                        </div>

                        <div class="container">
                            <div class="row">
                                <div class="col-md-12 divisoria">

                                </div>
                            </div>
                        </div>

                        <div class="row">

                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="senha" class="control-label">Senha</label>
                                    <input type="password" name="senha" id="senha"  class="form-control" placeholder="Senha" required="required">
                                    <small class="form-text">A senha deve conter entre 4 e 15 caracteres, de preferência com letras e números</small>
                                </div>
                            </div>

                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="confSenha" class="control-label">Confirme a senha</label>
                                    <input type="password" name="confSenha" id="confSenha"  class="form-control" placeholder="Confirmação da senha" required="required">
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-12">
                                <input type="submit" class="btn btn-danger btn-submit text-white" value="Cadastrar">
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="col-md-3">

            </div>

            <!-- Modal -->
            <div class="modal fade" id="modalCadastro" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div id="modal_titulo_div" class="modal-header text-danger">
                            <h5 class="modal-title" id="modal_titulo">Erro no cadastro</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body" id="modal_conteudo">

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" id="modal_btn" data-dismiss="modal">Voltar e corrigir</button>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    </body>
</html>
