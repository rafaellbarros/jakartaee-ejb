package br.com.rafaellbarros.jakartaee.ejb.model.request;

import br.com.rafaellbarros.jakartaee.ejb.model.dto.PersonDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Setter
@Getter
@EqualsAndHashCode
public class UserRequestUpdateDTO implements Serializable {
    private Long id;
    private PersonDTO person;
    private String username;
    private String password;
    private String email;
}
