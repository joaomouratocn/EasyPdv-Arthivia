CREATE INDEX idx_sale_date_time ON sale(date_and_time);
CREATE INDEX idx_sale_status ON sale(sale_status);
CREATE INDEX idx_product_barcode ON product(bar_code);
CREATE INDEX idx_customer_document ON customer(cpf_cnpj);
CREATE INDEX idx_spun_status ON spun_payment(status);
CREATE INDEX idx_spun_late ON spun_payment(late_date);

CREATE VIEW dashboard AS
SELECT 
    CURRENT_DATE as data,
    COALESCE(SUM(v.tot_pay), 0) as sales_day,
    COUNT(p.product_id) FILTER (WHERE p.amount <= p.min_amount) as critical_stock,
    COALESCE(SUM(s.current_value), 0) as spun_open
FROM sale v
FULL OUTER JOIN product p ON true
FULL OUTER JOIN spun_payment s ON s.status = 'OPEN'
WHERE v.date_and_time::date = CURRENT_DATE;
