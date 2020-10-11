--
-- PostgreSQL database dump
--

-- Dumped from database version 10.14
-- Dumped by pg_dump version 10.14

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: ss_address; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ss_address (
    id character varying(6) NOT NULL,
    user_id character varying(6) NOT NULL,
    default_address_flg boolean NOT NULL,
    country_code character varying(10) NOT NULL,
    district_code character varying(10),
    state_code character varying(10),
    address character varying(500) NOT NULL,
    full_name character varying(255) NOT NULL,
    phone character varying(30) NOT NULL
);


ALTER TABLE public.ss_address OWNER TO postgres;

--
-- Name: TABLE ss_address; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.ss_address IS 'Table user''s address';


--
-- Name: ss_brand_model; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ss_brand_model (
    brand_id character varying(30) NOT NULL,
    model_id character varying(30) NOT NULL,
    brand_name character varying(255) NOT NULL,
    model_name character varying(255) NOT NULL
);


ALTER TABLE public.ss_brand_model OWNER TO postgres;

--
-- Name: ss_cart; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ss_cart (
    id character varying(6) NOT NULL,
    user_id character varying(6) NOT NULL,
    product_id character varying(6) NOT NULL,
    variant_id character varying(6) NOT NULL,
    quantity integer DEFAULT 0 NOT NULL,
    status character varying(1) NOT NULL,
    total_price numeric(17,2) NOT NULL,
    ins_time character varying(14),
    upd_time character varying(14)
);


ALTER TABLE public.ss_cart OWNER TO postgres;

--
-- Name: TABLE ss_cart; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.ss_cart IS 'Table cart';


--
-- Name: ss_order; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ss_order (
    id character varying(6) NOT NULL,
    user_id character varying(6) NOT NULL,
    cart_id character varying(6) NOT NULL,
    address_id character varying(6) NOT NULL,
    promotion_code character varying(20),
    delivery_way character varying(1) NOT NULL,
    payment_method character varying(1) NOT NULL,
    status character varying(1) NOT NULL,
    note character varying(1000),
    shipping_price numeric(17,2) DEFAULT 0 NOT NULL,
    ins_time character varying(14),
    upd_time character varying(14),
    point integer NOT NULL
);


ALTER TABLE public.ss_order OWNER TO postgres;

--
-- Name: ss_product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ss_product (
    id character varying(6) NOT NULL,
    brand_id character varying(30) NOT NULL,
    name character varying(255) NOT NULL,
    present_price numeric(17,2) NOT NULL,
    description character varying(1000),
    del_flg character varying(1) NOT NULL,
    status character varying(1) NOT NULL,
    ins_time character varying(14),
    upd_time character varying(14),
    model_id character varying(30) NOT NULL
);


ALTER TABLE public.ss_product OWNER TO postgres;

--
-- Name: ss_product_variant; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ss_product_variant (
    id character varying(6) NOT NULL,
    product_id character varying(6) NOT NULL,
    size character varying(255),
    color numeric(17,2),
    quantity character varying(1) NOT NULL,
    price character varying(1) NOT NULL,
    status character varying(1) NOT NULL,
    ins_time character varying(14),
    upd_time character varying(14)
);


ALTER TABLE public.ss_product_variant OWNER TO postgres;

--
-- Name: ss_promotion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ss_promotion (
    id integer NOT NULL,
    code character varying(20) NOT NULL,
    due_date character varying(14) NOT NULL,
    expiration_time integer NOT NULL,
    point integer NOT NULL,
    type character varying(1) NOT NULL
);


ALTER TABLE public.ss_promotion OWNER TO postgres;

--
-- Name: TABLE ss_promotion; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.ss_promotion IS 'Table promotion';


--
-- Name: ss_promotion_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ss_promotion_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ss_promotion_id_seq OWNER TO postgres;

--
-- Name: ss_promotion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ss_promotion_id_seq OWNED BY public.ss_promotion.id;


--
-- Name: ss_recent_product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ss_recent_product (
    id integer NOT NULL,
    product_id character varying(6) NOT NULL,
    variant_id character varying(6) NOT NULL,
    user_id character varying(6) NOT NULL,
    ins_time character varying(14),
    upd_time character varying(14)
);


ALTER TABLE public.ss_recent_product OWNER TO postgres;

--
-- Name: ss_recent_product_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ss_recent_product_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ss_recent_product_id_seq OWNER TO postgres;

--
-- Name: ss_recent_product_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ss_recent_product_id_seq OWNED BY public.ss_recent_product.id;


--
-- Name: ss_store_setting; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ss_store_setting (
    master_key character varying(30) NOT NULL,
    sub_key character varying(30) NOT NULL,
    namev character varying(255) NOT NULL,
    value character varying(1) NOT NULL,
    description character varying(500)
);


ALTER TABLE public.ss_store_setting OWNER TO postgres;

--
-- Name: ss_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ss_user (
    id character varying(6) NOT NULL,
    username character varying(100) NOT NULL,
    password character varying(36) NOT NULL,
    dob character varying(8),
    phone character varying(30) NOT NULL,
    point integer,
    user_role character varying(1) NOT NULL,
    status character varying(1) NOT NULL,
    upd_time character varying(14),
    ins_time character varying(14),
    del_flg character varying(1) NOT NULL
);


ALTER TABLE public.ss_user OWNER TO postgres;

--
-- Name: TABLE ss_user; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.ss_user IS 'User table';


--
-- Name: ss_promotion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ss_promotion ALTER COLUMN id SET DEFAULT nextval('public.ss_promotion_id_seq'::regclass);


--
-- Name: ss_recent_product id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ss_recent_product ALTER COLUMN id SET DEFAULT nextval('public.ss_recent_product_id_seq'::regclass);


--
-- Data for Name: ss_address; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ss_address (id, user_id, default_address_flg, country_code, district_code, state_code, address, full_name, phone) FROM stdin;
\.


--
-- Data for Name: ss_brand_model; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ss_brand_model (brand_id, model_id, brand_name, model_name) FROM stdin;
\.


--
-- Data for Name: ss_cart; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ss_cart (id, user_id, product_id, variant_id, quantity, status, total_price, ins_time, upd_time) FROM stdin;
\.


--
-- Data for Name: ss_order; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ss_order (id, user_id, cart_id, address_id, promotion_code, delivery_way, payment_method, status, note, shipping_price, ins_time, upd_time, point) FROM stdin;
\.


--
-- Data for Name: ss_product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ss_product (id, brand_id, name, present_price, description, del_flg, status, ins_time, upd_time, model_id) FROM stdin;
\.


--
-- Data for Name: ss_product_variant; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ss_product_variant (id, product_id, size, color, quantity, price, status, ins_time, upd_time) FROM stdin;
\.


--
-- Data for Name: ss_promotion; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ss_promotion (id, code, due_date, expiration_time, point, type) FROM stdin;
\.


--
-- Data for Name: ss_recent_product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ss_recent_product (id, product_id, variant_id, user_id, ins_time, upd_time) FROM stdin;
\.


--
-- Data for Name: ss_store_setting; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ss_store_setting (master_key, sub_key, namev, value, description) FROM stdin;
\.


--
-- Data for Name: ss_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ss_user (id, username, password, dob, phone, point, user_role, status, upd_time, ins_time, del_flg) FROM stdin;
\.


--
-- Name: ss_promotion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ss_promotion_id_seq', 1, false);


--
-- Name: ss_recent_product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ss_recent_product_id_seq', 1, false);


--
-- Name: ss_address ss_address_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ss_address
    ADD CONSTRAINT ss_address_pk PRIMARY KEY (user_id, id);


--
-- Name: ss_brand_model ss_brand_model_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ss_brand_model
    ADD CONSTRAINT ss_brand_model_pkey PRIMARY KEY (brand_id, model_id);


--
-- Name: ss_cart ss_cart_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ss_cart
    ADD CONSTRAINT ss_cart_pkey PRIMARY KEY (id, user_id, product_id, variant_id);


--
-- Name: ss_order ss_order_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ss_order
    ADD CONSTRAINT ss_order_pkey PRIMARY KEY (id, user_id, cart_id);


--
-- Name: ss_product ss_product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ss_product
    ADD CONSTRAINT ss_product_pkey PRIMARY KEY (id);


--
-- Name: ss_product_variant ss_product_variant_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ss_product_variant
    ADD CONSTRAINT ss_product_variant_pkey PRIMARY KEY (id, product_id);


--
-- Name: ss_promotion ss_promotion_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ss_promotion
    ADD CONSTRAINT ss_promotion_pk PRIMARY KEY (code, id);


--
-- Name: ss_recent_product ss_recent_product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ss_recent_product
    ADD CONSTRAINT ss_recent_product_pkey PRIMARY KEY (id, product_id, variant_id, user_id);


--
-- Name: ss_store_setting ss_store_setting_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ss_store_setting
    ADD CONSTRAINT ss_store_setting_pkey PRIMARY KEY (master_key, sub_key);


--
-- Name: ss_user ss_user_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ss_user
    ADD CONSTRAINT ss_user_pk PRIMARY KEY (id, username);


--
-- PostgreSQL database dump complete
--

