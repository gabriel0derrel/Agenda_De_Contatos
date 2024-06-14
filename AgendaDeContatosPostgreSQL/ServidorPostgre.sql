--
-- PostgreSQL database cluster dump
--

-- Started on 2024-06-10 12:35:05 -03

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Roles
--

CREATE ROLE aluno;
ALTER ROLE aluno WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:HgTYwjyegsf9Cuiumb04og==$aWjA9DFbJdiq0/xqAyHCMJ8AG07NXjZe+KNfhhLtJ4M=:8mgZlug7dIIiPKbRq6tGWc/OSNbNTnbSR6O4wRgFsKk=';
CREATE ROLE postgres;
ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS;






--
-- Databases
--

--
-- Database "template1" dump
--

\connect template1

--
-- PostgreSQL database dump
--

-- Dumped from database version 14.12 (Ubuntu 14.12-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.12 (Ubuntu 14.12-0ubuntu0.22.04.1)

-- Started on 2024-06-10 12:35:05 -03

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

-- Completed on 2024-06-10 12:35:06 -03

--
-- PostgreSQL database dump complete
--

--
-- Database "ContatosDB" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 14.12 (Ubuntu 14.12-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.12 (Ubuntu 14.12-0ubuntu0.22.04.1)

-- Started on 2024-06-10 12:35:06 -03

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
-- TOC entry 3348 (class 1262 OID 16385)
-- Name: ContatosDB; Type: DATABASE; Schema: -; Owner: aluno
--

CREATE DATABASE "ContatosDB" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'pt_BR.UTF-8';


ALTER DATABASE "ContatosDB" OWNER TO aluno;

\connect "ContatosDB"

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

-- Completed on 2024-06-10 12:35:06 -03

--
-- PostgreSQL database dump complete
--

--
-- Database "postgres" dump
--

\connect postgres

--
-- PostgreSQL database dump
--

-- Dumped from database version 14.12 (Ubuntu 14.12-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.12 (Ubuntu 14.12-0ubuntu0.22.04.1)

-- Started on 2024-06-10 12:35:06 -03

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

-- Completed on 2024-06-10 12:35:07 -03

--
-- PostgreSQL database dump complete
--

-- Completed on 2024-06-10 12:35:07 -03

--
-- PostgreSQL database cluster dump complete
--

