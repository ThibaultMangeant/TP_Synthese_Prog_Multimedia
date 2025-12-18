package metier;

import java.awt.image.BufferedImage;

/**
 * Classe pour l'anti-aliasing (lissage)
 * @author Equipe 5
 */
public class AntiAlias
{
	private ImageUtil utilitaireImage;
	private String     fichierDestination;

	/**
	 * Constructeur fichier -> fichier (mode sauvegarde).
	 * @param fichierSource chemin image source
	 * @param fichierDestination   chemin image destination
	 */
	public AntiAlias(String fichierSource, String fichierDestination)
	{
		this.fichierDestination  = fichierDestination;
		this.utilitaireImage     = new ImageUtil(fichierSource);
	}

	/**
	 * Applique un lissage (anti-aliasing) de type gaussien 3x3 et sauvegarde.
	 */
	public void lisser()
	{
		BufferedImage imageSource, imageSortie;

		imageSource = this.utilitaireImage.getImage();
		imageSortie = AntiAlias.appliquerAntiAliasing(imageSource);
		this.utilitaireImage.setImage(imageSortie);
		this.utilitaireImage.sauvegarderImage(this.fichierDestination);
	}

	/**
	 * Méthode statique: applique un lissage gaussien 3x3 (anti-aliasing) et retourne l'image lissée.
	 * Noyau utilisé (normalisation 1/16):
	 * 1 2 1
	 * 2 4 2
	 * 1 2 1
	 * Les composantes A,R,G,B sont traitées et clampées dans [0,255].
	 * @param src image source
	 * @return image lissée
	 */
	public static BufferedImage appliquerAntiAliasing(BufferedImage src)
	{
		int            largeur, hauteur;
		BufferedImage  imageSortie;
		int[]          noyau;
		int            sommeNoyau;
		int            accumulateurAlpha, accumulateurRouge, accumulateurVert, accumulateurBleu;
		int            indiceNoyau;
		int            ligneVoisine, colonneVoisine;
		int            pixel;
		int            alpha, rouge, vert, bleu;
		int            nouvelAlpha, nouveauRouge, nouveauVert, nouveauBleu;
		int            couleurRGBA;

		largeur     = src.getWidth();
		hauteur     = src.getHeight();
		imageSortie = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_ARGB);

		noyau = new int[]
		{
			1, 2, 1,
			2, 4, 2,
			1, 2, 1
		};
		sommeNoyau = 16;

		for (int ligne = 0; ligne < hauteur; ligne++)
		{
			for (int colonne = 0; colonne < largeur; colonne++)
			{
				accumulateurAlpha = accumulateurRouge = accumulateurVert = accumulateurBleu = 0;
				indiceNoyau       = 0;

				for (int j = -1; j <= 1; j++)
				{
					ligneVoisine = ligne + j;
					if (ligneVoisine < 0)             { ligneVoisine = 0;           }
					else if (ligneVoisine >= hauteur) { ligneVoisine = hauteur - 1; }

					for (int i = -1; i <= 1; i++)
					{
						colonneVoisine = colonne + i;
						if (colonneVoisine < 0)             { colonneVoisine = 0; }
						else if (colonneVoisine >= largeur) { colonneVoisine = largeur - 1; }

						pixel = src.getRGB(colonneVoisine, ligneVoisine);
						alpha = (pixel >>> 24) & 0xFF;
						rouge = (pixel >>> 16) & 0xFF;
						vert  = (pixel >>> 8)  & 0xFF;
						bleu  = pixel & 0xFF;

						accumulateurAlpha += noyau[indiceNoyau] * alpha;
						accumulateurRouge += noyau[indiceNoyau] * rouge;
						accumulateurVert  += noyau[indiceNoyau] * vert;
						accumulateurBleu  += noyau[indiceNoyau] * bleu;
						indiceNoyau++;
					}
				}

				nouvelAlpha  = accumulateurAlpha / sommeNoyau;
				nouveauRouge = accumulateurRouge / sommeNoyau;
				nouveauVert  = accumulateurVert  / sommeNoyau;
				nouveauBleu  = accumulateurBleu  / sommeNoyau;

				if (nouvelAlpha < 0)         { nouvelAlpha = 0;    }
				else if (nouvelAlpha > 255)  { nouvelAlpha = 255;  }
				if (nouveauRouge < 0)        { nouveauRouge = 0;   }
				else if (nouveauRouge > 255) { nouveauRouge = 255; }
				if (nouveauVert < 0)         { nouveauVert = 0;    }
				else if (nouveauVert > 255)  { nouveauVert = 255;  }
				if (nouveauBleu < 0)         { nouveauBleu = 0;    }
				else if (nouveauBleu > 255)  { nouveauBleu = 255;  }

				couleurRGBA = (nouvelAlpha << 24) | (nouveauRouge << 16) | (nouveauVert << 8) | nouveauBleu;
				imageSortie.setRGB(colonne, ligne, couleurRGBA);
			}
		}

		return imageSortie;
	}
}
