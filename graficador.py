import numpy as np
import matplotlib.pyplot as plt
import os
import sys
import networkx as nx


def configPath():
    os.system('export CLASSPATH=".:/usr/local/lib/antlr-4.7.1-complete.jar:$CLASSPATH"')
    os.system("alias antlr4='java -jar /usr/local/lib/antlr-4.7.1-complete.jar'")
    os.system("alias grun='java org.antlr.v4.gui.TestRig'")

G = nx.Graph()
"""G.add_node("clase")
G.add_node("metodo")"""
G.add_edge("clase","metodo")
options = {
     'node_color': 'black',
     'node_size': 100,
     'width': 3,
}
nx.draw(G, with_labels=True, font_weight='bold')
plt.show()
