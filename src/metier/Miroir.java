package metier;

import java.awt.image.BufferedImage;

/**
 * Classe pour les effets miroir (flip horizontal/vertical)
 * @author Equipe 5
 */
public class Miroir
{
	private ImageUtil imgUtil;

	/**
	 * Constructeur de la classe Miroir
	 * @param fichierSource Chemin du fichier image source
	 */
	public Miroir(String fichierSource)
	{
		this.imgUtil       = new ImageUtil(fichierSource);
	}

	/**
	 * Retourne l'image comme dans un miroir horizontal (gauche <-> droite)
	 * Effectue une symétrie axiale verticale.
	 */
	public void miroirHorizontal()
	{
		BufferedImage src, out;

		// Récupération de l'image source et de ses dimensions
		src = this.imgUtil.getImage();
		out = appliquerMiroirHorizontal(src);
		this.imgUtil.setImage(out);
	}

	/**
	 * Retourne l'image comme dans un miroir vertical (haut <-> bas)
	 * Effectue une symétrie axiale horizontale.
	 */
	public void miroirVertical()
	{
		BufferedImage src, out;

		// Récupération de l'image source et de ses dimensions
		src = this.imgUtil.getImage();
		out = appliquerMiroirVertical(src);
		this.imgUtil.setImage(out);
	}

	/**
	 * Applique un miroir horizontal sur une image source et renvoie le résultat.
	 */
	public static BufferedImage appliquerMiroirHorizontal(BufferedImage src)
	{
		BufferedImage out;
		int w, h;

		w = src.getWidth();
		h = src.getHeight();
		out = new BufferedImage(w, h, src.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : src.getType());
		for (int y = 0; y < h; y++)
		{
			for (int x = 0; x < w; x++)
			{
				out.setRGB(w - 1 - x, y, src.getRGB(x, y));
			}
		}
		return out;
	}

	/**
	 * Applique un miroir vertical sur une image source et renvoie le résultat.
	 */
	public static BufferedImage appliquerMiroirVertical(BufferedImage src)
	{
		BufferedImage out;
		int w, h;

		w = src.getWidth();
		h = src.getHeight();
		out = new BufferedImage(w, h, src.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : src.getType());
		for (int y = 0; y < h; y++)
		{
			for (int x = 0; x < w; x++)
			{
				out.setRGB(x, h - 1 - y, src.getRGB(x, y));
			}
		}
		return out;
	}
}