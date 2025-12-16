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
		this.cheminImageCourant = this.getImage();
		this.framePrincipale = new FramePrincipale(this);
		this.imageUtil = new ImageUtil(this.cheminImageCourant);
	}

	public String getImage()
	{
		return this.imageUtil.getCheminImage();
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
