package br.com.rafaellbarros.jakartaee.ejb.model.dto;

import br.com.rafaellbarros.jakartaee.ejb.model.entity.Person;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@EqualsAndHashCode
public class UserDTO implements Serializable {

    private Long id;
    private PersonDTO person;
    private String username;
    private String password;
    private String email;

}
