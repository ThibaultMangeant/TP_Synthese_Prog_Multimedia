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

	public String getImage()
	{
		return this.cheminImageCourant;
	}

	public void ouvrirImage(String path)
	{
		this.cheminImageCourant = path;
		this.imageUtil = new ImageUtil(path);
		this.framePrincipale.afficherImage(path);
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

	public void appliquerAntiAliasing()
	{
		System.out.println("Anti-aliasing appliqué.");
	}

	public void appliquerRotation(int angle)
	{
		Rotation rotation = new Rotation(this.cheminImageCourant, this.cheminImageCourant + "_rotated.png");
		rotation.rotation(angle);

		// Mettre à jour l'affichage
		this.framePrincipale.afficherImage(this.cheminImageCourant + "_rotated.png");
	}

	public void appliquerRedimensionnement(int nouvelleLargeur, int nouvelleHauteur)
	{
		Redimensionnement redim = new Redimensionnement(this.cheminImageCourant, this.cheminImageCourant + "_redim.png");
		redim.redimensionner(nouvelleLargeur, nouvelleHauteur);

		// Mettre à jour l'affichage
		this.framePrincipale.afficherImage(this.cheminImageCourant + "_redim.png");
	}

	public void appliquerPotPeinture()
	{
		System.out.println("Pot de peinture appliqué.");
	}

	public void appliquerTeinte(int teinteRouge, int teinteVerte, int teinteBleue)
	{
		Teinte teinte = new Teinte(this.cheminImageCourant, this.cheminImageCourant + "_teinte.png");
		teinte.teinter(teinteRouge, teinteVerte, teinteBleue);

		// Mettre à jour l'affichage
		this.framePrincipale.afficherImage(this.cheminImageCourant + "_teinte.png");
	}

	public void appliquerContraste(int valeurContraste)
	{
		Contraste contraste = new Contraste(this.cheminImageCourant, this.cheminImageCourant + "_contraste.png");
		contraste.appliquerContraste(valeurContraste);

		// Mettre à jour l'affichage
		this.framePrincipale.afficherImage(this.cheminImageCourant  + "_contraste.png");
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
