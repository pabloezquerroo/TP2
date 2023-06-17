# Tecnología de la programación 2
Proyecto asignatura Tecnología de la programación 2

## Proyecto 
En esta práctica vamos a implementar un simulador para algunas _leyes de la física_ en un espacio bidimensional (2D). El simulador tendrá dos componentes principales:

* _Cuerpos_, que representan entidades físicas (por ejemplo planetas), que tienen una velocidad, aceleración, posición y masa. Estos cuerpos, cuando se les solicite, se pueden mover, modificando su posición de acuerdo a algunas leyes físicas.
* _Leyes de fuerza_, que aplican fuerzas a los cuerpos (por ejemplo, fuerzas gravitacionales).

Utilizaremos un diseño orientado a objetos para poder manejar distintas clases de cuerpos y de leyes de fuerzas. Además, utilizaremos genéricos para implementar factorías, tanto para los cuerpos como para las leyes de fuerza.
Un _paso de simulación_ consiste en inicializar la fuerza aplicada a cada cuerpo, aplicar las leyes de fuerza para cambiar las fuerzas aplicadas a los cuerpos, y después solicitar a cada cuerpo que se mueva.

<img src=./imagen.jpg>  </img>

[Enunciados](../Enunciados)