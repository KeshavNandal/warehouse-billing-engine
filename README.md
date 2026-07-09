# Enterprise Warehouse Inventory & Transaction Engine

A high-throughput, standalone backend billing and warehouse inventory management engine built natively using **Core Java (JDBC)** and **MySQL**. This system purposefully avoids high-level framework abstractions (like Spring Boot or Hibernate) to demonstrate bare-metal mastery of relational database architecture, manual ACID transaction boundaries, and optimal network stream performance.

---



## 🏗️ System Architecture & Data Flow

The system strictly implements a **3-Tier Architecture Pattern** to enforce separation of concerns and ensure complete decoupling between data management and presentation states.

| Architectural Layer | Target Components | Core Responsibility | Data Flow Payload |
| :--- | :--- | :--- | :--- |
| **1. UI / Application Layer** | `Main.java` | Manages defensive CLI loop, sanitizes inputs, and handles buffer flushes. | Passes populated `Invoice` and `Product` models downward. |
| **2. Data Access Layer (DAOs)**| `ProductDAO`, `InvoiceDAO` | Isolates SQL drivers, translates Java states into structural relational queries. | Executes transactional parallel batches via JDBC network socket. |
| **3. Persistence Layer** | `MySQL Database` | Hard-disk data storage, executes atomic constraints, maintains schema tables. | Permanently locks or rolls back relational state updates. |

---
---

## 🛠️ Key Engineering Features

### 1. Manual ACID Transaction Boundaries & Atomic Rollbacks
To prevent database corruption and anomalies during multi-table insertions (writing to `invoices` while simultaneously updating `invoice_items`), automated database commits are explicitly disabled via `conn.setAutoCommit(false)`.
* **Programmatic Middleware Constraints:** Inside the execution loop, the engine fetches the live product row state. If warehouse stock levels fall below the cart's requested quantity, a manual `SQLException` is thrown.
* **Fault-Tolerant Rollbacks:** The matching `catch` block intercepts any failure mid-flight and triggers a strict `conn.rollback()`. This guarantees that if a line-item insertion fails, the temporary parent invoice header is completely purged from the disk—ensuring zero orphaned records.

### 2. Performance Optimization via JDBC Batch Processing
Instead of choking database throughput with sequential network trips inside loops, performance is optimized using **JDBC Batches** (`addBatch()` and `executeBatch()`). Line items and stock quantity deductions are packaged into parallel statement arrays and shipped to the MySQL server in a single network round-trip, drastically dropping network latency.

### 3. Strict Resource-Leak Mitigation
All underlying network streams (`Connection`, `PreparedStatement`, `ResultSet`) are declared outside the operational scope and systematically closed using null-safe verification blocks inside a structural `finally` clause. This ensures that even if a fatal network crash breaks execution, database channels are securely terminated.

---

## 📊 Database Schema Design

The relational schema is engineered to normalize inventory records across three decoupled, foreign-key-linked tables:

```sql
CREATE DATABASE warehouse_db;
USE warehouse_db;

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    sku VARCHAR(20) UNIQUE NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    stock_quantity INT NOT NULL
);

CREATE TABLE invoices (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE invoice_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    invoice_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price_per_unit DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (invoice_id) REFERENCES invoices(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id)
);