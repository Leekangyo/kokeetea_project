package com.guro.kokeetea_project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WarehouseInfoDTO {
    Long id;
    String name;
    List<CurrentStockInfoDTO> currentStockInfoDTOList;
}
