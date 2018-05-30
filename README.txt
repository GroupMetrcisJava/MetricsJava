Creado por:
    Nicolas Ricardo Enciso
    Diego Felipe Rodriguez Chaparro
Para la materia Lenguajes de Programación. 2018-I

MANUAL DE USO Y COMPILACIÓN

Para usar este metricador de Java, asumimos que ya tiene instalado antlr4 en su maquina.
De no ser asi, por favor referirse a la pagina del curso : https://sites.google.com/unal.edu.co/lenguajesdeprogramacion-2018-1?pli=1&authuser=1
Y en el apartado "Tutorial ANTLR", encontrara una guia para instalar los recursos necesarios.

1. En esta carpeta encontrara un archivo llamado "Java8.g4", la cual es la grámatica a usar y con ella
   se generaran los archivos necesarios para ejecutar este proyecto. Utilizando la terminal, correr el 
   comando :

        antlr4 Java8.g4

2. Con el paso anterior se crearon unos nuevos archivos de extensión .java que es necesario compilar,
   En la terminal, ejecutar:

        javac *.java

3. Una vez compilados los archivos nuevos, podemos correr el metricador. Incluimos una carpeta con 5 ejemplos
   (cuyos nombres son entrada.in, entrada2.in ... hasta llegar a entrada5.in) para demostrar el funcionamiento
   del metricador, ejecutar en la consola:

        java correr ./Ejemplos/entrada.in > salida.txt

   Esto generara un archivo ".txt" con un reporte de las metricas encontradas en las funciones y clases del codigo
   fuente.

NOTA: Para el siguiente paso, es necesario instalar los paquetes matplotlib y networkx de python.
      Puede encontrar las instrucciones para su instalacion en estos links:
        https://matplotlib.org/users/installing.html
        https://networkx.github.io/documentation/stable/install.html

4. Para generar los graficos correspondientes, utilizamos el siguiente comando:

        python3 graficador.py|

    Donde podremos ver de manera grafica las relaciones que existen entre las clases y las funciones
    con las variables que utilizan y las estructuras de control asociadas. 