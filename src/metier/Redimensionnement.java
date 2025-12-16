package metier;

import java.awt.image.BufferedImage;

/**
 * Classe pour redimensionner les images
 */
public class Redimensionnement
{
	/** Chemin du fichier image source. */
	String fichierSource;
	/** Chemin du fichier image destination (sauvegarde apres redimensionnement). */
	String fichierDest;
	/** Utilitaire image pour charger/sauvegarder. */
	ImageUtil imgUtil;

	/**
	 * Constructeur
	 * @param fichierSource Chemin de l'image a redimensionner
	 * @param fichierDest Chemin du fichier de sortie
	 */
	public Redimensionnement(String fichierSource, String fichierDest)
	{
		this.fichierSource = fichierSource;
		this.fichierDest   = fichierDest;
		this.imgUtil       = new ImageUtil(fichierSource);
	}

	/**
	 * Redimensionne l'image aux dimensions donnees (sans preserver le ratio).
	 * 
	 * Cette methode utilise l'echantillonnage inverse (inverse mapping) :
	 * Pour chaque pixel de l'image destination, on calcule quel pixel de l'image
	 * source doit etre copie en appliquant un facteur d'echelle.
	 * 
	 * @param newWidth Largeur cible en pixels
	 * @param newHeight Hauteur cible en pixels
	 */
	public void redimensionner(int newWidth, int newHeight)
	{
		BufferedImage src = this.imgUtil.getImage();
		if (newWidth <= 0 || newHeight <= 0)
			return;

		// Dimensions de l'image source
		int w = src.getWidth();
		int h = src.getHeight();

		// Creation de l'image destination avec transparence (ARGB)
		BufferedImage dest = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

		// Calcul des facteurs d'echelle
		double scaleX = (double) w / newWidth;
		double scaleY = (double) h / newHeight;

		// Coordonnees du centre de l'image source
		double i0 = w / 2.0;
		double j0 = h / 2.0;

		// Coordonnees du centre de l'image destination
		double i0d = newWidth / 2.0;
		double j0d = newHeight / 2.0;

		// Parcours de chaque pixel de l'image destination
		for (int jd = 0; jd < newHeight; jd++) {
			for (int id = 0; id < newWidth; id++) {
				// Coordonnees du pixel destination par rapport au centre
				double xd = id - i0d;
				double yd = jd - j0d;

				// Application de l'echelle pour trouver le pixel correspondant
				// dans l'image source
				double xs = xd * scaleX;
				double ys = yd * scaleY;

				// Conversion en coordonnees absolues dans l'image source
				int is = (int) Math.round(xs + i0);
				int js = (int) Math.round(ys + j0);

				// Verification que le pixel source est dans les limites de l'image
				if (is >= 0 && is < w && js >= 0 && js < h) {
					// Copie du pixel source vers le pixel destination
					int rgb = src.getRGB(is, js);
					dest.setRGB(id, jd, rgb);
				}
				// Sinon, le pixel reste transparent (valeur par defaut)
			}
		}

		this.imgUtil.setImage(dest);
		this.imgUtil.sauvegarderImage(this.fichierDest);
	}

	/**
	 * Redimensionne l'image en conservant le ratio via un facteur d'echelle.
	 * @param scale Facteur d'echelle (>0). 2.0 double la taille, 0.5 la divise par deux.
	 */
	public void redimensionnerRatio(double scale)
	{
		if (scale <= 0)
			return;
		BufferedImage src = this.imgUtil.getImage();
		int newWidth  = Math.max(1, (int)Math.round(src.getWidth()  * scale));
		int newHeight = Math.max(1, (int)Math.round(src.getHeight() * scale));
		redimensionner(newWidth, newHeight);
	}
}