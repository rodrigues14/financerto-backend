CREATE TYPE category_expense AS ENUM (
'MORADIA', 'ALIMENTACAO', 'TRANSPORTE', 'SAUDE', 'EDUCACAO', 'LAZER', 'VESTUARIO', 'DESPESAS_DOMESTICAS', 'OUTRA'
);

CREATE TABLE expenses(
	local VARCHAR(100),
	category category_expense NOT NULL
) INHERITS(finance_transactions);