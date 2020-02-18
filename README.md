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

### Diagrama Entidad-Relación
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

A continuación se muestra el esquema general de navegación y transiciones entre las distintas páginas, cada una de ellas se desglosará más adelante.

![alt text](./Documentos/diagramaFlujoOurProject.png "Diagrama Flujo")

- Página Principal: contendrá los proyectos disponibles, de todos los desarrolladores, con su nombre y una imagen. Al hacer clic sobre estos proyectos, se despliega un modal que presenta una pequeña descripción del proyecto y permite el acceso a su página con información completa. Además, tendrá accesos directos tanto para cerrar sesión (o iniciar sesión en versiones futuras) como al perfil del propio usuario.

![alt text](./Documentos/ImgREADME/PP.png "Página Principal")
![alt text](./Documentos/ImgREADME/PP_modal.png "Página Principal - Modal")

- Página Proyecto: en la página del proyecto se puede ver con todo detalle la información del mismo: el nombre, el desarrollador, la imagen, la descripción, así como dos bloques con las diferentes metas y recompensas. Además, en la parte inferior se encuentra la sección de comentarios.

![alt text](./Documentos/ImgREADME/PProj.png "Página Proyecto")
![alt text](./Documentos/ImgREADME/PProj2.png "Página Proyecto")

- Página Donación: se presenta una pantalla simple con la información básica del proyecto al que vas a donar, así como el método de pago si no lo tienes configurado en tu perfil, el número de cuenta y la cantidad de donar.

![alt text](./Documentos/ImgREADME/PDonacion.png "Página Donación")

- Página Perfil: en esta pantalla se presenta toda la información relativa a un usuario, y ésta dependerá de si dicho usuario es desarrollador o no. 
    - Si no eres desarrollador, aparecerán diferentes campos con tu información personal con la posibilidad de editarla, las donaciones que has realizado con sus respectivos contratos y un botón de "convertirse a desarrollador". 
    - Si ya eres un desarrollador, este último desaparece, y en su lugar aparecen los proyectos personales que hayas creado, así como la posibilidad de eliminarlo y acceder a su página.

![alt text](./Documentos/ImgREADME/PPerfil_noDesarrollador_noProyectos.png "Perfil Contribuidor")
![alt text](./Documentos/ImgREADME/PPerfil_desarrollador_conProyectos.png "Página Desarrollador")

- Contrato: en la página del perfil, si haces clic en ver contrato, aparecerá un modal en el que podrás ver su información específica, así como las recompensas obtenidas según la cantidad que hayas donado.

![alt text](./Documentos/ImgREADME/modalContrato.png "Modal Contrato")

- Página Añadir Proyecto: aparecerá un formulario en el que podrás configurar todos los parámetros necesarios para la creación de un proyecto. Estos son: el nombre, una descripción, un tipo de cuenta y número al que se deberá ingresar el dinero de las donaciones. Además, aparecen cinco campos para especificar metas y cinco para recompensas, siendo solo obligatorios el primero de cada tipo.

![alt text](./Documentos/ImgREADME/anadirProj.png "Modal Contrato")


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
