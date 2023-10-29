package scala

import java.io.{BufferedWriter, FileWriter}
import scala.io.Source

/**
 * La classe Bibliotheque représente une bibliothèque de livres et fournit des méthodes
 * pour gérer les opérations liées aux livres.
 */
class Bibliotheque {
  var livres: List[Livre] = List()

  /**
   * Ajoute un livre à la bibliothèque.
   *
   * @param livre Le livre à ajouter.
   * @return Un message d'erreur en cas d'échec ou None en cas de succès.
   */
  def ajouterLivre(livre: Livre): Option[String] = {
    if (livres.exists(_.titre == livre.titre)) {
      Some(s"Un livre avec le titre '${livre.titre}' existe déjà dans la bibliothèque.")
    } else {
      livres = livre :: livres
      None
    }
  }

  /**
   * Emprunte un livre de la bibliothèque.
   *
   * @param titre Le titre du livre à emprunter.
   * @return Un message d'erreur en cas d'échec ou None en cas de succès.
   */
  def emprunterLivre(titre: String): Option[String] = {
    livres.find(_.titre == titre) match {
      case Some(livre) =>
        if (livre.estEmprunte) {
          Some(s"Le livre avec le titre '$titre' est déjà emprunté.")
        } else {
          livre.emprunter()
          None // Opération réussie
        }
      case None =>
        Some(s"Le livre avec le titre '$titre' n'existe pas dans la bibliothèque.")
    }
  }

  /**
   * Rend un livre emprunté à la bibliothèque.
   *
   * @param titre Le titre du livre à rendre.
   * @return Un message d'erreur en cas d'échec ou None en cas de succès.
   */
  def rendreLivre(titre: String): Option[String] = {
    livres.find(_.titre == titre) match {
      case Some(livre) if livre.estEmprunte =>
        livre.rendre()
        None // Opération réussie
      case Some(livre) if !livre.estEmprunte =>
        Some(s"Le livre avec le titre '$titre' n'est pas emprunté.")
      case None =>
        Some(s"Le livre avec le titre '$titre' n'existe pas dans la bibliothèque.")
    }
  }

  /**
   * Recherche des livres par titre.
   *
   * @param titre Le titre du livre à rechercher.
   * @return Une liste de livres correspondant au titre ou None si aucun livre n'est trouvé.
   */
  def rechercherParTitre(titre: String): Option[List[Livre]] = {
    val resultats = livres.filter(_.titre == titre)
    if (resultats.isEmpty) {
      None
    } else {
      Some(resultats)
    }
  }

  /**
   * Recherche des livres par auteur.
   *
   * @param auteur Le nom de l'auteur à rechercher.
   * @return Une liste de livres de l'auteur ou None si aucun livre n'est trouvé.
   */
  def rechercherParAuteur(auteur: String): Option[List[Livre]] = {
    val resultats = livres.filter(_.auteur == auteur)
    if (resultats.isEmpty) {
      None
    } else {
      Some(resultats)
    }
  }

  /**
   * Sauvegarde l'état actuel de la bibliothèque dans un fichier.
   */
  def sauvegarderDansFichier(): Unit = {
    val filename = "src//main//ressources//bibliotheque.csv"
    val writer = new BufferedWriter(new FileWriter(filename))
    try {
      livres.foreach(livre => {
        writer.write(s"${livre.titre},${livre.auteur},${livre.anneeDePublication},${if (livre.estEmprunte) "Emprunté" else "Disponible"}\n")
      })
    } finally {
      writer.close()
    }
  }

  /**
   * Charge l'état de la bibliothèque depuis un fichier.
   */
  def chargerDepuisFichier(): Unit = {
    try {
      val lines = Source.fromResource("bibliotheque.csv").getLines()
      for (line <- lines) {
        val parts = line.split(",")
        val titre = parts(0)
        val auteur = parts(1)
        val anneeDePublication = parts(2).toInt
        val estEmprunte = parts(3) == "Emprunté"
        val livre = new Livre(titre, auteur, anneeDePublication)
        if (estEmprunte) livre.emprunter()
        livres = livre :: livres
      }
    } catch {
      case e: Exception => println(e.getMessage)
    }
  }
}
