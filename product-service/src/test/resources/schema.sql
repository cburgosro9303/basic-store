drop schema if exists product CASCADE;
drop schema if exists audit CASCADE;

CREATE SCHEMA IF NOT EXISTS PRODUCT;
CREATE SCHEMA IF NOT EXISTS AUDIT;
CREATE SCHEMA IF NOT EXISTS PUBLIC;
drop sequence if exists public.hibernate_sequence;
CREATE SEQUENCE public.hibernate_sequence
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 100;

CREATE SEQUENCE product.product_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 100;

CREATE TABLE product.product
(
    id               integer        NOT NULL DEFAULT product.product_id_seq.nextval,
    name             varchar(100)   NOT NULL,
    price            numeric(21, 6) NOT NULL,
    stock            bigint         NOT NULL,
    discount         numeric(3, 2)  NOT NULL,
    product_state_id integer        NOT NULL,
    brand_id         integer        NOT NULL,
    CONSTRAINT pk_product_product_id PRIMARY KEY (id),
    CONSTRAINT uq_product_product_name UNIQUE (name)

);

CREATE SEQUENCE product.product_state_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 100;

CREATE TABLE product.product_state
(
    id   integer     NOT NULL DEFAULT product.product_state_id_seq.nextval,
    name varchar(50) NOT NULL,
    CONSTRAINT pk_product_state_id PRIMARY KEY (id),
    CONSTRAINT uq_product_state_name UNIQUE (name)

);

CREATE SEQUENCE product.brand_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 100;

CREATE TABLE product.brand
(
    id   integer      NOT NULL DEFAULT product.brand_id_seq.nextval,
    name varchar(100) NOT NULL,
    CONSTRAINT pk_product_brand_id PRIMARY KEY (id),
    CONSTRAINT uq_producto_brand_name UNIQUE (name)

);

ALTER TABLE product.product
    ADD CONSTRAINT fk_product_product_state FOREIGN KEY (product_state_id)
        REFERENCES product.product_state (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE product.product
    ADD CONSTRAINT fk_product_product_brand FOREIGN KEY (brand_id)
        REFERENCES product.brand (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;

INSERT INTO product.product_state
    (id, name)
VALUES (1, 'Active');

INSERT INTO PRODUCT.brand(id, name)
VALUES (1, 'Apple');

INSERT INTO product.product(id, name, price, stock, discount, product_state_id, brand_id)
VALUES (1, 'Iphone 6', 600000.000000, 10, 0.10, 1, 1);

INSERT INTO product.product(id, name, price, stock, discount, product_state_id, brand_id)
VALUES (2, 'Iphone 7', 800000.000000, 5, 0.80, 1, 1);
