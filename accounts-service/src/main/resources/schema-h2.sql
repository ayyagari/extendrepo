-- Create a Users table
DROP TABLE  IF EXISTS Users;
CREATE TABLE users (id VARCHAR(36) PRIMARY KEY, full_name VARCHAR(255) NOT NULL);
