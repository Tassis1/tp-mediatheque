--
-- PostgreSQL database dump
--

\restrict Okadu3EyiUsKtkOmLqf3OzTHR3Y1GfU80PCW7yPAjhLOrzYPu3kQSi0YkKgkWDy

-- Dumped from database version 16.14 (Ubuntu 16.14-1.pgdg24.04+1)
-- Dumped by pg_dump version 16.14 (Ubuntu 16.14-1.pgdg24.04+1)

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: emprunt; Type: TABLE; Schema: public; Owner: app_mediatheque
--

CREATE TABLE public.emprunt (
    id bigint NOT NULL,
    utilisateur_id bigint NOT NULL,
    livre_id bigint NOT NULL,
    date_emprunt date DEFAULT CURRENT_DATE NOT NULL,
    date_retour_prevue date NOT NULL,
    date_retour_effective date
);


ALTER TABLE public.emprunt OWNER TO app_mediatheque;

--
-- Name: emprunt_id_seq; Type: SEQUENCE; Schema: public; Owner: app_mediatheque
--

CREATE SEQUENCE public.emprunt_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.emprunt_id_seq OWNER TO app_mediatheque;

--
-- Name: emprunt_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: app_mediatheque
--

ALTER SEQUENCE public.emprunt_id_seq OWNED BY public.emprunt.id;


--
-- Name: livre; Type: TABLE; Schema: public; Owner: app_mediatheque
--

CREATE TABLE public.livre (
    id bigint NOT NULL,
    titre character varying(200) NOT NULL,
    auteur character varying(150) NOT NULL,
    isbn character varying(20) NOT NULL,
    disponible boolean DEFAULT true NOT NULL
);


ALTER TABLE public.livre OWNER TO app_mediatheque;

--
-- Name: livre_id_seq; Type: SEQUENCE; Schema: public; Owner: app_mediatheque
--

CREATE SEQUENCE public.livre_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.livre_id_seq OWNER TO app_mediatheque;

--
-- Name: livre_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: app_mediatheque
--

ALTER SEQUENCE public.livre_id_seq OWNED BY public.livre.id;


--
-- Name: utilisateur; Type: TABLE; Schema: public; Owner: app_mediatheque
--

CREATE TABLE public.utilisateur (
    id bigint NOT NULL,
    nom character varying(100) NOT NULL,
    email character varying(150) NOT NULL,
    mot_de_passe_hash character varying(255) NOT NULL,
    role character varying(20) DEFAULT 'USER'::character varying NOT NULL,
    CONSTRAINT utilisateur_role_check CHECK (((role)::text = ANY ((ARRAY['ADMIN'::character varying, 'USER'::character varying])::text[])))
);


ALTER TABLE public.utilisateur OWNER TO app_mediatheque;

--
-- Name: utilisateur_id_seq; Type: SEQUENCE; Schema: public; Owner: app_mediatheque
--

CREATE SEQUENCE public.utilisateur_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.utilisateur_id_seq OWNER TO app_mediatheque;

--
-- Name: utilisateur_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: app_mediatheque
--

ALTER SEQUENCE public.utilisateur_id_seq OWNED BY public.utilisateur.id;


--
-- Name: emprunt id; Type: DEFAULT; Schema: public; Owner: app_mediatheque
--

ALTER TABLE ONLY public.emprunt ALTER COLUMN id SET DEFAULT nextval('public.emprunt_id_seq'::regclass);


--
-- Name: livre id; Type: DEFAULT; Schema: public; Owner: app_mediatheque
--

ALTER TABLE ONLY public.livre ALTER COLUMN id SET DEFAULT nextval('public.livre_id_seq'::regclass);


--
-- Name: utilisateur id; Type: DEFAULT; Schema: public; Owner: app_mediatheque
--

ALTER TABLE ONLY public.utilisateur ALTER COLUMN id SET DEFAULT nextval('public.utilisateur_id_seq'::regclass);


--
-- Data for Name: emprunt; Type: TABLE DATA; Schema: public; Owner: app_mediatheque
--

COPY public.emprunt (id, utilisateur_id, livre_id, date_emprunt, date_retour_prevue, date_retour_effective) FROM stdin;
1	2	1	2026-08-05	2026-08-26	2026-08-05
2	2	1	2026-08-05	2026-08-26	2026-08-05
3	2	1	2026-08-06	2026-08-27	2026-08-06
4	2	1	2026-08-10	2026-08-31	2026-08-10
5	2	2	2026-08-11	2026-09-01	2026-08-17
6	2	1	2026-08-11	2026-09-01	2026-08-17
\.


--
-- Data for Name: livre; Type: TABLE DATA; Schema: public; Owner: app_mediatheque
--

COPY public.livre (id, titre, auteur, isbn, disponible) FROM stdin;
2	Le Petit Prince	Antoine de Saint-Exupéry	1111111111	t
1	1984	George Orwell	9782070368228	t
\.


--
-- Data for Name: utilisateur; Type: TABLE DATA; Schema: public; Owner: app_mediatheque
--

COPY public.utilisateur (id, nom, email, mot_de_passe_hash, role) FROM stdin;
1	Test User	test@tp.local	$2a$10$wVDFKxmE.5O/ZAsAjXaVGezfcn.uGYD87d6NupMf4uYXF/RQ8v0E.	USER
2	Test User 2	test2@tp.local	$2a$10$jj7PIpAJ3/GAD1IWYB0G1uFxxQUObGnKVLJ2t1hDH7ePzOKN40Xfm	ADMIN
\.


--
-- Name: emprunt_id_seq; Type: SEQUENCE SET; Schema: public; Owner: app_mediatheque
--

SELECT pg_catalog.setval('public.emprunt_id_seq', 6, true);


--
-- Name: livre_id_seq; Type: SEQUENCE SET; Schema: public; Owner: app_mediatheque
--

SELECT pg_catalog.setval('public.livre_id_seq', 2, true);


--
-- Name: utilisateur_id_seq; Type: SEQUENCE SET; Schema: public; Owner: app_mediatheque
--

SELECT pg_catalog.setval('public.utilisateur_id_seq', 2, true);


--
-- Name: emprunt emprunt_pkey; Type: CONSTRAINT; Schema: public; Owner: app_mediatheque
--

ALTER TABLE ONLY public.emprunt
    ADD CONSTRAINT emprunt_pkey PRIMARY KEY (id);


--
-- Name: livre livre_isbn_key; Type: CONSTRAINT; Schema: public; Owner: app_mediatheque
--

ALTER TABLE ONLY public.livre
    ADD CONSTRAINT livre_isbn_key UNIQUE (isbn);


--
-- Name: livre livre_pkey; Type: CONSTRAINT; Schema: public; Owner: app_mediatheque
--

ALTER TABLE ONLY public.livre
    ADD CONSTRAINT livre_pkey PRIMARY KEY (id);


--
-- Name: utilisateur utilisateur_email_key; Type: CONSTRAINT; Schema: public; Owner: app_mediatheque
--

ALTER TABLE ONLY public.utilisateur
    ADD CONSTRAINT utilisateur_email_key UNIQUE (email);


--
-- Name: utilisateur utilisateur_pkey; Type: CONSTRAINT; Schema: public; Owner: app_mediatheque
--

ALTER TABLE ONLY public.utilisateur
    ADD CONSTRAINT utilisateur_pkey PRIMARY KEY (id);


--
-- Name: emprunt emprunt_livre_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: app_mediatheque
--

ALTER TABLE ONLY public.emprunt
    ADD CONSTRAINT emprunt_livre_id_fkey FOREIGN KEY (livre_id) REFERENCES public.livre(id);


--
-- Name: emprunt emprunt_utilisateur_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: app_mediatheque
--

ALTER TABLE ONLY public.emprunt
    ADD CONSTRAINT emprunt_utilisateur_id_fkey FOREIGN KEY (utilisateur_id) REFERENCES public.utilisateur(id);


--
-- PostgreSQL database dump complete
--

\unrestrict Okadu3EyiUsKtkOmLqf3OzTHR3Y1GfU80PCW7yPAjhLOrzYPu3kQSi0YkKgkWDy

