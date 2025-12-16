package metier;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
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
		this.fichierDest   = fichierDest;
		this.imgUtil       = new ImageUtil(fichierSource);
	}

	/**
	 * Cree une image de la taille du texte avec l'image source comme texture
	 * Seules les lettres sont visibles avec l'image dedans
	 * 
	 * @param texte Le texte a afficher
	 * @param taillePolice Taille de la police
	 */
	public void creerTexteImage(String texte, int taillePolice)
	{
		BufferedImage imgSource = this.imgUtil.getImage();
		
		// Calcul de la taille du texte
		BufferedImage imgTemp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D    g2dTemp = imgTemp.createGraphics();
		Font          police  = new Font("Arial", Font.BOLD, taillePolice);

		g2dTemp.setFont(police);
		
		FontRenderContext contexte = g2dTemp.getFontRenderContext();
		Rectangle2D       limites  = police.getStringBounds(texte, contexte);
		g2dTemp.dispose();
		
		// Dimensions du texte
		int largeurTexte = (int) Math.ceil(limites.getWidth());
		int hauteurTexte = (int) Math.ceil(limites.getHeight());
		int posY         = (int) Math.ceil(-limites.getY());
		
		// Creation du masque du texte
		BufferedImage masqueTexte = new BufferedImage(largeurTexte, hauteurTexte, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2dMasque = masqueTexte.createGraphics();
		g2dMasque.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2dMasque.setFont(police);
		g2dMasque.setColor(Color.WHITE);
		g2dMasque.drawString(texte, 0, posY);
		g2dMasque.dispose();
		
		// Creation de l'image finale de la taille du texte
		BufferedImage imgFinale = new BufferedImage(largeurTexte, hauteurTexte, BufferedImage.TYPE_INT_ARGB);
		
		// Application du masque : copier les pixels de l'image source ou le texte est
		for (int y = 0; y < hauteurTexte; y++)
		{
			for (int x = 0; x < largeurTexte; x++)
			{
				// Si le pixel fait partie du texte
				int alpha = (masqueTexte.getRGB(x, y) >> 24) & 0xff;
				
				if (alpha > 0)
				{
					// Copier le pixel correspondant de l'image source
					int xs      = x % imgSource.getWidth();
					int ys      = y % imgSource.getHeight();
					int couleur = imgSource.getRGB(xs, ys);
					
					imgFinale.setRGB(x, y, couleur);
				}
				// Sinon le pixel reste transparent
			}
		}
		
		// Sauvegarde
		this.imgUtil.setImage(imgFinale);
		this.imgUtil.sauvegarderImage(this.fichierDest);
	}

	/**
	 * Cree uniquement le masque du texte (contour blanc sur fond transparent)
	 * 
	 * @param texte Le texte a afficher
	 * @param taillePolice Taille de la police
	 * @return Le masque du texte
	 */
	public static BufferedImage creerMasqueTexte(String texte, int taillePolice)
	{
		// Calcul de la taille du texte
		BufferedImage imgTemp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D    g2dTemp = imgTemp.createGraphics();
		Font          police  = new Font("Arial", Font.BOLD, taillePolice);

		g2dTemp.setFont(police);
		
		FontRenderContext contexte = g2dTemp.getFontRenderContext();
		Rectangle2D       limites  = police.getStringBounds(texte, contexte);
		g2dTemp.dispose();
		
		// Dimensions du texte
		int largeurTexte = (int) Math.ceil(limites.getWidth());
		int hauteurTexte = (int) Math.ceil(limites.getHeight());
		int posY         = (int) Math.ceil(-limites.getY());
		
		// Creation du masque du texte
		BufferedImage masqueTexte = new BufferedImage(largeurTexte, hauteurTexte, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2dMasque = masqueTexte.createGraphics();
		g2dMasque.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2dMasque.setFont(police);
		g2dMasque.setColor(Color.WHITE);
		g2dMasque.drawString(texte, 0, posY);
		g2dMasque.dispose();
		
		return masqueTexte;
	}

	/**
	 * Applique une image de fond dans un masque de texte a une position donnee
	 * 
	 * @param masqueTexte Le masque de texte
	 * @param imgFond L'image de fond
	 * @param posTexteX Position X du texte sur l'image de fond
	 * @param posTexteY Position Y du texte sur l'image de fond
	 * @param posFondX Position X de l'image de fond a l'ecran
	 * @param posFondY Position Y de l'image de fond a l'ecran
	 * @return L'image du texte remplie avec les pixels de l'image de fond
	 */
	public static BufferedImage appliquerImageDansTexte(BufferedImage masqueTexte, BufferedImage imgFond, 
	                                                     int posTexteX, int posTexteY, 
	                                                     int posFondX, int posFondY)
	{
		int largeurTexte = masqueTexte.getWidth();
		int hauteurTexte = masqueTexte.getHeight();
		
		// Creation de l'image finale
		BufferedImage imgFinale = new BufferedImage(largeurTexte, hauteurTexte, BufferedImage.TYPE_INT_ARGB);
		
		// Application du masque : copier les pixels de l'image de fond la ou le texte est
		for (int y = 0; y < hauteurTexte; y++)
		{
			for (int x = 0; x < largeurTexte; x++)
			{
				// Si le pixel fait partie du texte
				int alpha = (masqueTexte.getRGB(x, y) >> 24) & 0xff;
				
				if (alpha > 0)
				{
					// Calculer la position correspondante dans l'image de fond
					int xFond = (posTexteX + x) - posFondX;
					int yFond = (posTexteY + y) - posFondY;
					
					// Verifier que la position est dans l'image de fond
					if (xFond >= 0 && xFond < imgFond.getWidth() && yFond >= 0 && yFond < imgFond.getHeight())
					{
						int couleur = imgFond.getRGB(xFond, yFond);
						imgFinale.setRGB(x, y, couleur);
					}
				}
				// Sinon le pixel reste transparent
			}
		}
		
		return imgFinale;
	}

	/**
	 * Version statique : cree une image de la taille du texte avec l'image source comme texture
	 * Seules les lettres sont visibles avec l'image dedans
	 * 
	 * @param imgSource L'image source a utiliser comme texture
	 * @param texte Le texte a afficher
	 * @param taillePolice Taille de la police
	 * @return L'image finale avec le texte texture
	 */
	public static BufferedImage creerTexteImage(BufferedImage imgSource, String texte, int taillePolice)
	{
		// Calcul de la taille du texte
		BufferedImage imgTemp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D    g2dTemp = imgTemp.createGraphics();
		Font          police  = new Font("Arial", Font.BOLD, taillePolice);

		g2dTemp.setFont(police);
		
		FontRenderContext contexte = g2dTemp.getFontRenderContext();
		Rectangle2D       limites  = police.getStringBounds(texte, contexte);
		g2dTemp.dispose();
		
		// Dimensions du texte
		int largeurTexte = (int) Math.ceil(limites.getWidth());
		int hauteurTexte = (int) Math.ceil(limites.getHeight());
		int posY         = (int) Math.ceil(-limites.getY());
		
		// Creation du masque du texte
		BufferedImage masqueTexte = new BufferedImage(largeurTexte, hauteurTexte, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2dMasque = masqueTexte.createGraphics();
		g2dMasque.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2dMasque.setFont(police);
		g2dMasque.setColor(Color.WHITE);
		g2dMasque.drawString(texte, 0, posY);
		g2dMasque.dispose();
		
		// Creation de l'image finale de la taille du texte
		BufferedImage imgFinale = new BufferedImage(largeurTexte, hauteurTexte, BufferedImage.TYPE_INT_ARGB);
		
		// Application du masque : copier les pixels de l'image source ou le texte est
		for (int y = 0; y < hauteurTexte; y++)
		{
			for (int x = 0; x < largeurTexte; x++)
			{
				// Si le pixel fait partie du texte
				int alpha = (masqueTexte.getRGB(x, y) >> 24) & 0xff;
				
				if (alpha > 0)
				{
					// Copier le pixel correspondant de l'image source
					int xs      = x % imgSource.getWidth();
					int ys      = y % imgSource.getHeight();
					int couleur = imgSource.getRGB(xs, ys);
					
					imgFinale.setRGB(x, y, couleur);
				}
				// Sinon le pixel reste transparent
			}
		}
		
		return imgFinale;
	}
}
