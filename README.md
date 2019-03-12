# Tu Voto Cuenta

Aplicación para el Proyecto de Desarrollo de Aplicaciones Multiplataforma. Se trata de una herramienta que ayude al votante indeciso de cara a las elecciones generales, a saber, una aplicación con una finalidad dirigida a la información y el sondeo de
opiniones políticas de cara a las próximas elecciones que tienen cita en nuestro país.
Cada día se generan nuevas encuestas que vislumbran diversos resultados sobre las elecciones y un
posible cambio de gobierno, o bien un reforzamiento del partido existente. La intención de esta
aplicación es ayudar a ese votante, perteneciente a una gran masa creciente conocida como
“indecisos”, a que pueda clarificar qué partido puede acercarse más a su ideología analizando las
numerosas propuestas electorales que se formulan por parte de los partidos políticos. Una
herramienta para el ciudadano, a disposición de todo el mundo, y también para los partidos que
puedan ver las distintas recomendaciones que le llegan desde los votantes.
#El desarrollo de la aplicación versará sobre distintas partes, en suma son:
•
En primer lugar, un API que se realizará usando NodeJS y MongoDB, desde la cual la
aplicación consumirá todos los datos necesarios y donde se almacenará toda la información.
Integrará el modelo de datos necesario para la mayor optimización de la aplicación posible,
buscando el equilibrio entre escritura y lectura.
•
En segundo lugar, tendremos una aplicación web desarrollada en Angular y Angular
Material, mediante la cual se desarrollarán las tareas de gestión de la información. Existirá la
posibilidad de añadir, editar y eliminar, de mano de un administrador, los diversos partidos,
así como las propuestas políticas. También se podrán añadir las categorías, que en este caso
serán diversas materias sobre las que pueden versar las propuestas (vivienda, economía,
salud, educación, medio ambiente...). Por último, se gestionará también el añadir, editar,
eliminar y listar todos los usuarios registrados en la aplicación.
•
En tercer lugar, tendremos la aplicación cliente que se hará en Android. Desde esta
aplicación, podremos listar los distintos partidos que se enfrentan en los comicios
electorales, así como las distintas propuestas que cada uno de ellos proponen. Se podrán
filtrar por materia electoral (categorías). Por otro lado, el usuario podrá añadir a una pestañade “Favoritos”, las distintas propuestas que considere que concuerde más con su ideología,
de tal manera que pueda emitir un juicio sobre su posible afinidad a un partido u otro viendo
de qué partido tiene más propuestas como favoritas. Seguidamente, el usuario podrá crear
propuestas a partidos para que éstos puedan estudiarlas y ver si les interesa incluirlas en sus
discursos políticos o no, de tal manera que se genere un feedback entre votante y
representante. Por supuesto, se podrá gestionar la información del perfil del usuario
logueado. Por último, se incluirá una funcionalidad de mapa en el que se encontraremos el
partido con más votantes afines por provincia, de cara que se pueda realizar una encuesta en
tiempo real de este tipo de información. Elijo la provincia y no otra unidad territorial, ya que
es la circunscripción que rige la ley electoral en nuestro país.
