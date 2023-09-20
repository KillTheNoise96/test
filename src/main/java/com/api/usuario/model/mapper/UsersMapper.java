package com.api.usuario.model.mapper;


import com.api.usuario.model.dto.UsersDto;
import com.api.usuario.model.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(
        componentModel = "spring",
        uses = {PhonesMapper.class},
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UsersMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name", ignore = true)
    @Mapping(target = "email", source = "email", ignore = true)
    @Mapping(target = "password", source = "password", ignore = true)
    @Mapping(target = "phones", source = "phones", ignore = true)
    @Mapping(target = "created", source = "created")
    @Mapping(target = "modified", source = "modified")
    @Mapping(target = "active", source = "active")
    @Mapping(target = "token", source = "token")
    UsersDto userToUserDto(Users usuario);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "created", target = "created")
    @Mapping(source = "modified", target = "modified")
    @Mapping(source = "active", target = "active")
    @Mapping(source = "phones", target = "phones")
    Users userDtoToUser(UsersDto usuario);
}
