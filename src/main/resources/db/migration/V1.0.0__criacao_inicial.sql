CREATE TABLE pessoa (
  pes_id bigserial PRIMARY KEY,
  pes_nome VARCHAR(200),
  pes_data_nascimento date,
  pes_sexo varchar(9),
  pes_mae varchar(200),
  pes_pai varchar(200)
);

CREATE TABLE servidor_temporario(
  pes_id int8 PRIMARY KEY,
  st_data_admissao date,
  st_data_demissao date
);

CREATE TABLE endereco(
  end_id bigserial PRIMARY KEY,
  end_tipo_logradouro varchar(50),
  end_logradouro varchar(200),
  end_numero varchar(10),
  end_bairro varchar(100),
  cid_id int8
);

CREATE TABLE pessoa_endereco(
  pes_id int8,
  end_id int8,
  PRIMARY KEY (pes_id, end_id)
);

CREATE TABLE servidor_efetivo(
  pes_id int8 PRIMARY KEY,
  se_matricula varchar(20)
);

CREATE TABLE unidade(
  unid_id bigserial PRIMARY KEY,
  unid_nome varchar(200),
  unid_sigla varchar(20)
);

CREATE TABLE unidade_endereco(
  unid_id int8,
  end_id int8,
  PRIMARY KEY (unid_id, end_id)
);

CREATE TABLE lotacao(
  lot_id bigserial PRIMARY KEY,
  pes_id int8,
  unid_id int8,
  lot_data_lotacao date,
  lot_data_remocao date,
  lot_portaria varchar(100)
);

CREATE TABLE usuario(
  user_id bigserial PRIMARY KEY,
  user_name varchar(80),
  user_login varchar(40),
  user_password varchar(250)
);

CREATE TABLE role(
  role_id bigserial PRIMARY KEY,
  role_nome varchar(40),
  role_descricao varchar(100)
);

create TABLE role_user(
  role_id int8,
  user_id int8,
  primary key(role_id, user_id)
);

create table foto_pessoa(
  fp_id bigserial,
  pes_id int8,
  fp_data date,
  fp_bucket varchar(50),
  fp_hash varchar(50)
);

create table cidade(
    cid_id bigserial primary key,
    cid_nome varchar(200),
    cid_uf varchar(2)
);

ALTER TABLE servidor_temporario ADD CONSTRAINT fk_servidor_pessoa FOREIGN KEY(pes_id) REFERENCES public.pessoa(pes_id);
ALTER TABLE servidor_efetivo ADD CONSTRAINT fk_servidor_efetivo_pessoa FOREIGN KEY(pes_id) REFERENCES public.pessoa(pes_id);
ALTER TABLE pessoa_endereco ADD CONSTRAINT fk_pessoa_endereco_pessoa FOREIGN KEY(pes_id) REFERENCES public.pessoa(pes_id);
ALTER TABLE pessoa_endereco ADD CONSTRAINT fk_pessoa_endereco_endereco FOREIGN KEY(end_id) REFERENCES public.endereco(end_id);
ALTER TABLE unidade_endereco ADD CONSTRAINT fk_unidade_endereco_endereco FOREIGN KEY(end_id) REFERENCES public.endereco(end_id);
ALTER TABLE unidade_endereco ADD CONSTRAINT fk_unidade_endereco_unidade FOREIGN KEY(unid_id) REFERENCES public.unidade(unid_id);
ALTER TABLE lotacao ADD CONSTRAINT fk_lotacao_pessoa FOREIGN KEY(pes_id) REFERENCES public.pessoa(pes_id);
ALTER TABLE lotacao ADD CONSTRAINT fk_unidade FOREIGN KEY(unid_id) REFERENCES public.unidade(unid_id);
ALTER TABLE role_user ADD CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES public.usuario(user_id);
ALTER TABLE role_user ADD CONSTRAINT fk_role FOREIGN KEY(role_id) REFERENCES public.role(role_id);
ALTER TABLE foto_pessoa ADD CONSTRAINT fk_foto_pessoa_pessoa FOREIGN KEY(pes_id) REFERENCES public.pessoa(pes_id);
ALTER TABLE endereco ADD CONSTRAINT fk_cidade FOREIGN KEY(cid_id) REFERENCES public.cidade(cid_id);