package metier;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

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
		int type = src.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : src.getType();
		BufferedImage out = new BufferedImage(newWidth, newHeight, type);
		Graphics2D g2d = out.createGraphics();
		try {
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.drawImage(src, 0, 0, newWidth, newHeight, null);
		} finally {
			g2d.dispose();
		}
		return out;
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