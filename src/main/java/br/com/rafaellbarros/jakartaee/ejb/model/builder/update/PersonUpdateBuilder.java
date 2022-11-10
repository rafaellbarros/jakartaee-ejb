package br.com.rafaellbarros.jakartaee.ejb.model.builder.update;

import br.com.rafaellbarros.jakartaee.ejb.model.dto.PersonDTO;
import br.com.rafaellbarros.jakartaee.ejb.model.entity.Person;

import javax.ejb.Stateless;
import java.sql.Date;
import java.time.LocalDate;

@Stateless
public class PersonUpdateBuilder {

    public Person build(final PersonDTO personDTO) {
        return Person.builder()
                .id(personDTO.getId())
                .name(personDTO.getName())
                .middle(personDTO.getMiddle())
                .family(personDTO.getFamily())
                .issn(personDTO.getIssn())
                .status(personDTO.getStatus())
                .creation(personDTO.getCreation())
                .build();
    }
}
