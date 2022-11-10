package br.com.rafaellbarros.jakartaee.ejb.model.builder.update;

import br.com.rafaellbarros.jakartaee.ejb.model.dto.UserDTO;
import br.com.rafaellbarros.jakartaee.ejb.model.entity.Person;
import br.com.rafaellbarros.jakartaee.ejb.model.entity.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class UserUpdateBuilder {

    @EJB
    private PersonUpdateBuilder personUpdateBuilder;

    public User build(final UserDTO userDTO) {
        final Person person = personUpdateBuilder.build(userDTO.getPerson());
        return User.builder()
                .id(userDTO.getId())
                .person(person)
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .build();
    }
}
