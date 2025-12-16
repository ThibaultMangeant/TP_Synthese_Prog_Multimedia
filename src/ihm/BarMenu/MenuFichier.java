package ihm.BarMenu;

import controleur.Controleur;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class MenuFichier extends JMenu implements ActionListener
{
	private Controleur controleur;

	private JMenuItem itemOuvrir;
	private JMenuItem itemSauvegarderSous;
	private JMenuItem itemSauvegarder;
	private JMenuItem itemQuitter;

	public MenuFichier(Controleur controleur)
	{
		super("Fichier");

		this.controleur = controleur;

		/* Création des items */
		this.itemOuvrir          = new JMenuItem("Ouvrir");
		this.itemSauvegarderSous = new JMenuItem("Sauvegarder sous...");
		this.itemSauvegarder     = new JMenuItem("Sauvegarder");
		this.itemQuitter         = new JMenuItem("Quitter");

		/* Ajout des items au menu Fichier */
		this.add(this.itemOuvrir);
		this.add(this.itemSauvegarderSous);
		this.add(this.itemSauvegarder);
		this.addSeparator();
		this.add(this.itemQuitter);

		/* Activation des composants */
		this.itemOuvrir.addActionListener(this);
		this.itemSauvegarderSous.addActionListener(this);
		this.itemSauvegarder.addActionListener(this);
		this.itemQuitter.addActionListener(this);
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
	}
}
