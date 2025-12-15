#!/usr/bin/env bash

set -euo pipefail

# Compile les fichiers
echo "Sauvegarde du CLASSPATH actuel"
OLD_CLASSPATH="${CLASSPATH-}"

echo "Définition du CLASSPATH..."
export CLASSPATH="lib/*:bin:.:${CLASSPATH-}"
echo "CLASSPATH défini à \"$CLASSPATH\""

echo "Compilation en cours..."
javac -cp "$CLASSPATH" @compile.list -d ./bin -encoding UTF-8

echo "Compilation terminée."

# Pause (équivalent de PAUSE)
read -n 1 -s -r -p "Appuyez sur une touche pour continuer..."; echo

# Lance le programme
cd bin || { echo "Erreur: dossier 'bin' introuvable."; export CLASSPATH="$OLD_CLASSPATH"; exit 1; }
java controleur.Controleur &
app_pid=$!

# Optionnel: vérifier si le processus tourne
sleep 1
if ! kill -0 "$app_pid" 2>/dev/null; then
  echo "Erreur lors de l'exécution."
  export CLASSPATH="$OLD_CLASSPATH"
  exit 1
fi

echo "Programme lancé en arrière-plan (PID: $app_pid)."

# Restaurer l'ancien CLASSPATH
export CLASSPATH="$OLD_CLASSPATH"

# Ferme la console initiale
exit 0
