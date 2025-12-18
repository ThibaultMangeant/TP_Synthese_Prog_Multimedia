package metier;

/**
 * Classe representant un point avec des coordonnees x et y
 * @author Equipe 5
 */
public class Point
{
	/** Coordonnee x du point */
	public int x;
	
	/** Coordonnee y du point */
	public int y;
	
	/**
	 * Constructeur
	 * @param x Coordonnee x
	 * @param y Coordonnee y
	 */
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Compare deux points pour l'egalite
	 * @param obj Objet a comparer
	 * @return true si les points ont les memes coordonnees
	 */
	@Override
	public boolean equals(Object obj)
	{
		Point point;

		if (this == obj)
		{
			return true;
		}
		
		if (obj == null || this.getClass() != obj.getClass())
		{
			return false;
		}
		
		point = (Point) obj;
		
		return this.x == point.x && this.y == point.y;
	}
	
}
