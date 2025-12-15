package metier;

import java.awt.image.BufferedImage;

/**
 * Classe pour la superposition d'images avec gestion de la transparence
 */
public class Superposition
{
	
	ImageUtil imgUtil1;
	ImageUtil imgUtil2;
	String cheminImage1;
	String cheminImage2;

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
	 * Superpose imgUtil2 sur imgUtil1 aux coordonnées (x, y)
	 * 
	 * @param x Position x de la superposition
	 * @param y Position y de la superposition
	 */
	public void superposer(int x, int y)
	{
		BufferedImage image1 = imgUtil1.getImage();
		BufferedImage image2 = imgUtil2.getImage();

		int largeur2 = image2.getWidth();
		int hauteur2 = image2.getHeight();

		for (int i = 0; i < largeur2; i++) {
			for (int j = 0; j < hauteur2; j++) {
				int pixel2 = image2.getRGB(i, j);
				int alpha2 = (pixel2 >> 24) & 0xff;

				if (alpha2 > 0) { // Si le pixel n'est pas totalement transparent
					int pixel1 = image1.getRGB(x + i, y + j);

					// Calcul de la nouvelle couleur en fonction de l'alpha
					int r1 = (pixel1 >> 16) & 0xff;
					int g1 = (pixel1 >> 8) & 0xff;
					int b1 = pixel1 & 0xff;

					int r2 = (pixel2 >> 16) & 0xff;
					int g2 = (pixel2 >> 8) & 0xff;
					int b2 = pixel2 & 0xff;

					int rFinal = (r2 * alpha2 + r1 * (255 - alpha2)) / 255;
					int gFinal = (g2 * alpha2 + g1 * (255 - alpha2)) / 255;
					int bFinal = (b2 * alpha2 + b1 * (255 - alpha2)) / 255;

					int pixelFinal = (255 << 24) | (rFinal << 16) | (gFinal << 8) | bFinal;
					image1.setRGB(x + i, y + j, pixelFinal);
				}
			}
		}
	}
}
