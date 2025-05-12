# 📊 Database Natural Language Query (NLQ) Server

This is an MCP-compliant server that allows users to query relational databases (like MySQL) using plain natural language.  
It translates user questions into SQL queries automatically by leveraging an LLM (Large Language Model) backend.

🔥 This project is an MCP-compatible server built from scratch in Java, without relying on the official MCP Java SDK.
🔥 It follows MCP principles: structured messaging, model interaction, and protocol alignment, while using LangChain4j for LLM orchestration.

---

## ✨ Features

- **Natural Language to SQL**: Accepts plain text questions from users about a connected database.
- **SQL Query Generation**: Automatically translates natural language queries into precise SQL statements.
- **Schema-Aware Intelligence**: Only queries tables specified in the request, keeping the LLM focused and accurate.
- **Secure Execution**: Runs the generated SQL safely against the database without exposing sensitive data to the LLM.
- **Clear Human-Friendly Responses**: Returns both the SQL query and a simple explanation of the result to the user.
- **Data Privacy**: No actual database rows or sensitive data are ever sent to the LLM — only schema information is shared.
- **Flexible Integration**: Easily adaptable to different databases and schemas with minimal configuration.

---

## 📂 Project Structure

```plaintext
database-nlq-server/ 
│
├── src/main/java/
│   ├── com/ekagra/mcpstarter/   # Main code
│   │   ├── config/                 # Configuration classes (LangChain, DB configs)
│   │   ├── controller/             # REST API endpoints
|   |   ├── exception/
│   │   ├── model/                  # Request/Response DTOs (MCPMessage, LLMResponse)
│   │   ├── service/                # Business logic (DatabaseService, LLM interaction)
│   │   └── McpServerStarterJavaApplication.java # Main Spring Boot class
│
├── src/main/resources/
│   ├── application.properties      # DB, LLM, and other configuration
│
├── pom.xml                          # Maven build configuration
└── README.md                        # This file
```
---

## 🚀 How It Works

1. User sends a POST request with:
```json
{
  "tableName":"HDFCBANK",
  "query":"What was the hdfc bank closing price on 22 july 2024?"
}
```
2. Server:

    - Fetches Table Schema: It dynamically pulls only the column names and data types of the specified table from the connected database.
          📢 Note: No actual table data (i.e., no row values) are fetched or exposed.
    - Builds LLM Prompt: The server constructs a carefully formatted prompt for the LLM.
      The prompt includes:
      - The table name
      - The column names and types
      - Clear instructions to only generate the SQL query
      - Strict guidelines for formatting the response as a small JSON object ({sqlQuery, responseText})
    - Sends to LLM: Only the table structure (not the data) and the user’s question are sent to the LLM model.
      ✅ This protects sensitive business or customer data.
    - Generates and Parses SQL: The LLM returns a JSON object containing thehe generated SQL query and a human-readable explanation of the query.
    - Executes SQL Securely: The server runs the SQL against the database using JDBC, fetches the result (if needed), and prepares the final response.
    - Returns the Response: The final API response sent back to the user contains
      - The generated SQL query
      - The human-readable explanation
      - The SQL execution result, depending on configuration

3. Example Response:
```json
    {
    "sqlQuery": "SELECT close FROM HDFCBANK WHERE date = '2024-07-22';",
    "responseText": "The closing price of HDFC Bank on 22 July 2024 was retrieved from the database.",
    "queryResult": [
        {
            "close": 1642.55
        }
    ]
  }
```

🔥 Note: You can customize the request format, response structure, or the LLM prompt as per your specific use case or integration requirements.
---

## 🛠️ Setup

Prerequisites:

- Java 17+ (Java version should match the version specified in `pom.xml`; Java 21 is tested)
- Maven 3.8+
- MySQL (or any other relational database)
- OpenAI API key or compatible LLM provider (a demo key can be used for development)

## Configuration:

Edit the application.properties file:

```plaintext
# ================================
# Database connection settings
# ================================
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password

# ================================
# LLM (Language Model) settings
# ================================

# Base URL for the LLM API
# - When using the demo server provided by LangChain4j, keep it as: http://langchain4j.dev/demo/openai/v1
# - In production, replace this with your actual LLM provider's base URL (e.g., https://api.openai.com/v1 for OpenAI).
langchain4j.base-url=http://langchain4j.dev/demo/openai/v1

# Model name to be used with the LLM provider
# - Example: 'gpt-4o-mini' for the demo, or 'gpt-4', 'gpt-3.5-turbo' for OpenAI
langchain4j.model-name=gpt-4o-mini

# NOTE:
# - When switching from demo to production, you must update:
#     - The base URL (e.g., to OpenAI's or another LLM provider's endpoint)
#     - The API key (handled in the environment variables, not here for security reasons)

```

🔥 Note: If no API key is found in environment variables, the system falls back to the demo key.

```java
// In the Java code the api key is checked like this:
String apiKey = System.getenv("OPENAI_API_KEY");
// If the environment variable is not set, code will fallback to a default (e.g., "demo")
// This is handled in your LangChainConfig class.
```

To set the environment variable in your system:

On Linux / Mac (Terminal)
```bash
export OPENAI_API_KEY=your_actual_openai_api_key
```

On Windows (Command Prompt)
```cmd
set OPENAI_API_KEY=your_actual_openai_api_key
```

---

## 🧩 MCP Compliance

- Input follows the Model Context Protocol (MCP) message format.
- Output adheres to a structured MCP-style response, ready for integration with MCP agents.

---

## 📚 Future Enhancements

- Automatic table detection using embeddings.
- Support for JOIN queries across multiple tables.
- Fine-tuned error handling and validation.
- Support for Postgres and other databases.

## 📄 License

This project is open-source and available under the MIT License.
