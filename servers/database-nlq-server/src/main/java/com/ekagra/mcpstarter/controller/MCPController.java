package com.ekagra.mcpstarter.controller;

import com.ekagra.mcpstarter.model.MCPMessage;
import com.ekagra.mcpstarter.model.MCPResponse;
import com.ekagra.mcpstarter.service.QueryService;

import dev.langchain4j.rag.query.Query;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mcp")
public class MCPController {

    private static final Logger logger = LoggerFactory.getLogger(MCPController.class);

    @Autowired
    private QueryService queryService;

    @PostMapping("/message")
    public ResponseEntity<MCPResponse> receiveMessage(@Valid @RequestBody MCPMessage message) {
        // Log standard fields
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("\n=== Received MCP Message ===\n");
        logMessage.append("Table name: ").append(message.getTableName()).append("\n");
        logMessage.append("Query: ").append(message.getQuery()).append("\n");

        // Now log all in one go
        logger.info(logMessage.toString());

        MCPResponse response =  queryService.processUserQuery(message);

        return ResponseEntity.ok(response);
    }
}
