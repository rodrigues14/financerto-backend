CREATE TABLE finance_transactions(
	id TEXT PRIMARY KEY UNIQUE NOT NULL,
	user_id TEXT REFERENCES users(id) NOT NULL,
	amount NUMERIC(10,2) NOT NULL,
	description TEXT,
	date DATE NOT NULL,
	method TEXT
);