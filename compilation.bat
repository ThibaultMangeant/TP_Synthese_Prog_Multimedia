@echo off

REM Sauvegarde du CLASSPATH actuel
set "OLD_CLASSPATH=%CLASSPATH%"

REM Definition du CLASSPATH
echo Definition du CLASSPATH...
set "CLASSPATH=lib\*;bin;.;%CLASSPATH%"
echo CLASSPATH defini a "%CLASSPATH%"

REM Compilation
echo Compilation en cours...
javac -cp "%CLASSPATH%" @compile.list -d bin -encoding UTF-8
if %ERRORLEVEL% NEQ 0 (
	echo Erreur lors de la compilation.
	goto end
)
echo Compilation terminee.