package metier;

import java.awt.image.BufferedImage;

/**
 * Classe pour les effets miroir (flip horizontal/vertical)
 */
public class Miroir
{
	String fichierSource;
	String fichierDest;
	ImageUtil imgUtil;

	public Miroir(String fichierSource, String fichierDest)
	{
		this.fichierSource = fichierSource;
		this.fichierDest   = fichierDest;
		this.imgUtil       = new ImageUtil(fichierSource);
	}

	/**
	 * Retourne l'image comme dans un miroir horizontal (gauche <-> droite)
	 */
	public void miroirHorizontal()
	{
		BufferedImage src = this.imgUtil.getImage();
		int w = src.getWidth();
		int h = src.getHeight();

		BufferedImage out = new BufferedImage(w, h,
				src.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : src.getType());

		for (int y = 0; y < h; y++)
		{
			for (int x = 0; x < w; x++)
			{
				int rgb = src.getRGB(x, y);
				out.setRGB(w - 1 - x, y, rgb);
			}
		}

		this.imgUtil.setImage(out);
		this.imgUtil.sauvegarderImage(this.fichierDest);
	}

	/**
	 * Retourne l'image comme dans un miroir vertical (haut <-> bas)
	 */
	public void miroirVertical()
	{
		BufferedImage src = this.imgUtil.getImage();
		int w = src.getWidth();
		int h = src.getHeight();

		BufferedImage out = new BufferedImage(w, h,
				src.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : src.getType());

		for (int y = 0; y < h; y++)
		{
			for (int x = 0; x < w; x++)
			{
				int rgb = src.getRGB(x, y);
				out.setRGB(x, h - 1 - y, rgb);
			}
		}

		this.imgUtil.setImage(out);
		this.imgUtil.sauvegarderImage(this.fichierDest);
	}
}