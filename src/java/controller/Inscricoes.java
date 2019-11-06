package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Eventos;
import model.Salas;
import model.Status;
import model.Subeventos;


public class Inscricoes extends Conexao{
    public Inscricoes() throws Exception {}
    
    public List<Eventos> listarEventos() throws Exception{
        List<Eventos> eventos = new ArrayList<Eventos>();
        String sql = "SELECT * FROM EVENTOS WHERE IDSTATUS = 1";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Eventos evento = new Eventos();
            evento.setIdevento(rs.getInt("idevento"));
            evento.setNome(rs.getString("nome"));
            evento.setDescricao(rs.getString("descricao"));
            evento.setLocal(rs.getString("local"));
            evento.setDatainicio(rs.getDate("dataInicio"));
            evento.setDatafim(rs.getDate("dataFim"));
            evento.setDatainicioinsc(rs.getDate("datainicioinsc"));
            evento.setDatafiminsc(rs.getDate("dataFimInsc"));
            
            Status status = new Status();
            status.setIdstatus(rs.getInt("idStatus"));
            String sqlStatus = "Select descricao FROM STATUS WHERE idstatus = ?";
            PreparedStatement psSattus = getConexao().prepareStatement(sqlStatus);
            psSattus.setInt(1, status.getIdstatus());
            ResultSet rsStatus = psSattus.executeQuery();
            if(rsStatus.next()){
                status.setDescricao(rsStatus.getString("descricao"));
            }
            evento.setStatus(status);
            
            eventos.add(evento);
        }
        return eventos;
    }
    
    public List<Subeventos> listarSubeventos(int idEvento) throws Exception{
        List<Subeventos> subeventos = new ArrayList<>();
        String sql = "SELECT * FROM SUBEVENTOS WHERE IDEVENTO = ? AND IDSTATUS = 1";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, idEvento);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Subeventos subevento = new Subeventos();
            subevento.setIdsubevento(rs.getInt("idSubevento"));
            subevento.setNome(rs.getString("nome"));
            subevento.setDescricao(rs.getString("descricao"));
            subevento.setDatainicio(rs.getDate("datainicio"));
            subevento.setDatafim(rs.getDate("datafim"));
            subevento.setDatainicioinsc(rs.getDate("datainicioinsc"));
            subevento.setDatafiminsc(rs.getDate("datafiminsc"));
            subevento.setQtdemin(rs.getInt("qtdemin"));
            subevento.setQtdemax(rs.getInt("qtdemax"));
            subevento.setQtdemaxequipes(rs.getInt("qtdemaxequipes"));
            
            Status status = new Status();
            status.setIdstatus(rs.getInt("idStatus"));
            String sqlStatus = "Select descricao FROM STATUS WHERE idstatus = ?";
            PreparedStatement psSattus = getConexao().prepareStatement(sqlStatus);
            psSattus.setInt(1, status.getIdstatus());
            ResultSet rsStatus = psSattus.executeQuery();
            if(rsStatus.next()){
                status.setDescricao(rsStatus.getString("descricao"));
            }
            subevento.setStatus(status);
            
            Salas sala = new Salas();
            
            sala.setIdsala(rs.getInt("idsala"));
            String sqlSala = "SELECT * FROM SALAS WHERE IDSTATUS = 1 AND IDSALA = ?";
            PreparedStatement psSala = getConexao().prepareStatement(sqlSala);
            psSala.setInt(1, sala.getIdsala());
            ResultSet rsSala = psSala.executeQuery();
            if(rsSala.next()){
                sala.setDescricao(rsSala.getString("descricao"));
                sala.setCapacidadetotal(rsSala.getInt("capacidadetotal"));
                sala.setCapacidadeocupada(rsSala.getInt("capacidadeocupada"));
                
                Status statusSala = new Status();
                statusSala.setIdstatus(rsSala.getInt("idStatus"));
                String sqlStatusSala = "Select descricao FROM STATUS WHERE idstatus = 1";
                PreparedStatement psStatusSala = getConexao().prepareStatement(sqlStatusSala);
                ResultSet rsStatusSala = psStatusSala.executeQuery();
                if(rsStatusSala.next()){
                    statusSala.setDescricao(rsStatus.getString("descricao"));
                }
                sala.setStatus(statusSala);
                subevento.setSalas(sala);
            }
            subeventos.add(subevento);
        }
        return subeventos;
    }
    
    public boolean inscreverEvento(int idUsuario, int idEvento) throws Exception{
        getConexao().setAutoCommit(false);
        String sql = "INSERT INTO PARTICIPANTES (IDUSUARIO, IDEVENTO) VALUES (1, 1)";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, idUsuario);
        ps.setInt(2, idEvento);
        if(ps.executeUpdate() > 0){
            String sqlPart = "SELECT MAX(IDPARTICIPANTE) AS IDPARTICIPANTE FROM PARTICIPANTES";
            PreparedStatement psPart = getConexao().prepareStatement(sqlPart);
            ResultSet rsPart = psPart.executeQuery();
            if(rsPart.next()){
                int idParticipante = rsPart.getInt("IDPARTICIPANTE");
                String sqlInsc = "INSERT INTO INSCRICAO_PART_EVENTO(DATAHORA, IDPARTICIPANTE, IDEVENTO) VALUES (?,?,?)";
                PreparedStatement psInsc = getConexao().prepareStatement(sqlInsc);
                psInsc.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                psInsc.setInt(2, idParticipante);
                psInsc.setInt(3, idEvento);
                if(psInsc.executeUpdate() == 0){
                    getConexao().rollback();
                    return false;
                }
            }
        }
        getConexao().commit();
        getConexao().setAutoCommit(true);
        return true;
    }
}
