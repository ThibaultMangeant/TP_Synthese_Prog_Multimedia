package metier;

import java.awt.image.BufferedImage;

public class Luminosite
{ 
	private ImageUtil imgUtil;
	private String    cheminImage;

	/**
	 * Constructeur
	 * 
	 * @param cheminImage Chemin de l'image
	 */
	public Luminosite(String cheminImage)
	{
		this.cheminImage = cheminImage;
		this.imgUtil     = new ImageUtil(cheminImage);
	}

	/**
	 * Applique un décalage de luminosité et sauvegarde vers le chemin fourni.
	 * @param valeur décalage à ajouter aux composantes R/V/B (négatif ou positif)
	 * @param fichierDest chemin de sortie (ex: "sortie.png")
	 */
	public void appliquerLuminosite(int valeur, String fichierDest)
	{
		BufferedImage src = this.imgUtil.getImage();
		BufferedImage out = appliquerLuminosite(src, valeur);
		this.imgUtil.setImage(out);
		this.imgUtil.sauvegarderImage(fichierDest);
	}

	/**
	 * Méthode statique: applique un décalage de luminosité sur une image.
	 * Préserve l'alpha et effectue un clamp [0..255] pour R/V/B.
	 */
	public static BufferedImage appliquerLuminosite(BufferedImage src, int valeur)
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

				int nr = r + valeur; if (nr < 0) nr = 0; else if (nr > 255) nr = 255;
				int ng = g + valeur; if (ng < 0) ng = 0; else if (ng > 255) ng = 255;
				int nb = b + valeur; if (nb < 0) nb = 0; else if (nb > 255) nb = 255;

				int rgba = (a << 24) | (nr << 16) | (ng << 8) | nb;
				out.setRGB(x, y, rgba);
			}
		}

		return out;
	}
}
