package br.com.rafaellbarros.jakartaee.ejb.service;

import br.com.rafaellbarros.jakartaee.ejb.model.dto.UserDTO;
import br.com.rafaellbarros.jakartaee.ejb.model.mapper.UserMapper;
import br.com.rafaellbarros.jakartaee.ejb.repository.UserRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class UserService {

    @EJB
    private UserRepository userRepository;

    public UserDTO getById(final Long id) {
        return UserMapper.INSTANCE.toDTO(userRepository.getUserById(id));
    }

}
