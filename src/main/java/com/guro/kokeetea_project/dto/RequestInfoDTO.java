package com.guro.kokeetea_project.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RequestInfoDTO {
    private Long id;
    private String ingredientName;
    private Long amount;
    private String storeName;
    private String warehouseName;
    private LocalDateTime date;
}
