CREATE TABLE IF NOT EXISTS mara (matnr INT NOT NULL PRIMARY KEY, description VARCHAR(50));

CREATE TABLE IF NOT EXISTS units (uom_sap VARCHAR(3) NOT NULL PRIMARY KEY,
uom_iso VARCHAR(3), description VARCHAR(30));

CREATE TABLE IF NOT EXISTS mbew (matnr INT NOT NULL, werks INT NOT NULL,
base_uom VARCHAR(3), pur_group VARCHAR(3), price_control VARCHAR(1),
fixed_price DECIMAL(20,2), weighted_price DECIMAL(20,2),
price_unit DECIMAL(20,2), PRIMARY KEY (matnr, werks));

CREATE TABLE IF NOT EXISTS mrpitems (matnr INT NOT NULL, werks INT NOT NULL,
avail_date DATE NOT NULL, per_segment VARCHAR(22) NOT NULL, requirement DECIMAL(20,3),
quality_stock DECIMAL(20,3), blocked_stock DECIMAL(20,3),
PRIMARY KEY (matnr, werks, avail_date, per_segment));