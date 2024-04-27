CREATE TYPE category_revenue AS ENUM (
	'SALARIO', 'RENDA_EXTRA', 'INVESTIMENTOS', 'ALUGUEL', 'VENDAS', 'PRESENTES', 'OUTRA'
);

CREATE TABLE revenues(
	category category_revenue
) INHERITS(finance_transactions);