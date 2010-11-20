update estante set ingrediente = null, fechaColocado=null, cantidad=0;
delete from menu;

insert into menu (receta) values("splng");
insert into menu (receta) values("salad");
insert into menu (receta) values("arroz");

update estante set ingrediente="jitom", cantidad=15, fechaColocado = now() where posicionX= 1 and posicionY=1 and altura=2;
update estante set ingrediente="huevo", cantidad=12, fechaColocado = now() where posicionX= 1 and posicionY=1 and altura=3;
update estante set ingrediente="lechu", cantidad=3, fechaColocado = now() where posicionX= 1 and posicionY=2 and altura=1;
update estante set ingrediente="lngst", cantidad=1, fechaColocado = now() where posicionX= 7 and posicionY=2 and altura=3;
update estante set ingrediente="lngst", cantidad=1, fechaColocado = now() where posicionX= 7 and posicionY=2 and altura=2;
update estante set ingrediente="lngst", cantidad=1, fechaColocado = now() where posicionX= 7 and posicionY=2 and altura=1;

update estante set ingrediente="arroz", cantidad=1, fechaColocado = now() where posicionX= 7 and posicionY=1 and altura=1;
update estante set ingrediente="arroz", cantidad=1, fechaColocado = now() where posicionX= 7 and posicionY=1 and altura=2;
update estante set ingrediente="arroz", cantidad=1, fechaColocado = now() where posicionX= 7 and posicionY=1 and altura=3;
update estante set ingrediente="ajo", cantidad=12, fechaColocado = now() where posicionX=13  and posicionY=2 and altura=3;
update estante set ingrediente="salmn", cantidad=3, fechaColocado = now() where posicionX= 19 and posicionY=4 and altura=1;
update estante set ingrediente="pmnto", cantidad=1, fechaColocado = now() where posicionX= 13 and posicionY=1 and altura=1;
update estante set ingrediente="pmnto", cantidad=1, fechaColocado = now() where posicionX= 13 and posicionY=1 and altura=2;
update estante set ingrediente="pmnto", cantidad=1, fechaColocado = now() where posicionX= 13 and posicionY=1 and altura=3;


delete from peticion;

insert into peticion values ("camrn", now());
insert into peticion values ("arroz", now());
insert into peticion values ("almjs", now());
insert into peticion values ("chile", now());
insert into peticion values ("trtll", now());
insert into peticion values ("queso", now());
insert into peticion values ("pollo", now());
insert into peticion values ("frjls", now());
