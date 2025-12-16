package controleur;

import ihm.FramePrincipale;
import metier.*;

public class Controleur
{
	private FramePrincipale framePrincipale;
	private ImageUtil imageUtil;

	public Controleur()
	{
		this.framePrincipale = new FramePrincipale(this);
		this.imageUtil = new ImageUtil(this.getImage());
	}

	public String getImage()
	{
		return "images/david_tennant.png";
	}

	public static void main(String[] args)
	{
		new Controleur();
	}
}
