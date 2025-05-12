package com.ekagra.mcpstarter.model;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MCPResponse {
    private String sqlQuery;
    private String responseText;
    private List<Map<String, Object>> queryResult;
}
