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

	public Controleur()
	{
		// Initialisation avec l'image par defaut
		// Chemin relatif depuis le dossier bin/
		this.cheminImageCourant = "../src/images/david_tennant.png";
		this.imageUtil = new ImageUtil(this.cheminImageCourant);
		this.historiqueImages = new Stack<>();
		this.imagesAnnulees = new Stack<>();
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

	public void appliquerSuperpositionImages()
	{
		System.out.println("Superposition d'images appliquée.");
	}

	public void appliquerCreationImageTexte(String texte, int taillePolice)
	{
		BufferedImage src, out;

		this.sauvegarderEtat();
		src = this.imageUtil.getImage();
		out = TexteImage.creerTexteImage(src,texte, taillePolice);
		this.imageUtil.setImage(out);
		this.framePrincipale.afficherImage(out);
	}

	public static void main(String[] args)
	{
		new Controleur();
	}
}