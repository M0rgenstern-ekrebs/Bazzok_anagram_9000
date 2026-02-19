package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import Trie.TrieGenerique;

public class GestionnaireDictionnaire {
	private TrieGenerique<NoeudDico, Character, String> trie;
	private final String FICHIER_SAUVEGARDE;

	public GestionnaireDictionnaire(String nom)
	{
		FICHIER_SAUVEGARDE = "Trie_"+nom+".dat";
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

	private void ApprendreDico(String chemin)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {
			String mot;
			while ((mot = br.readLine()) != null) {
				mot = mot.trim();
				if (!mot.isEmpty() && mot.length() > 2) {
					trie.ajouteMot(mot, null);
				}
			}
			System.out.println(" " + chemin + " chargé !");
		} catch (IOException e) {
			System.err.println("Dico absent : " + e.getMessage());
		}
	}

	public GestionnaireDictionnaire(String nom, String chemin) 
	{
		FICHIER_SAUVEGARDE = "Trie_"+nom+".dat";
		ApprendreDico(chemin);
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
