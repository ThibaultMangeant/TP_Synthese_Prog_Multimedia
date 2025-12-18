package metier;

import java.awt.image.BufferedImage;

/**
 * Classe pour gerer les teintes de couleurs
 * @author Equipe 5
 */
public class Teinte
{

	private ImageUtil imgUtil;
	private String    fichierDest;

	/**
	 * Constructeur
	 * 
	 * @param fichierSource Chemin de l'image source
	 * @param fichierDest Chemin du fichier destination
	 */
	public Teinte(String fichierSource, String fichierDest)
	{
		this.fichierDest = fichierDest;
		this.imgUtil     = new ImageUtil(fichierSource);
	}

	/**
	 * Appliquer une teinte a l'image
	 * 
	 * @param teinteRouge Ajustement de la composante rouge (-255 a +255)
	 * @param teinteVerte Ajustement de la composante verte (-255 a +255)
	 * @param teinteBleue Ajustement de la composante bleue (-255 a +255)
	 */
	public void teinter(int teinteRouge, int teinteVerte, int teinteBleue)
	{
		BufferedImage source;
		BufferedImage sortie;

		source = this.imgUtil.getImage();
		sortie = Teinte.appliquerTeinte(source, teinteRouge, teinteVerte, teinteBleue);
		
		this.imgUtil.setImage(sortie);
		this.imgUtil.sauvegarderImage(this.fichierDest);
	}

	/**
	 * Methode statique: applique une teinte RGB sur une image et retourne le resultat.
	 * @param source Image source
	 * @param teinteRouge delta rouge (-255..255)
	 * @param teinteVerte delta vert (-255..255)
	 * @param teinteBleue delta bleu (-255..255)
	 * @return Image teintee
	 */
	public static BufferedImage appliquerTeinte(BufferedImage source, int teinteRouge, int teinteVerte, int teinteBleue)
	{
		BufferedImage sortie;
		int           largeur, hauteur;
		int           x, y;
		int           pixel;
		int           a, r, g, b;
		int           nouveauR, nouveauG, nouveauB;
		int           rgba;

		largeur = source.getWidth();
		hauteur = source.getHeight();
		sortie  = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_ARGB);

		for (x = 0; x < largeur; x++)
		{
			for (y = 0; y < hauteur; y++)
			{
				pixel = source.getRGB(x, y);
				
				// Extraire RGBA correctement
				a = (pixel >>> 24) & 0xFF;
				r = (pixel >>> 16) & 0xFF;
				g = (pixel >>> 8)  & 0xFF;
				b = pixel          & 0xFF;

				nouveauR = r + teinteRouge;
				nouveauG = g + teinteVerte;
				nouveauB = b + teinteBleue;
				
				if (nouveauR < 0) { nouveauR = 0; } else if (nouveauR > 255) { nouveauR = 255; }
				if (nouveauG < 0) { nouveauG = 0; } else if (nouveauG > 255) { nouveauG = 255; }
				if (nouveauB < 0) { nouveauB = 0; } else if (nouveauB > 255) { nouveauB = 255; }

				rgba = (a << 24) | (nouveauR << 16) | (nouveauG << 8) | nouveauB;
				
				sortie.setRGB(x, y, rgba);
			}
		}

		return sortie;
	}
}