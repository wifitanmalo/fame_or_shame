# Fame or Shame

-------------

_Fame or Shame_ es una aplicación desarrollada con **Java** para administrar las calificaciones de tus materias. En esta puedes crear una materia con sus respectivos datos: id, nombre y créditos, en la que puedes agregar tus calificaciones con el puntaje obtenido y su porcentaje para calcular en cuánto te quedó la materia.

El nombre [_Fame or Shame_](https://gta.fandom.com/wiki/Fame_or_Shame) es propiedad de la empresa [_Rockstar Games_](https://www.rockstargames.com/es/), siendo un programa de televisión presente en el videojuego [_Grand Theft Auto V_](https://www.rockstargames.com/es/gta-v).

> [!IMPORTANT] Asegúrate de tener instalado lo siguiente:
>
> Java Development Kit **(versión 8 o superior).**
> - [Descargar JDK](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)
> 
> Un IDE compatible con Java, como _IntelliJ IDEA_ **(recomendado)** o _Eclipse._
> - [Descargar IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
> - [Descargar Eclipse](https://www.eclipse.org/downloads/)


## Inicio de programa

-------------

Al ejecutar el programa aparecerá una ventana con el cuadro donde serán mostradas las materias y un botón `+` para crear una nueva.

## Crear una materia

-------------

Al pulsar el botón `+` se mostrará una ventana con tres campos de texto para ingresar el id, nombre y créditos de la materia. Además, habrá un botón "Back" para regresar al menú de las materias y un botón "Add" para confirmar la creación de la materia.

> [!CAUTION]
> - El ID y los créditos deben ser **enteros positivos** no mayores a 9 dígitos.
> - El ID debe ser **diferente** a los ya existentes.
> - El nombre no puede estar **vacío.**
> - El nombre no puede ser mayor a **50 carácteres.**
> - El número de créditos no puede ser mayor al **límite** admitido por el programa.
 
Tras finalizar la creación de la materia, los datos se almacenarán en el archivo `subjects.txt` en el siguiente orden: `id, name, credits, total_score, total_evaluated`. Estos dos últimos valores serán predeterminadamente `0.0` cuando se crea una nueva materia.
> _**Ejemplo de materia:**_
    
    111021,Univariate Calculus,3,0.0,0.0

Finalmente se te redirigirá nuevamente al menú de las materias en donde ahora se mostrará la nueva materia agregada en el recuadro con dos botones: `+` para entrar al menú de calificaciones y `x` para eliminar la materia.

## Crear una calificación

-------------

Al pulsar el botón `+` de la materia se mostrará una ventana similar a la de las materias, pero con dos nuevos botones: `Total` para calcular el total que suman las calificaciones y un botón `Back` para regresar al menú de las materias. Encima del botón `+` estará el valor calculado de la materia, mostrándose en verde `#C5EF48` si has aprobado y en rojo `#FF6865` si has reprobado.

El uso es igual al del menú anterior, al pulsar el botón `+` se añadirá una nueva calificación con dos campos de texto para el puntaje y el porcentaje respectivamente, además de un botón `x` para eliminar la calificación. Cada calificación será almacenada en el archivo `grades.txt` en el siguiente orden: `id,score,percentage` y a medida que realices un cambio en alguno de los dos campos de texto, el archivo se irá actualizando con las nuevas calificaciones.

> _**Ejemplo de calificaciones:**_

    111021,5,10
    111021,3.4,30
    111021,4.2,10
    111021,1.4,20
    111021,5,10
    111021,0.5,20

## Calcular la calificación total

-------------

Ya habiendo agregado todas las calificaciones de tu materia, puedes pulsar el botón `Total` para calcular cuál fue el total que obtuviste y si lograste culminar exitosamente la materia.

> [!CAUTION]
> - Las calificaciones y sus porcentajes deben ser **double positivos.**
> - Las calificaciones no pueden ser mayores al **límite** admitido por el programa.
> - Los porcentajes deben ser **mayores** a 0.
> - La suma de los porcentajes no puede ser **mayor a 100%.**

## Configuración

-------------

### Calificación mínima y máxima

En los atributos de la clase Subject encontrarás las siguientes variables:

    // minimum score to approve
    public static final double passing_score = 3.0;

    // maximum score possible
    public static final double max_score = 5.0;

Puedes cambiarlo por los valores para calificar que tiene tu institución.

### Límite de créditos

En los atributos de la clase Management encontrarás la siguiente variable: 

    // maximum amount of credits
    public static final int max_credits = 21;

Puedes cambiarlo por el valor máximo de créditos que tiene tu institución.

## Comentario del autor

-------------

Espero que este programa les facilite la vida a la hora de calcular sus calificaciones sin verse obligados a tener que recordar cada calificación y porcentaje en el transcurso del curso. 

¡Muchos éxitos !

    By: Nicolás Chaparro
