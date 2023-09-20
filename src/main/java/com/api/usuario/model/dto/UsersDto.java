package com.api.usuario.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsersDto {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private List<PhonesDto> phones;
    private Date created;
    private Date modified;
    private Date lastLogin;
    private String token;
    private Boolean active;
}
