package controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import model.Usuarios;

public class UsuariosControllerH extends Conexao{

    public UsuariosControllerH() throws Exception {
    }
    
    public boolean create(Usuarios user) throws Exception{
        getConexao().setAutoCommit(false);
        String sql = "INSERT INTO USUARIOS (nome, rg, cpf, email, senha, telefone, datanascimento, endereco, idstatus) "
                + "VALUES (?,?,?,?,?,?,?,?, 1)";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setString(1, user.getNome());
        ps.setString(2, user.getRg());
        ps.setString(3, user.getCpf());
        ps.setString(4, user.getEmail());
        ps.setString(5, user.getSenha());
        ps.setString(6, user.getTelefone());
        ps.setDate(7, (Date) user.getDatanascimento());
        ps.setString(8, user.getEndereco());
        
        if(ps.executeUpdate() == 0){
            getConexao().rollback();
            return false;
        }
        
        getConexao().commit();
        getConexao().setAutoCommit(true);
        return true;
    }
}
