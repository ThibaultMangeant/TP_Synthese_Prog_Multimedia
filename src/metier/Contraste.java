package metier;

import java.awt.image.BufferedImage;


/**
 * Classe pour gérer le contraste et la lumière
 */
public class Contraste
{

	String    fichierSource;
	String    fichierDest;
	ImageUtil imgUtil;

	public Contraste(String fichierSource, String fichierDest)
	{
		this.fichierSource = fichierSource;
		this.fichierDest   = fichierDest;
		this.imgUtil       = new ImageUtil(fichierSource);
	}

	public void appliquerContraste(int contraste)
	{
		BufferedImage src = this.imgUtil.getImage();
		BufferedImage out = appliquerContraste(src, contraste);
		this.imgUtil.setImage(out);
		this.imgUtil.sauvegarderImage(this.fichierDest);
	}

	/**
	 * Méthode statique: applique un contraste sur une image et retourne le résultat (préserve l'alpha).
	 * Le calcul suit: c' = clamp(c + (contraste * (c - 127)) / 100).
	 *
	 * @param src       Image source
	 * @param contraste Valeur du contraste (-100..100 typiquement)
	 * @return          Nouvelle image avec contraste appliqué
	 */
	public static BufferedImage appliquerContraste(BufferedImage src, int contraste)
	{
		int largeur = src.getWidth();
		int hauteur = src.getHeight();
		BufferedImage out = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_ARGB);

		// Convertit la valeur [-100..100] en échelle [-255..255] puis calcule le facteur standard
		double c = Math.max(-100, Math.min(100, contraste));
		double c255 = c * 255.0 / 100.0;
		double factor = (259.0 * (c255 + 255.0)) / (255.0 * (259.0 - c255));

		for (int x = 0; x < largeur; x++)
		{
			for (int y = 0; y < hauteur; y++)
			{
				int pixel = src.getRGB(x, y);
				int a = (pixel >>> 24) & 0xFF;
				int r = (pixel >>> 16) & 0xFF;
				int g = (pixel >>> 8)  & 0xFF;
				int b = pixel & 0xFF;

				int nr = (int)Math.round(factor * (r - 128) + 128);
				int ng = (int)Math.round(factor * (g - 128) + 128);
				int nb = (int)Math.round(factor * (b - 128) + 128);

				nr = Math.min(255, Math.max(0, nr));
				ng = Math.min(255, Math.max(0, ng));
				nb = Math.min(255, Math.max(0, nb));

				int rgba = (a << 24) | (nr << 16) | (ng << 8) | (nb);
				out.setRGB(x, y, rgba);
			}
		}

		return out;
	}
}
