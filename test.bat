@echo off

:: Compile les fichiers
javac -encoding UTF8 @compile.list -d ./bin

:: Lance le programme
cd bin
java controleur.Controleur

:: Ferme la console initial
exit