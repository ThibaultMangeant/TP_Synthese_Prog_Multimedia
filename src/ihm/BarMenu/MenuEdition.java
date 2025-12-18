package ihm.BarMenu;

import controleur.Controleur;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

/** Menu pour les options d'édition
 * @author Equipe 5
 */
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

		/* Définition des raccourcis clavier */
		this.itemAnnuler.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK)); // Raccourci ctrl+Z
		this.itemRepeter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK)); // Raccourci ctrl+Y

		/* Ajout des items au menu Édition */
		this.add(this.itemAnnuler);
		this.add(this.itemRepeter);

		/* Activation des composants */
		this.itemAnnuler.addActionListener(this);
		this.itemRepeter.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if      (e.getSource() == this.itemAnnuler) { this.controleur.annuler(); }
		else if (e.getSource() == this.itemRepeter) { this.controleur.refaire(); }
	}
}
