package controleur;

import ihm.FramePrincipale;
import metier.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class Controleur
{
	private FramePrincipale framePrincipale;
	private ImageUtil imageUtil;
	private String cheminImageCourant;
	
	// Historique pour undo/redo
	private Stack<BufferedImage> historiqueImages;
	private Stack<BufferedImage> imagesAnnulees;
	private static final int LIMITE_HISTORIQUE = 15;
	
	// Calque texte temporaire
	private BufferedImage calqueTexte;
	private int calqueTexteX;
	private int calqueTexteY;
	
	// Calque de superposition temporaire
	private BufferedImage calqueSuperposition;
	private int calqueSuperpositionX;
	private int calqueSuperpositionY;

	public Controleur()
	{
		// Initialisation avec l'image par defaut
		// Chemin relatif depuis le dossier bin/
		this.cheminImageCourant = "../src/images/david_tennant.png";
		this.imageUtil = new ImageUtil(this.cheminImageCourant);
		this.historiqueImages = new Stack<>();
		this.imagesAnnulees = new Stack<>();
		this.calqueTexte = null;
		this.calqueTexteX = 0;
		this.calqueTexteY = 0;
		this.calqueSuperposition = null;
		this.calqueSuperpositionX = 0;
		this.calqueSuperpositionY = 0;
		this.framePrincipale = new FramePrincipale(this);
	}

	public String getCheminImageCourant()
	{
		return this.cheminImageCourant;
	}

	public BufferedImage getImage()
	{
		return this.imageUtil.getImage();
	}

	/**
	 * Méthode importée depuis la version GitHub.
	 * Vérifie si les coordonnées sont dans l'image.
	 */
	public boolean contient(int x, int y)
	{
		return this.imageUtil.contient(x, y);
	}

	public int getPosX()
	{
		return this.imageUtil.getX0();
	}

	public int getPosY()
	{
		return this.imageUtil.getY0();
	}

	public void setPosX(int x)
	{
		this.imageUtil.setX0(x);
	}

	public void setPosY(int y)
	{
		this.imageUtil.setY0(y);
	}

	public BufferedImage getCalqueTexte()
	{
		return this.calqueTexte;
	}

	public int getCalqueTexteX()
	{
		return this.calqueTexteX;
	}

	public int getCalqueTexteY()
	{
		return this.calqueTexteY;
	}

	public void setCalqueTexteX(int x)
	{
		this.calqueTexteX = x;
	}

	public void setCalqueTexteY(int y)
	{
		this.calqueTexteY = y;
	}

	public boolean contientCalqueTexte(int x, int y)
	{
		if (this.calqueTexte == null) return false;
		return (x >= this.calqueTexteX && x < this.calqueTexteX + this.calqueTexte.getWidth() &&
		        y >= this.calqueTexteY && y < this.calqueTexteY + this.calqueTexte.getHeight());
	}

	public void fusionnerCalqueTexte()
	{
		if (this.calqueTexte != null)
		{
			this.sauvegarderEtat();
			BufferedImage imgFond = this.imageUtil.getImage();
			
			// Appliquer l'image de fond dans le masque de texte a la position actuelle
			BufferedImage texteRempli = TexteImage.appliquerImageDansTexte(
				this.calqueTexte, 
				imgFond, 
				this.calqueTexteX, 
				this.calqueTexteY, 
				this.getPosX(), 
				this.getPosY()
			);
			
			// Remplacer l'image par le texte rempli uniquement
			this.calqueTexte = null;
			this.imageUtil.setImage(texteRempli);
			this.imageUtil.setX0(this.calqueTexteX);
			this.imageUtil.setY0(this.calqueTexteY);
			this.framePrincipale.afficherImage(texteRempli);
		}
	}

	public BufferedImage getCalqueSuperposition()
	{
		return this.calqueSuperposition;
	}

	public int getCalqueSuperpositionX()
	{
		return this.calqueSuperpositionX;
	}

	public int getCalqueSuperpositionY()
	{
		return this.calqueSuperpositionY;
	}

	public void setCalqueSuperpositionX(int x)
	{
		this.calqueSuperpositionX = x;
	}

	public void setCalqueSuperpositionY(int y)
	{
		this.calqueSuperpositionY = y;
	}

	public boolean contientCalqueSuperposition(int x, int y)
	{
		if (this.calqueSuperposition == null) return false;
		return (x >= this.calqueSuperpositionX && x < this.calqueSuperpositionX + this.calqueSuperposition.getWidth() &&
		        y >= this.calqueSuperpositionY && y < this.calqueSuperpositionY + this.calqueSuperposition.getHeight());
	}

	public void fusionnerCalqueSuperposition()
	{
		if (this.calqueSuperposition != null)
		{
			this.sauvegarderEtat();
			BufferedImage imgFond = this.imageUtil.getImage();
			
			// Creer une image de la meme taille que le fond
			BufferedImage imgFinale = new BufferedImage(imgFond.getWidth(), imgFond.getHeight(), BufferedImage.TYPE_INT_ARGB);
			
			// Copier l'image de fond
			for (int y = 0; y < imgFond.getHeight(); y++)
			{
				for (int x = 0; x < imgFond.getWidth(); x++)
				{
					imgFinale.setRGB(x, y, imgFond.getRGB(x, y));
				}
			}
			
			// Superposer le calque avec alpha blending
			for (int y = 0; y < this.calqueSuperposition.getHeight(); y++)
			{
				for (int x = 0; x < this.calqueSuperposition.getWidth(); x++)
				{
					int posX = x + this.calqueSuperpositionX - this.getPosX();
					int posY = y + this.calqueSuperpositionY - this.getPosY();
					
					if (posX >= 0 && posX < imgFond.getWidth() && posY >= 0 && posY < imgFond.getHeight())
					{
						int rgbSup = this.calqueSuperposition.getRGB(x, y);
						int alpha = (rgbSup >> 24) & 0xff;
						
						if (alpha > 0)
						{
							int rgbFond = imgFinale.getRGB(posX, posY);
							
							int r1 = (rgbFond >> 16) & 0xff;
							int g1 = (rgbFond >> 8) & 0xff;
							int b1 = rgbFond & 0xff;
							
							int r2 = (rgbSup >> 16) & 0xff;
							int g2 = (rgbSup >> 8) & 0xff;
							int b2 = rgbSup & 0xff;
							
							int rFinal = (r2 * alpha + r1 * (255 - alpha)) / 255;
							int gFinal = (g2 * alpha + g1 * (255 - alpha)) / 255;
							int bFinal = (b2 * alpha + b1 * (255 - alpha)) / 255;
							
							int pixelFinal = (255 << 24) | (rFinal << 16) | (gFinal << 8) | bFinal;
							imgFinale.setRGB(posX, posY, pixelFinal);
						}
					}
				}
			}
			
			this.calqueSuperposition = null;
			this.imageUtil.setImage(imgFinale);
			this.framePrincipale.afficherImage(imgFinale);
		}
	}

	private BufferedImage copierImage(BufferedImage src)
	{
		BufferedImage copie = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
		copie.getGraphics().drawImage(src, 0, 0, null);
		return copie;
	}
	
	private void sauvegarderEtat()
	{
		BufferedImage copie = copierImage(this.imageUtil.getImage());
		this.historiqueImages.push(copie);
		
		// Limiter la taille de l'historique
		if (this.historiqueImages.size() > LIMITE_HISTORIQUE)
		{
			this.historiqueImages.remove(0);
		}
		
		// Vider le redo apres une nouvelle action
		this.imagesAnnulees.clear();
	}
	
	public void annuler()
	{
		if (!this.historiqueImages.isEmpty())
		{
			BufferedImage imageActuelle = copierImage(this.imageUtil.getImage());
			this.imagesAnnulees.push(imageActuelle);
			
			BufferedImage imagePrec = this.historiqueImages.pop();
			this.imageUtil.setImage(imagePrec);
			this.framePrincipale.afficherImage(imagePrec);
		}
	}
	
	public void refaire()
	{
		if (!this.imagesAnnulees.isEmpty())
		{
			BufferedImage imageActuelle = copierImage(this.imageUtil.getImage());
			this.historiqueImages.push(imageActuelle);
			
			BufferedImage imageSuiv = this.imagesAnnulees.pop();
			this.imageUtil.setImage(imageSuiv);
			this.framePrincipale.afficherImage(imageSuiv);
		}
	}

	public void ouvrirImage(String path)
	{
		this.cheminImageCourant = path;
		this.imageUtil = new ImageUtil(path);
		this.historiqueImages.clear();
		this.imagesAnnulees.clear();
		this.framePrincipale.afficherImage(path);
	}

	public void sauvegarderImageSous(String path)
	{
		this.imageUtil.sauvegarderImage(path);
	}

	public void sauvegarderImage()
	{
		this.imageUtil.sauvegarderImage(this.cheminImageCourant);
	}

	public void miroirHorizontal()
	{
		this.sauvegarderEtat();
		BufferedImage src = this.imageUtil.getImage();
		BufferedImage out = Miroir.appliquerMiroirHorizontal(src);
		this.imageUtil.setImage(out);
		this.framePrincipale.afficherImage(out);
	}

	public void miroirVertical()
	{
		this.sauvegarderEtat();
		BufferedImage src = this.imageUtil.getImage();
		BufferedImage out = Miroir.appliquerMiroirVertical(src);
		this.imageUtil.setImage(out);
		this.framePrincipale.afficherImage(out);
	}

	/** * Redimensionne l'image courante à de nouvelles dimensions (largeur et hauteur). 
	 */
	public void redimensionner(int newWidth, int newHeight)
	{
		this.sauvegarderEtat();
		BufferedImage src = this.imageUtil.getImage();
		BufferedImage out = Redimensionnement.redimensionner(src, newWidth, newHeight);
		this.imageUtil.setImage(out);
		this.framePrincipale.afficherImage(out);
	}

	/** * Redimensionne l'image courante en conservant le ratio via un facteur d'échelle. 
	 */
	public void redimensionnerRatio(double scale)
	{
		this.sauvegarderEtat();
		BufferedImage src = this.imageUtil.getImage();
		BufferedImage out = Redimensionnement.redimensionnerRatio(src, scale);
		this.imageUtil.setImage(out);
		this.framePrincipale.afficherImage(out);
	}

	public void appliquerAntiAliasing()
	{
		this.sauvegarderEtat();
		BufferedImage src = this.imageUtil.getImage();
        BufferedImage out = AntiAlias.appliquerAntiAliasing(src);
        this.imageUtil.setImage(out);
        this.framePrincipale.afficherImage(out);
	}

	public void rotation(int angle)
	{
		this.sauvegarderEtat();
		BufferedImage src = this.imageUtil.getImage();
		BufferedImage out = Rotation.appliquerRotation(src, angle);
		this.imageUtil.setImage(out);
		this.framePrincipale.afficherImage(out);
	}

	public void appliquerRotation(int angle)
	{
		this.rotation(angle);
	}

	public void appliquerRedimensionnement(int nouvelleLargeur, int nouvelleHauteur)
	{
		this.redimensionner(nouvelleLargeur, nouvelleHauteur);
	}

	public void appliquerPotPeinture()
	{
		System.out.println("Pot de peinture appliqué.");
	}

	public void appliquerTeinte(int teinteRouge, int teinteVerte, int teinteBleue)
	{
		this.sauvegarderEtat();
		// Préférer application en mémoire via méthode statique
		BufferedImage src = this.imageUtil.getImage();
		BufferedImage out = Teinte.appliquerTeinte(src, teinteRouge, teinteVerte, teinteBleue);
		this.imageUtil.setImage(out);
		this.framePrincipale.afficherImage(out);
	}

	public void appliquerContraste(int valeurContraste)
	{
		this.sauvegarderEtat();
		BufferedImage src = this.imageUtil.getImage();
		BufferedImage out = Contraste.appliquerContraste(src, valeurContraste);
		this.imageUtil.setImage(out);
		this.framePrincipale.afficherImage(out);
	}

	public void appliquerSuperpositionImages(String cheminImageSup)
	{
		// Charger l'image de superposition
		ImageUtil imgSup = new ImageUtil(cheminImageSup);
		this.calqueSuperposition = imgSup.getImage();
		
		// Positionner a droite de l'image principale
		BufferedImage src = this.imageUtil.getImage();
		this.calqueSuperpositionX = this.getPosX() + src.getWidth() + 20;
		this.calqueSuperpositionY = this.getPosY();
		
		this.framePrincipale.majIHM();
	}

	public void appliquerCreationImageTexte(String texte, int taillePolice)
	{
		BufferedImage src = this.imageUtil.getImage();
		// Creer juste le masque (blanc sur transparent)
		this.calqueTexte = TexteImage.creerMasqueTexte(texte, taillePolice);
		
		// Positionner le texte à droite de l'image
		this.calqueTexteX = this.getPosX() + src.getWidth() + 20;
		this.calqueTexteY = this.getPosY();
		
		this.framePrincipale.majIHM();
	}

	public static void main(String[] args)
	{
		new Controleur();
	}
}