package models.generic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import models.NoeudDico;

public class GestionnaireDictionnaire {
	private TrieGenerique<NoeudDico, Character, String> dico;
	private final String CHEMIN_RESSOURCE_DICO;
	private final String FICHIER_SAUVEGARDE;

	public GestionnaireDictionnaire(String nom, String chemin_ressource_dico) {
		CHEMIN_RESSOURCE_DICO = chemin_ressource_dico;
		FICHIER_SAUVEGARDE = "Trie_" + nom + ".dat";
		try {
			System.out.println("Chargement Dictionnaire existant...");
			dico = charger_serialisation();
			System.out.println("Dictionnaire chargé !");
		} catch (Exception e) {
			System.out.println("Chargement impossible, création de nouveau dico...");
			dico = new TrieGenerique<>(NoeudDico::new);
			ApprendreDico(CHEMIN_RESSOURCE_DICO);
			System.out.println("Nouveau Dictionnaire créé");
			sauvegarder_serialisation();
			System.out.println("Dictionnaire créé et sauvegardé !");
		}
	}

	private void ApprendreDico(String chemin) {
		try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {
			String mot;
			while ((mot = br.readLine()) != null) {
				mot = mot.trim();
				if (!mot.isEmpty() && mot.length() > 2) {
					dico.ajouteMot(mot, null);
				}
			}
			System.out.println(" " + chemin + " chargé !");
		} catch (IOException e) {
			System.err.println("Dico absent : " + e.getMessage());
		}
	}

	private TrieGenerique<NoeudDico, Character, String> charger_serialisation() throws IOException, ClassNotFoundException {
		return TrieGenerique.charger(FICHIER_SAUVEGARDE);
	}

	private void sauvegarder_serialisation() {
		try {
			dico.sauvegarder(FICHIER_SAUVEGARDE);
		} catch (IOException e) {
			System.err.println("Erreur sauvegarde : " + e.getMessage());
		}
	}

	public String checkMot(String mot) {
		return (dico.checkMot(mot));
	}

	public void ajouteMot(String mot, String definition) {
		dico.ajouteMot(mot, definition);
	}

	public void fermer() {
		sauvegarder_serialisation(); // Sauvegarde auto à la fermeture
	}
}
