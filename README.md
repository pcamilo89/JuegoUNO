JuegoUNO
========
Juego [UNO](https://es.wikipedia.org/wiki/Uno_(juego)) de 2-4 jugadores para red anillo unidireccional por puerto serial

Proyecto para la catedra de Redes de Computadores I

<p align="center">
  <br>Al iniciar puedes seleccionar el puerto a conectar, la velocidad de transmisión y el tiempo de espera
  <br><img src=docs/Inicio.png />
  <br><br>Una vez repartidas las cartas entre el número de jugadores encontrado inicia el tablero de juego
  <br><img src=docs/Tablero.png />
  <br><br>Partida Ejemplo
  <br><img src=docs/Game.gif />
</p>

## Documentación
La descripción de las caracteristicas del juego implementado y el protocolo se encuentran en el siguiente [link](docs/Caracteristicas%20y%20Protocolo.pdf) 

## Requerimientos

- Java 8
- [RXTX](http://rxtx.qbang.org/wiki/index.php/Main_Page) es una librería Java, usando una implementación nativa (vía JNI), proporcionando comunicación serial y paralela para el Kit de herramientas de desarrollo de Java (JDK).
- Puerto Serial en cada computador a utilizar.
- Cable Serial simple para 2 jugadores o cable Serial en configuración de anillo unidireccional para 3-4 jugadores.
- (Opcional) Emulador de puerto serial, para realizar partidas con múltiples clientes en el mismo computador. 

## Licencia

El código fuente de este proyecto esta licenciado bajo la Licencia [MIT](LICENSE), imágenes y otros recursos en la carpeta 'resources' están explícitamente excluidos.

Copyright © 2019 Camilo Pérez
