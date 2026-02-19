import models.GestionnaireDictionnaire;
import java.io.File;
import java.io.IOException;

import Trie.TrieGenerique;

public class Main
{
	public static void main(String[] args)
	{
		GestionnaireDictionnaire dico = new GestionnaireDictionnaire();

		// Dès la 2ème exécution -> INSTANTANÉ !
		System.out.println(dico.checkMot("chat") != null); // true (0.0001s)

		// Ajoute quelques mots
		dico.ajouteMot("ordinateur", "Machine électronique programmable");

		// Sauvegarde auto à la fermeture
		Runtime.getRuntime().addShutdownHook(new Thread(dico::fermer));
	}
}
