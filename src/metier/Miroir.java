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
		this.imgUtil = new ImageUtil(fichierSource);
	}

	/**
	 * Retourne l'image comme dans un miroir horizontal (gauche <-> droite)
	 * Effectue une symétrie axiale verticale.
	 */
	public void miroirHorizontal()
	{
		BufferedImage src, sortie;

		src    = this.imgUtil.getImage();
		sortie = Miroir.appliquerMiroirHorizontal(src);
		
		this.imgUtil.setImage(sortie);
	}

	/**
	 * Retourne l'image comme dans un miroir vertical (haut <-> bas)
	 * Effectue une symétrie axiale horizontale.
	 */
	public void miroirVertical()
	{
		BufferedImage src, sortie;

		src    = this.imgUtil.getImage();
		sortie = Miroir.appliquerMiroirVertical(src);
		
		this.imgUtil.setImage(sortie);
	}

	/**
	 * Applique un miroir horizontal sur une image source et renvoie le résultat.
	 */
	public static BufferedImage appliquerMiroirHorizontal(BufferedImage src)
	{
		BufferedImage sortie;
		int           largeur, hauteur;
		int           x, y;
		int           typeImage;

		largeur = src.getWidth();
		hauteur = src.getHeight();
		
		typeImage = src.getType();
		if (typeImage == 0)
		{
			typeImage = BufferedImage.TYPE_INT_ARGB;
		}
		
		sortie = new BufferedImage(largeur, hauteur, typeImage);
		
		for (y = 0; y < hauteur; y++)
		{
			for (x = 0; x < largeur; x++)
			{
				sortie.setRGB(largeur - 1 - x, y, src.getRGB(x, y));
			}
		}
		
		return sortie;
	}

	/**
	 * Applique un miroir vertical sur une image source et renvoie le résultat.
	 */
	public static BufferedImage appliquerMiroirVertical(BufferedImage src)
	{
		BufferedImage sortie;
		int           largeur, hauteur;
		int           x, y;
		int           typeImage;

		largeur = src.getWidth();
		hauteur = src.getHeight();
		
		typeImage = src.getType();
		if (typeImage == 0) { typeImage = BufferedImage.TYPE_INT_ARGB; }
		
		sortie = new BufferedImage(largeur, hauteur, typeImage);
		
		for (y = 0; y < hauteur; y++)
		{
			for (x = 0; x < largeur; x++)
			{
				sortie.setRGB(x, hauteur - 1 - y, src.getRGB(x, y));
			}
		}
		
		return sortie;
	}
}