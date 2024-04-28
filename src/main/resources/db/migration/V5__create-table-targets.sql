CREATE TABLE targets(
	id TEXT PRIMARY KEY UNIQUE NOT NULL,
	user_id TEXT REFERENCES users(id) NOT NULL,
	name VARCHAR(100) NOT NULL,
	target_amount NUMERIC(10,2) NOT NULL,
	current_amount NUMERIC(10,2) NOT NULL,
	deadline DATE,
	category VARCHAR(100),
	progress VARCHAR(7) NOT NULL
);