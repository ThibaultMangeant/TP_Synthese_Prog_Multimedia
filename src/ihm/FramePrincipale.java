package ihm;

import controleur.Controleur;
import javax.swing.*;

public class FramePrincipale extends JFrame
{
	private PanelPrincipal panelPrincipal;

	public FramePrincipale(Controleur ctrl)
	{
		this.panelPrincipal = new PanelPrincipal();
		this.setTitle("Application Multimedia");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.add(panelPrincipal);
		this.setVisible(true);
	}
}