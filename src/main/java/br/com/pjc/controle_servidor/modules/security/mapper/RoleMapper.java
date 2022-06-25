package br.com.pjc.controle_servidor.modules.security.mapper;

import br.com.pjc.controle_servidor.modules.security.dto.RoleDto;
import br.com.pjc.controle_servidor.modules.security.model.Role;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {
    RoleMapper MAPPER = Mappers.getMapper(RoleMapper.class);

    Role roleDtoToRole(RoleDto roleDto);

    RoleDto roleToRoleDto(Role role);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRoleFromRoleDto(RoleDto roleDto, @MappingTarget Role role);
}
