/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author henrique
 */
@Entity
@Table(name = "patrocinios", catalog = "proj_web", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Patrocinios.findAll", query = "SELECT p FROM Patrocinios p")})
public class Patrocinios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpatrocinio")
    private Integer idpatrocinio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "documento")
    private String documento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "descricao")
    private String descricao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "segmento")
    private String segmento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "endereco")
    private String endereco;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "site")
    private String site;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private BigDecimal valor;
    @JoinColumn(name = "idevento", referencedColumnName = "idevento")
    @ManyToOne(optional = false)
    private Eventos eventos;
    @JoinColumn(name = "idstatus", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patrocinios")
    private Collection<Patrocinioimagens> patrocinioimagensCollection;

    public Patrocinios() {
    }

    public Patrocinios(Integer idpatrocinio) {
        this.idpatrocinio = idpatrocinio;
    }

    public Patrocinios(Integer idpatrocinio, String nome, String documento, String descricao, String segmento, String endereco, String site, BigDecimal valor) {
        this.idpatrocinio = idpatrocinio;
        this.nome = nome;
        this.documento = documento;
        this.descricao = descricao;
        this.segmento = segmento;
        this.endereco = endereco;
        this.site = site;
        this.valor = valor;
    }

    public Integer getIdpatrocinio() {
        return idpatrocinio;
    }

    public void setIdpatrocinio(Integer idpatrocinio) {
        this.idpatrocinio = idpatrocinio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Eventos getEventos() {
        return eventos;
    }

    public void setEventos(Eventos eventos) {
        this.eventos = eventos;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Collection<Patrocinioimagens> getPatrocinioimagensCollection() {
        return patrocinioimagensCollection;
    }

    public void setPatrocinioimagensCollection(Collection<Patrocinioimagens> patrocinioimagensCollection) {
        this.patrocinioimagensCollection = patrocinioimagensCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpatrocinio != null ? idpatrocinio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Patrocinios)) {
            return false;
        }
        Patrocinios other = (Patrocinios) object;
        if ((this.idpatrocinio == null && other.idpatrocinio != null) || (this.idpatrocinio != null && !this.idpatrocinio.equals(other.idpatrocinio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Patrocinios[ idpatrocinio=" + idpatrocinio + " ]";
    }
    
}
