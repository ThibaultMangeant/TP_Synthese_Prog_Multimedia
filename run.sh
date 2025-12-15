#!/usr/bin/env bash

set -euo pipefail

echo "Exécution en cours..."

# Aller dans le dossier des classes compilées
cd "bin" || { echo "Erreur: dossier 'bin' introuvable."; exit 1; }

# Lancer l'application Java
java . controleur.Controleur &
app_pid=$!

# Optionnel: attendre une seconde et vérifier si le processus tourne
sleep 1
if ! kill -0 "$app_pid" 2>/dev/null; then
	echo "Erreur lors de l'exécution."
	exit 1
fi

echo "Exécution terminée - FIN DU PROGRAMME"

exit 0