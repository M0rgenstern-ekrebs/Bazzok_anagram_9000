package Trie;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.nio.file.*;
import java.io.*;

public class TrieGenerique<T extends NoeudTrie<T, E, P> & Serializable, E, P>
{
	private T racine;

	public TrieGenerique(T racine) {
		this.racine = racine;
	}

	@SuppressWarnings("unchecked") // le cast est 'unchecked' car le type est générique
	public void ajouteMot(String mot, P payload) {
		if (mot == null || mot.isEmpty())
			return;

		Character[] temp = mot.toLowerCase().chars()
				.mapToObj(c -> (char) c)
				.toArray(Character[]::new);

		ajouteMot((E[]) temp, payload);
	}

	public void ajouteMot(E[] etiquettes, P payload) {
		T courant;
		T enfant;

		courant = racine;
		for (E etiquette : etiquettes) {
			enfant = courant.getEnfant(etiquette);
			if (enfant == null) {
				enfant = courant.creerNoeud();
				courant.setEnfant(etiquette, enfant);
			}
			courant = enfant;
			courant.setData(payload);
		}
		courant.setFin(true);
	}

	public P checkMot(String mot) {
		if (mot == null || mot.isEmpty())
			return (null);

		@SuppressWarnings("unchecked")
		E[] etiquettes = (E[]) mot.toLowerCase().chars()
				.mapToObj(c -> Character.valueOf((char) c))
				.toArray(size -> (E[]) new Character[size]);

		return (checkMot(etiquettes));
	}

	public P checkMot(E[] etiquettes) {
		T courant;
		T enfant;
		StringBuilder chemin;

		chemin = new StringBuilder();
		courant = racine;
		for (E etiquette : etiquettes) {
			enfant = courant.getEnfant(etiquette);
			if (enfant == null) {
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

	// old Sauvegarde
	// public void sauvegarder(String fichier) throws IOException {
	// try (ObjectOutputStream oos = new ObjectOutputStream(new
	// FileOutputStream(fichier))) {
	// oos.writeObject(this);
	// }
	// }

	// sync() :
	// Force réellement l’écriture physique
	// Sinon le système peut garder en cache.
	// ATOMIC_MOVE :
	// Le système garantit :
	// remplacement complet
	// pas d’état intermédiaire
	public void sauvegarder(String fichier) throws IOException {

		Path original = Paths.get(fichier);
		Path temp = Paths.get(fichier + ".tmp");

		// 1 Écriture dans fichier temporaire
		try (FileOutputStream fos = new FileOutputStream(temp.toFile());
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {

			oos.writeObject(this);
			oos.flush();

			// 2 Force écriture disque
			fos.getFD().sync();
		}

		// 3 crée une bakup avant remplcement
		Files.copy(original, Paths.get(fichier + ".bak"), StandardCopyOption.REPLACE_EXISTING);

		// 4 Remplacement atomique
		try {
			Files.move(temp, original, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
		} catch (AtomicMoveNotSupportedException e) {
			Files.move(temp, original, StandardCopyOption.REPLACE_EXISTING);
		}
	}

	// @SuppressWarnings("unchecked")
	// public static <T extends NoeudTrie<T, E, P> & Serializable, E, P>
	// TrieGenerique<T, E, P> charger(String fichier) throws IOException,
	// ClassNotFoundException {
	// try (ObjectInputStream ois = new ObjectInputStream(
	// new FileInputStream(fichier))) {
	// Object obj = ois.readObject();
	// if (obj instanceof TrieGenerique) {
	// return (TrieGenerique<T, E, P>) obj;
	// }
	// throw new ClassCastException("Fichier corrompu");
	// }
	// }

	// Chargement
	@SuppressWarnings("unchecked")
	public static <T extends NoeudTrie<T, E, P> & Serializable, E, P> TrieGenerique<T, E, P> charger(String fichier)
			throws IOException, ClassNotFoundException {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier))) {
			return (TrieGenerique<T, E, P>) ois.readObject();
		}
	}
}
