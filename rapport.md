
Université du Québec à Montréal


Rapport de conception détaillée du système dossier médical centralisé

<br><br><br><br>

Travail présenté à


**Monsieur Serge Dogny Gnagnely**


<br><br>
Dans le cadre du cours

Génie logiciel: conception(INF5153)


<br><br><br><br>
par 
<br><br>
**Fethi bey Abi ayad :**

 *Code permanent :* ABIF10108204

 *adresse courriel :* abi_ayad.fethi_bey@courrier.uqam.ca 

**Hamza Yahi :**

 *Code permanent :*  YAHH07019802  

 *adresse courriel :* af491086@ens.uqam.ca
 
 **Mohamed Hocine Rehouma :**

 *Code permanent :* rehm23029604

 *adresse courriel :* ck191923@ens.uqam.ca

<br>



**Table des matières**

[[_TOC_]]

<div id='id-section1'/>

## Diagramme des usecases

![Diagramme des usecases](models/Diagramme_Usecases/usecase_diagram.png)

<div id='id-section2'/>

## Diagrammes de classe

Les types d'acteurs qui intéragissent avec le système du dossier médical centralisé se distinguent en 4 catégories :

- Le médecin qui peut consulter et modifier le dossier du patient
- Le professionnel de la santé autre que le médecin qui ne peut que consulter le dossier du patient
- L'assuré qui peut consulter son propre dossier médical avec la possibilité de modification des coordonnées
- Le système RAMQ qui est responsable de la création des dossiers des nouvaux assurés avec la possibilité de reconstruction d'un dossier à une date précise

De ce fait, on modélise un diagramme de classe pour chaque type d'acteur utilisant une application spécifique à son besoin.

<div id='id-section3'/>

### Diagramme de classe De l'application du medecin

La modélisation de l'application utilisée par le médecin se distingue par la présence des classes PrescripteurVue et PrescripteurControleur.

Ces deux classes permettent à l'utilisateur de modifier certaines données tel que l'ajout d'une nouvelle visite, l'ajout d'un nouvel antécédent et la modification d'un ancien antécédent lié au medecin utilisateur

![Diagramme de classe du medecin](models/Diagramme_Classes/medecin/class_diagram_medecin_mvc.png)

<div id='id-section4'/>

### Diagramme de classe De l'application du professionnel

La modélisation de l'application utilisée par le professionnel de santé autre qu'un médecin se distingue par l'absence des classes PrescripteurVue et PrescripteurControleur.

L'utilisateur ne pourra modifier aucune information sur le dossier, mais peut les consulter en lecture seule à travers la classe HistoriqueVue. 

![Diagramme de classe du professionnel](models/Diagramme_Classes/professionnel/class_diagram_professionnel_mvc.png)

<div id='id-section5'/>

### Diagramme de classe De l'application de l'assuré (application web et mobile) 

La modélisation de l'application utilisée par l'assuré se distingue aussi par l'absence des classes PrescripteurVue et PrescripteurControleur mais avec une différence pour la classe PatientControleur qui permet à l'utilisateur de modifier ses coordonnées.

![Diagramme de classe de l'assuré](models/Diagramme_Classes/assure/class_diagram_assure_mvc.png)

<div id='id-section6'/>

### Diagramme de classe De l'application de la RAMQ


<div id='id-section7'/>

## Diagrammes de séquence 

<div id='id-section8'/>

### Diagramme de séquence : S'authentifier

![Diagramme de séquence s'authentifier](models/Diagramme_Sequences/S'authentifier/sequence_diagram_authentifier.png)

<div id='id-section9'/>

### Diagramme de séquence : Télécharger dossier

![Diagramme de séquence télécharger dossier](models/Diagramme_Sequences/Telecharger_dossier/sequence_diagram_telechargerDossier.png)

<div id='id-section10'/>

### Diagramme de séquence : Consulter l'historique des visites

Ce cas d'utilisation est déclenché par le chargement du dossier. le résultat obtenu est l'affichage de la liste de l'historique des visites dans la vue Historique. 

![Diagramme de séquence consulter historique visites](models/Diagramme_Sequences/Consulter_historiqe_visites/sequence_diagram_consulter_historique.png)


<div id='id-section11'/>

### Diagramme de séquence : Consulter l'historique des traitements

Ce cas d'utilisation est déclenché par le chargement du dossier. le résultat obtenu est l'affichage de la liste des traitements du patient dans la vue Historique. 

![Diagramme de séquence Consulter l'historique des traitements](models/Diagramme_Sequences/Consulter_historique_traitements/sequence_diagram_consulter_traitements.png)
git 
### Diagramme de séquence : Consulter les coordonnées

Ce cas d'utilisation est déclenché par le chargement du dossier. le résultat obtenu est l'affichage des coordonnées du patient dans la vue Patient.


![Diagramme de séquence Consulter les coordonnées](models/Diagramme_Sequences/Consulter_coordonnees/sequence_diagram_consulter_coordonnees.png)


<div id='id-section14'/>

### Diagramme de séquence : Ajouter une nouvelle visite

Ce cas d'utilisation est déclenché par le médecin sur la vue Prescripteur. le résultat obtenu est l'ajout d'un nouveau objet visite au dossier.


![Diagramme de séquence Ajouter une nouvelle visite](models/Diagramme_Sequences/Creer_visite/sequence_diagram_nouvelle_visite.png)


<div id='id-section15'/>

### Diagramme de séquence : Ajouter un nouvel antécédent

Ce cas d'utilisation est déclenché par le médecin sur la vue Prescripteur. le résultat obtenu est l'ajout d'un nouveau objet antecedent au dossier.


![Diagramme de séquence Ajouter un nouvel antécédent](models/Diagramme_Sequences/Ajouter_antecedent/sequence_diagram_ajouter_antecedent.png)


<div id='id-section151'/>

### Diagramme de séquence : Modifier antécédent

Ce cas d'utilisation est déclenché par le médecin sur la vue Prescripteur sur une ligne d'un ancien antécédent. L'entécédent doit être enregistré auparavant par le même médecin utilisateur. Le résultat obtenu est la modification de l'ancien antecedent au dossier.


![Diagramme de séquence Modifier un antécédent](models/Diagramme_Sequences/Modifier_antecedent/sequence_diagram_modifier_antecedent.png)

<div id='id-section16'/>

### Diagramme de séquence : modifier les coordonnées

Ce cas d'utilisation est déclenché par le patient. La saisies des nouvelles données sont envoyées à la vue Coordonnées. Le résultat obtenu est la modification des données du patient.
![Diagramme de séquence modifier les coordonnées](models/Diagramme_Sequences/Modifier_Coordonnees/diag_seq_modifier_coordonnees.png)


<div id='id-section17'/>

### Diagramme de séquence : Annuler les modifications

![Diagramme de séquence Annuler les modifications](models/Diagramme_Sequences/Annuler_modification/sequence_diagram_annuler_modification.png)


<div id='id-section18'/>

### Diagramme de séquence : Enregistrer le dossier

Ce cas d'utilisation est déclenché par le médecin sur la vue Prescripteur . En cas de modification de dossier, le résultat obtenu est la mise à jour du dossier sur la base de données distante avec une création d'une nouvelle ligne sur la table archive incluant un objet json contenant toutes les information de l'objet dossier et correspondant à la date du mise à jour et le numéro d'assurance du patient.

![Diagramme de séquence Enregistrer le dossier](models/Diagramme_Sequences/Enregistrer_dossier/sequence_diagram_enregistrer_dossier.png)


<div id='id-section19'/>

### Diagramme de séquence : Reconstruire le dossier par date
Ce cas d'utilisation est déclenché par la RAMQ. Un requête est alors envoyée à la base de données qui contient l'archive des dossier et effectue une recherche selon la date. Une fois le dossier trouvé, un fichier JSON contenant les informations du dossier est retourné à la RAMQ. 

![Diagramme de séquence Reconstruire le dossier par date](models/Diagramme_Sequences/Reconstruire_dossier_date/dossierDate_code.png)


<div id='id-section20'/>

### Diagramme de séquence : Reconstruire le dossier par modification
Ce cas d'utilisation est déclenché par la RAMQ. Un requête est alors envoyée à la base de données qui contient l'archive des dossier effectue une recherche selon la modification entrée. Une fois le dossier trouvé, un fichier JSON contenant les informations du dossier est retourné à la RAMQ. 


![Diagramme de séquence Reconstruire le dossier par modification](models/Diagramme_Sequences/Reconstruire_dossier_modification/reconstruireDossier_Modification.png)


<div id='id-section21'/>

### Diagramme de séquence : Création d'un dossier

![Diagramme de séquence Création d'un dossier](models/Diagramme_Sequences/Creer_Dossier/creerDossier_code.png)

<div id='id-section22'/>


## Diagramme de packages

On peut séparer les classes de notre application en cinq packages différents :

- Le package `main` : ne contient que la classe Main qui est le point d'entrée de l'application.
- Le  package `vue` : contient toutes les classes générant la vue affichée à l'utilisateur.
- Le package `controleur` : contient toutes les classes de responsabilité de contrôl.
- Le package `model` : contient les classes constituants les entités de notre application.
- Le package `connexion` : contient les classes responsable de la communication avec l'environnement extérne à notre applicattion par des requettes Http ou SQL

![Diagramme de packages](models/Diagramme_Package/package_diagram.png)


<div id='id-section22'/>

## Diagramme de composants

![Diagramme de composants](models/Diagramme_Composant/diag_component.png)


<div id='id-section23'/>

## Diagramme de déploiement du système

![Diagramme de déploiement](models/Diagramme_Deploiement_Systeme/system_deployment_diagram.png)


<div id='id-section24'/>

## Justification des responsabilités des classes selon le pattern GRASP



- **ConnexionControleur** : a la responsabilité de contrôleur qui gère la connexion de l'utilisateur en récupérant les données saisies de la classe ConnexionVue, les valident avec la méthode privée validerSaisie() puis délègue la connexion à la classe Session via la méthode publique login().

- **PrescripteurControleur** : présente que dans l'application du médecin a la responsabilité de contrôleur qui gère : 
    - L'affichage des visites du médecin connecté via la méthode afficherVisites() qui récupère les données via la méthode publique getVisitesPrescripteur() de la classe Session.
    - L'affichage des antécédents du médecin connecté via la méthode afficherAntecedents() qui récupère les données via la méthode publique getAntecedentsPrescripteur() de la classe Session.
    - L'ajout d'une nouvelle visite via la méthode creerVisites() et enregistrerVisite() qui délègue la création de l'objet Visite à la classe Session via sa méthode publique creerVisite().
    - L'ajout d'un nouvel antécédent via la méthode ajouterAntecedent() et enregistrerEntecedent()qui délègue la création de l'objet Antecedent à la classe Session via sa méthode publique creerAntecedent().

- **HistoriqueControleur** : a la responsabilité de contrôleur qui gère : 
    - L'affichage des visites des médecins autres que le médecin connecté via la méthode afficherVisites() qui récupère les données via la méthode publique getVisitesHistorique() de la classe Session.
    - L'affichage des antécédents des médecins autres que le médecin connecté via la méthode afficherAntecedents() qui récupère les données via la méthode publique getAntecedentsHistorique() de la classe Session.
   - L'affichage des traitements du patient via la méthode afficherTraitements() qui récupère les données via la méthode publique getTraitements() de la classe Session.

- **PatientControleur** : a la responsabilité de contrôleur qui gère : 
   - L'affichage des coordonnées du patient via la méthode afficherPatient() qui récupère les données via la méthode publique getPatient() de la classe Session.
   - La modification des coordonnées du patient (Application de l'assuré) via la méthode modifierCoordonnees() qui délègue cette opération à la classe Session via sa métode publique modifierCoordonnees()

- **Session :**

    + **telechargerDossier(noAss : String)**<br>
        Controleur : la classe Session a la responsabilité de gérer le chargement de l'objet Dossier en déléguant cette opération à la classe SourceDonnees via la méthode publique findbyId(id: String).

    + **enregistrerDossier()**<br>
        Controleur, spécialiste de l'information : la classe Session a la responsabilité de gérer la sauvegarde de l'objet Dossier car elle a l'information de cet objet à sauvegarder, en déléguant cette opération à la classe SourceDonnees via la méthode publique update(d: Dossier).

    + **creerVisite()**<br>
        Créateur, spécialiste de l'information : la classe Session a la responsabilité de créer un objet Visite en tant que nouvelle visite, et cette information résidera au niveau de la classe Session.
    
    + **creerAntecedent()**<br>
        Créateur, spécialiste de l'information : la classe Session a la responsabilité de créer un objet Antecedent en tant que nouvel antécédent, et cette information résidera au niveau de la classe Session.
    
    + **annulerModification()** <br>
        spécialiste de l'information : la classe Session peut annuler toutes les modifications aportées au dossier car elle détient toute l'information concernant le dossier ouvert ainsi que la nouvelle visite ou le nouvel antécédent créés.
    
    + **getVisitesPrescripteur() : List\<Visite>** / **getVisitesHistorique() : List\<Visite>** <br>
        spécialiste de l'information : la classe Session récupère la liste des objets Visite de la classs Dossier via la méthode publique getVisites() puis filtre cette liste selon l'objet Medecin (le médecin connecté). La classe Session détient l'information du dossier et du médecin connecté.

    + **getAntecedentsPrescripteur() : List\<Antecedent>** / **getAntecedentsHistorique() : List\<Antecedent>** <br>
        spécialiste de l'information : la classe Session récupère la liste des objets Antecedent de la classs Dossier via la méthode publique getAntecedents() puis filtre cette liste selon l'objet Medecin (le médecin connecté). La classe Session détient l'information du dossier et du médecin connecté.
    
    + **getTraitements() : List\<Traitement>** <br>
        spécialiste de l'information : la classe Session récupère la liste des objets Traitement de la classs Dossier via la méthode publique getTraitements() car elle détient l'information de l'objet Dossier.
    
    + **login(user: String, pass: String) : boolean** <br>
        Controleur : la classe Session implémente l'interface Authentifiable qui est responsable de la gestion de la connexion de l'utilisateur en déléguant cette opération à la classe SourceDonnees via la méthode publique findbyUsernameAndPassword().

- **SourceDonneesImplem :**

    + **update(d : Dossier)**<br>
        Controleur , spécialiste de l'information: L'interface SourceDonnees implémente l'interface Modifiable qui a la responsabilité de gérer la mise à jour des données du dossier sur la base de données distante car elle détient l'information de connexion et de communication avec cette base de données.

    + **findbyUsernameAndPassword (username : String, password: String) : Optional\<Utilisateur>**<br>
        Créateur , spécialiste de l'information: L'interface SourceDonnees implémente l'interface Recherchable qui a la responsabilité de créer l'objet Utilisateur à partir la base de données distante car elle détient l'information de connexion et de communication avec cette base de données.

    + **findbyId(id : String) : Optional\<Dossier>**<br>
        Créateur , spécialiste de l'information: L'interface SourceDonnees implémente l'interface Recherchable qui a la responsabilité de créer l'objet Dossier à partir la base de données distante car elle détient l'information de connexion et de communication avec cette base de données.

- **Dossier :**

    + **AjouterVisite(visite: Visite)**<br>
        Créateur : La classe dossier a la responsabilité d'ajouter les visites car c'est elle qui contient la liste des visites associées au dossier, c'est a dire que c'est elle qui est <b> composée</b> de "Visite".


    + **AjouterAntecedent(antecdent: Antecedent)**<br> 
        Créateur : La classe dossier a la responsabilité d'ajouter les antecedents car c'est elle qui contient la liste des antecedents associées au dossier, c'est a dire que c'est elle qui est <b> composée</b> de "Antecedents".


    + **getVisites(List\<Visite\>)**<br>
        Specialiste de l'information : La classe dossier a la responsabilité de recupérer les visites car c'est elle qui contient la liste des visites associées au dossier.

    + **getAntecedents(List\<Antecedent\>)**<br>
        Spécialiste de l'information : La classe dossier a la responsabilité de recuperer les antecedents car c'est elle qui contient la liste des antecedents du patient .

- **Visite :**

    + **Générale**<br>
        Createur : La classe Visite est composée de traitements,diagnostics et un medecin, elle a donc la responsabilité de creer une nouvelle instance de ces objets lorsque necessaire tel que lors de la recuperation d'un dossier de la base de données.
    + **setResume (resume: String)**<br>
        Specialiste de l'information : La classe Visite a la responsabilité de définir le resume d'une visite car c'est elle qui contient la variable "resume".
    + **setDiagnostic (dg: Diagnostic)**<br>
        Specialiste de l'information : La classe Visite a la responsabilité de définir le diagnostic d'une visite car c'est elle qui est associée a 0 ou 1 diagnostic.
    + **setTraitement (dg: Diagnostic)**<br>
        Specialiste de l'information : La classe Visite a la responsabilité de définir le traitement d'une visite car c'est elle qui est associée à 0 ou 1 traitement .

- **Utilisateur:**

    + **getUsername()**<br>
        Specialiste de l'information : La classe Utilisateur a la responsabilité de recuperer le username car c'est elle qui contient le username  de l'utilisateur.
    
- **Antecedent:**

    + **Générale**<br>
        Createur : La classe Antecedents est composée de traitements,diagnostics et un medecin, elle a donc la responsabilité de creer une nouvelle instance de ces objets lorsque necessaire tel que lors de la recuperation d'un dossier de la base de données.
    + **setDebut (date: Date), setFin(date: Date), setDiagnostic(dg: Diagnostic) et setTraitement (trt: Traitement)**<br>
        Specialiste de l'information : La classe Antecednt a la responsabilité de définir ces valeurs car c'est elle qui contient les variables "debut" et "fin", ainsi que les instances des classes "Diagnostic" et Traitement .
    

- **Patient**

    + **Générale**<br>
        Createur : La classe Patient est composée de coordonnées, elle a donc la responsabilité de creer une nouvelle instance de "Coordonnées" lorsque necessaire tel que lors de la récuperation d'un dossier de la base de données.
    + **getCoordonnées()**<br>
        Specialiste de l'information : La classe Patient a la responsabilité de recuperer les coordonnées car c'est elle qui contient  une instance d'un objet appartenant a la classe "Coordonnées".



