package br.com.rafaellbarros.jakartaee.ejb.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Setter
@Getter
@EqualsAndHashCode
public class PersonDTO implements Serializable {

    private Long id;
    private String name;
    private String middle;
    private String family;
    private String issn;
    private Date creation;
    private String status;

}
