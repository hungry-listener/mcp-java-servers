package com.ekagra.mcpstarter.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.model.openai.OpenAiChatModel;

@Configuration
public class LangChainConfig {

    private static final Logger logger = LoggerFactory.getLogger(LangChainConfig.class);

    @Value("${langchain4j.base-url}")
    private String baseUrl;

    @Value("${langchain4j.model-name}")
    private String modelName;

    @Bean
    public OpenAiChatModel openAiChatModel() {
        // First, check if API key exists in environment variables
        String apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            apiKey = "demo"; // fallback to "demo" if not found
            logger.warn("\u001B[31m" + 
                "[IMPORTANT] Demo API key is being used. " +
                "This key is intended ONLY for demonstration and development purposes. " +
                "For production use, please set the 'OPENAI_API_KEY' environment variable with your actual API key. " +
                "Failure to do so may result in limited functionality, rate limits, or security risks." +
                "\u001B[0m"
            );

        }

        return OpenAiChatModel.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .modelName(modelName)
                .build();
    }
}
