import numpy as np
import matplotlib.pyplot as plt
import os
import sys


def configPath():
    os.system('export CLASSPATH=".:/usr/local/lib/antlr-4.7.1-complete.jar:$CLASSPATH"')
    os.system("alias antlr4='java -jar /usr/local/lib/antlr-4.7.1-complete.jar'")
    os.system("alias grun='java org.antlr.v4.gui.TestRig'")

configPath()
