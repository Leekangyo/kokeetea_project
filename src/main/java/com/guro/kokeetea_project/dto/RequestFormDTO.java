package com.guro.kokeetea_project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestFormDTO {
    private Long id;
    private Long ingredientId;
    private Long amount;
    private Long storeId;
}
