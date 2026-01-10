CREATE INDEX idx_sale_date_time ON sale(date_and_time);
CREATE INDEX idx_sale_status ON sale(sale_status);
CREATE INDEX idx_product_barcode ON product(bar_code);
CREATE INDEX idx_customer_document ON customer(cpf_cnpj);
CREATE INDEX idx_spun_status ON spun_payment(status);
CREATE INDEX idx_spun_late ON spun_payment(late_date);

CREATE VIEW dashboard AS
SELECT
    CURRENT_DATE AS data,

    --sale of day
    COALESCE((
        SELECT SUM(v.tot_pay)
        FROM sale v
        WHERE CAST(v.date_and_time as DATE) = CURRENT_DATE
    ), 0) AS sales_day,

    --critical stock
    (
        SELECT COUNT(*)
        FROM product p
        WHERE p.amount <= p.min_amount
    ) AS critical_stock,

    --open payment
    COALESCE((
        SELECT SUM(s.current_value)
        FROM spun_payment s
        WHERE s.status = 'OPEN'
    ), 0) AS spun_open;

