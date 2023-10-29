package scala
/**
 * La classe Livre représente un livre dans une bibliothèque.
 *
 * @param titre Le titre du livre.
 * @param auteur L'auteur du livre.
 * @param anneeDePublication L'année de publication du livre.
 */
class Livre(val titre: String, val auteur: String, val anneeDePublication: Int) {
  var estEmprunte: Boolean = false // Indique si le livre est actuellement emprunté ou non

  /**
   * Emprunte le livre en mettant à jour son état.
   * Si le livre est déjà emprunté, affiche un message d'erreur.
   */
  def emprunter(): Unit = {
    if (!estEmprunte) {
      estEmprunte = true
    } else {
      println("Le livre est déjà emprunté.")
    }
  }

  /**
   * Rend le livre en mettant à jour son état.
   * Si le livre n'est pas emprunté, affiche un message d'erreur.
   */
  def rendre(): Unit = {
    if (estEmprunte) {
      estEmprunte = false
    } else {
      println("Le livre n'est pas emprunté.")
    }
  }

  /**
   * Génère une représentation textuelle du livre.
   *
   * @return Une chaîne de caractères représentant le livre avec son titre, auteur,
   *         année de publication et son état d'emprunt (oui ou non).
   */
  override def toString: String = {
    val estEmprunteText = if (estEmprunte) "oui" else "non"
    s"Titre: $titre, Auteur: $auteur, Année de publication: $anneeDePublication, Emprunté: $estEmprunteText"
  }
}

