package business

import scala.util.{Try, Success, Failure}

/**
 * L'objet Main représente le point d'entrée du programme de gestion de bibliothèque.
 * Il permet à l'utilisateur d'interagir avec la bibliothèque en choisissant diverses actions depuis un menu.
 */
object Main {
  def main(args: Array[String]): Unit = {
    val bibliotheque = new Bibliotheque()
    bibliotheque.chargerDepuisFichier()

    while (true) {
      println("********************************************************************")
      println("              Application de Gestion de Bibliothèque")
      println("********************************************************************")
      println("1. Ajouter un livre")
      println("2. Emprunter un livre")
      println("3. Rendre un livre")
      println("4. Rechercher un livre par titre")
      println("5. Rechercher un livre par auteur")
      println("6. Sauvegarder et quitter")
      print("Votre choix (1-6) : ")

      val choixInput = scala.io.StdIn.readLine()
      Try(choixInput.toInt) match {
        case Success(choix) =>
          choix match {
            case 1 =>
              println("**************** Ajouter un livre ****************")
              print("Titre : ")
              val titre = scala.io.StdIn.readLine()
              print("Auteur : ")
              val auteur = scala.io.StdIn.readLine()
              var anneeDePublication: Option[Int] = None
              var anneeDePublicationValid = false

              while (!anneeDePublicationValid) {
                try {
                  print("Année de publication : ")
                  anneeDePublication = Some(scala.io.StdIn.readInt())
                  anneeDePublicationValid = true
                } catch {
                  case _: NumberFormatException =>
                    println("Format de l'année de publication invalide. Veuillez entrer une année valide.")
                }
              }

              val livre = new Livre(titre, auteur, anneeDePublication.getOrElse(0))
              bibliotheque.ajouterLivre(livre) match {
                case Some(message) => println(s"Erreur : $message")
                case None => println("Livre ajouté à la bibliothèque.")
              }
            case 2 =>
              println("**************** Emprunter un livre ****************")
              print("Titre du livre : ")
              val titre = scala.io.StdIn.readLine()

              bibliotheque.emprunterLivre(titre) match {
                case Some(message) => println(s"Erreur : $message")
                case None => println(s"Livre avec le titre '$titre' a été emprunté.")
              }
            case 3 =>
              println("**************** Rendre un livre ****************")
              print("Titre du livre : ")
              val titre = scala.io.StdIn.readLine()
              bibliotheque.rendreLivre(titre) match {
                case Some(message) => println(s"Erreur : $message")
                case None => println(s"Livre avec le titre '$titre' a été rendu.")
              }
            case 4 =>
              println("**************** Rechercher par titre ****************")
              print("Titre : ")
              val titre = scala.io.StdIn.readLine()
              val resultatsOption = bibliotheque.rechercherParTitre(titre)
              resultatsOption match {
                case Some(resultats) if resultats.nonEmpty =>
                println("Résultats :")
                  resultats.foreach(println)
                case Some(_) =>
                  println(s"Aucun livre trouvé avec le titre '$titre'.")
                case None =>
                  println("Aucun livre trouvé dans la bibliothèque avec le titre spécifié.")
              }
            case 5 =>
              println("**************** Rechercher par auteur ****************")
              print("Auteur : ")
              val auteur = scala.io.StdIn.readLine()
              val resultatsOption = bibliotheque.rechercherParAuteur(auteur)
              resultatsOption match {
                case Some(resultats) if resultats.nonEmpty =>
                println("Résultats :")
                  resultats.foreach(println)
                case Some(_) => println(s"Aucun livre trouvé de l'auteur '$auteur'.")
                case None => println("Aucun livre trouvé dans la bibliothèque de cet auteur.")
              }
            case 6 =>
              println("**************** Sauvegarde en cours... ****************")
              bibliotheque.sauvegarderDansFichier()
              println("Au revoir !")
              return
            case _ => println("Choix invalide, veuillez réessayer.")
          }
        case _ =>
          println("Choix invalide, veuillez entrer un chiffre de 1 à 6.")
      }
    }
  }
}
