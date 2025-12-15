package metier;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Classe pour l'outil pot de peinture (remplissage et suppression de fond)
 */
public class PotPeinture
{
	/**
	 * Retire une couleur et ses voisines proches de l'image (rend transparent)
	 * @param imgUtil ImageUtil contenant l'image
	 * @param x Position X du clic
	 * @param y Position Y du clic
	 * @param tolerance Tolérance de couleur (0-255)
	 */
	public static void retirerCouleur(ImageUtil imgUtil, int x, int y, int tolerance)
	{
		if (x < 0 || x >= imgUtil.img.getWidth() || y < 0 || y >= imgUtil.img.getHeight())
			return;
		
		// Récupérer la couleur cliquée
		Color couleurCible = new Color(imgUtil.img.getRGB(x, y));
		
		// Algorithme de remplissage pour retirer la couleur
		retirerCouleurIteratif(imgUtil, x, y, couleurCible, tolerance);
	}
	
	/**
	 * Algorithme itératif pour retirer une zone de couleur
	 */
	private static void retirerCouleurIteratif(ImageUtil imgUtil, int x, int y, Color couleurCible, int tolerance)
	{
		Queue<Point> file = new LinkedList<>();
		file.add(new Point(x, y));
		
		boolean[][] visite = new boolean[imgUtil.img.getWidth()][imgUtil.img.getHeight()];
		
		while (!file.isEmpty())
		{
			Point p = file.poll();
			
			// Vérifier les limites
			if (p.x < 0 || p.x >= imgUtil.img.getWidth() || p.y < 0 || p.y >= imgUtil.img.getHeight())
				continue;
			
			// Si déjà visité, passer
			if (visite[p.x][p.y])
				continue;
			
			Color couleurActuelle = new Color(imgUtil.img.getRGB(p.x, p.y), true);
			
			// Vérifier si la couleur est proche de la couleur cible
			if (!couleurProche(couleurActuelle, couleurCible, tolerance))
				continue;
			
			// Marquer comme visité
			visite[p.x][p.y] = true;
			
			// Rendre transparent (Alpha = 0)
			int rgb = imgUtil.img.getRGB(p.x, p.y);
			int transparent = rgb & 0x00FFFFFF; // Garde RGB, met Alpha à 0
			imgUtil.img.setRGB(p.x, p.y, transparent);
			
			// Ajouter les 4 voisins (haut, bas, gauche, droite)
			file.add(new Point(p.x + 1, p.y));
			file.add(new Point(p.x - 1, p.y));
			file.add(new Point(p.x, p.y + 1));
			file.add(new Point(p.x, p.y - 1));
		}
	}
	
	/**
	 * Remplit une zone avec une couleur
	 * @param imgUtil ImageUtil contenant l'image
	 * @param x Position X du clic
	 * @param y Position Y du clic
	 * @param nouvelleCouleur Couleur de remplissage
	 * @param tolerance Tolérance
	 */
	public static void remplir(ImageUtil imgUtil, int x, int y, Color nouvelleCouleur, int tolerance)
	{
		if (x < 0 || x >= imgUtil.img.getWidth() || y < 0 || y >= imgUtil.img.getHeight())
			return;
		
		Color couleurCible = new Color(imgUtil.img.getRGB(x, y));
		
		// Si même couleur, ne rien faire
		if (couleurProche(couleurCible, nouvelleCouleur, 0))
			return;
		
		remplirIteratif(imgUtil, x, y, couleurCible, nouvelleCouleur, tolerance);
	}
	
	/**
	 * Algorithme itératif de remplissage
	 */
	private static void remplirIteratif(ImageUtil imgUtil, int x, int y, Color couleurCible, Color nouvelleCouleur, int tolerance)
	{
		Queue<Point> file = new LinkedList<>();
		file.add(new Point(x, y));
		
		boolean[][] visite = new boolean[imgUtil.img.getWidth()][imgUtil.img.getHeight()];
		
		while (!file.isEmpty())
		{
			Point p = file.poll();
			
			if (p.x < 0 || p.x >= imgUtil.img.getWidth() || p.y < 0 || p.y >= imgUtil.img.getHeight())
				continue;
			
			if (visite[p.x][p.y])
				continue;
			
			Color couleurActuelle = new Color(imgUtil.img.getRGB(p.x, p.y));
			
			if (!couleurProche(couleurActuelle, couleurCible, tolerance))
				continue;
			
			visite[p.x][p.y] = true;
			imgUtil.img.setRGB(p.x, p.y, nouvelleCouleur.getRGB());
			
			// Ajouter les voisins
			file.add(new Point(p.x + 1, p.y));
			file.add(new Point(p.x - 1, p.y));
			file.add(new Point(p.x, p.y + 1));
			file.add(new Point(p.x, p.y - 1));
		}
	}
	
	/**
	 * Vérifie si deux couleurs sont proches selon une tolérance
	 */
	private static boolean couleurProche(Color c1, Color c2, int tolerance)
	{
		int diffRouge = Math.abs(c1.getRed() - c2.getRed());
		int diffVert = Math.abs(c1.getGreen() - c2.getGreen());
		int diffBleu = Math.abs(c1.getBlue() - c2.getBlue());
		
		return diffRouge <= tolerance && diffVert <= tolerance && diffBleu <= tolerance;
	}
	
	/**
	 * Classe interne pour représenter un point
	 */
	private static class Point
	{
		int x, y;
		
		Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
}
