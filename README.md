# OurProject

## Descripción
Servicio web basado en mecenazgo por recompensa, en el cual el usuario tendrá la posibilidad tanto de publicar un proyecto propio para conseguir financiación, como apoyar otros proyectos para que lleguen a la meta, y con ello conseguir recompensas.

Este servicio da soporte a proyectos de videojuegos, con la posibilidad de comunicar directamente a contribuidores y desarrolladores, creando así una comunidad mutuamente beneficiosa para el desarrollo de videojuegos.

El desarrollador sólo podrá obtener la financiación recaudada si sobrepasa la meta propuesta. En este caso, el desarrollador será el encargado de hacer llegar las recompensas a los contribuidores. Si no se alcanza la meta en el tiempo asignado, la recaudación será devuelta a cada usuario.

### Funcionalidades públicas
- Visualizar proyectos (información, mensajes de los contribuidores, imágenes, tablas de recompensas...)
- Crear Cuenta

### Funcionalidades privadas
- Publicar/eliminar proyecto
- Contribuir
- Escribir mensajes y valoración (siempre y cuando el proyecto sea propio del usuario o se trate de un contribuidor del proyecto)
- Recibir facturas de los proyectos en los cuales el usuario contribuya
- Recibir notificaciones (correo):
    - Al recibir un nuevo mensaje en un proyecto propio
    - Al recibir una respuesta a un mensaje previamente publicado por el usuario
    - Al alcanzar la meta de presupuesto
    - Al recibir una donación (recibes el contrato con el contribuidor)
___
## Entidades principales
- Proyecto - Conjunto de recursos (información, imágenes, avances, vídeos, recompensas) añadidos por el desarrollador para alcanzar una meta de financiación para su videojuego.
- Contribuyente - Usuario básico, el cual puede acceder a todos los proyectos publicados, así como aportar tanto su opinión y valoración, siempre y cuando haya realizado una contribución al proyecto.
- Desarrollador - Usuario básico con privilegios especiales, como es una interfaz propia para el tratamiento de los proyectos añadidos a *OurProject*
- Comentario - Unidad básica de comunicación entre contribuidores y desarrolladores
- Contrato - Acuerdo entre la contribución realizada por un usuario y las recompensas que podrá obtener.
- Meta - Objetivos parciales de financiación a alcanzar en un proyecto. Su consecución implica desbloquear ciertas partes del proyecto o dar recompensas a todos los usuarios.
- Recompensa - Según la cantidad donada, el desarrollador entregará ciertos beneficios al contribuidor, a mayor cantidad mayores beneficios.

### Diagrama entidad relación
![alt text](./Documentos/diagramaER.png "Diagrama ER")

A continuación se detallan las relaciones entre las entidades de forma jerárquica de importancia:

- User:
    * Un Usuario puede contribuir en varios Proyectos, a su vez, varios Proyecto pueden tener multiples contribuidores. Relación **M:N**
    * Un Usuario puede realizar múltiples Comentarios, pero un Comentario solo tiene asociado un Usuario. Relación **1:N**
    * Un Usuario puede aportar dinero a muchos Proyectos por lo que obtiene un Contrato por cada uno, el Contrato relaciona un Usuario y un Proyecto. Relación **1:N**
- Desarrollador:
    * Cuenta con las mismas relaciones que usuario ya que hereda de este, pero incorpora una relación extra con Proyecto. Un Desarrollador desarrolla múltiples Proyectos pero un Proyecto solo tiene un Desarrollador. Relación **1:N**
- Proyecto:
    * Como se explicó anteriormente, cuando un Usuario contribuye a un Proyecto se genera un Contrato. Un proyecto puede tener múltiples Contratos pero un Contrato solo referencia a un Proyecto. Relación **1:N**
    * Lo mismo ocurre con los comentarios que realiza un Usuario sobre un Proyecto. Relación **1:N**
    * Un Proyecto se compone por múltiples Metas a alcanzar en el Proyecto, estas Metas son únicas por Proyecto. Relación **1:N**
    * A su vez un Proyecto también se compone por multiples Recompensas que se otorgarán dependiendo de la cantidad de dinero donada. Su cardinalidad es igual que la relación anterior **1:N**

Las relaciones del resto de clases ya han sido explicadas en los niveles superiores.

### Diagrama UML

![alt text](./Documentos/DiagramaUML.jpeg "Diagrama UML")

## Navegación por el sitio web

A continuación se muestra el esquema general de navegación y transiciones entre la sdistintas páginas, cada una de ellas se desglosará más adelante.

![alt text](./Documentos/FlowChartWeb.png "Diagrama UML")





___
## Funcionalidades Servicio Interno
- Generador de facturas de los contratos firmados entre contribuidor y desarrollador.
- Envio de notificaciones por correo, tanto cuando se firma un contrato, como cuando se realiza una interacción entre desarrollador y contribuidor.
___
## Equipo
| Nombres | Correo URJC  | Usuario Git Hub  |
| ------------- |:-------------:| -----:|
| García Rodríguez, Alejandro | a.garciar.2016@alumnos.urjc.es | [DaxelTh](https://github.com/DaxelTH "Usuario Axel") |
| Jiménez Pacheco, Daniel | d.jimenez.2016@alumnos.urjc.es | [Numan4221](https://github.com/Numan4221 "Usuario Daniel") |
| Plaza Larrosa, Sergio | s.plazal.2016@alumnos.urjc.es | [heyimsergio](https://github.com/heyimsergio "Usuario Sergio") |
