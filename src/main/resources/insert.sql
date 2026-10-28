/* =====================================================================
   Insert
   ===================================================================== */
INSERT INTO rol (nombre, descripcion)
VALUES ('ADMIN', 'Administrador del sistema'),
       ('OPERADOR', 'Registra y actualiza establecimientos');

INSERT INTO departamento (nombre)
VALUES ('AMAZONAS'),
       ('ANCASH'),
       ('APURIMAC'),
       ('AREQUIPA'),
       ('AYACUCHO'),
       ('CAJAMARCA'),
       ('CALLAO'),
       ('CUSCO'),
       ('HUANCAVELICA'),
       ('HUANUCO'),
       ('ICA'),
       ('JUNIN'),
       ('LA LIBERTAD'),
       ('LAMBAYEQUE'),
       ('LIMA'),
       ('LORETO'),
       ('MADRE DE DIOS'),
       ('MOQUEGUA'),
       ('PASCO'),
       ('PIURA'),
       ('PUNO'),
       ('SAN MARTIN'),
       ('TACNA'),
       ('TUMBES'),
       ('UCAYALI');


----------Provincias
INSERT INTO provincia (id_departamento, nombre)
SELECT id_departamento, 'CHACHAPOYAS' FROM departamento WHERE nombre = 'AMAZONAS';

INSERT INTO provincia (id_departamento, nombre)
SELECT id_departamento, 'UTCUBAMBA' FROM departamento WHERE nombre = 'AMAZONAS';

INSERT INTO provincia (id_departamento, nombre)
SELECT id_departamento, 'HUARAZ' FROM departamento WHERE nombre = 'ANCASH';

INSERT INTO provincia (id_departamento, nombre)
SELECT id_departamento, 'SANTA' FROM departamento WHERE nombre = 'ANCASH';

INSERT INTO provincia (id_departamento, nombre)
SELECT id_departamento, 'ABANCAY' FROM departamento WHERE nombre = 'APURIMAC';

INSERT INTO provincia (id_departamento, nombre)
SELECT id_departamento, 'ANDAHUAYLAS' FROM departamento WHERE nombre = 'APURIMAC';

INSERT INTO provincia (id_departamento, nombre)
SELECT id_departamento, 'AREQUIPA' FROM departamento WHERE nombre = 'AREQUIPA';

INSERT INTO provincia (id_departamento, nombre)
SELECT id_departamento, 'HUAMANGA' FROM departamento WHERE nombre = 'AYACUCHO';

INSERT INTO provincia (id_departamento, nombre)
SELECT id_departamento, 'HUANTA' FROM departamento WHERE nombre = 'AYACUCHO';


----------Districto
INSERT INTO distrito (id_provincia, nombre)
SELECT id_provincia, 'CHACHAPOYAS' FROM provincia WHERE nombre = 'CHACHAPOYAS';

INSERT INTO distrito (id_provincia, nombre)
SELECT id_provincia, 'BAGUA GRANDE' FROM provincia WHERE nombre = 'UTCUBAMBA';

INSERT INTO distrito (id_provincia, nombre)
SELECT id_provincia, 'HUARAZ' FROM provincia WHERE nombre = 'HUARAZ';

INSERT INTO distrito (id_provincia, nombre)
SELECT id_provincia, 'NUEVO CHIMBOTE' FROM provincia WHERE nombre = 'SANTA';

INSERT INTO distrito (id_provincia, nombre)
SELECT id_provincia, 'ABANCAY' FROM provincia WHERE nombre = 'ABANCAY';

INSERT INTO distrito (id_provincia, nombre)
SELECT id_provincia, 'ANDAHUAYLAS' FROM provincia WHERE nombre = 'ANDAHUAYLAS';

INSERT INTO distrito (id_provincia, nombre)
SELECT id_provincia, 'TALAVERA' FROM provincia WHERE nombre = 'ANDAHUAYLAS';

INSERT INTO distrito (id_provincia, nombre)
SELECT id_provincia, 'PAUCARPATA' FROM provincia WHERE nombre = 'AREQUIPA';

INSERT INTO distrito (id_provincia, nombre)
SELECT id_provincia, 'JESUS NAZARENO' FROM provincia WHERE nombre = 'HUAMANGA';

INSERT INTO distrito (id_provincia, nombre)
SELECT id_provincia, 'HUANTA' FROM provincia WHERE nombre = 'HUANTA';

INSERT INTO distrito (id_provincia, nombre)
SELECT id_provincia, 'LLOCHEGUA' FROM provincia WHERE nombre = 'HUANTA';


----------Escuela de conductores
INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20604309833', 'CENTRO MÉDICO AMAZONAS E.I.R.L.', 'JR. GRAU N° 205, 1ER. PISO' FROM distrito WHERE nombre = 'CHACHAPOYAS';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20613258346', 'MANEJANDO PERU E.I.R.L.', 'JR. LIBERTAD S/N (CUADRA 01), PRIMER PISO' FROM distrito WHERE nombre = 'CHACHAPOYAS';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20479461822', 'SERVICIOS GENERALES JP CAR S.R.L.', 'JR. CIRCUNV. N.º 612, LT. 1J, MZ. V38, URB. VISALOT, 1ER P.' FROM distrito WHERE nombre = 'BAGUA GRANDE';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20477238336', 'ASOCIACION GUIA MIS RUTAS', 'AV.PEDRO PABLO ATUSPARIA / JR.GABINO URIBE S/N., MZ. 8 LT. 13, BARRIO PEDREGAL MEDIO,1ER. Y 2DO. PISO' FROM distrito WHERE nombre = 'HUARAZ';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20607082902', 'AUTO LATINO E.I.R.L.', 'AV. CONFRATERNIDAD INTERNACIONAL SUR N° 244 PRIMER PISO VILLON ALTO' FROM distrito WHERE nombre = 'HUARAZ';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20482764151', 'VIAS SEGURAS S.A.C.', 'AV. CONFRATERNIDAD INTERNACIONAL SUR MZ. 176-B, LT.24, 1ER PISO BARRIO VILLON ALTO' FROM distrito WHERE nombre = 'HUARAZ';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20609522365', 'GARO CAR E.I.R.L.', 'URB. BRUCES MZ. B LT 5, 2DO PISO' FROM distrito WHERE nombre = 'NUEVO CHIMBOTE';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20609373092', 'GLOBAL CAR CHIMBOTE S.A.C.', 'MZ. A LT. 46 URB. BRUCES –1ER PISO' FROM distrito WHERE nombre = 'NUEVO CHIMBOTE';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20490899023', 'ESCUELA DE CONDUCTORES MI BREVET SOCIEDAD ANONIMA CERRADA - ECOMIBRE S.A.C.', 'AV. MARIÑO N° 117, 1ER Y 2DO PISO' FROM distrito WHERE nombre = 'ABANCAY';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20563859246', 'ESCUELA DE CONDUCTORES SEÑOR DE LOS MILAGROS E.I.R.L.', 'Lote 10, Mz. A de la Asoc. Proviv. Luis Alberto Sanchez, Barrio de Patibamba' FROM distrito WHERE nombre = 'ANDAHUAYLAS';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20608607430', 'ESCUELA DE CONDUCTORES PRADERA DE LOS CELAJES E.I.R.L.', 'JR. LEONIDAS BERROCAL N° 110' FROM distrito WHERE nombre = 'TALAVERA';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20495109004', 'SAN CRISTOBAL DEL PERU S.A.C.', 'JR. LEÓNIDAS BERROCAL 145' FROM distrito WHERE nombre = 'TALAVERA';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20311650751', 'CETPRO PEDRO RUIZ GALLO S.R.L.', 'URB. CESAR VALLEJO MZ. C, LT. 14-A' FROM distrito WHERE nombre = 'PAUCARPATA';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20608763733', 'ESCUELA DE CONDUCTORES EDUCACION VIAL SAC', 'MZ. B LT. 13, 1ER. Y 2DO. PISO, FUNDO KENNEDY' FROM distrito WHERE nombre = 'PAUCARPATA';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20605825673', 'ESCUELA DE CONDUCTORES INTEGRALES BREVETES ELVIS S.A.C.', 'AV. GUARDIA CIVIL 2DA FASE, III ETAPA, MZ. B, LT.13 (3ER PISO)' FROM distrito WHERE nombre = 'PAUCARPATA';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20611959789', 'ESCUELA DE CONDUCTORES INTEGRALES MANEJO AVANZADO Y SEGURO AQP E.I.R.L.', 'C. R. PALMA MZ. B LT. 10 PISO 1. URB. G. CIVIL, ETAPA 3' FROM distrito WHERE nombre = 'PAUCARPATA';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20455807841', 'ESCUELA DE CONDUCTORES INTEGRALES TRANSITO LIBRE S.A.C.', 'Av. Jhon F. Kennedy 1410, 3er Piso, Urb. Jesus Maria' FROM distrito WHERE nombre = 'PAUCARPATA';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20529391430', 'ESCUELA PERUANA DE CONDUCTORES INTEGRALES RUTAS SEGURAS S.A.C', 'ASOCIACIÓN CENTRO INDUSTRIAL LAS CANTERAS, MZ. O, LOTE 1' FROM distrito WHERE nombre = 'PAUCARPATA';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20601235928', 'FERMAT SERVICIOS GENERALES S.A.C.', 'PUEBLO JOVEN CÉSAR VALLEJO, MZ. D LOTE 31, 3ER. Y 4TO. PISO' FROM distrito WHERE nombre = 'PAUCARPATA';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20455114561', 'GITOCA S.R.L.', 'PUEBLO JOVEN CESAR VALLEJO MZ. D LT. 13 PARTE DEL 1ER Y 2DO PISO' FROM distrito WHERE nombre = 'PAUCARPATA';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20498476539', 'SGM INGENIEROS E.I.R.L.', 'Av. John F. Kennedy N° 1410, 3er Piso, Urb. Jesus Maria' FROM distrito WHERE nombre = 'PAUCARPATA';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20611725982', 'T-CONDUCE VIRGEN DE CHAPI E.I.R.L.', 'C. R. PALMA MZ. B, LT. 5, URB. G. CIVIL, ETAPA 3' FROM distrito WHERE nombre = 'PAUCARPATA';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20612379557', 'CORPORACION BREVETES PERU VIP S.A.C.', 'JR. MARIANO MELGAR N°445, 1ER P, URB. LAS NAZARENAS' FROM distrito WHERE nombre = 'JESUS NAZARENO';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20607478067', 'ESCUELA DE CONDUCTORES INTEGRALES CONDUCE AYACUCHO S.A.C.', 'JR. MARIANO MELGAR N° 451, URB. LAS NAZARENAS' FROM distrito WHERE nombre = 'JESUS NAZARENO';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20534589639', 'ESCUELA DE CONDUCTORES INTEGRALES SOCIEDAD ANONIMA CERRADA BREVETE AYACUCHO S.A.C.', 'JR. MARIANO MELGAR 475' FROM distrito WHERE nombre = 'JESUS NAZARENO';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20534789565', 'INSTITUTO DE INVESTIGACION VIAL CLUB AUTOMOVIL PERU S.A.C. - INSTIVI CLUB AUTOMOVIL PERU S.A.C.', 'Jr. Mariano Melgar N° 403, 3er Piso, Urb. Las Nazarenas' FROM distrito WHERE nombre = 'JESUS NAZARENO';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20604034893', 'MI BREVETE SEGURO S.A.C.', 'JR. MARIANO MELGAR N° 456, URB. SIMÓN BOLÍVAR' FROM distrito WHERE nombre = 'JESUS NAZARENO';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20609578140', 'INSTITUTO DRIVE CAR MI PERU J & B S.A.C', 'JIRÓN GONZALES VIGIL N° 222' FROM distrito WHERE nombre = 'HUANTA';

INSERT INTO escuela_conductores (id_distrito, ruc, nombre, direccion)
SELECT id_distrito, '20609578140', 'INSTITUTO DRIVE CAR MI PERU J & B S.A.C', 'JR. CHOYMACOTA MZ. L, LOTE 11' FROM distrito WHERE nombre = 'LLOCHEGUA';