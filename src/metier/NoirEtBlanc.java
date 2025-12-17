package metier;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Classe pour convertir une image en noir et blanc (niveaux de gris)
 * @author Equipe 5
 */
public class NoirEtBlanc
{
	private ImageUtil imgUtil;
	private String fichierDest;

	/**
	 * Constructeur
	 * @param fichierSource Chemin de l'image source
	 * @param fichierDest Chemin du fichier destination
	 */
	public NoirEtBlanc(String fichierSource, String fichierDest)
	{
		this.imgUtil = new ImageUtil(fichierSource);
		this.fichierDest = fichierDest;
	}

	/**
	 * Convertit l'image en noir et blanc
	 */
	public void convertir()
	{
		BufferedImage biSource = this.imgUtil.getImage();
		BufferedImage biDest = convertir(biSource);
		
		this.imgUtil.setImage(biDest);
		this.imgUtil.sauvegarderImage(this.fichierDest);
	}

	/**
	 * Version statique : convertit une image en noir et blanc
	 * @param biSource Image source
	 * @return Image en niveaux de gris
	 */
	public static BufferedImage convertir(BufferedImage biSource)
	{
		BufferedImage biDest = new BufferedImage(biSource.getWidth(), biSource.getHeight(), BufferedImage.TYPE_INT_RGB);

		for (int y = 0; y < biSource.getHeight(); y++)
		{
			for (int x = 0; x < biSource.getWidth(); x++)
			{
				int coul = biSource.getRGB(x, y);
				int gris = ImageUtil.luminance(new Color(coul));
				int coulGris = (gris << 16) | (gris << 8) | gris;
				biDest.setRGB(x, y, coulGris);
			}
		}

		return biDest;
	}
}
