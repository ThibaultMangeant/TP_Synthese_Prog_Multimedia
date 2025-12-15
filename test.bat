@echo off

:: Compile les fichiers
javac -encoding UTF8 @compile.list -d ./bin

:: Test Compilation
PAUSE

:: Lance le programme
cd bin
start java controleur.Controleur

:: Ferme la console initial
exit