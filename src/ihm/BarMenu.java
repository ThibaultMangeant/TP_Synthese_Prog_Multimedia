package ihm;

import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
public class BarMenu extends JMenuBar implements ActionListener
{
	private JMenuItem itemQuitter;
	private JMenuItem itemOuvrir;

	public BarMenu()
	{
		/* Création des menus */
		JMenu menuFichier = new JMenu("Fichier");
		JMenu menuEdition = new JMenu("Édition");
		
		this.itemOuvrir = new JMenuItem("Ouvrir");
		this.itemQuitter = new JMenuItem("Quitter");


		/* Ajout des items au menu Fichier */
		menuFichier.add(itemOuvrir);
		menuFichier.addSeparator();
		menuFichier.add(itemQuitter);

		/* Ajout des items au menu Édition */
		menuEdition.add(new JMenuItem("Paramètres"));


		/* Positionnement des composants */
		this.add(menuFichier);
		this.add(menuEdition);


		/* Activation des composants */
		this.itemQuitter.addActionListener(this);
		this.itemOuvrir.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.itemQuitter)
		{
			System.exit(0);
		}

		if (e.getSource() == this.itemOuvrir)
		{
			System.out.println("Clic sur Ouvrir -> Appel au contrôleur");
		}
	}
}
