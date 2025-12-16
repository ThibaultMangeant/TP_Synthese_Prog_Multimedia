package metier;

import java.awt.image.BufferedImage;

/**
 * Classe pour la superposition d'images avec gestion de la transparence
 */
public class Superposition
{
	private ImageUtil imgUtil1;
	private ImageUtil imgUtil2;
	private String    cheminImage1;
	private String    cheminImage2;

	/**
	 * Constructeur
	 * 
	 * @param cheminImage1 Chemin de la première image (base)
	 * @param cheminImage2 Chemin de la deuxième image (à superposer)
	 */
	public Superposition(String cheminImage1, String cheminImage2)
	{
		this.cheminImage1 = cheminImage1;
		this.cheminImage2 = cheminImage2;

		this.imgUtil1 = new ImageUtil(cheminImage1);
		this.imgUtil2 = new ImageUtil(cheminImage2);
	}

	/**
	 * Superpose imgUtil2 sur imgUtil1 aux coordonnees donnees
	 * 
	 * @param position Position de la superposition
	 */
	public void superposer(Point position)
	{
		BufferedImage image1, image2;
		int           largeur2, hauteur2;
		int           pixel1, pixel2;
		int           alpha2;
		int           r1, g1, b1;
		int           r2, g2, b2;
		int           rFinal, gFinal, bFinal, pixelFinal;

		image1 = imgUtil1.getImage();
		image2 = imgUtil2.getImage();

		largeur2 = image2.getWidth();
		hauteur2 = image2.getHeight();

		for (int i = 0; i < largeur2; i++)
		{
			for (int j = 0; j < hauteur2; j++)
			{
				pixel2 = image2.getRGB(i, j);
				alpha2 = (pixel2 >> 24) & 0xff;

				if (alpha2 > 0)
				{ // Si le pixel n'est pas totalement transparent
					pixel1 = image1.getRGB(position.x + i, position.y + j);

					// Calcul de la nouvelle couleur en fonction de l'alpha
					r1 = (pixel1 >> 16) & 0xff;
					g1 = (pixel1 >> 8) & 0xff;
					b1 = pixel1 & 0xff;

					r2 = (pixel2 >> 16) & 0xff;
					g2 = (pixel2 >> 8) & 0xff;
					b2 = pixel2 & 0xff;

					rFinal = (r2 * alpha2 + r1 * (255 - alpha2)) / 255;
					gFinal = (g2 * alpha2 + g1 * (255 - alpha2)) / 255;
					bFinal = (b2 * alpha2 + b1 * (255 - alpha2)) / 255;

					pixelFinal = (255 << 24) | (rFinal << 16) | (gFinal << 8) | bFinal;
					image1.setRGB(position.x + i, position.y + j, pixelFinal);
				}
			}
		}
	}
	
	/**
	 * Superpose imgUtil2 sur imgUtil1 aux coordonnees (x, y)
	 * 
	 * @param x Position x de la superposition
	 * @param y Position y de la superposition
	 */
	public void superposer(int x, int y)
	{
		superposer(new Point(x, y));
	}
}
