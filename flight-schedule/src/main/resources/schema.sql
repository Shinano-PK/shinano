DROP TABLE IF EXISTS FLIGHT;
DROP TABLE IF EXISTS FLIGHT_SCHEDULE;
DROP TABLE IF EXISTS PLANE;

CREATE TABLE IF NOT EXISTS FLIGHT (
  ID_FLIGHT INT GENERATED BY DEFAULT AS IDENTITY,
  ID_PLANE VARCHAR(64) NOT NULL,
  ID_FLIGHT_SCHEDULE INT NOT NULL,
  DELAY INT NOT NULL,
  PRIMARY KEY (ID_FLIGHT)
);
CREATE TABLE IF NOT EXISTS PLANE (
  ID_PLANE VARCHAR(64) NOT NULL,
  FIRST_CLASS_CAPACITY INT NOT NULL,
  SECOND_CLASS_CAPACITY INT NOT NULL,
  CARRYING_CAPACITY INT NOT NULL,
  OWNER VARCHAR(256) NOT NULL,
  PRIMARY KEY (ID_PLANE)
);
CREATE TABLE IF NOT EXISTS FLIGHT_SCHEDULE (
  ID_FLIGHT_SCHEDULE INT GENERATED BY DEFAULT AS IDENTITY,
  START_WEEKDAY SMALLINT NOT NULL,
  END_WEEKDAY SMALLINT NOT NULL,
  START_TIME TIME NOT NULL,
  END_TIME TIME NOT NULL,
  SCHEDULE_START_DATE DATE NOT NULL,
  SCHEDULE_END_DATE DATE NOT NULL,
  DESTINATION VARCHAR(128) NOT NULL,
  "FROM" VARCHAR(128) NOT NULL,
  KIND VARCHAR(64) NOT NULL,
  PRIMARY KEY (ID_FLIGHT_SCHEDULE)
);
ALTER TABLE FLIGHT
ADD CONSTRAINT FK_PLANE_FLIGHT FOREIGN KEY (ID_PLANE) REFERENCES PLANE (ID_PLANE);
ALTER TABLE FLIGHT
ADD CONSTRAINT FK_FLIGHT_SCHEDULE_FLIGHT FOREIGN KEY (ID_FLIGHT_SCHEDULE) REFERENCES FLIGHT_SCHEDULE (ID_FLIGHT_SCHEDULE);