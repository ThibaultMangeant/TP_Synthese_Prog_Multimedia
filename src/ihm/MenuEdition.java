package ihm;

import controleur.Controleur;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuEdition extends JMenu implements ActionListener
{
	private Controleur controleur;

	private JMenuItem itemAnnuler;
	private JMenuItem itemRepeter;

	public MenuEdition(Controleur controleur)
	{
		super("Édition");

		this.controleur = controleur;

		/* Création des items */
		this.itemAnnuler = new JMenuItem("Annuler");
		this.itemRepeter = new JMenuItem("Répéter");

		/* Ajout des items au menu Édition */
		this.add(this.itemAnnuler);
		this.add(this.itemRepeter);

		/* Activation des composants */
		this.itemAnnuler.addActionListener(this);
		this.itemRepeter.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.itemAnnuler)
		{
			System.out.println("Annuler");
		}
		else if (e.getSource() == this.itemRepeter)
		{
			System.out.println("Répéter");
		}
	}
}
