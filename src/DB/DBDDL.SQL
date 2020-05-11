drop database if exists javaproductos;
create database javaproductos;
use javaproductos;
-- CREATE TABLE IF NOT EXISTS "suppliers" ------------------------------------
CREATE TABLE `suppliers`( 
	`name` VarChar( 255 ) NOT NULL,
	`desc` VarChar( 255 ) NULL,
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	PRIMARY KEY ( `id` ),
	CONSTRAINT `unique_id` UNIQUE( `id` ) )
ENGINE = InnoDB;
-- -------------------------------------------------------------

-- CREATE TABLE IF NOT EXISTS "users" ----------------------------------------
CREATE TABLE `users`(
	`nickname` VarChar( 255 ) NOT NULL,
	`name` VarChar( 255 ) NOT NULL,
	`isAdmin` Int NULL DEFAULT 2,
	`isSeller` Int NULL DEFAULT 1,
	`pwd` VarChar( 255 ) NULL DEFAULT 'admin',
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	PRIMARY KEY ( `id` ),
	CONSTRAINT `unique_id` UNIQUE( `id` ) )
ENGINE = InnoDB;
-- -------------------------------------------------------------

INSERT INTO `users` ( `nickname`,`name`, `isAdmin`, `isSeller`)
VALUES ( 'carlos','Carlos Jose Hernandez Dueñas', 1, 1);
INSERT INTO `users` ( `nickname`,`name`, `isAdmin`, `isSeller`)
VALUES ( 'raul','Raul Humberto Peñate', 1, 1);

-- CREATE TABLE IF NOT EXISTS "products" -------------------------------------
CREATE TABLE `products`( 
	`id` Int( 255 ) NOT NULL,
	`name` VarChar( 255 ) NOT NULL,
	`descripcion` LongText NULL,
	`precio` Double NOT NULL,
	`prom` Int( 255 ) NULL DEFAULT 2,
	PRIMARY KEY ( `id` ),
	CONSTRAINT `unique_id` UNIQUE( `id` ) )
ENGINE = InnoDB;
-- -------------------------------------------------------------


-- CREATE TABLE IF NOT EXISTS "supplier_product" -----------------------------
CREATE TABLE `supplier_product`( 
	`id` Int( 255 ) NOT NULL,
	`users_id` Int( 255 ) NOT NULL,
	`products_id` Int( 255 ) NOT NULL,
	`created_at` Timestamp NULL,
	`suppliers_id` VarChar( 255 ) NOT NULL,
	`stock` BigInt( 255 ) NULL DEFAULT 1,
	PRIMARY KEY ( `id` ) )
ENGINE = InnoDB;
-- -------------------------------------------------------------

-- CREATE INDEX "index_products_id" ----------------------------
CREATE INDEX `index_products_id` ON `supplier_product`( `products_id` );
-- -------------------------------------------------------------

-- CREATE INDEX "index_suppliers_id" ---------------------------
CREATE INDEX `index_suppliers_id` ON `supplier_product`( `suppliers_id` );
-- -------------------------------------------------------------


-- CREATE TABLE IF NOT EXISTS "sales" ----------------------------------------
CREATE TABLE `sales`( 
	`id` Int( 255 ) NOT NULL,
	`total` Double NOT NULL,
	`users_id` Int( 255 ) NOT NULL,
	PRIMARY KEY ( `id` ),
	CONSTRAINT `unique_id` UNIQUE( `id` ) )
ENGINE = InnoDB;
-- -------------------------------------------------------------

-- CREATE INDEX "index_users_id" -------------------------------
CREATE INDEX `index_users_id` ON `sales`( `users_id` );
-- -------------------------------------------------------------

-- CREATE TABLE IF NOT EXISTS "sales_products" -------------------------------
CREATE TABLE `sales_products`( 
	`id` Int( 255 ) NOT NULL,
	`cant` Int( 255 ) NOT NULL,
	`sales_id` Int( 255 ) NOT NULL,
	`supplier_product_id` Int( 255 ) NOT NULL,
	PRIMARY KEY ( `id` ) )
ENGINE = InnoDB;
-- -------------------------------------------------------------

-- CREATE INDEX "index_sales_id" -------------------------------
CREATE INDEX `index_sales_id` ON `sales_products`( `sales_id` );
-- -------------------------------------------------------------


-- CREATE INDEX "index_supplier_product_id" --------------------
CREATE INDEX `index_supplier_product_id` ON `sales_products`( `supplier_product_id` );