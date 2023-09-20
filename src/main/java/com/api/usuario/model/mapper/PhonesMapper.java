package com.api.usuario.model.mapper;

import com.api.usuario.model.dto.PhonesDto;
import com.api.usuario.model.entity.Phones;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface PhonesMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "number", source = "number")
    @Mapping(target = "cityCode", source = "cityCode")
    @Mapping(target = "countryCode", source = "countryCode")
    PhonesDto convertir(Phones phones);

    @InheritInverseConfiguration
    Phones convertir(PhonesDto phonesDto);
}
