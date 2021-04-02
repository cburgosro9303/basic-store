-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.9.3-beta1
-- PostgreSQL version: 13.0
-- Project Site: pgmodeler.io
-- Model Author: ---
-- -- object: techbox | type: ROLE --
-- -- DROP ROLE IF EXISTS techbox;
-- CREATE ROLE techbox WITH 
-- 	SUPERUSER
-- 	CREATEDB
-- 	CREATEROLE
-- 	INHERIT
-- 	LOGIN
-- 	REPLICATION
-- 	BYPASSRLS
-- 	ENCRYPTED PASSWORD '********';
-- -- ddl-end --
-- 

-- Database creation must be performed outside a multi lined SQL file. 
-- These commands were put in this file only as a convenience.
-- 

-- object: batch | type: SCHEMA --
-- DROP SCHEMA IF EXISTS batch CASCADE;
CREATE SCHEMA batch;
-- ddl-end --
ALTER SCHEMA batch OWNER TO techbox;
-- ddl-end --

-- object: product | type: SCHEMA --
-- DROP SCHEMA IF EXISTS product CASCADE;
CREATE SCHEMA product;
-- ddl-end --
ALTER SCHEMA product OWNER TO techbox;
-- ddl-end --

create user sonarqube with encrypted password 'Sonarqube123*';

create database sonarqube;
ALTER database sonarqube OWNER TO sonarqube;

grant all privileges on database sonarqube to sonarqube;





-- object: audit | type: SCHEMA --
-- DROP SCHEMA IF EXISTS audit CASCADE;
CREATE SCHEMA audit;
-- ddl-end --
ALTER SCHEMA audit OWNER TO techbox;
-- ddl-end --

-- object: shop | type: SCHEMA --
-- DROP SCHEMA IF EXISTS shop CASCADE;
CREATE SCHEMA shop;
-- ddl-end --
ALTER SCHEMA shop OWNER TO techbox;
-- ddl-end --

SET search_path TO pg_catalog,public,batch,product,audit,shop;
-- ddl-end --

-- object: public.hibernate_sequence | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.hibernate_sequence CASCADE;
CREATE SEQUENCE public.hibernate_sequence
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE public.hibernate_sequence OWNER TO techbox;
-- ddl-end --

-- object: batch.batch_job_instance | type: TABLE --
-- DROP TABLE IF EXISTS batch.batch_job_instance CASCADE;
CREATE TABLE batch.batch_job_instance (
	job_instance_id bigint NOT NULL,
	version bigint,
	job_name character varying(100) NOT NULL,
	job_key character varying(32) NOT NULL,
	CONSTRAINT batch_job_instance_pkey PRIMARY KEY (job_instance_id),
	CONSTRAINT job_inst_un UNIQUE (job_name,job_key)

);
-- ddl-end --
ALTER TABLE batch.batch_job_instance OWNER TO techbox;
-- ddl-end --

-- object: batch.batch_job_execution | type: TABLE --
-- DROP TABLE IF EXISTS batch.batch_job_execution CASCADE;
CREATE TABLE batch.batch_job_execution (
	job_execution_id bigint NOT NULL,
	version bigint,
	job_instance_id bigint NOT NULL,
	create_time timestamp NOT NULL,
	start_time timestamp,
	end_time timestamp,
	status character varying(10),
	exit_code character varying(2500),
	exit_message character varying(2500),
	last_updated timestamp,
	job_configuration_location character varying(2500),
	CONSTRAINT batch_job_execution_pkey PRIMARY KEY (job_execution_id)

);
-- ddl-end --
ALTER TABLE batch.batch_job_execution OWNER TO techbox;
-- ddl-end --

-- object: batch.batch_job_execution_params | type: TABLE --
-- DROP TABLE IF EXISTS batch.batch_job_execution_params CASCADE;
CREATE TABLE batch.batch_job_execution_params (
	job_execution_id bigint NOT NULL,
	type_cd character varying(6) NOT NULL,
	key_name character varying(100) NOT NULL,
	string_val character varying(250),
	date_val timestamp,
	long_val bigint,
	double_val double precision,
	identifying character(1) NOT NULL
);
-- ddl-end --
ALTER TABLE batch.batch_job_execution_params OWNER TO techbox;
-- ddl-end --

-- object: batch.batch_step_execution | type: TABLE --
-- DROP TABLE IF EXISTS batch.batch_step_execution CASCADE;
CREATE TABLE batch.batch_step_execution (
	step_execution_id bigint NOT NULL,
	version bigint NOT NULL,
	step_name character varying(100) NOT NULL,
	job_execution_id bigint NOT NULL,
	start_time timestamp NOT NULL,
	end_time timestamp,
	status character varying(10),
	commit_count bigint,
	read_count bigint,
	filter_count bigint,
	write_count bigint,
	read_skip_count bigint,
	write_skip_count bigint,
	process_skip_count bigint,
	rollback_count bigint,
	exit_code character varying(2500),
	exit_message character varying(2500),
	last_updated timestamp,
	CONSTRAINT batch_step_execution_pkey PRIMARY KEY (step_execution_id)

);
-- ddl-end --
ALTER TABLE batch.batch_step_execution OWNER TO techbox;
-- ddl-end --

-- object: batch.batch_step_execution_context | type: TABLE --
-- DROP TABLE IF EXISTS batch.batch_step_execution_context CASCADE;
CREATE TABLE batch.batch_step_execution_context (
	step_execution_id bigint NOT NULL,
	short_context character varying(2500) NOT NULL,
	serialized_context text,
	CONSTRAINT batch_step_execution_context_pkey PRIMARY KEY (step_execution_id)

);
-- ddl-end --
ALTER TABLE batch.batch_step_execution_context OWNER TO techbox;
-- ddl-end --

-- object: batch.batch_job_execution_context | type: TABLE --
-- DROP TABLE IF EXISTS batch.batch_job_execution_context CASCADE;
CREATE TABLE batch.batch_job_execution_context (
	job_execution_id bigint NOT NULL,
	short_context character varying(2500) NOT NULL,
	serialized_context text,
	CONSTRAINT batch_job_execution_context_pkey PRIMARY KEY (job_execution_id)

);
-- ddl-end --
ALTER TABLE batch.batch_job_execution_context OWNER TO techbox;
-- ddl-end --

-- object: batch.batch_step_execution_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS batch.batch_step_execution_seq CASCADE;
CREATE SEQUENCE batch.batch_step_execution_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE batch.batch_step_execution_seq OWNER TO techbox;
-- ddl-end --

-- object: batch.batch_job_execution_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS batch.batch_job_execution_seq CASCADE;
CREATE SEQUENCE batch.batch_job_execution_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE batch.batch_job_execution_seq OWNER TO techbox;
-- ddl-end --

-- object: batch.batch_job_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS batch.batch_job_seq CASCADE;
CREATE SEQUENCE batch.batch_job_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE batch.batch_job_seq OWNER TO techbox;
-- ddl-end --

-- object: product.product_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS product.product_id_seq CASCADE;
CREATE SEQUENCE product.product_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE product.product_id_seq OWNER TO techbox;
-- ddl-end --

-- object: product.product | type: TABLE --
-- DROP TABLE IF EXISTS product.product CASCADE;
CREATE TABLE product.product (
	id integer NOT NULL DEFAULT nextval('product.product_id_seq'::regclass),
	name character varying(100) NOT NULL,
	price numeric(21,6) NOT NULL,
	stock bigint NOT NULL,
	discount numeric(3,2) NOT NULL,
	product_state_id integer NOT NULL,
	brand_id integer NOT NULL,
	CONSTRAINT pk_product_product_id PRIMARY KEY (id),
	CONSTRAINT uq_product_product_name UNIQUE (name)

);
-- ddl-end --
ALTER TABLE product.product OWNER TO techbox;
-- ddl-end --

-- object: product.product_state_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS product.product_state_id_seq CASCADE;
CREATE SEQUENCE product.product_state_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE product.product_state_id_seq OWNER TO techbox;
-- ddl-end --

-- object: product.product_state | type: TABLE --
-- DROP TABLE IF EXISTS product.product_state CASCADE;
CREATE TABLE product.product_state (
	id integer NOT NULL DEFAULT nextval('product.product_state_id_seq'::regclass),
	name character varying(50) NOT NULL,
	CONSTRAINT pk_product_state_id PRIMARY KEY (id),
	CONSTRAINT uq_product_state_name UNIQUE (name)

);
-- ddl-end --
ALTER TABLE product.product_state OWNER TO techbox;
-- ddl-end --

-- object: product.brand_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS product.brand_id_seq CASCADE;
CREATE SEQUENCE product.brand_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE product.brand_id_seq OWNER TO techbox;
-- ddl-end --

-- object: product.brand | type: TABLE --
-- DROP TABLE IF EXISTS product.brand CASCADE;
CREATE TABLE product.brand (
	id integer NOT NULL DEFAULT nextval('product.brand_id_seq'::regclass),
	name character varying(100) NOT NULL,
	CONSTRAINT pk_product_brand_id PRIMARY KEY (id),
	CONSTRAINT uq_producto_brand_name UNIQUE (name)

);
-- ddl-end --
ALTER TABLE product.brand OWNER TO techbox;
-- ddl-end --

-- object: audit.product_aud | type: TABLE --
-- DROP TABLE IF EXISTS audit.product_aud CASCADE;
CREATE TABLE audit.product_aud (
	id bigint NOT NULL,
	rev integer NOT NULL,
	revtype smallint,
	stock bigint,
	product_state_id bigint,
	CONSTRAINT product_aud_pkey PRIMARY KEY (id,rev)

);
-- ddl-end --
ALTER TABLE audit.product_aud OWNER TO techbox;
-- ddl-end --

-- object: audit.revinfo | type: TABLE --
-- DROP TABLE IF EXISTS audit.revinfo CASCADE;
CREATE TABLE audit.revinfo (
	rev integer NOT NULL,
	revtstmp bigint,
	CONSTRAINT revinfo_pkey PRIMARY KEY (rev)

);
-- ddl-end --
ALTER TABLE audit.revinfo OWNER TO techbox;
-- ddl-end --

-- object: shop.shopping_cart_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS shop.shopping_cart_id_seq CASCADE;
CREATE SEQUENCE shop.shopping_cart_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE shop.shopping_cart_id_seq OWNER TO techbox;
-- ddl-end --

-- object: shop.shopping_cart | type: TABLE --
-- DROP TABLE IF EXISTS shop.shopping_cart CASCADE;
CREATE TABLE shop.shopping_cart (
	id integer NOT NULL DEFAULT nextval('shop.shopping_cart_id_seq'::regclass),
	purchase_id bigint NOT NULL,
	shop_date timestamp,
	shop_state character varying(30) NOT NULL,
	CONSTRAINT shopping_cart_pk PRIMARY KEY (id)

);
-- ddl-end --
COMMENT ON COLUMN shop.shopping_cart.shop_date IS E'date when purchase change state';
-- ddl-end --
ALTER TABLE shop.shopping_cart OWNER TO techbox;
-- ddl-end --

-- object: shop.shop_state_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS shop.shop_state_id_seq CASCADE;
CREATE SEQUENCE shop.shop_state_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE shop.shop_state_id_seq OWNER TO techbox;
-- ddl-end --

-- object: shop.cart_product_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS shop.cart_product_id_seq CASCADE;
CREATE SEQUENCE shop.cart_product_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE shop.cart_product_id_seq OWNER TO techbox;
-- ddl-end --

-- object: shop.cart_product | type: TABLE --
-- DROP TABLE IF EXISTS shop.cart_product CASCADE;
CREATE TABLE shop.cart_product (
	id integer NOT NULL DEFAULT nextval('shop.cart_product_id_seq'::regclass),
	name character varying(100) NOT NULL,
	price numeric(21,6) NOT NULL,
	discount numeric(3,2) NOT NULL,
	shopping_cart_id bigint NOT NULL,
	brand character varying(100) NOT NULL,
	original_product_id int8 NOT NULL,
	CONSTRAINT cart_shop_pk PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE shop.cart_product OWNER TO techbox;
-- ddl-end --

-- object: job_inst_exec_fk | type: CONSTRAINT --
-- ALTER TABLE batch.batch_job_execution DROP CONSTRAINT IF EXISTS job_inst_exec_fk CASCADE;
ALTER TABLE batch.batch_job_execution ADD CONSTRAINT job_inst_exec_fk FOREIGN KEY (job_instance_id)
REFERENCES batch.batch_job_instance (job_instance_id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: job_exec_params_fk | type: CONSTRAINT --
-- ALTER TABLE batch.batch_job_execution_params DROP CONSTRAINT IF EXISTS job_exec_params_fk CASCADE;
ALTER TABLE batch.batch_job_execution_params ADD CONSTRAINT job_exec_params_fk FOREIGN KEY (job_execution_id)
REFERENCES batch.batch_job_execution (job_execution_id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: job_exec_step_fk | type: CONSTRAINT --
-- ALTER TABLE batch.batch_step_execution DROP CONSTRAINT IF EXISTS job_exec_step_fk CASCADE;
ALTER TABLE batch.batch_step_execution ADD CONSTRAINT job_exec_step_fk FOREIGN KEY (job_execution_id)
REFERENCES batch.batch_job_execution (job_execution_id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: step_exec_ctx_fk | type: CONSTRAINT --
-- ALTER TABLE batch.batch_step_execution_context DROP CONSTRAINT IF EXISTS step_exec_ctx_fk CASCADE;
ALTER TABLE batch.batch_step_execution_context ADD CONSTRAINT step_exec_ctx_fk FOREIGN KEY (step_execution_id)
REFERENCES batch.batch_step_execution (step_execution_id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: job_exec_ctx_fk | type: CONSTRAINT --
-- ALTER TABLE batch.batch_job_execution_context DROP CONSTRAINT IF EXISTS job_exec_ctx_fk CASCADE;
ALTER TABLE batch.batch_job_execution_context ADD CONSTRAINT job_exec_ctx_fk FOREIGN KEY (job_execution_id)
REFERENCES batch.batch_job_execution (job_execution_id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_product_product_state | type: CONSTRAINT --
-- ALTER TABLE product.product DROP CONSTRAINT IF EXISTS fk_product_product_state CASCADE;
ALTER TABLE product.product ADD CONSTRAINT fk_product_product_state FOREIGN KEY (product_state_id)
REFERENCES product.product_state (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_product_product_brand | type: CONSTRAINT --
-- ALTER TABLE product.product DROP CONSTRAINT IF EXISTS fk_product_product_brand CASCADE;
ALTER TABLE product.product ADD CONSTRAINT fk_product_product_brand FOREIGN KEY (brand_id)
REFERENCES product.brand (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_audit_product_aud_rev | type: CONSTRAINT --
-- ALTER TABLE audit.product_aud DROP CONSTRAINT IF EXISTS fk_audit_product_aud_rev CASCADE;
ALTER TABLE audit.product_aud ADD CONSTRAINT fk_audit_product_aud_rev FOREIGN KEY (rev)
REFERENCES audit.revinfo (rev) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_shopping_cart_cart_product | type: CONSTRAINT --
-- ALTER TABLE shop.cart_product DROP CONSTRAINT IF EXISTS fk_shopping_cart_cart_product CASCADE;
ALTER TABLE shop.cart_product ADD CONSTRAINT fk_shopping_cart_cart_product FOREIGN KEY (shopping_cart_id)
REFERENCES shop.shopping_cart (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: "grant_CU_64ccc5561e" | type: PERMISSION --
GRANT CREATE,USAGE
   ON SCHEMA public
   TO techbox;
-- ddl-end --

-- object: "grant_CU_cd8e46e7b6" | type: PERMISSION --
GRANT CREATE,USAGE
   ON SCHEMA public
   TO PUBLIC;
-- ddl-end --


INSERT INTO product.product_state(id, name)
VALUES(1, 'Nuevo'),(2, 'Usado');

INSERT INTO product.brand(id, "name")
VALUES(1, 'Apple'),(2, 'HTC'),(3, 'Huawey'),(4, 'LG'),(5, 'Motorola'),(6, 'Samsung');