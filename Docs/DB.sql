DROP TABLE RESTAURANTE;


CREATE TABLE RESTAURANTE 
(
  NIT NUMBER NOT NULL,
  NOMBRE VARCHAR2(255 BYTE) NOT NULL,
  ESPECIALIDAD VARCHAR2(255 BYTE) NOT NULL,
  TOTALPRODUCTOSDISPONIBLES NUMBER NOT NULL,
  TIPOCOMIDA VARCHAR2(255 BYTE) NOT NULL,
  PAGINAWEBURL VARCHAR2(255 BYTE) 
);


INSERT INTO RESTAURANTE (NIT, NOMBRE, ESPECIALIDAD, TOTALPRODUCTOSDISPONIBLES, TIPOCOMIDA) VALUES ('1', 'Taco Taco', 'Tacos', '100', 'Mexicano')
INSERT INTO RESTAURANTE (NIT, NOMBRE, ESPECIALIDAD, TOTALPRODUCTOSDISPONIBLES, TIPOCOMIDA, PAGINAWEBURL) VALUES ('2', 'PPC', 'Pasta', '200', 'Variado', 'ppc.com')
INSERT INTO RESTAURANTE (NIT, NOMBRE, ESPECIALIDAD, TOTALPRODUCTOSDISPONIBLES, TIPOCOMIDA) VALUES ('3', 'El italiano', 'Pizza', '30', 'Italiana')
INSERT INTO RESTAURANTE (NIT, NOMBRE, ESPECIALIDAD, TOTALPRODUCTOSDISPONIBLES, TIPOCOMIDA, PAGINAWEBURL) VALUES ('4', 'Don Jediondo', 'Carne', '50', 'Colombiana', 'donjediondo.com')
INSERT INTO RESTAURANTE (NIT, NOMBRE, ESPECIALIDAD, TOTALPRODUCTOSDISPONIBLES, TIPOCOMIDA) VALUES ('5', 'Mr. Lee', 'Arroz chinoi', '12', 'China')


COMMIT;


