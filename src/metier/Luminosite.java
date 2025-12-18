package metier;

import java.awt.image.BufferedImage;

/**
 * Classe pour gérer la luminosité d'une image
 * @author Equipe 5
 */
public class Luminosite
{ 
	private ImageUtil imgUtil;

	/**
	 * Constructeur
	 * 
	 * @param cheminImage Chemin de l'image
	 */
	public Luminosite(String cheminImage)
	{
		this.imgUtil = new ImageUtil(cheminImage);
	}

	/**
	 * Applique un décalage de luminosité et sauvegarde vers le chemin fourni.
	 * @param valeur décalage à ajouter aux composantes R/V/B (négatif ou positif)
	 * @param fichierDest chemin de sortie (ex: "sortie.png")
	 */
	public void appliquerLuminosite(int valeur, String fichierDest)
	{
		BufferedImage src;
		BufferedImage sortie;

		src    = this.imgUtil.getImage();
		sortie = Luminosite.appliquerLuminosite(src, valeur);
		
		this.imgUtil.setImage(sortie);
		this.imgUtil.sauvegarderImage(fichierDest);
	}

	/**
	 * Méthode statique: applique un décalage de luminosité sur une image.
	 * Préserve l'alpha et effectue un clamp [0..255] pour R/V/B.
	 */
	public static BufferedImage appliquerLuminosite(BufferedImage src, int valeur)
	{
		int           largeur, hauteur;
		BufferedImage sortie;
		int           x, y;
		int           pixel;
		int           a, r, g, b;
		int           nouveauR, nouveauG, nouveauB;
		int           rgba;

		largeur = src.getWidth();
		hauteur = src.getHeight();
		sortie  = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_ARGB);

		for (y = 0; y < hauteur; y++)
		{
			for (x = 0; x < largeur; x++)
			{
				pixel = src.getRGB(x, y);
				
				a = (pixel >>> 24) & 0xFF;
				r = (pixel >>> 16) & 0xFF;
				g = (pixel >>> 8)  & 0xFF;
				b = pixel          & 0xFF;

				nouveauR = r + valeur;
				nouveauG = g + valeur;
				nouveauB = b + valeur;
				
				if      (nouveauR < 0)   { nouveauR =   0; }
				else if (nouveauR > 255) { nouveauR = 255; }
				if      (nouveauG < 0)   { nouveauG =   0; }
				else if (nouveauG > 255) { nouveauG = 255; }
				if      (nouveauB < 0)   { nouveauB =   0; }
				else if (nouveauB > 255) { nouveauB = 255; }

				rgba = (a << 24) | (nouveauR << 16) | (nouveauG << 8) | nouveauB;
				
				sortie.setRGB(x, y, rgba);
			}
		}

		return sortie;
	}
}
