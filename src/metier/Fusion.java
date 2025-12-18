package metier;

import java.awt.image.BufferedImage;

/**
 * Classe pour fusionner deux images avec un effet de fondu
 * @author Equipe 5
 */
public class Fusion
{
	private ImageUtil imgUtil1;
	private ImageUtil imgUtil2;
	private String    fichierDest;

	/**
	 * Constructeur
	 * @param fichierImage1 Chemin de la première image (à gauche)
	 * @param fichierImage2 Chemin de la deuxième image (à droite)
	 * @param fichierDest Chemin du fichier destination
	 */
	public Fusion(String fichierImage1, String fichierImage2, String fichierDest)
	{
		this.imgUtil1    = new ImageUtil(fichierImage1);
		this.imgUtil2    = new ImageUtil(fichierImage2);
		this.fichierDest = fichierDest;
	}

	/**
	 * Fusionne les deux images avec un effet de fondu
	 * @param largeurFondue Largeur de la zone de fondu en pixels
	 */
	public void fusionnerAvecFondu(int largeurFondue)
	{
		BufferedImage imgGauche, imgDroite;
		BufferedImage imgResultat;

		imgGauche   = this.imgUtil1.getImage();
		imgDroite   = this.imgUtil2.getImage();
		imgResultat = Fusion.fusionnerAvecFondu(imgGauche, imgDroite, largeurFondue);
		
		this.imgUtil1.setImage(imgResultat);
		this.imgUtil1.sauvegarderImage(this.fichierDest);
	}

	/**
	 * Version statique : fusionne deux images avec un effet de fondu
	 * @param imgGauche Image de gauche
	 * @param imgDroite Image de droite
	 * @param largeurFondue Largeur de la zone de fondu en pixels
	 * @return L'image fusionnée avec effet de fondu
	 */
	public static BufferedImage fusionnerAvecFondu(BufferedImage imgGauche, BufferedImage imgDroite, int largeurFondue)
	{
		int           largeurTotale, hauteurMax;
		BufferedImage imgResultat;
		int           x, y;
		int           debutFondue, finFondue;
		float         alpha;
		int           couleurGauche, couleurDroite;
		int           xDroite, xGauche;
		int           rougeGauche, vertGauche, bleuGauche, alphaGauche;
		int           rougeDroite, vertDroite, bleuDroite, alphaDroite;
		int           rougeFinal, vertFinal, bleuFinal, alphaFinal;
		int           couleurFinal;

		largeurTotale = imgGauche.getWidth() + imgDroite.getWidth();
		hauteurMax    = Math.max(imgGauche.getHeight(), imgDroite.getHeight());
		imgResultat   = new BufferedImage(largeurTotale, hauteurMax, BufferedImage.TYPE_INT_ARGB);

		// Copier l'image gauche
		for (x = 0; x < imgGauche.getWidth(); x++)
		{
			for (y = 0; y < imgGauche.getHeight(); y++)
			{
				imgResultat.setRGB(x, y, imgGauche.getRGB(x, y));
			}
		}

		// Copier l'image droite
		for (x = 0; x < imgDroite.getWidth(); x++)
		{
			for (y = 0; y < imgDroite.getHeight(); y++)
			{
				imgResultat.setRGB(x + imgGauche.getWidth(), y, imgDroite.getRGB(x, y));
			}
		}

		// Appliquer le fondu
		debutFondue = imgGauche.getWidth() - largeurFondue / 2;
		finFondue   = imgGauche.getWidth() + largeurFondue / 2;

		// S'assurer que la zone de fondu est valide
		if (debutFondue < 0)            { debutFondue = 0;              }
		if (finFondue > largeurTotale)  { finFondue = largeurTotale;   }

		for (x = debutFondue; x < finFondue; x++)
		{
			for (y = 0; y < hauteurMax; y++)
			{
				// Calculer alpha (progression du fondu de 0 à 1)
				alpha = (float)(x - debutFondue) / (float)(finFondue - debutFondue);

				couleurGauche = 0;
				couleurDroite = 0;

				// Déterminer les coordonnées selon la position
				if (x < imgGauche.getWidth())
				{
					// Zone de fondu dans l'image gauche
					xDroite = x - debutFondue;
					
					if (y < imgGauche.getHeight())
					{
						couleurGauche = imgGauche.getRGB(x, y);
					}
					if (xDroite >= 0 && xDroite < imgDroite.getWidth() && y < imgDroite.getHeight())
					{
						couleurDroite = imgDroite.getRGB(xDroite, y);
					}
				}
				else
				{
					// Zone de fondu dans l'image droite
					xDroite = x - imgGauche.getWidth();
					xGauche = imgGauche.getWidth() - 1 - (x - imgGauche.getWidth());
					
					if (xGauche >= 0 && xGauche < imgGauche.getWidth() && y < imgGauche.getHeight())
					{
						couleurGauche = imgGauche.getRGB(xGauche, y);
					}
					if (xDroite >= 0 && xDroite < imgDroite.getWidth() && y < imgDroite.getHeight())
					{
						couleurDroite = imgDroite.getRGB(xDroite, y);
					}
				}

				// Extraire les composantes RGB
				rougeGauche = (couleurGauche >> 16) & 0xFF;
				vertGauche  = (couleurGauche >> 8)  & 0xFF;
				bleuGauche  = couleurGauche         & 0xFF;
				alphaGauche = (couleurGauche >> 24) & 0xFF;

				rougeDroite = (couleurDroite >> 16) & 0xFF;
				vertDroite  = (couleurDroite >> 8)  & 0xFF;
				bleuDroite  = couleurDroite         & 0xFF;
				alphaDroite = (couleurDroite >> 24) & 0xFF;

				// Interpolation linéaire
				rougeFinal = Math.round(rougeGauche * (1 - alpha) + rougeDroite * alpha);
				vertFinal  = Math.round(vertGauche  * (1 - alpha) + vertDroite  * alpha);
				bleuFinal  = Math.round(bleuGauche  * (1 - alpha) + bleuDroite  * alpha);
				alphaFinal = Math.round(alphaGauche * (1 - alpha) + alphaDroite * alpha);

				couleurFinal = (alphaFinal << 24) | (rougeFinal << 16) | (vertFinal << 8) | bleuFinal;
				
				imgResultat.setRGB(x, y, couleurFinal);
			}
		}

		return imgResultat;
	}
}
