package br.com.rafaellbarros.jakartaee.ejb.model.builder.create;

import br.com.rafaellbarros.jakartaee.ejb.helper.CredentialHelper;
import br.com.rafaellbarros.jakartaee.ejb.model.entity.Person;
import br.com.rafaellbarros.jakartaee.ejb.model.entity.User;

import javax.ejb.Stateless;

@Stateless
public class UserCreateBuilder {

    public User build(final Person person) {
        return User.builder()
                .id(person.getId())
                .person(person)
                .username(person.getName())
                .password(getTemporaryCredential().generate(20))
                .email("-")
                .build();
    }


    private CredentialHelper getTemporaryCredential() {
        // User will not have direct access upon registration.
        // An update-password required action will be set for new users.
        return new CredentialHelper.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .usePunctuation(true)
                .build();
    }
}
