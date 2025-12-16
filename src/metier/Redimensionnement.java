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

		BufferedImage dest = redimensionner(src, newWidth, newHeight);
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
		BufferedImage dest = redimensionnerRatio(src, scale);
		this.imgUtil.setImage(dest);
		this.imgUtil.sauvegarderImage(this.fichierDest);
	}

	/**
	 * Methode statique: redimensionne une image aux dimensions donnees.
	 */
	public static BufferedImage redimensionner(BufferedImage src, int newWidth, int newHeight)
	{
		if (newWidth <= 0 || newHeight <= 0) return src;

		int w = src.getWidth();
		int h = src.getHeight();

		// On force l'ARGB pour conserver la transparence si besoin
		BufferedImage dest = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

		// Échantillonnage inverse (nearest neighbor) centré comme pour la rotation
		double scaleX = (double) w / newWidth;
		double scaleY = (double) h / newHeight;

		double i0  = w / 2.0;        // centre source
		double j0  = h / 2.0;
		double i0d = newWidth / 2.0;  // centre destination
		double j0d = newHeight / 2.0;

		for (int jd = 0; jd < newHeight; jd++)
		{
			for (int id = 0; id < newWidth; id++)
			{
				double xd = id - i0d;
				double yd = jd - j0d;

				double xs = xd * scaleX;
				double ys = yd * scaleY;

				int is = (int)Math.round(xs + i0);
				int js = (int)Math.round(ys + j0);

				if (is >= 0 && is < w && js >= 0 && js < h)
				{
					int rgb = src.getRGB(is, js);
					dest.setRGB(id, jd, rgb);
				}
			}
		}

		return dest;
	}

	/**
	 * Methode statique: redimensionne une image en conservant le ratio via un facteur d'echelle.
	 */
	public static BufferedImage redimensionnerRatio(BufferedImage src, double scale)
	{
		if (scale <= 0) return src;
		int newWidth  = Math.max(1, (int)Math.round(src.getWidth()  * scale));
		int newHeight = Math.max(1, (int)Math.round(src.getHeight() * scale));
		return redimensionner(src, newWidth, newHeight);
	}
}