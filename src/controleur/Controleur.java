package controleur;

import ihm.FramePrincipale;
import metier.*;

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

	public static void main(String[] args)
	{
		new Controleur();
	}
}
