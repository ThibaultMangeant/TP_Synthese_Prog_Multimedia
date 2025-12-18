package metier;

import java.awt.image.BufferedImage;

/**
 * Classe pour convertir une image en négatif (inversion des couleurs)
 * @author Equipe 5
 */
public class Negatif
{
	private ImageUtil imgUtil;
	private String    fichierDest;

	/**
	 * Constructeur fichier -> fichier (mode sauvegarde)
	 */
	public Negatif(String fichierSource, String fichierDest)
	{
		this.fichierDest = fichierDest;
		this.imgUtil     = new ImageUtil(fichierSource);
	}

	/**
	 * Applique le négatif et sauvegarde vers le fichier destination.
	 */
	public void appliquer()
	{
		BufferedImage src;
		BufferedImage sortie;

		src    = this.imgUtil.getImage();
		sortie = Negatif.appliquerNegatif(src);
		
		this.imgUtil.setImage(sortie);
		this.imgUtil.sauvegarderImage(this.fichierDest);
	}

	/**
	 * Méthode statique: retourne l'image passée en négatif (alpha préservé)
	 */
	public static BufferedImage appliquerNegatif(BufferedImage src)
	{
		int           largeur, hauteur;
		BufferedImage sortie;
		int           x, y;
		int           pixel;
		int           a, r, g, b;
		int           nouveauR, nouveauG, nouveauB;
		int           rgba;

		largeur = src.getWidth();
		hauteur = src.getHeight();
		sortie  = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_ARGB);

		for (y = 0; y < hauteur; y++)
		{
			for (x = 0; x < largeur; x++)
			{
				pixel = src.getRGB(x, y);
				
				a = (pixel >>> 24) & 0xFF;
				r = (pixel >>> 16) & 0xFF;
				g = (pixel >>> 8)  & 0xFF;
				b = pixel          & 0xFF;

				nouveauR = 255 - r;
				nouveauG = 255 - g;
				nouveauB = 255 - b;

				rgba = (a << 24) | (nouveauR << 16) | (nouveauG << 8) | nouveauB;
				
				sortie.setRGB(x, y, rgba);
			}
		}
		
		return sortie;
	}
}
