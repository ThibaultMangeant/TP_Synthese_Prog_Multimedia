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

	public static void main(String[] args)
	{
		new Controleur();
	}
}
