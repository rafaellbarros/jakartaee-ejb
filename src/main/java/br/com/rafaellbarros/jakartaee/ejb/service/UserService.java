package br.com.rafaellbarros.jakartaee.ejb.service;

import br.com.rafaellbarros.jakartaee.ejb.model.dto.UserDTO;
import br.com.rafaellbarros.jakartaee.ejb.model.mapper.UserMapper;
import br.com.rafaellbarros.jakartaee.ejb.model.request.UserRequestDTO;
import br.com.rafaellbarros.jakartaee.ejb.repository.UserRepository;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class UserService {

    @EJB
    private UserRepository userRepository;

    public UserDTO getById(final Long id) {
        return UserMapper.INSTANCE.toDTO(userRepository.getUserById(id));
    }

    public UserDTO getByUsername(final String username) {
        return UserMapper.INSTANCE.toDTO(userRepository.getUserByUsername(username));
    }

    public UserDTO getByEmail(final String email) {
        return UserMapper.INSTANCE.toDTO(userRepository.getUserByEmail(email));
    }

    public int getUsersCount() {
        return userRepository.getUsersCount();
    }

    public List<UserModel> getUsers() {
        return userRepository.getUsers(0, 10, null);
    }

    public List<UserModel> searchForUserByUsernameOrEmail(final String search) {
        return userRepository.searchForUserByUsernameOrEmail(search, 0, 10, null);
    }

    public UserModel addUser(final UserRequestDTO userRequestDTO) {
        return userRepository.addUser(userRequestDTO.getUsername());
    }

}
