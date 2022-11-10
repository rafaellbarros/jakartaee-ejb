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
            throw new BusinessException("Ocorreu um erro ao tentar atualizar o usuário.");
        }

        return UserMapper.INSTANCE.toDTO(userUpdate);
    }
}
