package ihm.BarMenu;

import controleur.Controleur;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

/** Menu pour les options d'image
 * @author Equipe 5
 */
public class MenuImage extends JMenu implements ActionListener
{
	private Controleur controleur;

	private JMenuItem itemRotation;
	private JMenuItem itemMiroirH;
	private JMenuItem itemMiroirV;
	private JMenuItem itemRedimensionner;
	private JMenuItem itemDecouper;

	public MenuImage(Controleur controleur)
	{
		super("Image");

		this.controleur = controleur;

		/* Création des items */
		this.itemRotation       = new JMenuItem("Rotation"          );
		this.itemMiroirH        = new JMenuItem("Miroir Horizontal" );
		this.itemMiroirV        = new JMenuItem("Miroir Vertical"   );
		this.itemRedimensionner = new JMenuItem("Redimensionner"    );
		this.itemDecouper       = new JMenuItem("Découper (2 clics)");

		/* Définition des raccourcis clavier */
		this.itemRotation       .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK)); // Raccourci ctrl+R
		this.itemMiroirH        .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK)); // Raccourci ctrl+H
		this.itemMiroirV        .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK)); // Raccourci ctrl+V
		this.itemRedimensionner .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK)); // Raccourci ctrl+D

		/* Ajout des items au menu Image */
		this.add(this.itemRedimensionner);
		this.add(this.itemRotation      );
		this.addSeparator();
		this.add(this.itemMiroirH       );
		this.add(this.itemMiroirV       );
		this.addSeparator();
		this.add(this.itemDecouper      );

		/* Activation des composants */
		this.itemRotation      .addActionListener(this);
		this.itemMiroirH       .addActionListener(this);
		this.itemMiroirV       .addActionListener(this);
		this.itemRedimensionner.addActionListener(this);
		this.itemDecouper      .addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (this.controleur != null)
		{
			if (e.getSource() == this.itemMiroirH) { this.controleur.miroirHorizontal(); }

			if (e.getSource() == this.itemMiroirV) { this.controleur.miroirVertical  (); }

			if (e.getSource() == this.itemRotation)
			{
				int angle;
				
				angle = Integer.parseInt(JOptionPane.showInputDialog("Rentrer l'angle : "));

				this.controleur.rotation(angle);
			}

			if (e.getSource() == this.itemRedimensionner)
			{
				int nouvelleLargeur, nouvelleHauteur;
				
				nouvelleLargeur = Integer.parseInt(JOptionPane.showInputDialog("Rentrer la nouvelle largeur : "));
				nouvelleHauteur = Integer.parseInt(JOptionPane.showInputDialog("Rentrer la nouvelle hauteur : "));

				this.controleur.redimensionner(nouvelleLargeur, nouvelleHauteur);
			}

			if (e.getSource() == this.itemDecouper)
			{
				this.controleur.activerModeDecoupage();
			}
		}
	}
}
