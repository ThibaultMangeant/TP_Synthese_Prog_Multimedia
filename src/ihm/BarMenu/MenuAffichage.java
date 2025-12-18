package ihm.BarMenu;

import controleur.Controleur;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/** Menu pour les options d'affichage
 * @author Equipe 5
 */
public class MenuAffichage extends JMenu implements ActionListener
{
	private Controleur controleur;

	private JMenuItem itemZoomAvant;
	private JMenuItem itemZoomArriere;

	public MenuAffichage(Controleur controleur)
	{
		super("Affichage");

		this.controleur = controleur;

		/* Création des items */
		this.itemZoomAvant   = new JMenuItem("Zoom Avant");
		this.itemZoomArriere = new JMenuItem("Zoom Arrière");

		/* Ajout des items au menu Affichage */
		this.add(this.itemZoomAvant);
		this.add(this.itemZoomArriere);

		/* Activation des composants */
		this.itemZoomAvant  .addActionListener(this);
		this.itemZoomArriere.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (this.controleur != null)
		{
			if      (e.getSource() == this.itemZoomAvant  ) { this.controleur.zoomAvant  (); }
			else if (e.getSource() == this.itemZoomArriere) { this.controleur.zoomArriere(); }
		}
	}
}
