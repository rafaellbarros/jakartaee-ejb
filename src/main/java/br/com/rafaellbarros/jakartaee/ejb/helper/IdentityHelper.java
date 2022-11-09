package br.com.rafaellbarros.jakartaee.ejb.helper;

import br.com.rafaellbarros.jakartaee.ejb.config.UserFederationConfig;
import br.com.rafaellbarros.jakartaee.ejb.model.entity.User;
import br.com.rafaellbarros.jakartaee.ejb.validator.impl.PasswordValidator;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class IdentityHelper {

    public static boolean isValidUsername(EntityManager em, String username) {
        TypedQuery<User> query = em.createNamedQuery("getUserByUsername", User.class);
        query.setParameter("username", username);
        List<User> result = query.getResultList();
        return result.isEmpty();
    }

    public static boolean isValidPassword(String pw, UserFederationConfig configmap) {
        return new PasswordValidator(pw, configmap).isValid();
    }

}
