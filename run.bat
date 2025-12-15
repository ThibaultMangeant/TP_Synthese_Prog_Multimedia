@echo off

REM Execution
echo Execution en cours...
cd bin
start javaw controleur.Controleur
if %ERRORLEVEL% NEQ 0 (
	echo Erreur lors de l'execution.
)
echo Execution terminee - FIN DU PROGRAMME

:: Ferme la console initial
exit