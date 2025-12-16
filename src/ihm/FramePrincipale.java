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

	public int getPosX()
	{
		return this.controleur.getPosX();
	}

	public int getPosY()
	{
		return this.controleur.getPosY();
	}

	public void setPosX(int x)
	{
		this.controleur.setPosX(x);
	}

	public void setPosY(int y)
	{
		this.controleur.setPosY(y);
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