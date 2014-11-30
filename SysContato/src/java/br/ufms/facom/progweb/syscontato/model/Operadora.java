/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufms.facom.progweb.syscontato.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author joshua
 */
@Entity
@Table(name = "operadora")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Operadora.findAll", query = "SELECT o FROM Operadora o"),
    @NamedQuery(name = "Operadora.findByIdOperadora", query = "SELECT o FROM Operadora o WHERE o.idOperadora = :idOperadora"),
    @NamedQuery(name = "Operadora.findByNome", query = "SELECT o FROM Operadora o WHERE o.nome = :nome")})
public class Operadora implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_operadora")
    private Integer idOperadora;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "nome")
    private String nome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "operadora")
    private List<Contato> contatoList;

    public Operadora() {
    }

    public Operadora(Integer idOperadora) {
        this.idOperadora = idOperadora;
    }

    public Operadora(Integer idOperadora, String nome) {
        this.idOperadora = idOperadora;
        this.nome = nome;
    }

    public Integer getIdOperadora() {
        return idOperadora;
    }

    public void setIdOperadora(Integer idOperadora) {
        this.idOperadora = idOperadora;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlTransient
    public List<Contato> getContatoList() {
        return contatoList;
    }

    public void setContatoList(List<Contato> contatoList) {
        this.contatoList = contatoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOperadora != null ? idOperadora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Operadora)) {
            return false;
        }
        Operadora other = (Operadora) object;
        if ((this.idOperadora == null && other.idOperadora != null) || (this.idOperadora != null && !this.idOperadora.equals(other.idOperadora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.ufms.facom.progweb.syscontato.model.Operadora[ idOperadora=" + idOperadora + " ]";
    }
    
}
