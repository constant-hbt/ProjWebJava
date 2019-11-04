/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author henrique
 */
@Entity
@Table(name = "usuarios", catalog = "proj_web", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u")})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idusuario")
    private Integer idusuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nome")
    private String nome;
    @Size(max = 12)
    @Column(name = "rg")
    private String rg;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 14)
    @Column(name = "cpf")
    private String cpf;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "senha")
    private String senha;
    @Size(max = 20)
    @Column(name = "telefone")
    private String telefone;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datanascimento")
    @Temporal(TemporalType.DATE)
    private Date datanascimento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "endereco")
    private String endereco;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarios")
    private Collection<Organizadores> organizadoresCollection;
    @JoinColumn(name = "idstatus", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarios")
    private Collection<Submissao> submissaoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarios")
    private Collection<Editais> editaisCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarios")
    private Collection<Apresentacao> apresentacaoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarios")
    private Collection<Participantes> participantesCollection;

    public Usuarios() {
    }

    public Usuarios(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public Usuarios(Integer idusuario, String nome, String cpf, String rg, String email, String senha, String telefone, Date datanascimento, String endereco) {
        this.idusuario = idusuario;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.datanascimento = datanascimento;
        this.endereco = endereco;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getDatanascimento() {
        return datanascimento;
    }

    public void setDatanascimento(Date datanascimento) {
        this.datanascimento = datanascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Collection<Organizadores> getOrganizadoresCollection() {
        return organizadoresCollection;
    }

    public void setOrganizadoresCollection(Collection<Organizadores> organizadoresCollection) {
        this.organizadoresCollection = organizadoresCollection;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Collection<Submissao> getSubmissaoCollection() {
        return submissaoCollection;
    }

    public void setSubmissaoCollection(Collection<Submissao> submissaoCollection) {
        this.submissaoCollection = submissaoCollection;
    }

    public Collection<Editais> getEditaisCollection() {
        return editaisCollection;
    }

    public void setEditaisCollection(Collection<Editais> editaisCollection) {
        this.editaisCollection = editaisCollection;
    }

    public Collection<Apresentacao> getApresentacaoCollection() {
        return apresentacaoCollection;
    }

    public void setApresentacaoCollection(Collection<Apresentacao> apresentacaoCollection) {
        this.apresentacaoCollection = apresentacaoCollection;
    }

    public Collection<Participantes> getParticipantesCollection() {
        return participantesCollection;
    }

    public void setParticipantesCollection(Collection<Participantes> participantesCollection) {
        this.participantesCollection = participantesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idusuario != null ? idusuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.idusuario == null && other.idusuario != null) || (this.idusuario != null && !this.idusuario.equals(other.idusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Usuarios[ idusuario=" + idusuario + " ]";
    }
    
}
