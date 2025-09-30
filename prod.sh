docker run -it --rm -v "$HOME/.m2:/root/.m2" \ #crea contenedor y lo elimina
-v /tmp/backend-ingenieria-web-3/iwr:/usr/src/mymaven\  #monta una carpeta local dentro del contenedr de maven
 -w /usr/src/mymaven\ #define como directorio de trabajo dentro del contenedor
 maven:3.9.11-amazoncorretto-21-debian\
 mvn clean package \ #comando maven dentro del contenedor
-Dmaven.test.skip=true -Dspring.profiles.active=mysqlprod
