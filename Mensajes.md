## Bodego ##
1.Confirmar
  * Al chef:

> Contenido vacío

> Se envía como respuesta a una petición de n ingredientes, en caso de poder cargarlos.

  * Al proveedor:

> Contenido vacío

> Se envía como respuesta a una petición de paquete, en caso de poder cargarlo.

## Chef ##
1.Traer
  * A los bodegos:

> Contenido: n Ingredientes

> Se envía por cada tipo de ingrediente que requiere el chef, especificando la cantidad.

2.Confirmar
  * A un bodego

> Contenido: clave del Ingrediente#cantidad

> Se envía al primer agente que confirma traer un ingrediente.

3.Rechazar
  * A los bodegos

> ~~Contenido vacío~~

> ~~Se envía a todos los agentes que confirmaron y que no serán contratados.~~

> No se  envía mensaje, los bodegos deben intuir que si no les contestan es porque fueron rechazados

## Proveedor ##

1.Informar
  * A los bodegos

> Contenido: Tipo de ingrediente

> Se envía por cada paquete que llega a la bodega.

2.Confirmar
  * A un bodego

> Contenido: clave del Ingrediente

> Se envía al primer agente que confirma llevarse el paquete.

3.Rechazar
  * A los bodegos

> ~~Contenido vacío~~

> ~~Se envía a todos los agentes que confirmaron y que no serán contratados.~~

> No se  envía mensaje, los bodegos deben intuir que si no les contestan es porque fueron rechazados

## Administrador ##

1.Cambiar
  * A los menú

> Contenido: Receta

> Se envía cuando es necesario quitar una receta del menú (se acabaron los ingredientes).

## Menú ##

1.Sugerir
  * A los menú

> Contenido: Platillo1, Platillo2, Platillo3

> Se envían los 3 patillos que  se tomarán en cuenta en la elección del nuevo platillo.

  * Al administrador

> Contenido: Platillo1-Valor, Platillo2-Valor,…, Platillo8-Valor, Platillo9-Valor

> Se envía al administrador para que decida que platillo irá al menú.


El contenido de los mensajes se separá por un gato (#) para poder aplicar split y facilitar el cast.