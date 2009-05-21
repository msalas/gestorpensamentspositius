-- Database: gpp

-- DROP DATABASE gpp;
-- LA DB s'ha de crear primer, i dp un cop connectats a aquesta crear les taules.
CREATE DATABASE gpp
  WITH OWNER = postgres
       ENCODING = 'UTF8';


-- Table: usuari_grup

-- DROP TABLE usuari_grup;

CREATE TABLE usuari_grup
(
  id serial NOT NULL,
  nom character varying(100) NOT NULL,
  descripcio character varying(150),
  CONSTRAINT usuari_grup_pkey PRIMARY KEY (id)
)
WITH (OIDS=FALSE);
ALTER TABLE usuari_grup OWNER TO postgres;


-- Table: usuari

-- DROP TABLE usuari;

CREATE TABLE usuari
(
  id serial NOT NULL,
  nom_usuari character varying(50) NOT NULL,
  contrassenya character varying(100) NOT NULL,
  nom character varying(50),
  cognoms character varying(100),
  email text,
  edat integer,
  tipus integer NOT NULL,
  CONSTRAINT usuari_pkey PRIMARY KEY (id),
  CONSTRAINT usuari_tipus_fkey FOREIGN KEY (tipus)
      REFERENCES usuari_grup (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (OIDS=FALSE);
ALTER TABLE usuari OWNER TO postgres;

-- DROP TABLE pensament_comentari;

CREATE TABLE pensament_comentari
(
  id serial NOT NULL,
  autor integer NOT NULL,
  "text" character varying(500),
  CONSTRAINT pensament_comentari_pkey PRIMARY KEY (id),
  CONSTRAINT pensament_comentari_autor_fkey FOREIGN KEY (autor)
      REFERENCES usuari (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (OIDS=FALSE);
ALTER TABLE pensament_comentari OWNER TO postgres;

-- Table: pensament

-- DROP TABLE pensament;

CREATE TABLE pensament
(
  id serial NOT NULL,
  titol character varying(150) NOT NULL,
  descripcio character varying(500) NOT NULL,
  autor integer NOT NULL,
  comentari integer,
  estat integer NOT NULL,
  data_creacio timestamp with time zone DEFAULT now(),
  data_publicacio timestamp with time zone,
  data_modificacio timestamp with time zone,
  vots integer NOT NULL DEFAULT 0,
  CONSTRAINT pensament_pkey PRIMARY KEY (id),
  CONSTRAINT pensament_autor_fkey FOREIGN KEY (autor)
      REFERENCES usuari (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT pensament_comentari_fkey FOREIGN KEY (comentari)
      REFERENCES pensament_comentari (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (OIDS=FALSE);
ALTER TABLE pensament OWNER TO postgres;


-- Table: pensament_vot

-- DROP TABLE pensament_vot;

CREATE TABLE pensament_vot
(
  id serial NOT NULL,
  votant integer,
  pensament integer NOT NULL,
  puntuacio integer NOT NULL DEFAULT 1,
  data_votacio timestamp with time zone DEFAULT now(),
  CONSTRAINT pensament_vot_pkey PRIMARY KEY (id),
  CONSTRAINT pensament_vot_pensament_fkey FOREIGN KEY (pensament)
      REFERENCES pensament (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT pensament_vot_votant_fkey FOREIGN KEY (votant)
      REFERENCES usuari (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (OIDS=FALSE);
ALTER TABLE pensament_vot OWNER TO postgres;


-- CREACIO DE GRUPS D'USUARIS
insert into usuari_grup(nom,descripcio) values('REGISTRAT','Usuari registrat al sistema');
insert into usuari_grup(nom,descripcio) values('MODERADOR','Moderador del sistema');


-- CREACIO DE MODERADORS DE PROVA
INSERT INTO usuari(nom_usuari, contrassenya, nom, cognoms, email, edat, tipus)
    VALUES ('auguste','test1234','Auguste','Comte','auguste.comte@filosof.com', NULL, 2);

INSERT INTO usuari(nom_usuari, contrassenya, nom, cognoms, email, edat, tipus)
    VALUES ('david','test1234','David','Hume','david.hume@filosof.com', NULL, 2);

INSERT INTO usuari(nom_usuari, contrassenya, nom, cognoms, email, edat, tipus)
    VALUES ('immanuel','test1234','Immanuel','Kant','immanuel.kant@filosof.com', NULL, 2);

-- CREACIO D'USUARIS REGISTRATS DE PROVA
INSERT INTO usuari(nom_usuari, contrassenya, nom, cognoms, email, edat, tipus)
    VALUES ('soclacanya','test1234','Manel','Canut','manel.canut@eureka.com',NULL, 1);

INSERT INTO usuari(nom_usuari, contrassenya, nom, cognoms, email, edat, tipus)
    VALUES ('socundesastre','test1234','Pepe','Velez','pepe.velez@eureka.com',NULL, 1);

INSERT INTO usuari(nom_usuari, contrassenya, nom, cognoms, email, edat, tipus)
    VALUES ('socfelis','test1234','Rodamón','Espardenya','roda.enya@eureka.com',28, 1);


-- CREACIO DE PENSAMENTS
INSERT INTO pensament(titol, descripcio, autor, estat) VALUES ('Fa bon dia','Avui fa un dia esplèndid. Fa molt de sol. És un dia alegre!',4,1);
INSERT INTO pensament(titol, descripcio, autor, estat) VALUES ('Fa mal dia','Avui fa un dia horroros!',4,3);
INSERT INTO pensament(titol, descripcio, autor, estat) VALUES ('Salut és vida!','M''encanta fer esport, i cuidar-me. ',5,1);
INSERT INTO pensament(titol, descripcio, autor, estat) VALUES ('Font Vella','Aigua natural Font Vella, la millor.',5,2);
INSERT INTO pensament(titol, descripcio, autor, estat) VALUES ('Què bonic tenir amics!','M''agrada passar l''estona amb els meus amics. I a tu? :)',6,1);
INSERT INTO pensament(titol, descripcio, autor, estat) VALUES ('Nyam nyam!','M''encanta menjar bé i bon menjar! Com la cuina catalana no hi ha res!',6,1);



CREATE TRUSTED PROCEDURAL LANGUAGE 'plpgsql'
  HANDLER plpgsql_call_handler
  VALIDATOR plpgsql_validator;

CREATE OR REPLACE FUNCTION actualitza_vots()
  RETURNS "trigger" AS 
$BODY$
BEGIN
        
        UPDATE pensament SET vots=vots+NEW.puntuacio WHERE id = NEW.pensament;

        RETURN NEW;
END;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE;
  
  
-- DROP TRIGGER votacio ON pensament_vot;

CREATE TRIGGER votacio
  AFTER INSERT
  ON pensament_vot
  FOR EACH ROW
  EXECUTE PROCEDURE actualitza_vots();