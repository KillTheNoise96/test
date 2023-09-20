# test
Aplicación para crear usuarios
Version v1

A continuación, se explicaran los aplicativos y pasos a usar para realizar pruebas en este API:

Aplicaciones sugeridas a usar:

- Instalar IntelliJ 2023.2.1.
- Instalar Postman (Para realizar las pruebas de la API)
- Instalar JDK 19 (Opcional, dado que posteriormente se puede instalar)

Una vez instalados estas aplicaciones, se procederá a usar IntelliJ como IDE para analizar el código fuente de la API, la cual está destinada a crear usuarios mediante el uso de peticiones JSON.

-- Configuraciones para IntelliJ:

Al abrir el programa, se observará que se puede abrir un proyecto local (almacenado en el equipo) o mediante el uso de repositorios (get from VCS).
En este caso, se usará este último clonando el proyecto de la siguiente url https://github.com/KillTheNoise96/test/ asignando como controlador de versiones git.

Una vez descargado el proyecto, se sugiere descargar las dependencias de Gradle.
Esto se puede realizar dando clic en el 3er icono del toolbar ubicado a la derecha de la pantalla, esto desplegará una sección en la que tendremos la opción "Reload All Gradle Projects", en el cual se dará clic y esperará a que finalice el proceso.

Posterior, se recomienda asignar un SDK, el cual será la base de la estructura del proyecto. Para hacer esto, daremos clic en el icono de engranaje que está ubicado en el costado superior derecho del IntelliJ,
Se verá una opcion llamada "Project Structure", en la que se accederá para seleccionar, ya sea si se descargó anteriormente JDK 19 la ubicación de su fichero o bien, descargar openjdk 19 propio para el IDE.
(Esto permitirá el correcto funcionamiento del proyecto dado que está construido con Java 19 y SpringBoot 3).

-- Uso de POSTMAN:

--- Metodo 1 - ejecución en linea:

  Teniendo desplegada la api, se accede al siguiente url: https://www.postman.com/cryosat-saganist-37831814/workspace/api/collection/29786852-6b4e0518-3d44-46a5-a15d-917551699089
  En este url se podrán realizar las instrucciones GET, PUT y POST de una manera más rápida y sencilla.
  
--- Metodo 2 - Local:
  
  Habiendo realizado los pasos anteriores, se procede a hacer debug o "correr" la api, lo que permitirá hacer uso de los endpoints que ésta contiene en la clase UserController, para lo cual se recomienda usar Postman.
  
  La url base que posee la api es: localhost:8080/api/v1/
  
  Estando desplegada la api, se procederá a abrir Postman, en el cual se abrirán 3 pestañas, cada una tendrá asignada 3 tipos de peticiones diferentes:
  
  - La primera tendrá como tipo de petición GET, en la cual asignaremos la url localhost:8080/api/v1/all (Listar todos los usuarios).
  
  - La segunda será una petición PUT en la que se usará la url localhost:8080/api/v1/login (equivalente a un inicio de sesión) con el siguiente cuerpo JSON:
  "
      {
          "email": "mark_pho@gmail.com",
          "password": "Gowrules123"
      } 
  
  "
  
  - La tercera y última corresponderá al tipo POST con la url localhost:8080/api/v1/user (Guardado de un nuevo usuario) con la siguiente estructura JSON:

  "
      {
            "name": "Juan Rodriguez",
            "email": "juane@rodriguez.org",
            "password": "Hunter24q",
            "phones": [
                {
                    "number": "1234567",
                    "cityCode": "1",
                    "countryCode": "57"
                }
            ]
      } 
  "
  
* La data en las peticiones JSON es sugerida.
* En caso de un error por tipo de datos en los JSON, se sugiere agregar en el header Content_Type: application/json;charset=UTF-8
