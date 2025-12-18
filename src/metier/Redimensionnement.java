package metier;

import java.awt.image.BufferedImage;

/**
 * Classe pour redimensionner les images
 * @author Equipe 5
 */
public class Redimensionnement
{
	/** Chemin du fichier image destination (sauvegarde apres redimensionnement). */
	private String fichierDest;
	/** Utilitaire image pour charger/sauvegarder. */
	private ImageUtil imgUtil;

	/**
	 * Constructeur
	 * @param fichierSource Chemin de l'image a redimensionner
	 * @param fichierDest Chemin du fichier de sortie
	 */
	public Redimensionnement(String fichierSource, String fichierDest)
	{
		this.fichierDest = fichierDest;
		this.imgUtil     = new ImageUtil(fichierSource);
	}

	/**
	 * Redimensionne l'image aux dimensions donnees (sans preserver le ratio).
	 * 
	 * Cette methode utilise l'echantillonnage inverse (inverse mapping) :
	 * Pour chaque pixel de l'image destination, on calcule quel pixel de l'image
	 * source doit etre copie en appliquant un facteur d'echelle.
	 * 
	 * @param nouvelleLargeur Largeur cible en pixels
	 * @param nouvelleHauteur Hauteur cible en pixels
	 */
	public void redimensionner(int nouvelleLargeur, int nouvelleHauteur)
	{
		BufferedImage source;
		BufferedImage destination;

		if (nouvelleLargeur <= 0 || nouvelleHauteur <= 0) { return; }

		source      = this.imgUtil.getImage();
		destination = Redimensionnement.redimensionner(source, nouvelleLargeur, nouvelleHauteur);
		
		this.imgUtil.setImage(destination);
		this.imgUtil.sauvegarderImage(this.fichierDest);
	}

	/**
	 * Redimensionne l'image en conservant le ratio via un facteur d'echelle.
	 * @param echelle Facteur d'echelle (>0). 2.0 double la taille, 0.5 la divise par deux.
	 */
	public void redimensionnerRatio(double echelle)
	{
		BufferedImage source;
		BufferedImage destination;

		if (echelle <= 0) { return; }
		
		source      = this.imgUtil.getImage();
		destination = Redimensionnement.redimensionnerRatio(source, echelle);
		
		this.imgUtil.setImage(destination);
		this.imgUtil.sauvegarderImage(this.fichierDest);
	}

	/**
	 * Methode statique: redimensionne une image aux dimensions donnees.
	 */
	public static BufferedImage redimensionner(BufferedImage source, int nouvelleLargeur, int nouvelleHauteur)
	{
		BufferedImage destination;
		double        echelleX, echelleY;
		double        centreSourceX, centreSourceY;
		double        centreDestX, centreDestY;
		double        xDest, yDest;
		double        xSource, ySource;
		int           iSource, jSource;
		int           rgb;
		int           largeur, hauteur;
		int           id, jd;

		if (nouvelleLargeur <= 0 || nouvelleHauteur <= 0) { return source; }

		largeur = source.getWidth();
		hauteur = source.getHeight();

		// On force l'ARGB pour conserver la transparence si besoin
		destination = new BufferedImage(nouvelleLargeur, nouvelleHauteur, BufferedImage.TYPE_INT_ARGB);

		// Échantillonnage inverse (nearest neighbor) centré comme pour la rotation
		echelleX = (double) largeur / nouvelleLargeur;
		echelleY = (double) hauteur / nouvelleHauteur;

		centreSourceX = largeur / 2.0;           // centre source
		centreSourceY = hauteur / 2.0;
		centreDestX   = nouvelleLargeur / 2.0;   // centre destination
		centreDestY   = nouvelleHauteur / 2.0;

		for (jd = 0; jd < nouvelleHauteur; jd++)
		{
			for (id = 0; id < nouvelleLargeur; id++)
			{
				xDest = id - centreDestX;
				yDest = jd - centreDestY;

				xSource = xDest * echelleX;
				ySource = yDest * echelleY;

				iSource = (int)Math.round(xSource + centreSourceX);
				jSource = (int)Math.round(ySource + centreSourceY);

				if (iSource >= 0 && iSource < largeur && jSource >= 0 && jSource < hauteur)
				{
					rgb = source.getRGB(iSource, jSource);
					destination.setRGB(id, jd, rgb);
				}
			}
		}

		return destination;
	}

	/**
	 * Methode statique: redimensionne une image en conservant le ratio via un facteur d'echelle.
	 */
	public static BufferedImage redimensionnerRatio(BufferedImage source, double echelle)
	{
		int nouvelleLargeur, nouvelleHauteur;

		if (echelle <= 0) { return source; }
		
		nouvelleLargeur = Math.max(1, (int)Math.round(source.getWidth()  * echelle));
		nouvelleHauteur = Math.max(1, (int)Math.round(source.getHeight() * echelle));
		
		return Redimensionnement.redimensionner(source, nouvelleLargeur, nouvelleHauteur);
	}
}