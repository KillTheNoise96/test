package com.api.usuario.model.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Data
public class PhonesDto {
    private Integer id;
    private String number;
    private String cityCode;
    private String countryCode;
}
