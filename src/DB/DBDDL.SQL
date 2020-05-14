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

INSERT INTO `suppliers` ( `name`, `desc`)
VALUES ( 'Diana', 'Producto salvadoreño' ),
( 'Yummi', 'Producto salvadoreño' ),
( 'Dilacsa', 'Producto salvadoreño de lacteos' ),
( 'Lacteos Metapanecos', 'Producto salvadoreño de lacteos de Metapan, Santa Ana' );

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
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`name` VarChar( 255 ) NOT NULL,
	`descripcion` LongText NULL,
	`precio` Double NOT NULL,
	`prom` Int( 2 ) NULL DEFAULT 2,
	PRIMARY KEY ( `id` ),
	CONSTRAINT `unique_id` UNIQUE( `id` ) )
ENGINE = InnoDB;
-- -------------------------------------------------------------

INSERT INTO `products` ( `name`, `descripcion`, `precio`, `prom`)
VALUES ( 'Ranchitas', 'Bocadillo', 10.0, false ),
( 'Buenachos', 'Bocadillo', 10.0, false ),
( 'Queso Duro/blando', 'derivados de la leche', 0.50, false );

-- CREATE TABLE IF NOT EXISTS "supplier_product" -----------------------------
CREATE TABLE `supplier_product`( 
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`users_id` Int( 255 ) NOT NULL,
	`created_at` Timestamp NULL,
	`products_id` Int (255) NOT NULL,
	`suppliers_id` Int (255) NOT NULL,
	`stock` Int ( 255 ) NULL DEFAULT 1,
  CONSTRAINT `fk_supplier`
    FOREIGN KEY (suppliers_id) REFERENCES suppliers (id),
  CONSTRAINT `fk_product`
    FOREIGN KEY (products_id) REFERENCES products (id),
	PRIMARY KEY ( `id` ) )
ENGINE = InnoDB;


INSERT INTO `supplier_product` ( `users_id`, `created_at`, `products_id`, `suppliers_id`, `stock`)
VALUES ( 1, '', 3, 3, 2 ),( 1, '', 3, 3, 12 );

-- CREATE TABLE IF NOT EXISTS "sales" ----------------------------------------
CREATE TABLE `sales`(
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`total` Double NOT NULL,
	`users_id` Int( 255 ) NOT NULL,
    `descripcion` VARCHAR(255) NULL,
  CONSTRAINT `fk_users`
    FOREIGN KEY (users_id) REFERENCES users (id),
	PRIMARY KEY ( `id` ),
	CONSTRAINT `unique_id` UNIQUE( `id` ) )
ENGINE = InnoDB;
-- -------------------------------------------------------------


-- CREATE TABLE IF NOT EXISTS "sales_products" -------------------------------
CREATE TABLE `sales_products`(
	`id` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`cant` Int( 255 ) NOT NULL,
	`sales_id` Int( 255 ) NOT NULL,
	`product_products_id` Int(255) NOT NULL,
  CONSTRAINT `fk_sales`
    FOREIGN KEY (sales_id) REFERENCES sales (id),
  CONSTRAINT `fk_suppliers_products`
    FOREIGN KEY (product_products_id) REFERENCES supplier_product (id),
	PRIMARY KEY ( `id` ) )
ENGINE = InnoDB;
-- -------------------------------------------------------------


