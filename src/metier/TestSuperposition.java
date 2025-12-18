package metier;

/**
 * Classe de test pour la superposition d'images
 * @author Equipe 5
 */
public class TestSuperposition
{
	public static void main(String[] args)
	{
		Superposition super1, super2;


		System.out.println("=== Test Superposition ===");
		
		// Test 1: Superposition simple avec Point
		System.out.println("\nTest 1: Superposition avec Point (50, 50)");
		super1 = new Superposition("../src/images/david_tennant.png", "../src/images/david_tennant.png");
		super1.superposer(new Point(50, 50));
		super1.sauvegarder("../src/images/superposition_point.png");
		System.out.println("Image sauvegardee: ../src/images/superposition_point.png");
		
		// Test 2: Superposition avec coordonnees x, y
		System.out.println("\nTest 2: Superposition avec x=100, y=100");
		super2 = new Superposition("../src/images/david_tennant.png", "../src/images/david_tennant.png");
		super2.superposer(100, 100);
		super2.sauvegarder("../src/images/superposition_xy.png");
		System.out.println("Image sauvegardee: ../src/images/superposition_xy.png");
		
		System.out.println("\n=== Tests termines ===");
	}
}
