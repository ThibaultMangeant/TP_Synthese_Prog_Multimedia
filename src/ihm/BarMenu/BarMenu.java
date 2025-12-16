package ihm.BarMenu;

import javax.swing.JMenuBar;

import controleur.Controleur;
 
public class BarMenu extends JMenuBar
{
	private MenuFichier    menuFichier;
	private MenuEdition    menuEdition;
	private MenuImage      menuImage;
	private MenuAjustement menuAjustement;

    private Controleur controleur;

	public BarMenu(Controleur controleur)
	{
		this.controleur = controleur;

		/* Création des menus */
		this.menuFichier    = new MenuFichier   (this.controleur);
		this.menuEdition    = new MenuEdition   (this.controleur);
		this.menuImage      = new MenuImage     (this.controleur);
		this.menuAjustement = new MenuAjustement(this.controleur);

		/* Positionnement des composants */
		this.add(this.menuFichier);
		this.add(this.menuEdition);
		this.add(this.menuImage);
		this.add(this.menuAjustement);
	}
}