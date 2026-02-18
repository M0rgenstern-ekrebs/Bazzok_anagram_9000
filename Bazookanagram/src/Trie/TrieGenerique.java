package Trie;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class TrieGenerique<T extends NoeudTrie<T, E, P> & Serializable,  E, P>
{
    private T racine;

    public TrieGenerique(T racine)
    {
        this.racine = racine;
    }

    @SuppressWarnings("unchecked") // le cast est 'unchecked' car le type est générique
    public void ajouteMot(String mot, P payload)
    {
        if (mot == null || mot.isEmpty())
            return;
        
        Character[] temp = mot.toLowerCase().chars()
                              .mapToObj(c -> (char)c)
                              .toArray(Character[]::new);

        ajouteMot((E[]) temp, payload);
    }

    public void ajouteMot(E[] etiquettes, P payload)
    {
        T       courant;
        T       enfant;

        courant = racine;
        for (E etiquette : etiquettes)
        {
            enfant = courant.getEnfant(etiquette);
            if (enfant == null)
            {
                enfant = courant.creerNoeud();
                courant.setEnfant(etiquette, enfant);
            }
            courant = enfant;
            courant.setData(payload);
        }
        courant.setFin(true);
    }

    public P CheckMot(String mot)
    {
        if (mot == null || mot.isEmpty())
            return (null);
        
        @SuppressWarnings("unchecked")
        E[] etiquettes = (E[]) mot.toLowerCase().chars()
                        .mapToObj(c -> Character.valueOf((char) c))
                        .toArray(size -> (E[]) new Character[size]);

        return (CheckMot(etiquettes));
    }

    public P CheckMot(E[] etiquettes)
    {
        T               courant;
        T               enfant;
        StringBuilder   chemin;
        
        chemin = new StringBuilder();
        courant = racine;
        for (E etiquette : etiquettes)
        {
            enfant = courant.getEnfant(etiquette);
            if (enfant == null)
            {
                return (null);
            }
            chemin.append(etiquette);
            courant = enfant;
        }
        if (courant.estFin() == true)
            return courant.getData();
        else
            return (null);
    }

    // Sauvegarde
    public void sauvegarder(String fichier) throws IOException
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(fichier)))
        {
            oos.writeObject(this);
        }
    }
    

    // @SuppressWarnings("unchecked")
    // public static <T extends NoeudTrie<T, E, P> & Serializable, E, P> 
    //     TrieGenerique<T, E, P> charger(String fichier) throws IOException, ClassNotFoundException {
    //     try (ObjectInputStream ois = new ObjectInputStream(
    //             new FileInputStream(fichier))) {
    //         Object obj = ois.readObject();
    //         if (obj instanceof TrieGenerique) {
    //             return (TrieGenerique<T, E, P>) obj;
    //         }
    //         throw new ClassCastException("Fichier corrompu");
    //     }
    // }

    // Chargement
    @SuppressWarnings("unchecked")
    public static <T extends NoeudTrie<T, E, P> & Serializable, E, P> 
        TrieGenerique<T, E, P> charger(String fichier) throws IOException, ClassNotFoundException 
    {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(fichier))) {
            return (TrieGenerique<T, E, P>) ois.readObject();
        }
    }
}
