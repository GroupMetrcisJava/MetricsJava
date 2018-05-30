import numpy as np
import matplotlib.pyplot as plt
import os
import sys
import networkx as nx


def configPath():
    os.system('export CLASSPATH=".:/usr/local/lib/antlr-4.7.1-complete.jar:$CLASSPATH"')
    os.system("alias antlr4='java -jar /usr/local/lib/antlr-4.7.1-complete.jar'")
    os.system("alias grun='java org.antlr.v4.gui.TestRig'")

def edges(G):
    archivo = open('salida.txt','r')
    for linea in archivo:
        nodos = ""
        if linea[0] == '|':
            nodos = linea.split('|')
            nodoDesde = str(nodos[1])
            nodoHacia = str(nodos[2])
            nodoHacia = nodoHacia.replace('\n','')
            G.add_edge(nodoDesde,nodoHacia)
    archivo.close()
    return G
            
G = nx.Graph()
G = edges(G)
options = {
     'node_color': 'yellow',
     'node_size': 500,
     'width': 3,
     'with_labels':True,
     'font_weight':'bold',
     'edge_color':'green',
     'font_size': 12,
     'title':'Grafo relacional software'
 }
nx.draw(G, **options)
plt.title('Grafo relacional software')
plt.show()

def cantidadDatosBarras():
    archivo = open('salida.txt','r')
    count = 0
    for linea in archivo:
        if linea[0] == '&':
            count+=1
    archivo.close()
    return count

def graficarBarras(count):
    archivo = open('salida.txt','r')
    xlabels = []
    numValues = []
    for linea in archivo:
        if linea[0] == '&':
            datos = linea.split(':')
            labels = datos[0].split('&')
            label = labels[1].replace(' ','')
            xlabels.append(label)
            number = datos[1].replace(' ','')
            numValues.append(int(number.replace('\n','')))
    x = np.arange(count)
    plt.bar(x, numValues)
    plt.xticks(x, xlabels)
    plt.title('Cantidad elementos en software')
    plt.show()
    archivo.close()

graficarBarras(cantidadDatosBarras())
    
    
