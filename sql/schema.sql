-- Table: user_

-- DROP TABLE user_;

CREATE TABLE user_
(
  uuid character varying(255) NOT NULL,
  created_date timestamp with time zone NOT NULL,
  birthdate bigint,
  children integer,
  city character varying(255),
  country character varying(255),
  district integer,
  education integer,
  email character varying(255) NOT NULL,
  height integer,
  marriage integer,
  name character varying(255),
  phone character varying(255),
  pushid character varying(255),
  religion integer,
  sex integer,
  weight integer,
  countrycode character varying(255),
  "countryCode" character varying(255),
  latitude double precision,
  longitude double precision,
  profile_questions text,
  is_banned boolean NOT NULL DEFAULT false,
  locale character varying(255),
  is_online boolean DEFAULT false,
  last_online_date timestamp without time zone,
  subscription boolean NOT NULL DEFAULT false,
  phone_uuid character varying(512),
  google_uuid character varying(1024),
  password character varying(255),
  telegram_id character varying(255),
  CONSTRAINT user__pkey PRIMARY KEY (uuid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_
  OWNER TO dating;

-- Index: email_unic_index

-- DROP INDEX email_unic_index;

CREATE UNIQUE INDEX email_unic_index
  ON user_
  USING btree
  (email COLLATE pg_catalog."default");

-- Index: uuid_unic_index

-- DROP INDEX uuid_unic_index;

CREATE UNIQUE INDEX uuid_unic_index
  ON user_
  USING btree
  (uuid COLLATE pg_catalog."default");





-- Table: telegram_user

-- DROP TABLE telegram_user;

CREATE TABLE telegram_user
(
  uuid character varying(255) NOT NULL,
  created_date timestamp with time zone NOT NULL,
  telegram_id character varying(255),
  city character varying(255),
  first_name character varying(255),
  last_name character varying(255),
  latitude double precision,
  longitude double precision,
  access_hash bigint,
  CONSTRAINT user_pkey PRIMARY KEY (uuid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE telegram_user
  OWNER TO dating;

-- Index: telegram_id_unic_index

-- DROP INDEX telegram_id_unic_index;

CREATE UNIQUE INDEX telegram_id_unic_index
  ON telegram_user
  USING btree
  (telegram_id COLLATE pg_catalog."default");





-- Table: treba

-- DROP TABLE treba;

CREATE TABLE treba
(
  uuid character varying(255) NOT NULL,
  created_date timestamp with time zone NOT NULL,
  owner_uuid character varying(255) NOT NULL,
  names text NOT NULL,
  type character varying(255) NOT NULL,
  priest_uuid character varying(255),
  status character varying(255),
  CONSTRAINT treba_pkey PRIMARY KEY (uuid),
  CONSTRAINT priest_fk FOREIGN KEY (priest_uuid)
      REFERENCES user_ (uuid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_id_fk FOREIGN KEY (owner_uuid)
      REFERENCES telegram_user (uuid) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE treba
  OWNER TO dating;




  -- Table: purchase

-- DROP TABLE purchase;

CREATE TABLE purchase
(
  uuid character varying(255) NOT NULL,
  created_date timestamp with time zone NOT NULL,
  telegram_user_uuid character varying(255) NOT NULL,
  sku character varying(255) NOT NULL,
  order_id character varying(1024),
  CONSTRAINT purchase_pkey PRIMARY KEY (uuid),
  CONSTRAINT fk_owener_telegram_user FOREIGN KEY (telegram_user_uuid)
      REFERENCES telegram_user (uuid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE purchase
  OWNER TO dating;


-- Table: news

-- DROP TABLE news;

CREATE TABLE news
(
  uuid character varying(255) NOT NULL,
  created_date timestamp with time zone NOT NULL,
  text text NOT NULL,
  user_type character varying(255) DEFAULT 'RUSSIANS'::character varying,
  CONSTRAINT news_pkey PRIMARY KEY (uuid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE news
  OWNER TO dating;


  -- Table: messages

-- DROP TABLE messages;

CREATE TABLE messages
(
  uuid character varying(512) NOT NULL,
  from_user_uuid character varying(256) NOT NULL,
  to_user_uuid character varying(256),
  text text NOT NULL,
  chat_room character varying(128),
  created_date timestamp with time zone NOT NULL,
  CONSTRAINT messages_pkey PRIMARY KEY (uuid),
  CONSTRAINT messages_from_user__uuid_fk FOREIGN KEY (from_user_uuid)
      REFERENCES user_ (uuid) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT messages_to_user__uuid_fk FOREIGN KEY (to_user_uuid)
      REFERENCES user_ (uuid) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE messages
  OWNER TO dating;



-- Table: avatar

-- DROP TABLE avatar;

CREATE TABLE avatar
(
  uuid character varying(255) NOT NULL,
  created_date timestamp with time zone NOT NULL,
  user_uuid character varying(255),
  is_main boolean NOT NULL DEFAULT false,
  type character varying(125) NOT NULL DEFAULT 'COMMON'::character varying,
  CONSTRAINT avatar_pkey PRIMARY KEY (uuid),
  CONSTRAINT user_id_fk FOREIGN KEY (user_uuid)
      REFERENCES user_ (uuid) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE avatar
  OWNER TO dating;


  -- Table: avatar_file

-- DROP TABLE avatar_file;

CREATE TABLE avatar_file
(
  file_data bytea NOT NULL,
  created_date timestamp with time zone NOT NULL DEFAULT now(),
  avatar_uuid character varying(255) NOT NULL,
  uuid character varying(255) NOT NULL,
  CONSTRAINT avatar_fk FOREIGN KEY (avatar_uuid)
      REFERENCES avatar (uuid) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE avatar_file
  OWNER TO dating;

-- Index: avatar_files_uindex

-- DROP INDEX avatar_files_uindex;

CREATE UNIQUE INDEX avatar_files_uindex
  ON avatar_file
  USING btree
  (avatar_uuid COLLATE pg_catalog."default");




