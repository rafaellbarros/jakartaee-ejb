package br.com.rafaellbarros.jakartaee.ejb.service;

import br.com.rafaellbarros.jakartaee.ejb.exception.BusinessException;
import br.com.rafaellbarros.jakartaee.ejb.model.builder.update.UserUpdateBuilder;
import br.com.rafaellbarros.jakartaee.ejb.model.dto.UserDTO;
import br.com.rafaellbarros.jakartaee.ejb.model.entity.User;
import br.com.rafaellbarros.jakartaee.ejb.model.mapper.UserMapper;
import br.com.rafaellbarros.jakartaee.ejb.model.request.UserRequestCreateDTO;
import br.com.rafaellbarros.jakartaee.ejb.model.request.UserRequestUpdateDTO;
import br.com.rafaellbarros.jakartaee.ejb.repository.UserRepository;
import lombok.SneakyThrows;
import org.keycloak.models.UserModel;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class UserService {

    @EJB
    private UserRepository userRepository;

    @EJB
    private UserUpdateBuilder userUpdateBuilder;

    @SneakyThrows
    public UserDTO getById(final Long id) {
        final User user = userRepository.getUserById(id);
        if (user == null) {
            throw new BusinessException("could not find user by id: " + id);
        }
        return UserMapper.INSTANCE.toDTO(user);
    }

    @SneakyThrows
    public UserDTO getByUsername(final String username) {
        final User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new BusinessException("username not found: " + username);
        }
        return UserMapper.INSTANCE.toDTO(user);
    }

    @SneakyThrows
    public UserDTO getByEmail(final String email) {
        final User user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new BusinessException("email not found: " + email);
        }
        return UserMapper.INSTANCE.toDTO(user);
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

    public UserModel create(final UserRequestCreateDTO userRequestCreateDTO) {
        return userRepository.addUser(userRequestCreateDTO.getUsername());
    }

    @SneakyThrows
    public UserDTO update(final Long id, final UserRequestUpdateDTO userRequestUpdateDTO) {
        UserDTO userDTO = this.getById(id);
        userDTO = new UserDTO(userRequestUpdateDTO);

        final User userUpdate = userUpdateBuilder.build(userDTO);
        Boolean isUpdated = userRepository.updateUser(userUpdate);

        if (!isUpdated) {
            throw new BusinessException("An error occurred while trying to update the user.");
        }

        return UserMapper.INSTANCE.toDTO(userUpdate);
    }

    @SneakyThrows
    public void delete(final String externalId) {
        Boolean isDeleted = userRepository.removeUser(externalId);
        if (!isDeleted) {
            throw new BusinessException("An error occurred while trying to delete the user.");
        }
    }
}
