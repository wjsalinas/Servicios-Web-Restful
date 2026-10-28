/*
DROP TABLE IF EXISTS centro_evaluacion;
DROP TABLE IF EXISTS centro_medico;
DROP TABLE IF EXISTS escuela_conductores;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS persona;
DROP TABLE IF EXISTS rol;
DROP TABLE IF EXISTS distrito;
DROP TABLE IF EXISTS provincia;
DROP TABLE IF EXISTS departamento;
*/

/* =====================================================================
    Gestión de usuarios
===================================================================== */
IF NOT EXISTS (SELECT * FROM sysobjects WHERE NAME='rol' AND xtype='U')
CREATE TABLE rol
(
    id_rol      INT IDENTITY(1,1) PRIMARY KEY,
    nombre      VARCHAR(30) NOT NULL,
    descripcion VARCHAR(120) NULL,
    estado      INT         NOT NULL DEFAULT 1,
    CONSTRAINT uq_rol_nombre UNIQUE (nombre)
);

IF NOT EXISTS (SELECT * FROM sysobjects WHERE NAME='persona' AND xtype='U')
CREATE TABLE persona
(
    id_persona       INT IDENTITY(1,1) PRIMARY KEY,
    tipo_documento   VARCHAR(10)  NOT NULL DEFAULT 'CI',
    numero_documento VARCHAR(15)  NOT NULL,
    nombres          VARCHAR(100) NOT NULL,
    apellidos        VARCHAR(100) NOT NULL,
    correo           VARCHAR(120) NULL,
    telefono         VARCHAR(20) NULL,
    fecha_registro   DATETIME2    NOT NULL DEFAULT SYSDATETIME(),
    estado           INT          NOT NULL DEFAULT 1,
    CONSTRAINT uq_persona_documento UNIQUE (tipo_documento, numero_documento)
);

IF NOT EXISTS (SELECT * FROM sysobjects WHERE NAME='usuario' AND xtype='U')
CREATE TABLE usuario
(
    id_usuario     INT IDENTITY(1,1) PRIMARY KEY,
    id_persona     INT          NOT NULL,
    id_rol         INT          NOT NULL,
    usuario        VARCHAR(50)  NOT NULL,
    clave          VARCHAR(128) NOT NULL,
    salt           VARCHAR(64)  NOT NULL,
    ultimo_acceso  DATETIME2 NULL,
    fecha_registro DATETIME2    NOT NULL DEFAULT SYSDATETIME(),
    estado         INT          NOT NULL DEFAULT 1,
    CONSTRAINT uq_usuario_usuario UNIQUE (usuario),
    CONSTRAINT uq_usuario_persona UNIQUE (id_persona),
    CONSTRAINT fk_usuario_persona FOREIGN KEY (id_persona) REFERENCES persona (id_persona),
    CONSTRAINT fk_usuario_rol FOREIGN KEY (id_rol) REFERENCES rol (id_rol)
);

/* =====================================================================
    Gestión de conductores, centros médicos y de evaluación
   ===================================================================== */

IF NOT EXISTS (SELECT * FROM sysobjects WHERE NAME='departamento' AND xtype='U')
CREATE TABLE departamento
(
    id_departamento INT IDENTITY(1,1) PRIMARY KEY,
    nombre          VARCHAR(100) NOT NULL,
    estado          INT          NOT NULL DEFAULT 1,
    CONSTRAINT uq_departamento_nombre UNIQUE (nombre)
);

IF NOT EXISTS (SELECT * FROM sysobjects WHERE NAME='provincia' AND xtype='U')
CREATE TABLE provincia
(
    id_provincia    INT IDENTITY(1,1) PRIMARY KEY,
    id_departamento INT          NOT NULL,
    nombre          VARCHAR(100) NOT NULL,
    estado          INT          NOT NULL DEFAULT 1,
    CONSTRAINT fk_provincia_departamento
        FOREIGN KEY (id_departamento) REFERENCES departamento (id_departamento),
    CONSTRAINT uq_provincia UNIQUE (id_departamento, nombre)
);

IF NOT EXISTS (SELECT * FROM sysobjects WHERE NAME='distrito' AND xtype='U')
CREATE TABLE distrito
(
    id_distrito  INT IDENTITY(1,1) PRIMARY KEY,
    id_provincia INT         NOT NULL,
    nombre       VARCHAR(60) NOT NULL,
    estado       INT         NOT NULL DEFAULT 1,
    CONSTRAINT fk_distrito_provincia
        FOREIGN KEY (id_provincia) REFERENCES provincia (id_provincia),
    CONSTRAINT uq_distrito UNIQUE (id_provincia, nombre)
);

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'ix_departamento_nombre' AND object_id = OBJECT_ID('departamento'))
CREATE INDEX ix_departamento_nombre ON departamento (nombre);


IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'ix_provincia_nombre' AND object_id = OBJECT_ID('provincia'))
CREATE INDEX ix_provincia_nombre ON provincia (nombre);


IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'ix_distrito_nombre' AND object_id = OBJECT_ID('distrito'))
CREATE INDEX ix_distrito_nombre ON distrito (nombre);


IF NOT EXISTS (SELECT * FROM sysobjects WHERE NAME='escuela_conductores' AND xtype='U')
CREATE TABLE escuela_conductores
(
    id_escuela     INT IDENTITY(1,1) PRIMARY KEY,
    id_distrito    INT          NOT NULL,
    ruc            CHAR(13)     NOT NULL,
    nombre         VARCHAR(200) NOT NULL,
    direccion      VARCHAR(250) NOT NULL,
    autorizacion   CHAR(1)      NOT NULL DEFAULT 'C',
    fecha_registro DATETIME2    NOT NULL DEFAULT SYSDATETIME(),
    estado         INT          NOT NULL DEFAULT 1,
    CONSTRAINT uq_escuela_distrito_ruc UNIQUE (id_distrito, ruc),
    CONSTRAINT fk_escuela_distrito FOREIGN KEY (id_distrito) REFERENCES distrito (id_distrito)
);

IF NOT EXISTS (SELECT * FROM sysobjects WHERE NAME='centro_medico' AND xtype='U')
CREATE TABLE centro_medico (
    id_centro_medico INT IDENTITY(1,1) PRIMARY KEY,
    id_distrito      INT          NOT NULL,
    ruc              CHAR(13)     NOT NULL,
    nombre           VARCHAR(200) NOT NULL,
    direccion        VARCHAR(250) NOT NULL,
    autorizacion     CHAR(1)      NOT NULL DEFAULT 'C',
    fecha_registro   DATETIME2    NOT NULL DEFAULT SYSDATETIME(),
    estado           INT          NOT NULL DEFAULT 1,
    CONSTRAINT uq_centro_medico_ruc UNIQUE (id_distrito, ruc),
    CONSTRAINT fk_centro_medico_distrito FOREIGN KEY (id_distrito) REFERENCES distrito (id_distrito)
);

IF NOT EXISTS (SELECT * FROM sysobjects WHERE NAME='centro_evaluacion' AND xtype='U')
CREATE TABLE centro_evaluacion (
        id_centro_evaluacion INT IDENTITY(1,1) PRIMARY KEY,
        id_distrito      INT          NOT NULL,
        ruc              CHAR(13)     NOT NULL,
        nombre           VARCHAR(200) NOT NULL,
        direccion        VARCHAR(250) NOT NULL,
        autorizacion     CHAR(1)      NOT NULL DEFAULT 'C',
        fecha_registro   DATETIME2    NOT NULL DEFAULT SYSDATETIME(),
        estado           INT          NOT NULL DEFAULT 1,
        CONSTRAINT uq_centro_evaluacion_ruc UNIQUE (id_distrito, ruc),
        CONSTRAINT fk_centro_evaluacion_distrito FOREIGN KEY (id_distrito) REFERENCES distrito (id_distrito)
);