package controleur;

import ihm.FramePrincipale;

public class Controleur
{
	private FramePrincipale framePrincipale;

	public Controleur()
	{
		this.framePrincipale = new FramePrincipale(this);
	}

	public static void main(String[] args)
	{
		new Controleur();
	}
}
