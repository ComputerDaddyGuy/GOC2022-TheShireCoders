DROP TABLE IF EXISTS TBL_PHISHING_EVENTS;

CREATE TABLE TBL_PHISHING_EVENTS (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  email_from VARCHAR(500),
  email_subject VARCHAR(500),
  email_host VARCHAR(500),
  suspicious_url_host VARCHAR(500)
);