package ihm;

import controleur.Controleur;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FramePrincipale extends JFrame
{
    private PanelPrincipal panelPrincipal;
    private Controleur controleur;

    public FramePrincipale(Controleur ctrl)
    {
        this.controleur = ctrl;
        
        this.panelPrincipal = new PanelPrincipal();
        
        this.setTitle("Application Multimedia");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocation(200, 200);
        
        this.initMenu(); 

        this.add(panelPrincipal);
        this.setVisible(true);
    }

    private void initMenu()
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuFichier = new JMenu("Fichier");
        
        JMenuItem itemOuvrir = new JMenuItem("Ouvrir");
        JMenuItem itemQuitter = new JMenuItem("Quitter");

        itemQuitter.addActionListener(new ActionListener()
		{
            @Override
            public void actionPerformed(ActionEvent e) 
			{
                System.exit(0);
            }
        });
    
        itemOuvrir.addActionListener(e -> {
            System.out.println("Clic sur Ouvrir -> Appel au contrôleur");
        });

        menuFichier.add(itemOuvrir);
        menuFichier.addSeparator();
        menuFichier.add(itemQuitter);

        JMenu menuEdition = new JMenu("Édition");
        menuEdition.add(new JMenuItem("Paramètres"));

        menuBar.add(menuFichier);
        menuBar.add(menuEdition);

        this.setJMenuBar(menuBar);
    }
}