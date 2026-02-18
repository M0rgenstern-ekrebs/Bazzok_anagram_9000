package models;

import java.io.File;
import java.io.IOException;

import Trie.TrieGenerique;

public class GestionnaireDictionnaire {
	private TrieGenerique<NoeudDico, Character, String> trie;
	private static final String FICHIER_SAUVEGARDE = ".trie.dat";

	public GestionnaireDictionnaire() {
		try {
			System.out.println("Chargement Dictionnaire existant...");
			trie = charger();
			System.out.println("Dictionnaire chargé !");
		} catch (Exception e) {
			System.out.println("Chargement impossible, création de nouveau trie...");
			trie = new TrieGenerique<>(new NoeudDico());
			chargerHunspell();
			System.out.println("Nouveau Dictionnaire créé");
			sauvegarder();
			System.out.println("Dictionnaire créé et sauvegardé !");
		}
	}

	private TrieGenerique<NoeudDico, Character, String> charger() throws IOException, ClassNotFoundException {
		return TrieGenerique.charger(FICHIER_SAUVEGARDE);
	}

	private void sauvegarder() {
		try {
			trie.sauvegarder(FICHIER_SAUVEGARDE);
		} catch (IOException e) {
			System.err.println("Erreur sauvegarde : " + e.getMessage());
		}
	}

	private void chargerHunspell() {
		// Ton code de chargement Hunspell
		// ~2 secondes pour 100k mots
	}

	public String checkMot(String mot) {
		return (trie.checkMot(mot));
	}

	public void ajouteMot(String mot, String definition) {
		trie.ajouteMot(mot, definition);
	}

	public void fermer() {
		sauvegarder(); // Sauvegarde auto à la fermeture
	}
}
