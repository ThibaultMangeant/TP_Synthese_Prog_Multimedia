package metier;

/**
 * Petite classe de test pour l'effet miroir.
 * Par défaut utilise l'image du dossier images et produit deux fichiers:
 *  - images/david_tennant_miroir_h.png (retournement horizontal)
 *  - images/david_tennant_miroir_v.png (retournement vertical)
 */
public class TestMiroir {

    public static void main(String[] args) {
        String src = args.length > 0 ? args[0] : "images/david_tennant.png";
        String destH = args.length > 1 ? args[1] : "images/david_tennant_miroir_h.png";
        String destV = args.length > 2 ? args[2] : "images/david_tennant_miroir_v.png";

        System.out.println("[TestMiroir] Source: " + src);
        System.out.println("[TestMiroir] Dest H: " + destH);
        System.out.println("[TestMiroir] Dest V: " + destV);

        try {
            Miroir mH = new Miroir(src, destH);
            mH.miroirHorizontal();
            System.out.println("[TestMiroir] Miroir horizontal OK");

            Miroir mV = new Miroir(src, destV);
            mV.miroirVertical();
            System.out.println("[TestMiroir] Miroir vertical OK");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("[TestMiroir] Terminé.");
    }
}
