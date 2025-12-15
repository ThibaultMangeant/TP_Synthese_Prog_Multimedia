package controleur;

import ihm.FramePrincipale;

public class Controleur
{
	private FramePrincipale framePrincipale;

	public Controleur()
	{
		this.framePrincipale = new FramePrincipale(this);
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
