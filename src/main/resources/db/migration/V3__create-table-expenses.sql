CREATE TABLE expenses(
	local VARCHAR(100),
	category TEXT NOT NULL
) INHERITS(finance_transactions);