
CREATE SCHEMA batch;
ALTER SCHEMA batch OWNER TO worldoffice;

CREATE SCHEMA product;
ALTER SCHEMA product OWNER TO worldoffice;

CREATE SCHEMA audit;
ALTER SCHEMA audit OWNER TO worldoffice;

SET search_path TO pg_catalog,public,batch,product,audit;

CREATE SEQUENCE public.brand_id_seq
	INCREMENT BY 50
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

ALTER SEQUENCE public.brand_id_seq OWNER TO worldoffice;

CREATE SEQUENCE public.hibernate_sequence
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;


ALTER SEQUENCE public.hibernate_sequence OWNER TO worldoffice;

CREATE SEQUENCE public.product_id_seq
	INCREMENT BY 50
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

ALTER SEQUENCE public.product_id_seq OWNER TO worldoffice;

CREATE TABLE batch.batch_job_instance (
	job_instance_id bigint NOT NULL,
	version bigint,
	job_name character varying(100) NOT NULL,
	job_key character varying(32) NOT NULL,
	CONSTRAINT batch_job_instance_pkey PRIMARY KEY (job_instance_id),
	CONSTRAINT job_inst_un UNIQUE (job_name,job_key)

);

ALTER TABLE batch.batch_job_instance OWNER TO worldoffice;

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

ALTER TABLE batch.batch_job_execution OWNER TO worldoffice;

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

ALTER TABLE batch.batch_job_execution_params OWNER TO worldoffice;

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

ALTER TABLE batch.batch_step_execution OWNER TO worldoffice;

CREATE TABLE batch.batch_step_execution_context (
	step_execution_id bigint NOT NULL,
	short_context character varying(2500) NOT NULL,
	serialized_context text,
	CONSTRAINT batch_step_execution_context_pkey PRIMARY KEY (step_execution_id)

);

ALTER TABLE batch.batch_step_execution_context OWNER TO worldoffice;

CREATE TABLE batch.batch_job_execution_context (
	job_execution_id bigint NOT NULL,
	short_context character varying(2500) NOT NULL,
	serialized_context text,
	CONSTRAINT batch_job_execution_context_pkey PRIMARY KEY (job_execution_id)

);

ALTER TABLE batch.batch_job_execution_context OWNER TO worldoffice;

CREATE SEQUENCE batch.batch_step_execution_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

ALTER SEQUENCE batch.batch_step_execution_seq OWNER TO worldoffice;

CREATE SEQUENCE batch.batch_job_execution_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

ALTER SEQUENCE batch.batch_job_execution_seq OWNER TO worldoffice;

CREATE SEQUENCE batch.batch_job_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

ALTER SEQUENCE batch.batch_job_seq OWNER TO worldoffice;

CREATE SEQUENCE product.product_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

ALTER SEQUENCE product.product_id_seq OWNER TO worldoffice;

CREATE TABLE product.product (
	id integer NOT NULL DEFAULT nextval('product.product_id_seq'::regclass),
	name character varying(100) NOT NULL,
	price numeric(21,6) NOT NULL,
	stock bigint NOT NULL,
	discount numeric(3,2) NOT NULL,
	state_id integer NOT NULL,
	brand_id integer NOT NULL,
	CONSTRAINT pk_product_product_id PRIMARY KEY (id),
	CONSTRAINT uq_product_product_name UNIQUE (name)

);

ALTER TABLE product.product OWNER TO worldoffice;

CREATE SEQUENCE product.state_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

ALTER SEQUENCE product.state_id_seq OWNER TO worldoffice;

CREATE TABLE product.state (
	id integer NOT NULL DEFAULT nextval('product.state_id_seq'::regclass),
	name character varying(50) NOT NULL,
	CONSTRAINT pk_product_state_id PRIMARY KEY (id),
	CONSTRAINT uq_product_state_name UNIQUE (name)

);

ALTER TABLE product.state OWNER TO worldoffice;

CREATE SEQUENCE product.brand_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

ALTER SEQUENCE product.brand_id_seq OWNER TO worldoffice;

CREATE TABLE product.brand (
	id integer NOT NULL DEFAULT nextval('product.brand_id_seq'::regclass),
	name character varying(100) NOT NULL,
	CONSTRAINT pk_product_brand_id PRIMARY KEY (id),
	CONSTRAINT uq_producto_brand_name UNIQUE (name)

);

ALTER TABLE product.brand OWNER TO worldoffice;

CREATE TABLE audit.product_aud (
	id bigint NOT NULL,
	rev integer NOT NULL,
	revtype smallint,
	stock bigint,
	state_id bigint,
	CONSTRAINT product_aud_pkey PRIMARY KEY (id,rev)

);

ALTER TABLE audit.product_aud OWNER TO worldoffice;

CREATE TABLE audit.revinfo (
	rev integer NOT NULL,
	revtstmp bigint,
	CONSTRAINT revinfo_pkey PRIMARY KEY (rev)

);

ALTER TABLE audit.revinfo OWNER TO worldoffice;

ALTER TABLE batch.batch_job_execution ADD CONSTRAINT job_inst_exec_fk FOREIGN KEY (job_instance_id)
REFERENCES batch.batch_job_instance (job_instance_id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE batch.batch_job_execution_params ADD CONSTRAINT job_exec_params_fk FOREIGN KEY (job_execution_id)
REFERENCES batch.batch_job_execution (job_execution_id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE batch.batch_step_execution ADD CONSTRAINT job_exec_step_fk FOREIGN KEY (job_execution_id)
REFERENCES batch.batch_job_execution (job_execution_id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE batch.batch_step_execution_context ADD CONSTRAINT step_exec_ctx_fk FOREIGN KEY (step_execution_id)
REFERENCES batch.batch_step_execution (step_execution_id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE batch.batch_job_execution_context ADD CONSTRAINT job_exec_ctx_fk FOREIGN KEY (job_execution_id)
REFERENCES batch.batch_job_execution (job_execution_id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE product.product ADD CONSTRAINT fk_product_product_state FOREIGN KEY (state_id)
REFERENCES product.state (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE product.product ADD CONSTRAINT fk_product_product_brand FOREIGN KEY (brand_id)
REFERENCES product.brand (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE audit.product_aud ADD CONSTRAINT fk_audit_product_aud_rev FOREIGN KEY (rev)
REFERENCES audit.revinfo (rev) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;

GRANT CREATE,USAGE
   ON SCHEMA public
   TO worldoffice;

GRANT CREATE,USAGE
   ON SCHEMA public
   TO PUBLIC;
