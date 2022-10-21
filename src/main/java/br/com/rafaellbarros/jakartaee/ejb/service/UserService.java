package br.com.rafaellbarros.jakartaee.ejb.service;

import br.com.rafaellbarros.jakartaee.ejb.model.entity.User;
import br.com.rafaellbarros.jakartaee.ejb.repository.UserRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class UserService {

    @EJB
    private UserRepository userRepository;


    public User getById(final Long id) {
        return userRepository.getUserById(id);
    }

}
