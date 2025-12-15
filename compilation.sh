#!/usr/bin/env bash

set -euo pipefail

echo "Sauvegarde du CLASSPATH actuel"
OLD_CLASSPATH="${CLASSPATH-}"

echo "Définition du CLASSPATH..."

export CLASSPATH="lib/*:bin:.:${CLASSPATH-}"
echo "CLASSPATH défini à \"$CLASSPATH\""

echo "Compilation en cours..."
if ! javac -cp "$CLASSPATH" @compile.list -d bin -encoding UTF-8; then
  echo "Erreur lors de la compilation."
  # Restaurer l'ancien CLASSPATH avant de quitter
  export CLASSPATH="$OLD_CLASSPATH"
  exit 1
fi

echo "Compilation terminée."

# Restaurer l'ancien CLASSPATH (optionnel)
export CLASSPATH="$OLD_CLASSPATH"

exit 0