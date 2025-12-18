package metier;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Classe pour creer du texte avec une image en fond
 * L'image apparait uniquement dans les lettres du texte
 */
public class TexteImage
{
	private ImageUtil imgUtil;
	private String    fichierDest;

	/**
	 * Constructeur
	 * 
	 * @param fichierSource Chemin de l'image de fond
	 * @param fichierDest Chemin du fichier destination
	 */
	public TexteImage(String fichierSource, String fichierDest)
	{
		this.fichierDest = fichierDest;
		this.imgUtil     = new ImageUtil(fichierSource);
	}

	/**
	 * Cree une image de la taille du texte avec l'image source comme texture
	 * 
	 * @param texte Le texte a afficher
	 * @param taillePolice Taille de la police
	 */
	public void creerTexteImage(String texte, int taillePolice)
	{
		BufferedImage imageSource, masqueTexte, imageFinale;
		int           largeurMasque, hauteurMasque;
		int           x, y;
		int           alpha;
		int           xSource, ySource;
		int           couleur;

		imageSource   = this.imgUtil.getImage();
		masqueTexte   = TexteImage.creerMasqueTexte(texte, taillePolice);
		largeurMasque = masqueTexte.getWidth ();
		hauteurMasque = masqueTexte.getHeight();
		imageFinale   = new BufferedImage(largeurMasque, hauteurMasque, BufferedImage.TYPE_INT_ARGB);
		
		for (y = 0; y < hauteurMasque; y++)
		{
			for (x = 0; x < largeurMasque; x++)
			{
				alpha = (masqueTexte.getRGB(x, y) >> 24) & 0xFF;
				
				if (alpha > 0)
				{
					xSource = x % imageSource.getWidth ();
					ySource = y % imageSource.getHeight();
					couleur = imageSource.getRGB(xSource, ySource);
					
					imageFinale.setRGB(x, y, couleur);
				}
			}
		}
		
		this.imgUtil.setImage(imageFinale);
		this.imgUtil.sauvegarderImage(this.fichierDest);
	}

	/**
	 * Cree le masque du texte
	 * 
	 * @param texte Le texte a afficher
	 * @param taillePolice Taille de la police
	 * @return Le masque du texte
	 */
	public static BufferedImage creerMasqueTexte(String texte, int taillePolice)
	{
		BufferedImage masqueTexte;
		Graphics2D    graphique;
		Font          police;

		masqueTexte = new BufferedImage(taillePolice * texte.length(), taillePolice * 2, BufferedImage.TYPE_INT_ARGB);
		graphique   = masqueTexte.createGraphics();
		police      = new Font("Arial", Font.BOLD, taillePolice);
		
		graphique.setFont(police);
		graphique.setColor(Color.WHITE);
		graphique.drawString(texte, 0, taillePolice);
		graphique.dispose();
		
		return masqueTexte;
	}

	/**
	 * Applique une image de fond dans un masque de texte
	 * 
	 * @param masqueTexte Le masque de texte
	 * @param imageFond L'image de fond
	 * @param posTexteX Position X du texte
	 * @param posTexteY Position Y du texte
	 * @param posFondX Position X du fond
	 * @param posFondY Position Y du fond
	 * @return L'image du texte remplie
	 */
	public static BufferedImage appliquerImageDansTexte(BufferedImage masqueTexte, BufferedImage imageFond, int posTexteX, int posTexteY, int posFondX, int posFondY)
	{
		BufferedImage imageFinale;
		int           largeurMasque, hauteurMasque;
		int           largeurFond, hauteurFond;
		int           x, y;
		int           alpha;
		int           xFond, yFond;
		int           couleur;

		largeurMasque = masqueTexte.getWidth ();
		hauteurMasque = masqueTexte.getHeight();
		largeurFond   = imageFond  .getWidth ();
		hauteurFond   = imageFond  .getHeight();
		imageFinale   = new BufferedImage(largeurMasque, hauteurMasque, BufferedImage.TYPE_INT_ARGB);
		
		for (y = 0; y < hauteurMasque; y++)
		{
			for (x = 0; x < largeurMasque; x++)
			{
				alpha = (masqueTexte.getRGB(x, y) >> 24) & 0xFF;
				
				if (alpha > 0)
				{
					xFond = (posTexteX + x) - posFondX;
					yFond = (posTexteY + y) - posFondY;
					
					if (xFond >= 0 && xFond < largeurFond && yFond >= 0 && yFond < hauteurFond)
					{
						couleur = imageFond.getRGB(xFond, yFond);
						
						imageFinale.setRGB(x, y, couleur);
					}
				}
			}
		}
		
		return imageFinale;
	}
}
