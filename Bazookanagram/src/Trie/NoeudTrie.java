package Trie;

//TypeNoeud, EtiquetteType, PayloadType
public interface NoeudTrie<T, E, P> {
    // Récupère l'enfant pour un caractère donné
    // null sinon
    T getEnfant(E etiquette);
    
    // Ajoute/modifie un enfant
    void setEnfant(E etiquette, T enfant);
    
    // Vérifie si c'est la fin d'un mot
    boolean estFin();

    // Marque la fin de mot
    void setFin(boolean fin);
    
    // extra Payload/data associé au noeud
    P getData();
    void setData(P data);

    // 🔥 FACTORY STATIQUE PAR DÉFAUT
    default T creerNoeud()
    {
        throw new UnsupportedOperationException("L'implémentation doit définir NoeudTrie.creerNoeud() !");
    }
}

