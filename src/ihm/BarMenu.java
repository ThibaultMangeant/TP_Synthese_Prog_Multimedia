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
	private JMenu menuImage;
	private JMenu menuAjustement;

	
	private JMenuItem itemOuvrir;
	private JMenuItem itemSauvegarderSous;
	private JMenuItem itemSauvegarder;
	private JMenuItem itemQuitter;

	private JMenuItem itemAnnuler;
	private JMenuItem itemRepeter;

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
		this.menuFichier    = new JMenu("Fichier");
		this.menuEdition    = new JMenu("Édition");
		this.menuImage      = new JMenu("Image");
		this.menuAjustement = new JMenu("Ajustement");
		
		this.itemOuvrir          = new JMenuItem("Ouvrir");
		this.itemSauvegarderSous = new JMenuItem("Sauvegarder sous...");
		this.itemSauvegarder     = new JMenuItem("Sauvegarder");
		this.itemQuitter         = new JMenuItem("Quitter");

		this.itemAnnuler = new JMenuItem("Annuler");
		this.itemRepeter = new JMenuItem("Répéter");

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
		this.menuFichier.add(this.itemOuvrir);
		this.menuFichier.add(this.itemSauvegarderSous);
		this.menuFichier.add(this.itemSauvegarder);
		this.menuFichier.addSeparator();
		this.menuFichier.add(this.itemQuitter);

		/* Ajout des items au menu Édition */
		this.menuEdition.add(this.itemAnnuler);
		this.menuEdition.add(this.itemRepeter);

		/* Ajout des items au menu Image */
		this.menuImage.add(this.itemRedimensionner);
		this.menuImage.add(this.itemRotation);
		this.menuImage.addSeparator();
		this.menuImage.add(this.itemMiroirH);
		this.menuImage.add(this.itemMiroirV);

		/* Ajout des items au menu Ajustement */
		this.menuAjustement.add(this.itemPeinture);
		this.menuAjustement.add(this.itemTeinte);
		this.menuAjustement.add(this.itemAntiAliasing);
		this.menuAjustement.add(this.itemContraste);
		this.menuAjustement.add(this.itemSuperposition);
		this.menuAjustement.add(this.itemTexteImage);


		/* Positionnement des composants */
		this.add(this.menuFichier);
		this.add(this.menuEdition);
		this.add(this.menuImage);
		this.add(this.menuAjustement);


		/* Activation des composants */
		this.itemOuvrir.addActionListener(this);
		this.itemSauvegarderSous.addActionListener(this);
		this.itemSauvegarder.addActionListener(this);
		this.itemQuitter.addActionListener(this);

		this.itemAnnuler.addActionListener(this);
		this.itemRepeter.addActionListener(this);

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

		if (e.getSource() == this.itemSauvegarderSous)
		{
			JFileChooser selecteurFichier;
			int          result;
			File         fichierChoisi;
			String       chemin;

			selecteurFichier = new JFileChooser();
			selecteurFichier.setAcceptAllFileFilterUsed(false);
			selecteurFichier.setFileFilter(new FileNameExtensionFilter("Images (png)", "png"));

			result = selecteurFichier.showSaveDialog(null);
			if (result == JFileChooser.APPROVE_OPTION)
			{
				fichierChoisi = selecteurFichier.getSelectedFile();
				chemin = fichierChoisi.getAbsolutePath();
				if (!chemin.toLowerCase().endsWith(".png"))
				{
					chemin += ".png";	
				}

				System.out.println(chemin);
				if (this.controleur != null)
				{
					this.controleur.sauvegarderImageSous(chemin);
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

		if (e.getSource() == this.itemSauvegarder)
		{
			this.controleur.sauvegarderImage();
		}

		if (e.getSource() == this.itemQuitter)
		{
			System.exit(0);
		}


		if (this.controleur != null)
		{
			if (e.getSource() == this.itemMiroirH)
			{
				this.controleur.miroirHorizontal();
			}

			if (e.getSource() == this.itemMiroirV)
			{
				this.controleur.miroirVertical();
			}

			if (e.getSource() == this.itemAntiAliasing)
			{
				this.controleur.appliquerAntiAliasing();
			}

			if (e.getSource() == this.itemRotation)
			{
				int angle = Integer.parseInt(JOptionPane.showInputDialog("Rentrer l'angle : "));

				this.controleur.appliquerRotation(angle);
			}

			if (e.getSource() == this.itemRedimensionner)
			{
				int nouvelleLargeur  = Integer.parseInt(JOptionPane.showInputDialog("Rentrer la nouvelle largeur : "));
				int nouvelleHauteur  = Integer.parseInt(JOptionPane.showInputDialog("Rentrer la nouvelle hauteur : "));

				this.controleur.appliquerRedimensionnement(nouvelleLargeur, nouvelleHauteur);
			}

			if (e.getSource() == this.itemPeinture)
			{
				this.controleur.appliquerPotPeinture();
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
				this.controleur.appliquerSuperpositionImages();
			}

			if (e.getSource() == this.itemTexteImage)
			{
				this.controleur.appliquerCreationImageTexte();
			}
		}
	}
}