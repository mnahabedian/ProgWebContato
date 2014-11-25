/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.classes;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Hercules Sandim
 */
public class Contato {
    private Integer id;
    private String nome;
    private String email;
    private String cel;
    private String operadora_cel;
    private String cidade;
    private String estado;
    private Timestamp data_nasc;
    private String str_data_nasc;

    public Contato(Integer id, String nome, String email, String cel, String operadora_cel, String cidade, String estado, Timestamp data_nasc) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cel = cel;
        this.operadora_cel = operadora_cel;
        this.cidade = cidade;
        this.estado = estado;
        setData_nasc(data_nasc);
    }

    public Contato(Integer id, String nome, String email, String cel, String operadora_cel, String cidade, String estado, String str_data_nasc) throws ParseException {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cel = cel;
        this.operadora_cel = operadora_cel;
        this.cidade = cidade;
        this.estado = estado;
        setData_nasc(str_data_nasc);
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the cel
     */
    public String getCel() {
        return cel;
    }

    /**
     * @param cel the cel to set
     */
    public void setCel(String cel) {
        this.cel = cel;
    }

    /**
     * @return the operadora_cel
     */
    public String getOperadora_cel() {
        return operadora_cel;
    }

    /**
     * @param operadora_cel the operadora_cel to set
     */
    public void setOperadora_cel(String operadora_cel) {
        this.operadora_cel = operadora_cel;
    }

    /**
     * @return the cidade
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the data_nasc
     */
    public Timestamp getData_nasc() {
        return data_nasc;
    }

    public String getStr_data_nasc() {
        return str_data_nasc;
    }
    
    /**
     * @param data_nasc the data_nasc to set
     */
    public void setData_nasc(Timestamp data_nasc) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        this.str_data_nasc = sdf.format(data_nasc);
        this.data_nasc = data_nasc;
    }

    public void setData_nasc(String str_data_nasc) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = sdf.parse(str_data_nasc);
        sdf.applyPattern("yyyy-MM-dd hh:mm:ss");
        
        this.str_data_nasc = str_data_nasc;
        this.data_nasc = Timestamp.valueOf(sdf.format(d));
    }

}
