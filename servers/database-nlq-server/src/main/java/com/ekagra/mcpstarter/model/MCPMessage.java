package com.ekagra.mcpstarter.model;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MCPMessage {

    @NotBlank(message = "Table name cannot be blank.")
    private String tableName;

    @NotBlank(message = "Queru cannot be blank")
    private String query;

}
