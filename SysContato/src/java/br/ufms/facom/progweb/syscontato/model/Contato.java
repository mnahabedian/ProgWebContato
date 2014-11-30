/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufms.facom.progweb.syscontato.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joshua
 */
@Entity
@Table(name = "contato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contato.findAll", query = "SELECT c FROM Contato c"),
    @NamedQuery(name = "Contato.findByIdContato", query = "SELECT c FROM Contato c WHERE c.idContato = :idContato"),
    @NamedQuery(name = "Contato.findByNome", query = "SELECT c FROM Contato c WHERE c.nome = :nome"),
    @NamedQuery(name = "Contato.findByDataNasc", query = "SELECT c FROM Contato c WHERE c.dataNasc = :dataNasc"),
    @NamedQuery(name = "Contato.findByCel", query = "SELECT c FROM Contato c WHERE c.cel = :cel"),
    @NamedQuery(name = "Contato.findByEmail", query = "SELECT c FROM Contato c WHERE c.email = :email"),
    @NamedQuery(name = "Contato.findByFacebook", query = "SELECT c FROM Contato c WHERE c.facebook = :facebook"),
    @NamedQuery(name = "Contato.findByTwitter", query = "SELECT c FROM Contato c WHERE c.twitter = :twitter"),
    @NamedQuery(name = "Contato.findBySite", query = "SELECT c FROM Contato c WHERE c.site = :site"),
    @NamedQuery(name = "Contato.findByFoto", query = "SELECT c FROM Contato c WHERE c.foto = :foto")})
public class Contato implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_contato")
    private Integer idContato;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_nasc")
    @Temporal(TemporalType.DATE)
    private Date dataNasc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "cel")
    private String cel;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "email")
    private String email;
    @Size(max = 256)
    @Column(name = "facebook")
    private String facebook;
    @Size(max = 256)
    @Column(name = "twitter")
    private String twitter;
    @Size(max = 256)
    @Column(name = "site")
    private String site;
    @Size(max = 256)
    @Column(name = "foto")
    private String foto;
    @JoinColumn(name = "id_operadora", referencedColumnName = "id_operadora")
    @ManyToOne(optional = false)
    private Operadora operadora;
    @JoinColumn(name = "id_cidade", referencedColumnName = "id_cidade")
    @ManyToOne(optional = false)
    private Cidade cidade;

    public Contato() {
    }

    public Contato(Integer idContato) {
        this.idContato = idContato;
    }

    public Contato(Integer idContato, String nome, Date dataNasc, String cel, String email) {
        this.idContato = idContato;
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.cel = cel;
        this.email = email;
    }

    public Integer getIdContato() {
        return idContato;
    }

    public void setIdContato(Integer idContato) {
        this.idContato = idContato;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public String getStrDataNasc() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(this.dataNasc);
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.dataNasc = sdf.parse(dataNasc);
        } catch (ParseException ex) {
            Logger.getLogger(Contato.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Operadora getOperadora() {
        return operadora;
    }

    public Integer getIdOperadora() {
        return operadora.getIdOperadora();
    }

    public void setOperadora(Operadora operadora) {
        this.operadora = operadora;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public Integer getIdCidade() {
        return cidade.getIdCidade();
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
    
    public boolean validar() {
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContato != null ? idContato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contato)) {
            return false;
        }
        Contato other = (Contato) object;
        if ((this.idContato == null && other.idContato != null) || (this.idContato != null && !this.idContato.equals(other.idContato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.ufms.facom.progweb.syscontato.model.Contato[ idContato=" + idContato + " ]";
    }
    
}
