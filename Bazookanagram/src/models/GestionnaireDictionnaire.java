package models;

import java.io.File;
import Trie.TrieGenerique;


public class MonProgramme {
    private TrieGenerique<NoeudDico, Character, String> trie;
    private static final String FICHIER_SAUVEGARDE = "trie.dat";
    
    public MonProgramme() {
        try {
            trie = charger();
        } catch (Exception e) {
            trie = new TrieGenerique<>(new NoeudDico());
            System.out.println("Nouveau trie créé");
        }
    }
    
    private TrieGenerique<NoeudDico, Character, String> charger() 
            throws IOException, ClassNotFoundException {
        return TrieGenerique.charger(FICHIER_SAUVEGARDE);
    }
    
    private void sauvegarder() {
        try {
            trie.sauvegarder(FICHIER_SAUVEGARDE);
        } catch (IOException e) {
            System.err.println("Erreur sauvegarde : " + e.getMessage());
        }
    }
    
    public void fermer() {
        sauvegarder();  // Sauvegarde auto à la fermeture
    }
}

public class GestionnaireDictionnaire {
    private static final String FICHIER_TRIE = "dictionnaire.trie";
    private TrieGenerique<NoeudDico, Character, String> trie;
    
    public GestionnaireDictionnaire() {
        if (fichierExiste(FICHIER_TRIE)) {
            System.out.println("✅ Chargement dictionnaire existant...");
            trie = chargerDictionnaire();
            System.out.println("✅ Dictionnaire chargé ! (" + compterMots() + " mots)");
        } else {
            System.out.println("📚 Création nouveau dictionnaire...");
            trie = new TrieGenerique<>(new NoeudDico());
            chargerHunspell();
            sauvegarder();
            System.out.println("✅ Dictionnaire créé et sauvegardé !");
        }
    }
    
    private boolean fichierExiste(String fichier) {
        return new File(fichier).exists();
    }
    
    private void chargerHunspell() {
        // Ton code de chargement Hunspell
        // ~2 secondes pour 100k mots
    }
}

public static void main(String[] args) {
    GestionnaireDictionnaire dico = new GestionnaireDictionnaire();
    
    // Dès la 2ème exécution → INSTANTANÉ !
    System.out.println(dico.trie.checkMot("chat") != null);  // true (0.0001s)
    
    // Ajoute quelques mots
    dico.ajouteMotAvecDef("ordinateur", "Machine électronique programmable");
    
    // Sauvegarde auto à la fermeture
    Runtime.getRuntime().addShutdownHook(new Thread(dico::sauvegarder));
}


