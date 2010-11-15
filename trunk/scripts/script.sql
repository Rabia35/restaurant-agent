-- Como root:
-- mysql -u root -p
-- poner su password

create database restaurantagent;

create user restaurantagent identified by 'restaurantagent'; -- Este es el password del usuario

grant all on restaurantagent.* to restaurantagent;

-- Cambiarse después a restaurantagent
-- mysql -u restaurantagent -p
-- poner el password

create table ingrediente (
   clave char(5) not null primary key,
   nombre varchar(30),
   peso int,
   costo int,
   refrigerado int,
   caducidad int,       -- Por definir se dará en segundos, minutos, horas, dias, etc
   cantidadPorPaquete int);

create table estante (
   posicionX int not null,
   posicionY int not null,
   altura int not null,
   refrigerador int,
   ingrediente char(5),
   fechaColocado datetime,
   cantidad int,
   primary key(posicionX, posicionY, altura));

create table receta (
   clave char(5) not null primary key,
   nombre varchar(30),
   ingrediente1 char(5),
   cantidad1 int,
   ingrediente2 char(5),
   cantidad2 int,
   ingrediente3 char(5),
   cantidad3 int,
   ingrediente4 char(5),
   cantidad4 int);

create table menu(
   receta char(5));

create table peticion(
   ingrediente char(5) not null primary key,
   hora datetime);

create table pedido(
   clave int not null auto_increment primary key,
   receta char(5),
   tiempoPedido datetime);

