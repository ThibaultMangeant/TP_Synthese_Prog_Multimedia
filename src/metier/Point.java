package metier;

/**
 * Classe representant un point avec des coordonnees x et y
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
	 * Retourne une representation textuelle du point
	 * @return Chaine de caracteres representant le point
	 */
	@Override
	public String toString()
	{
		return "Point(" + x + ", " + y + ")";
	}
	
	/**
	 * Compare deux points pour l'egalite
	 * @param obj Objet a comparer
	 * @return true si les points ont les memes coordonnees
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Point point = (Point) obj;
		return x == point.x && y == point.y;
	}
	
}
