package com.ekagra.mcpstarter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LLMResponse {
    private String sqlQuery;
    private String responseText;
}
