package metier;

import java.awt.image.BufferedImage;

/**
 * Classe pour convertir une image en négatif (inversion des couleurs)
 */
public class Negatif
{
	private ImageUtil imgUtil;
	private String fichierSource;
	private String fichierDest;

	/**
	 * Constructeur fichier -> fichier (mode sauvegarde)
	 */
	public Negatif(String fichierSource, String fichierDest)
	{
		this.fichierSource = fichierSource;
		this.fichierDest   = fichierDest;
		this.imgUtil       = new ImageUtil(fichierSource);
	}

	/**
	 * Applique le négatif et sauvegarde vers le fichier destination.
	 */
	public void appliquer()
	{
		BufferedImage src = this.imgUtil.getImage();
		BufferedImage out = appliquerNegatif(src);
		this.imgUtil.setImage(out);
		this.imgUtil.sauvegarderImage(this.fichierDest);
	}

	/**
	 * Méthode statique: retourne l'image passée en négatif (alpha préservé)
	 */
	public static BufferedImage appliquerNegatif(BufferedImage src)
	{
		int w = src.getWidth();
		int h = src.getHeight();
		BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		for (int y = 0; y < h; y++)
		{
			for (int x = 0; x < w; x++)
			{
				int pixel = src.getRGB(x, y);
				int a = (pixel >>> 24) & 0xFF;
				int r = (pixel >>> 16) & 0xFF;
				int g = (pixel >>> 8)  & 0xFF;
				int b =  pixel         & 0xFF;

				int nr = 255 - r;
				int ng = 255 - g;
				int nb = 255 - b;

				int rgba = (a << 24) | (nr << 16) | (ng << 8) | nb;
				out.setRGB(x, y, rgba);
			}
		}
		return out;
	}
}
