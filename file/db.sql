--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.20
-- Dumped by pg_dump version 9.5.20

SET statement_timeout = 0;
SET lock_timeout = 0;
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
-- Name: oauth2_client_details; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.oauth2_client_details (
    id character varying(256) NOT NULL,
    client_id character varying(256) NOT NULL,
    client_secret character varying(256),
    authorized_grant_types character varying(256),
    resource_ids character varying(256),
    authorities character varying(256),
    scopes character varying(256),
    redirect_uri character varying(256),
    access_token_validity integer,
    refresh_token_validity integer,
    additional_information character varying(4096),
    auto_approve character varying(256)
);


ALTER TABLE public.oauth2_client_details OWNER TO postgres;

--
-- Name: oauth2_permission; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.oauth2_permission (
    id character varying(256) NOT NULL,
    name character varying(256) NOT NULL,
    code character varying(256) NOT NULL,
    description character varying(256),
    create_time timestamp without time zone,
    update_time timestamp without time zone
);


ALTER TABLE public.oauth2_permission OWNER TO postgres;

--
-- Name: oauth2_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.oauth2_role (
    id character varying(256) NOT NULL,
    name character varying(256) NOT NULL,
    code character varying(256) NOT NULL,
    description character varying(256),
    create_time timestamp without time zone,
    update_time timestamp without time zone
);


ALTER TABLE public.oauth2_role OWNER TO postgres;

--
-- Name: oauth2_role_permission; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.oauth2_role_permission (
    id character varying(256) NOT NULL,
    role_id character varying(256) NOT NULL,
    permission_id character varying(256) NOT NULL
);


ALTER TABLE public.oauth2_role_permission OWNER TO postgres;

--
-- Name: oauth2_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.oauth2_user (
    id character varying(256) NOT NULL,
    username character varying(100) NOT NULL,
    nickname character varying(100) NOT NULL,
    password character varying(100) NOT NULL,
    phone character varying(32),
    gender integer,
    head_img_url character varying(200),
    create_time timestamp without time zone,
    update_time timestamp without time zone,
    del_flag integer,
    type integer,
    enabled integer
);


ALTER TABLE public.oauth2_user OWNER TO postgres;

--
-- Name: oauth2_user_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.oauth2_user_role (
    id character varying(256) NOT NULL,
    user_id character varying(256) NOT NULL,
    role_id character varying(256) NOT NULL
);


ALTER TABLE public.oauth2_user_role OWNER TO postgres;

--
-- Data for Name: oauth2_client_details; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.oauth2_client_details (id, client_id, client_secret, authorized_grant_types, resource_ids, authorities, scopes, redirect_uri, access_token_validity, refresh_token_validity, additional_information, auto_approve) FROM stdin;
6c0fc42f-0540-4c50-aaf2-7129b3bf4329	jianjukeji-client	$2a$10$T53aV3OUaqDwsfxt561Hse4Jh3ykfTepGyZL5/3qWZq1ct6CnBODq	authorization_code,refresh_token,password	all				3600	36000		true
\.


--
-- Data for Name: oauth2_permission; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.oauth2_permission (id, name, code, description, create_time, update_time) FROM stdin;
\.


--
-- Data for Name: oauth2_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.oauth2_role (id, name, code, description, create_time, update_time) FROM stdin;
\.


--
-- Data for Name: oauth2_role_permission; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.oauth2_role_permission (id, role_id, permission_id) FROM stdin;
\.


--
-- Data for Name: oauth2_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.oauth2_user (id, username, nickname, password, phone, gender, head_img_url, create_time, update_time, del_flag, type, enabled) FROM stdin;
3cb9c1f0-332c-4b0a-9b46-00251a636c2e	admin	admin	$2a$10$zHZJ9o/qZhcHqEBeEyEb4uva3iBUGgYhue7mww12wDdSICSPO.Zaa	13333333333	0	\N	\N	\N	0	\N	1
\.


--
-- Data for Name: oauth2_user_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.oauth2_user_role (id, user_id, role_id) FROM stdin;
\.


--
-- Name: oauth2_permission_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.oauth2_permission
    ADD CONSTRAINT oauth2_permission_pk PRIMARY KEY (id);


--
-- Name: oauth2_role_permission_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.oauth2_role_permission
    ADD CONSTRAINT oauth2_role_permission_pk PRIMARY KEY (id);


--
-- Name: oauth2_role_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.oauth2_role
    ADD CONSTRAINT oauth2_role_pk PRIMARY KEY (id);


--
-- Name: oauth2_user_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.oauth2_user
    ADD CONSTRAINT oauth2_user_pk PRIMARY KEY (id);


--
-- Name: oauth2_user_role_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.oauth2_user_role
    ADD CONSTRAINT oauth2_user_role_pk PRIMARY KEY (id);


--
-- Name: oauth2_user_username; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.oauth2_user
    ADD CONSTRAINT oauth2_user_username UNIQUE (username);


--
-- Name: oauth_client_details_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.oauth2_client_details
    ADD CONSTRAINT oauth_client_details_pk PRIMARY KEY (id);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

