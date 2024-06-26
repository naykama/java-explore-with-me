DROP table IF EXISTS events;
CREATE TABLE IF NOT EXISTS events (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  app VARCHAR(255) NOT NULL,
  uri VARCHAR(255) NOT NULL,
  ip VARCHAR(40) NOT NULL,
  exist_date TIMESTAMP,
  CONSTRAINT pk_event PRIMARY KEY (id)
);