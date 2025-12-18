package metier;

import java.awt.image.BufferedImage;


/**
 * Classe pour gérer le contraste et la lumière
 * @author Equipe 5
 */
public class Contraste
{

	private String    fichierDest;
	private ImageUtil imgUtil;

	public Contraste(String fichierSource, String fichierDest)
	{
		this.fichierDest   = fichierDest;
		this.imgUtil       = new ImageUtil(fichierSource);
	}

	public void appliquerContraste(int contraste)
	{
		BufferedImage src;
		BufferedImage sortie;

		src    = this.imgUtil.getImage();
		sortie = Contraste.appliquerContraste(src, contraste);
		
		this.imgUtil.setImage(sortie);
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
		int           largeur, hauteur;
		int           x, y;
		BufferedImage sortie;
		double        c, c255, facteur;
		int           pixel;
		int           a, r, g, b;
		int           nouveauR, nouveauG, nouveauB;
		int           rgba;

		largeur = src.getWidth();
		hauteur = src.getHeight();
		sortie  = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_ARGB);

		// Convertit la valeur [-100..100] en échelle [-255..255] puis calcule le facteur standard
		c       = Math.max(-100, Math.min(100, contraste));
		c255    = c * 255.0 / 100.0;
		facteur = (259.0 * (c255 + 255.0)) / (255.0 * (259.0 - c255));

		for (x = 0; x < largeur; x++)
		{
			for (y = 0; y < hauteur; y++)
			{
				pixel = src.getRGB(x, y);
				
				a = (pixel >>> 24) & 0xFF;
				r = (pixel >>> 16) & 0xFF;
				g = (pixel >>> 8)  & 0xFF;
				b = pixel          & 0xFF;

				nouveauR = (int)Math.round(facteur * (r - 128) + 128);
				nouveauG = (int)Math.round(facteur * (g - 128) + 128);
				nouveauB = (int)Math.round(facteur * (b - 128) + 128);

				if      (nouveauR < 0)   { nouveauR = 0;   }
				else if (nouveauR > 255) { nouveauR = 255; }
				if      (nouveauG < 0)   { nouveauG = 0;   }
				else if (nouveauG > 255) { nouveauG = 255; }
				if      (nouveauB < 0)   { nouveauB = 0;   }
				else if (nouveauB > 255) { nouveauB = 255; }

				rgba = (a << 24) | (nouveauR << 16) | (nouveauG << 8) | (nouveauB);
				
				sortie.setRGB(x, y, rgba);
			}
		}

		return sortie;
	}
}
