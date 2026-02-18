package models;

import java.io.Serializable;
import java.util.HashMap;

import Trie.NoeudTrie;

public class NoeudDico implements NoeudTrie<NoeudDico, Character, String>, Serializable
{
    private static final long               serialVersionUID = 1L;
    private boolean                         is_a_word;
    private String                          payload;
    private HashMap<Character, NoeudDico>   enfants;

    {
        is_a_word = false;
        payload = "";
        enfants = new HashMap<>();
    }
    // Récupère l'enfant pour un caractère donné
    // null sinon
    public NoeudDico getEnfant(Character etiquette)
    {
        return (enfants.get(etiquette));
    }
    
    // Ajoute/modifie un enfant
    public void setEnfant(Character etiquette, NoeudDico enfant)
    {
        enfants.put(etiquette, enfant);
    }
    
    // Vérifie si c'est la fin d'un mot
    public boolean estFin()
    {
        return (is_a_word);
    }

    // Marque la fin de mot
    public void setFin(boolean fin)
    {
        is_a_word = fin;
    }
    
    // extra Payload/data associé au noeud
    public String getData()
    {
        return (payload);
    }

    public void setData(String data)
    {
        payload = data;
    }

    // 🔥 FACTORY STATIQUE PAR DÉFAUT
    public NoeudDico creerNoeud()
    {
        throw new UnsupportedOperationException("L'implémentation doit définir NoeudTrie.creerNoeud() !");
    }
}
