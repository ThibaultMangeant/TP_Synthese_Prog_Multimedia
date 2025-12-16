package ihm;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import controleur.Controleur;
 
public class BarMenu extends JMenuBar implements ActionListener
{
	private JMenu menuFichier;
	private JMenu menuEdition;

	private JMenuItem itemQuitter;
	private JMenuItem itemOuvrir;

	private JMenuItem itemRotation;
	private JMenuItem itemMiroirH;
	private JMenuItem itemMiroirV;
	private JMenuItem itemRedimensionner;
	private JMenuItem itemPeinture;
	private JMenuItem itemTeinte;
	private JMenuItem itemAntiAliasing;
	private JMenuItem itemContraste;
	private JMenuItem itemSuperposition;
	private JMenuItem itemTexteImage;

    private Controleur controleur;

	public BarMenu(Controleur controleur)
	{
		this.controleur = controleur;
		/* Création des menus */
		this.menuFichier = new JMenu("Fichier");
		this.menuEdition = new JMenu("Édition");
		
		this.itemOuvrir  = new JMenuItem("Ouvrir");
		this.itemQuitter = new JMenuItem("Quitter");

		this.itemRotation       = new JMenuItem("Rotation");
		this.itemMiroirH        = new JMenuItem("Miroir Horizontal");
		this.itemMiroirV        = new JMenuItem("Miroir Vertical");
		this.itemRedimensionner = new JMenuItem("Redimensionner");
		this.itemPeinture       = new JMenuItem("Effet Peinture");
		this.itemTeinte         = new JMenuItem("Ajuster Teinte");
		this.itemAntiAliasing   = new JMenuItem("Anti-Aliasing");
		this.itemContraste      = new JMenuItem("Ajuster Contraste");
		this.itemSuperposition  = new JMenuItem("Superposition d'Images");
		this.itemTexteImage     = new JMenuItem("Créer une Image à partir de Texte");

		/* Ajout des items au menu Fichier */
		menuFichier.add(itemOuvrir);
		menuFichier.addSeparator();
		menuFichier.add(itemQuitter);

		/* Ajout des items au menu Édition */
		menuEdition.add(itemRotation);
		menuEdition.add(itemMiroirH);
		menuEdition.add(itemMiroirV);
		menuEdition.add(itemRedimensionner);
		menuEdition.add(itemPeinture);
		menuEdition.add(itemTeinte);
		menuEdition.add(itemAntiAliasing);
		menuEdition.add(itemContraste);
		menuEdition.add(itemSuperposition);
		menuEdition.add(itemTexteImage);


		/* Positionnement des composants */
		this.add(menuFichier);
		this.add(menuEdition);


		/* Activation des composants */
		this.itemQuitter.addActionListener(this);
		this.itemOuvrir.addActionListener(this);

		this.itemRotation.addActionListener(this);
		this.itemMiroirH.addActionListener(this);
		this.itemMiroirV.addActionListener(this);
		this.itemRedimensionner.addActionListener(this);
		this.itemPeinture.addActionListener(this);
		this.itemTeinte.addActionListener(this);
		this.itemAntiAliasing.addActionListener(this);
		this.itemContraste.addActionListener(this);
		this.itemSuperposition.addActionListener(this);
		this.itemTexteImage.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.itemQuitter)
		{
			System.exit(0);
		}

		if (e.getSource() == this.itemOuvrir)
		{
			JFileChooser selecteurFichier;
			int          result;
			File         fichierChoisi;
			String       chemin;

			selecteurFichier = new JFileChooser();
			selecteurFichier.setAcceptAllFileFilterUsed(false);
			selecteurFichier.setFileFilter(new FileNameExtensionFilter("Images (png)", "png"));

			result = selecteurFichier.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION)
			{
				fichierChoisi = selecteurFichier.getSelectedFile();
				chemin = fichierChoisi.getAbsolutePath();
				if (chemin.toLowerCase().endsWith(".png"))
				{
					System.out.println(chemin);
					if (this.controleur != null)
					{
						this.controleur.ouvrirImage(chemin);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner un fichier PNG.", "Format invalide", javax.swing.JOptionPane.ERROR_MESSAGE);
				}
			}
			else if (result == JFileChooser.CANCEL_OPTION)
			{
				System.out.println("Opération annulée par l'utilisateur.");
			}
			else if (result == JFileChooser.ERROR_OPTION)
			{
				System.out.println("Une erreur est survenue lors de la sélection du fichier.");
			}
		}


		if (this.controleur != null)
		{
			if(e.getSource() == this.itemMiroirH)
			{
				this.controleur.miroirHorizontal();
			}
			if(e.getSource() == this.itemMiroirV)
			{
				this.controleur.miroirVertical();
			}
		}
	}
}