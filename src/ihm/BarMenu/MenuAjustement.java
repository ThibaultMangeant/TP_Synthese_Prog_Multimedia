package ihm.BarMenu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	private JMenuItem itemNegatif;
	private JMenuItem itemNoirEtBlanc;
	private JMenuItem itemFusion;

	public MenuAjustement(Controleur controleur)
	{
		super("Ajustement");

		this.controleur = controleur;

		/* Création des items */
		this.itemRemplirCouleur = new JMenuItem("Pot de Peinture - Remplir"        );
		this.itemRetirerCouleur = new JMenuItem("Pot de Peinture - Retirer"        );
		this.itemTeinte         = new JMenuItem("Ajuster Teinte"                   );
		this.itemAntiAliasing   = new JMenuItem("Anti-Aliasing"                    );
		this.itemContraste      = new JMenuItem("Ajuster Contraste"                );
		this.itemSuperposition  = new JMenuItem("Superposition d'Images"           );
		this.itemTexteImage     = new JMenuItem("Créer une Image à partir de Texte");
		this.itemLuminosite     = new JMenuItem("Ajuster Luminosité"               );
		this.itemNegatif        = new JMenuItem("Négatif"                          );
		this.itemNoirEtBlanc    = new JMenuItem("Noir et Blanc"                    );
		this.itemFusion         = new JMenuItem("Fusion d'Images"                  );

		/* Ajout des items au menu Ajustement */
		this.add(this.itemRemplirCouleur);
		this.add(this.itemRetirerCouleur);
		this.add(this.itemTeinte        );
		this.add(this.itemAntiAliasing  );
		this.add(this.itemContraste     );
		this.add(this.itemSuperposition );
		this.add(this.itemTexteImage    );
		this.add(this.itemLuminosite    );
		this.add(this.itemNegatif       );
		this.add(this.itemNoirEtBlanc   );
		this.add(this.itemFusion        );

		/* Activation des composants */
		this.itemRemplirCouleur.addActionListener(this);
		this.itemRetirerCouleur.addActionListener(this);
		this.itemTeinte        .addActionListener(this);
		this.itemAntiAliasing  .addActionListener(this);
		this.itemContraste     .addActionListener(this);
		this.itemSuperposition .addActionListener(this);
		this.itemTexteImage    .addActionListener(this);
		this.itemLuminosite    .addActionListener(this);
		this.itemNegatif       .addActionListener(this);
		this.itemNoirEtBlanc   .addActionListener(this);
		this.itemFusion        .addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (this.controleur != null)
		{
			if (e.getSource() == this.itemAntiAliasing  ) { this.controleur.appliquerAntiAliasing    (); }

			if (e.getSource() == this.itemRemplirCouleur) { this.controleur.activerModeRemplirCouleur(); }

			if (e.getSource() == this.itemRetirerCouleur) { this.controleur.activerModeRetirerCouleur(); }

			if (e.getSource() == this.itemTeinte)
			{
				int teinteR, teinteV, teinteB;
				
				teinteR = Integer.parseInt(JOptionPane.showInputDialog("Rentrer la teinte rouge (entre -255 et 255) : "));
				teinteV = Integer.parseInt(JOptionPane.showInputDialog("Rentrer la teinte verte (entre -255 et 255) : "));
				teinteB = Integer.parseInt(JOptionPane.showInputDialog("Rentrer la teinte bleue (entre -255 et 255) : "));

				this.controleur.appliquerTeinte(teinteR, teinteV, teinteB);
			}

			if (e.getSource() == this.itemContraste)
			{
				int valeurContraste;
				
				valeurContraste = Integer.parseInt(JOptionPane.showInputDialog("Rentrer la valeur du contraste (entre -100 et 100) : "));

				this.controleur.appliquerContraste(valeurContraste);
			}

			if (e.getSource() == this.itemSuperposition)
			{
				JFileChooser fileChooser;
				int          result;
				String       cheminImage;
				
				fileChooser = new JFileChooser("../src/images");
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.setFileFilter(new FileNameExtensionFilter("Images PNG (*.png)", "png"));
				result = fileChooser.showOpenDialog(null);
				
				if (result == JFileChooser.APPROVE_OPTION)
				{
					cheminImage = fileChooser.getSelectedFile().getAbsolutePath();
					
					if (!cheminImage.toLowerCase().endsWith(".png"))
					{
						JOptionPane.showMessageDialog(null, "Veuillez sélectionner un fichier PNG (.png)", "Format non supporté", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					this.controleur.appliquerSuperpositionImages(cheminImage);
				}
			}	
			
			if (e.getSource() == this.itemTexteImage)
			{
				String texte;
				int    taillePolice;
				
				texte        = JOptionPane.showInputDialog("Rentrer le texte à afficher : ");
				taillePolice = Integer.parseInt(JOptionPane.showInputDialog("Rentrer la taille de la police : "));

				this.controleur.appliquerCreationImageTexte(texte, taillePolice);
			}

			if (e.getSource() == this.itemLuminosite)
			{
				int valeurLuminosite;
				
				valeurLuminosite = Integer.parseInt(JOptionPane.showInputDialog("Rentrer la valeur de la luminosité (entre -255 et 255) : "));

				this.controleur.appliquerLuminosite(valeurLuminosite);
			}

			if (e.getSource() == this.itemNegatif    ) { this.controleur.appliquerNegatif    (); }

			if (e.getSource() == this.itemNoirEtBlanc) { this.controleur.appliquerNoirEtBlanc(); }

			if (e.getSource() == this.itemFusion)
			{
				JFileChooser fileChooser;
				int          result;
				String       cheminImage, largeurStr;
				int          largeurFondue;
				
				fileChooser = new JFileChooser("../src/images");
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.setFileFilter(new FileNameExtensionFilter("Images PNG (*.png)", "png"));
				fileChooser.setDialogTitle("Sélectionner l'image à fusionner");
				result = fileChooser.showOpenDialog(null);
				
				if (result == JFileChooser.APPROVE_OPTION)
				{
					cheminImage = fileChooser.getSelectedFile().getAbsolutePath();
					
					if (!cheminImage.toLowerCase().endsWith(".png"))
					{
						JOptionPane.showMessageDialog(null, "Veuillez sélectionner un fichier PNG (.png)", "Format non supporté", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					largeurStr = JOptionPane.showInputDialog("Largeur de la zone de fondu (en pixels) :");
					
					if (largeurStr != null)
					{
						largeurFondue = Integer.parseInt(largeurStr);
						
						this.controleur.appliquerFusion(cheminImage, largeurFondue);
					}
				}
			}
		}
	}
}