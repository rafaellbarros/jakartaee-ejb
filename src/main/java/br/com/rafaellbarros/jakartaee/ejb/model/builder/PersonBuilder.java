package br.com.rafaellbarros.jakartaee.ejb.model.builder;

import br.com.rafaellbarros.jakartaee.ejb.model.entity.Person;

import javax.ejb.Stateless;
import java.sql.Date;
import java.time.LocalDate;

@Stateless
public class PersonBuilder {

    public Person buildAdd(final String username) {
        return Person.builder()
                .name(username)
                .middle("-")
                .family("-")
                .issn("-")
                .status("1")
                .creation(Date.valueOf(LocalDate.now()))
                .build();
    }
}
