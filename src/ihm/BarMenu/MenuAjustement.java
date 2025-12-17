package ihm.BarMenu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;

import controleur.Controleur;

import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/** Menu pour les options d'ajustement d'image
 * @author Equipe 5
 */
public class MenuAjustement extends JMenu implements ActionListener
{
	private Controleur controleur;

	private JMenuItem itemRemplirCouleur;
	private JMenuItem itemRetirerCouleur;
	private JMenuItem itemTeinte;
	private JMenuItem itemAntiAliasing;
	private JMenuItem itemContraste;
	private JMenuItem itemSuperposition;
	private JMenuItem itemTexteImage;
	private JMenuItem itemLuminosite;

	public MenuAjustement(Controleur controleur)
	{
		super("Ajustement");

		this.controleur = controleur;

		/* Création des items */
		this.itemRemplirCouleur = new JMenuItem("Pot de Peinture - Remplir");
		this.itemRetirerCouleur = new JMenuItem("Pot de Peinture - Retirer");
		this.itemTeinte         = new JMenuItem("Ajuster Teinte");
		this.itemAntiAliasing   = new JMenuItem("Anti-Aliasing");
		this.itemContraste      = new JMenuItem("Ajuster Contraste");
		this.itemSuperposition  = new JMenuItem("Superposition d'Images");
		this.itemTexteImage     = new JMenuItem("Créer une Image à partir de Texte");
		this.itemLuminosite     = new JMenuItem("Ajuster Luminosité");

		/* Ajout des items au menu Ajustement */
		this.add(this.itemRemplirCouleur);
		this.add(this.itemRetirerCouleur);
		this.add(this.itemTeinte);
		this.add(this.itemAntiAliasing);
		this.add(this.itemContraste);
		this.add(this.itemSuperposition);
		this.add(this.itemTexteImage);
		this.add(this.itemLuminosite);

		/* Activation des composants */
		this.itemRemplirCouleur.addActionListener(this);
		this.itemRetirerCouleur.addActionListener(this);
		this.itemTeinte       .addActionListener(this);
		this.itemAntiAliasing .addActionListener(this);
		this.itemContraste    .addActionListener(this);
		this.itemSuperposition.addActionListener(this);
		this.itemTexteImage   .addActionListener(this);
		this.itemLuminosite   .addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (this.controleur != null)
		{
			if (e.getSource() == this.itemAntiAliasing)
			{
				this.controleur.appliquerAntiAliasing();
			}

			if (e.getSource() == this.itemRemplirCouleur)
			{
				this.controleur.activerModeRemplirCouleur();
			}

			if (e.getSource() == this.itemRetirerCouleur)
			{
				this.controleur.activerModeRetirerCouleur();
			}

			if (e.getSource() == this.itemTeinte)
			{
				int teinteR = Integer.parseInt(JOptionPane.showInputDialog("Rentrer la teinte rouge (entre -255 et 255) : "));
				int teinteV = Integer.parseInt(JOptionPane.showInputDialog("Rentrer la teinte verte (entre -255 et 255) : "));
				int teinteB = Integer.parseInt(JOptionPane.showInputDialog("Rentrer la teinte bleue (entre -255 et 255) : "));

				this.controleur.appliquerTeinte(teinteR , teinteV, teinteB);
			}

			if (e.getSource() == this.itemContraste)
			{
				int valeurContraste = Integer.parseInt(JOptionPane.showInputDialog("Rentrer la valeur du contraste (entre -100 et 100) : "));

				this.controleur.appliquerContraste(valeurContraste);
			}

			if (e.getSource() == this.itemSuperposition)
			{
				JFileChooser fileChooser = new JFileChooser("../src/images");
				int result = fileChooser.showOpenDialog(null);
				
				if (result == JFileChooser.APPROVE_OPTION)
				{
					String cheminImage = fileChooser.getSelectedFile().getAbsolutePath();
					this.controleur.appliquerSuperpositionImages(cheminImage);
				}
			}	
			
			if (e.getSource() == this.itemTexteImage)
			{
				String texte = JOptionPane.showInputDialog("Rentrer le texte à afficher : ");
				int taillePolice = Integer.parseInt(JOptionPane.showInputDialog("Rentrer la taille de la police : "));

				this.controleur.appliquerCreationImageTexte(texte, taillePolice);
			}

			if (e.getSource() == this.itemLuminosite)
			{
				int valeurLuminosite = Integer.parseInt(JOptionPane.showInputDialog("Rentrer la valeur de la luminosité (entre -255 et 255) : "));

				this.controleur.appliquerLuminosite(valeurLuminosite);
			}
		}
	}
}