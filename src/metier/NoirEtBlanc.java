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
	private String    fichierDest;

	/**
	 * Constructeur
	 * @param fichierSource Chemin de l'image source
	 * @param fichierDest Chemin du fichier destination
	 */
	public NoirEtBlanc(String fichierSource, String fichierDest)
	{
		this.imgUtil     = new ImageUtil(fichierSource);
		this.fichierDest = fichierDest;
	}

	/**
	 * Convertit l'image en noir et blanc
	 */
	public void convertir()
	{
		BufferedImage imageSource;
		BufferedImage imageDestination;

		imageSource      = this.imgUtil.getImage();
		imageDestination = NoirEtBlanc.convertir(imageSource);
		
		this.imgUtil.setImage(imageDestination);
		this.imgUtil.sauvegarderImage(this.fichierDest);
	}

	/**
	 * Version statique : convertit une image en noir et blanc
	 * @param imageSource Image source
	 * @return Image en niveaux de gris
	 */
	public static BufferedImage convertir(BufferedImage imageSource)
	{
		BufferedImage imageDestination;
		int           largeur, hauteur;
		int           x, y;
		int           couleur;
		int           gris;
		int           couleurGris;

		largeur          = imageSource.getWidth ();
		hauteur          = imageSource.getHeight();
		imageDestination = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_RGB);

		for (y = 0; y < hauteur; y++)
		{
			for (x = 0; x < largeur; x++)
			{
				couleur     = imageSource.getRGB(x, y);
				gris        = ImageUtil.luminance(new Color(couleur));
				couleurGris = (gris << 16) | (gris << 8) | gris;
				
				imageDestination.setRGB(x, y, couleurGris);
			}
		}

		return imageDestination;
	}
}
