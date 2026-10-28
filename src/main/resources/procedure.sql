if
exists (select * from dbo.sysobjects WHERE id = object_id(N'sp_buscar_escuela_conductores') AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
drop procedure sp_buscar_escuela_conductores
    GO
CREATE PROCEDURE sp_buscar_escuela_conductores @idDepartamento INT = NULL,
    @idProvincia INT = NULL,
    @idDistrito INT = NULL,
    @nombreDepartamento VARCHAR (60) = NULL,
    @nombreProvincia VARCHAR (60) = NULL,
    @nombreDistrito VARCHAR (60) = NULL,
    @nombreEstablecimiento VARCHAR (200) = NULL
    AS
BEGIN
    SET
NOCOUNT ON;

SELECT e.*
FROM escuela_conductores e
         INNER JOIN distrito di ON di.id_distrito = e.id_distrito
         INNER JOIN provincia p ON p.id_provincia = di.id_provincia
         INNER JOIN departamento d ON d.id_departamento = p.id_departamento
WHERE e.estado = 1
  AND (@idDepartamento IS NULL OR d.id_departamento = @idDepartamento)
  AND (@idProvincia IS NULL OR p.id_provincia = @idProvincia)
  AND (@idDistrito IS NULL OR di.id_distrito = @idDistrito)
  AND (@nombreDepartamento IS NULL OR d.nombre LIKE '%' + @nombreDepartamento + '%')
  AND (@nombreProvincia IS NULL OR p.nombre LIKE '%' + @nombreProvincia + '%')
  AND (@nombreDistrito IS NULL OR di.nombre LIKE '%' + @nombreDistrito + '%')
  AND (@nombreEstablecimiento IS NULL OR e.nombre LIKE '%' + @nombreEstablecimiento + '%')
ORDER BY d.nombre, p.nombre, di.nombre, e.nombre;
END
GO

if exists (select * from dbo.sysobjects WHERE id = object_id(N'sp_buscar_rapido_escuela_conductores') AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
drop procedure sp_buscar_rapido_escuela_conductores
    GO
CREATE PROCEDURE sp_buscar_rapido_escuela_conductores @termino VARCHAR(200) = NULL
AS
BEGIN
    SET
NOCOUNT ON;
    DECLARE
@t VARCHAR(200) = NULLIF(LTRIM(RTRIM(@termino)), '');
SELECT e.*
FROM escuela_conductores e
         INNER JOIN distrito di ON di.id_distrito = e.id_distrito
         INNER JOIN provincia p ON p.id_provincia = di.id_provincia
         INNER JOIN departamento d ON d.id_departamento = p.id_departamento
WHERE e.estado = 1
  AND (
    @t IS NULL
        OR e.nombre LIKE '%' + @t + '%'
        OR di.nombre LIKE '%' + @t + '%'
        OR p.nombre LIKE '%' + @t + '%'
        OR d.nombre LIKE '%' + @t + '%'
    )
ORDER BY d.nombre, p.nombre, di.nombre, e.nombre;
END
GO