const validarCampos = () => {
    const nome = document.getElementById('nome')
    const cpf = document.getElementById('cpf')
    const rg = document.getElementById('rg')
    const email = document.getElementById('email')
    const telefone = document.getElementById('telefone')
    const dataNasc = document.getElementById('dataNasc')
    const rua = document.getElementById('rua')
    const numero = document.getElementById('numero')
    const bairro = document.getElementById('bairro')
    const cidade = document.getElementById('cidade')
    const cep = document.getElementById('cep')
    const estado = document.getElementById('estado')
    const senha = document.getElementById('senha')
    const confSenha = document.getElementById('confSenha')

    let msg = ''

    if(nome.value.length <= 4){
        msg += 'O nome deve conter mais de 3 caracteres. <br/>'
    }
    if(cpf.value.length != 11){
        msg += 'O cpf deve conter 11 dígitos. <br/>'
    }
    if(rg.value.length < 8 && rg.value.length > 11){
        msg += 'O rg deve conter mais de 8 digitos e menos que 11. <br/>'
    }
    if(email.value.length <= 5){
        msg += 'O e-mail deve conter mais de 5 caracteres. <br/>'
    }
    if(telefone.value.length < 10 || telefone.value.length > 11){
        msg += 'O telefone deve ter no mínimo 10 dígitos e no máximo 11.<br/>'
    }
    if(dataNasc.value.length != 10){
        msg += 'Atenção com a data de nascimento. ' + dataNasc.value.length + '<br/>'
    }
    if(rua.value.length < 3){
        msg += 'A rua deve ter no mínimo 5 dígitos. <br/>'
    }
    if(numero.value.length < 3){
        msg += 'O número deve ter no mínimo 3 dígitos. <br/>'
    }
    if(bairro.value.length < 3){
        msg += 'O bairro deve ter no mínimo 5 caracteres. <br/>'
    }
    if(cidade.value.length < 3){
        msg += 'A cidade deve ter no mínimo 3 caracteres. <br/>'
    }
    if(cep.value.length != 8){
        msg += 'O CEP deve ter 8 dígitos. <br/>'
    }
    if(estado.value === 'Selecione'){
        msg += 'Selecione o estado. <br/>'
    }
    if(senha.value.length < 4 || senha.value.length > 15){
        msg += 'A senha deve ter entre 4 e 15 caracteres. <br/>'
    }
    if(senha.value.length != confSenha.value.length){
        msg += 'Erro de confirmação de senhas, verifique se colocou as mesmas. <br/>'
    }
    if(senha.value != confSenha.value){
        msg += 'Verifique novamente a senha<br>'
    }
    alert(msg)
    return msg
}

const mostrarModal = (msgErro) => {
        document.getElementById('modal_conteudo').innerHTML = 'Verifique se todos os campos foram preenchidos corretamente: <br/>' + msgErro	
        $('#modalCadastro').modal('show')
}

function cadastrar() {
    alert('Chamou cadastrar')
    let msgErro = validarCampos()
    if(msgErro === ''){
        alert('Chamou cadastrar()')
        alert(nome.value + cpf.value + rg.value + telefone.value + rua.value)
        return true
    }else{
        alert('errado: ' + msgErro)
        mostrarModal(msgErro)
        return false
    }
}

function carregou(){
    alert('TOMA')
}