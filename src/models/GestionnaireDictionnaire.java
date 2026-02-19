package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import Trie.TrieGenerique;

public class GestionnaireDictionnaire {
	private TrieGenerique<NoeudDico, Character, String> dico;
	private final String FICHIER_SAUVEGARDE;

	public GestionnaireDictionnaire(String nom)
	{
		FICHIER_SAUVEGARDE = "Trie_"+nom+".dat";
		try {
			System.out.println("Chargement Dictionnaire existant...");
			dico = charger();
			System.out.println("Dictionnaire chargé !");
		} catch (Exception e) {
			System.out.println("Chargement impossible, création de nouveau dico...");
			dico = new TrieGenerique<>(new NoeudDico());
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
					dico.ajouteMot(mot, null);
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
			dico.sauvegarder(FICHIER_SAUVEGARDE);
		} catch (IOException e) {
			System.err.println("Erreur sauvegarde : " + e.getMessage());
		}
	}

	private void chargerHunspell() {
		// Ton code de chargement Hunspell
		// ~2 secondes pour 100k mots
	}

	public String checkMot(String mot) {
		return (dico.checkMot(mot));
	}

	public void ajouteMot(String mot, String definition) {
		dico.ajouteMot(mot, definition);
	}

	public void fermer() {
		sauvegarder(); // Sauvegarde auto à la fermeture
	}
}
