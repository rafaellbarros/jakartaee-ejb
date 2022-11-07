package br.com.rafaellbarros.jakartaee.ejb.model.mapper;

import br.com.rafaellbarros.jakartaee.ejb.core.repository.model.mapper.BaseMapper;
import br.com.rafaellbarros.jakartaee.ejb.model.dto.PersonDTO;
import br.com.rafaellbarros.jakartaee.ejb.model.dto.UserDTO;
import br.com.rafaellbarros.jakartaee.ejb.model.entity.Person;
import br.com.rafaellbarros.jakartaee.ejb.model.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User, UserDTO> {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Override
    UserDTO toDTO(User entity);

    @Override
    User toEntity(UserDTO dto);

    @Override
    List<UserDTO> toDTO(List<User> entities);

    @Override
    List<UserDTO> toDTO(Iterable<User> entities);

    @Override
    List<User> toEntity(List<UserDTO> dtos);

    @Override
    @InheritInverseConfiguration(name = "toDTO")
    void fromDTO(UserDTO dto, @MappingTarget  User entity);

    PersonDTO personToDTO(Person entity);

    Person personToEntity(PersonDTO dto);

}
