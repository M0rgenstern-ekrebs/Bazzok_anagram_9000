import java.io.File;
import java.io.IOException;

import models.generic.GestionnaireDictionnaire;
import models.generic.TrieGenerique;

public class Main {
	public static void main(String[] args) {
		final String CHEMIN_DICO_FR = "ressources/dico_fr_FR"; // FIXME
		// final String CHEMIN_DICO_EN = "ressources/dico_en_US";
		GestionnaireDictionnaire dico_fr = new GestionnaireDictionnaire("fr_FR", CHEMIN_DICO_FR);
		// GestionnaireDictionnaire dico_en = new GestionnaireDictionnaire("en_US",
		// CHEMIN_DICO_EN);

		// Dès la 2ème exécution -> INSTANTANÉ !
		System.out.println(dico_fr.checkMot("chat") != null); // true (0.0001s)

		// Ajoute quelques mots
		dico_fr.ajouteMot("ordinateur", "Machine électronique programmable");

		System.out.println(dico_fr.checkMot("ordinateur"));
		// Sauvegarde auto à la fermeture
		Runtime.getRuntime().addShutdownHook(new Thread(dico_fr::fermer));
		// Runtime.getRuntime().addShutdownHook(new Thread(dico_en::fermer));
	}
}
