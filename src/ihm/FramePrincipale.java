package ihm;

import controleur.Controleur;
import ihm.BarMenu.BarMenu;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class FramePrincipale extends JFrame
{
	private PanelPrincipal panelPrincipal;
	private Controleur controleur;

	public FramePrincipale(Controleur ctrl)
	{
		this.controleur = ctrl;
		
		this.panelPrincipal = new PanelPrincipal(this);
		
		this.setTitle("Application Multimedia");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLocation(200, 200);
		
		this.initMenu(); 

		this.add(panelPrincipal);
		this.setVisible(true);
	}

	private void initMenu()
	{
		this.setJMenuBar(new BarMenu(this.controleur));
	}

	public boolean contient(int x, int y)
	{
		return this.controleur.contient(x, y);
	}

	public int getX()
	{
		return this.controleur.getX();
	}

	public int getY()
	{
		return this.controleur.getY();
	}

	public void setX(int x)
	{
		this.controleur.setX(x);
	}

	public void setY(int y)
	{
		this.controleur.setY(y);
	}

	public BufferedImage getImage()
	{
		return this.controleur.getImage();
	}

	public void afficherImage(String path)
	{
		this.panelPrincipal.chargerImage();
	}

	public void afficherImage(BufferedImage image)
	{
		this.panelPrincipal.setImage(image);
	}
}