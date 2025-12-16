package controleur;

import ihm.FramePrincipale;
import metier.*;
import java.awt.image.BufferedImage;

public class Controleur
{
	private FramePrincipale framePrincipale;
	private ImageUtil imageUtil;
	private String cheminImageCourant;

	public Controleur()
	{
		// Initialisation avec l'image par defaut
		// Chemin relatif depuis le dossier bin/
		this.cheminImageCourant = "../src/images/david_tennant.png";
		this.imageUtil = new ImageUtil(this.cheminImageCourant);
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

	public void ouvrirImage(String path)
	{
		this.cheminImageCourant = path;
		this.imageUtil = new ImageUtil(path);
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
		BufferedImage src = this.imageUtil.getImage();
		BufferedImage out = Miroir.appliquerMiroirHorizontal(src);
		this.imageUtil.setImage(out);
		this.framePrincipale.afficherImage(out);
	}

	public void miroirVertical()
	{
		BufferedImage src = this.imageUtil.getImage();
		BufferedImage out = Miroir.appliquerMiroirVertical(src);
		this.imageUtil.setImage(out);
		this.framePrincipale.afficherImage(out);
	}

	/** Redimensionne l'image courante aux dimensions spécifiées et met à jour l'affichage. */
	public void redimensionner(int newWidth, int newHeight)
	{
		BufferedImage src = this.imageUtil.getImage();
		BufferedImage out = Redimensionnement.redimensionner(src, newWidth, newHeight);
		this.imageUtil.setImage(out);
		this.framePrincipale.afficherImage(out);
	}

	/** Redimensionne l'image courante en conservant le ratio via un facteur d'échelle. */
	public void redimensionnerRatio(double scale)
	{
		BufferedImage src = this.imageUtil.getImage();
		BufferedImage out = Redimensionnement.redimensionnerRatio(src, scale);
		this.imageUtil.setImage(out);
		this.framePrincipale.afficherImage(out);
	}

	public void appliquerAntiAliasing()
	{
		System.out.println("Anti-aliasing appliqué.");
	}

	public void appliquerRotation(int angle)
	{
		Rotation rotation = new Rotation(this.cheminImageCourant, "image_temp.png");
		rotation.rotation(angle);

		// Mettre à jour l'affichage
		this.framePrincipale.afficherImage("image_temp.png");
	}

	public void appliquerRedimensionnement(int nouvelleLargeur, int nouvelleHauteur)
	{
		Redimensionnement redim = new Redimensionnement(this.cheminImageCourant, "image_temp.png");
		redim.redimensionner(nouvelleLargeur, nouvelleHauteur);

		// Mettre à jour l'affichage
		this.framePrincipale.afficherImage("image_temp.png");
	}

	public void appliquerPotPeinture()
	{
		System.out.println("Pot de peinture appliqué.");
	}

	public void appliquerTeinte(int teinteRouge, int teinteVerte, int teinteBleue)
	{
		Teinte teinte = new Teinte(this.cheminImageCourant, "image_temp.png");
		teinte.teinter(teinteRouge, teinteVerte, teinteBleue);

		// Mettre à jour l'affichage
		this.framePrincipale.afficherImage("image_temp.png");
	}

	public void appliquerContraste(int valeurContraste)
	{
		Contraste contraste = new Contraste(this.cheminImageCourant, "image_temp.png");
		contraste.appliquerContraste(valeurContraste);

		// Mettre à jour l'affichage
		this.framePrincipale.afficherImage("image_temp.png");
	}

	public void appliquerSuperpositionImages()
	{
		System.out.println("Superposition d'images appliquée.");
	}

	public void appliquerCreationImageTexte()
	{
		System.out.println("Création d'un texte avec une image appliquée.");
	}

	public static void main(String[] args)
	{
		new Controleur();
	}
}
