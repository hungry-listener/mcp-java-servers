# 📊 Database Natural Language Query (NLQ) Server

This is an MCP-compliant server that allows users to query relational databases (like MySQL) using plain natural language.  
It translates user questions into SQL queries automatically by leveraging an LLM (Large Language Model) backend.

---

## ✨ Features

- Accepts plain text questions about a database.
- Translates natural language into SQL queries.
- Executes the SQL query against a connected database.
- Returns a clean, human-readable response along with the generated SQL.
- Schema-aware: only queries the relevant table(s) as specified.

---

## 📂 Project Structure

```plaintext
database-nlq-server/
│
├── src/main/java/
│   ├── com/yourdomain/database/   # Main code
│   │   ├── config/                 # Configuration classes (LangChain, DB configs)
│   │   ├── controller/             # REST API endpoints
│   │   ├── model/                  # Request/Response DTOs (MCPMessage, LLMResponse)
│   │   ├── service/                # Business logic (DatabaseService, LLM interaction)
│   │   └── DatabaseNlqServerApplication.java # Main Spring Boot class
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

    - Fetches the table schema.
    - Builds an LLM prompt to generate SQL based on the schema and query.
    - Executes the SQL against the database.
    - Returns the SQL and a human-readable answer.

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
---

## 🛠️ Setup

Prerequisites:

- Java 17+
- Maven 3.8+
- MySQL (or any relational DB)
- OpenAI or compatible LLM API key (demo key can be used for development)

## Configuration:

Edit the application.properties file:

```plaintext
# Database connection
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password

# LLM settings
llm.api.base-url=http://your-llm-url/v1
llm.api.key=your-api-key
llm.api.model-name=gpt-4o-mini
```

🔥 Note: If no API key is found in environment variables, the system falls back to the demo key.

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
