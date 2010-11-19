alter table ingrediente add (saludable int);

update ingrediente set saludable = 0;


update ingrediente set saludable = 1 where clave in 
('ajo',
'arroz',
'cblla',
'chich',
'chile',
'chmpn',
'frjls',
'jitom',
'lechu',
'limon',
'papa',
'pasta',
'tofu',
'trtll')
