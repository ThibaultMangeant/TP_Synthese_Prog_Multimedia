package controleur;

import ihm.FramePrincipale;
import metier.*;

import java.awt.image.BufferedImage;
import java.util.Stack;

/** Contrôleur principal de l'application
 * Gère les interactions entre l'interface utilisateur (IHM) et la logique métier
 * @author Equipe 5
 */
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
		// Initialisation avec l'image par defaut pour eviter imageUtil null
		this.cheminImageCourant = "../src/images/blanc.png";
		this.imageUtil = new ImageUtil(this.cheminImageCourant);
		this.historiqueImages = new Stack<BufferedImage>();
		this.imagesAnnulees = new Stack<BufferedImage>();
		this.calqueTexte = null;
		this.calqueTexteX = 0;
		this.calqueTexteY = 0;
		this.calqueSuperposition = null;
		this.calqueSuperpositionX = 0;
		this.calqueSuperpositionY = 0;
		this.framePrincipale = new FramePrincipale(this);
		this.initImage();
	}

	public String        getCheminImageCourant() { return this.cheminImageCourant;       }
	public BufferedImage getImage()              { return this.imageUtil.getImage();     }
	public boolean       contient(int x, int y)  { return this.imageUtil.contient(x, y); }

	public int  getPosX()      { return this.imageUtil.getX0(); }
	public int  getPosY()      { return this.imageUtil.getY0(); }
	public void setPosX(int x) { this.imageUtil.setX0(x); }
	public void setPosY(int y) { this.imageUtil.setY0(y); }

	public BufferedImage getCalqueTexte() { return this.calqueTexte; }

	public int  getCalqueTexteX()      { return this.calqueTexteX; }
	public int  getCalqueTexteY()      { return this.calqueTexteY; }
	public void setCalqueTexteX(int x) { this.calqueTexteX = x; }
	public void setCalqueTexteY(int y) { this.calqueTexteY = y; }

	public boolean contientCalqueTexte(int x, int y)
	{
		return this.contientCalque(this.calqueTexte, x, y, this.calqueTexteX, this.calqueTexteY);
	}

	public void fusionnerCalqueTexte()
	{
		BufferedImage imgFond;
		BufferedImage texteRempli;

		if (this.calqueTexte != null)
		{
			this.sauvegarderEtat();
			imgFond = this.imageUtil.getImage();
			
			// Appliquer l'image de fond dans le masque de texte a la position actuelle
			texteRempli = TexteImage.appliquerImageDansTexte(
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

	public BufferedImage getCalqueSuperposition() { return this.calqueSuperposition; }

	public int  getCalqueSuperpositionX()      { return this.calqueSuperpositionX; }
	public int  getCalqueSuperpositionY()      { return this.calqueSuperpositionY; }
	public void setCalqueSuperpositionX(int x) { this.calqueSuperpositionX = x;    }
	public void setCalqueSuperpositionY(int y) { this.calqueSuperpositionY = y;    }

	public boolean contientCalqueSuperposition(int x, int y)
	{
		return this.contientCalque(this.calqueSuperposition, x, y, this.calqueSuperpositionX, this.calqueSuperpositionY);
	}

	private boolean contientCalque(BufferedImage calque, int x, int y, int calqueX, int calqueY)
	{
		if (calque == null) return false;
		return (x >= calqueX && x < calqueX + calque.getWidth() &&
		        y >= calqueY && y < calqueY + calque.getHeight());
	}

	public void fusionnerCalqueSuperposition()
	{
		BufferedImage imgFond;
		BufferedImage imgFinale;
		int          posX;
		int          posY;
		int          rgbSup;
		int          alpha;
		int          rgbFond;
		int          pixelFinal;

		if (this.calqueSuperposition != null)
		{
			this.sauvegarderEtat();
			imgFond   = this.imageUtil.getImage();
			imgFinale = this.copierImage(imgFond);
			
			// Superposer le calque avec alpha blending
			for (int y = 0; y < this.calqueSuperposition.getHeight(); y++)
			{
				for (int x = 0; x < this.calqueSuperposition.getWidth(); x++)
				{
					posX = x + this.calqueSuperpositionX - this.getPosX();
					posY = y + this.calqueSuperpositionY - this.getPosY();
					
					if (posX >= 0 && posX < imgFond.getWidth() && posY >= 0 && posY < imgFond.getHeight())
					{
						rgbSup = this.calqueSuperposition.getRGB(x, y);
						alpha  = (rgbSup >> 24) & 0xff;
						
						if (alpha > 0)
						{
							rgbFond   = imgFinale.getRGB(posX, posY);
							pixelFinal = this.alphaBlend(rgbSup, rgbFond, alpha);
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

	private int alphaBlend(int rgbSrc, int rgbDst, int alpha)
	{
		int r1, g1, b1;
		int r2, g2, b2;
		int rFinal, gFinal, bFinal;

		r1 = (rgbDst >> 16) & 0xff;
		g1 = (rgbDst >> 8)  & 0xff;
		b1 =  rgbDst        & 0xff;

		r2 = (rgbSrc >> 16) & 0xff;
		g2 = (rgbSrc >> 8)  & 0xff;
		b2 =  rgbSrc        & 0xff;

		rFinal = (r2 * alpha + r1 * (255 - alpha)) / 255;
		gFinal = (g2 * alpha + g1 * (255 - alpha)) / 255;
		bFinal = (b2 * alpha + b1 * (255 - alpha)) / 255;

		return (255 << 24) | (rFinal << 16) | (gFinal << 8) | bFinal;
	}

	private BufferedImage copierImage(BufferedImage src)
	{
		BufferedImage copie;

		copie = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
		copie.getGraphics().drawImage(src, 0, 0, null);
		return copie;
	}
	
	private void sauvegarderEtat()
	{
		BufferedImage copie;

		copie = this.copierImage(this.imageUtil.getImage());
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
		BufferedImage imageActuelle, imagePrec;

		if (!this.historiqueImages.isEmpty())
		{
			imageActuelle = this.copierImage(this.imageUtil.getImage());
			this.imagesAnnulees.push(imageActuelle);
			
			imagePrec = this.historiqueImages.pop();
			this.imageUtil.setImage(imagePrec);
			this.framePrincipale.afficherImage(imagePrec);
		}
	}
	
	public void refaire()
	{
		BufferedImage imageActuelle, imageSuiv;

		if (!this.imagesAnnulees.isEmpty())
		{
			imageActuelle = this.copierImage(this.imageUtil.getImage());
			this.historiqueImages.push(imageActuelle);
			
			imageSuiv = this.imagesAnnulees.pop();
			this.imageUtil.setImage(imageSuiv);
			this.framePrincipale.afficherImage(imageSuiv);
		}
	}

	public void ouvrirImage(String path)
	{
		this.cheminImageCourant = path;
		this.imageUtil = new ImageUtil(path);
		this.historiqueImages.clear();
		this.imagesAnnulees  .clear();
		this.framePrincipale .afficherImage(path);
	}

	public void sauvegarderImageSous(String path)
	{
		this.imageUtil.sauvegarderImage(path);
	}

	public void sauvegarderImage()
	{
		this.imageUtil.sauvegarderImage(this.cheminImageCourant);
	}

	public void zoomAvant() { this.framePrincipale.zoomAvant(); }
	public void zoomArriere() { this.framePrincipale.zoomArriere(); }

	private void appliquerTransformation(BufferedImage resultat)
	{
		this.imageUtil.setImage(resultat);
		this.framePrincipale.afficherImage(resultat);
	}

	public void miroirHorizontal()
	{
		this.sauvegarderEtat();
		this.appliquerTransformation(Miroir.appliquerMiroirHorizontal(this.imageUtil.getImage()));
	}

	public void miroirVertical()
	{
		this.sauvegarderEtat();
		this.appliquerTransformation(Miroir.appliquerMiroirVertical(this.imageUtil.getImage()));
	}

	public void redimensionner(int newWidth, int newHeight)
	{
		this.sauvegarderEtat();
		this.appliquerTransformation(Redimensionnement.redimensionner(this.imageUtil.getImage(), newWidth, newHeight));
	}

	public void redimensionnerRatio(double scale)
	{
		this.sauvegarderEtat();
		this.appliquerTransformation(Redimensionnement.redimensionnerRatio(this.imageUtil.getImage(), scale));
	}

	public void appliquerAntiAliasing()
	{
		this.sauvegarderEtat();
		this.appliquerTransformation(AntiAlias.appliquerAntiAliasing(this.imageUtil.getImage()));
	}

	public void rotation(int angle)
	{
		this.sauvegarderEtat();
		this.appliquerTransformation(Rotation.appliquerRotation(this.imageUtil.getImage(), angle));
	}

	public void appliquerRotation(int angle) { this.rotation(angle); }
	public void appliquerRedimensionnement(int nouvelleLargeur, int nouvelleHauteur) { this.redimensionner(nouvelleLargeur, nouvelleHauteur); }
	public void appliquerPotPeinture() { System.out.println("Pot de peinture appliqué."); }

	public void appliquerTeinte(int teinteRouge, int teinteVerte, int teinteBleue)
	{
		this.sauvegarderEtat();
		this.appliquerTransformation(Teinte.appliquerTeinte(this.imageUtil.getImage(), teinteRouge, teinteVerte, teinteBleue));
	}

	public void appliquerContraste(int valeurContraste)
	{
		this.sauvegarderEtat();
		this.appliquerTransformation(Contraste.appliquerContraste(this.imageUtil.getImage(), valeurContraste));
	}

	public void appliquerLuminosite(int valeur)
	{
		this.sauvegarderEtat();
		this.appliquerTransformation(Luminosite.appliquerLuminosite(this.imageUtil.getImage(), valeur));
	}

	private int calculerPositionCalqueX()
	{
		BufferedImage src = this.imageUtil.getImage();
		double zoom = this.framePrincipale.getZoom();
		return this.getPosX() + (int)(src.getWidth() * zoom) + 20;
	}

	private int calculerPositionCalqueY()
	{
		return this.getPosY();
	}

	public void appliquerSuperpositionImages(String cheminImageSup)
	{
		this.calqueSuperposition = new ImageUtil(cheminImageSup).getImage();
		this.calqueSuperpositionX = calculerPositionCalqueX();
		this.calqueSuperpositionY = calculerPositionCalqueY();
		this.framePrincipale.majIHM();
	}

	public void appliquerCreationImageTexte(String texte, int taillePolice)
	{
		this.calqueTexte = TexteImage.creerMasqueTexte(texte, taillePolice);
		this.calqueTexteX = calculerPositionCalqueX();
		this.calqueTexteY = calculerPositionCalqueY();
		this.framePrincipale.majIHM();
	}

	public void initImage()
	{
		String chemin;

		chemin = this.framePrincipale.choisirImageInitiale();
		this.cheminImageCourant = chemin;
		this.imageUtil = new ImageUtil(chemin);
		this.framePrincipale.afficherImage(chemin);
	}

	public static void main(String[] args)
	{
		new Controleur();
	}
}