
# Rapport de conception détaillée du système dossier médical centralisé

## présenté à Dogny Gnagnely Serge  et Faidi Sofiane

<br>

<br>

## Composition de l'équipe : 

**Fethi bey Abi ayad :**

 *Code permanent :* ABIF10108204

 *adresse courriel :* abi_ayad.fethi_bey@courrier.uqam.ca 

**Hamza Yahi :**

 *Code permanent :*  YAHH07019802  

 *adresse courriel :* af491086@ens.uqam.ca
 
 **Mohamed Hocine Rehouma :**

 *Code permanent :* 

 *adresse courriel :* 

<br>

## Table des matières
- [Diagramme des usecases](#id-section1)

- [Diagramme de classe](#id-section2)

    - [Diagramme de classe de l'application du medecin](#id-section3)    
    - [Diagramme de classe de l'application du professionnel](#id-section4)    
    - [Diagramme de classe de l'application de l'assuré ](#id-section5)    
    - [Diagramme de classe de l'application de la RAMQ ](#id-section6)   

- [Diagramme de séquence](#id-section7)

    - [S'authentifier](#id-section8)    
    - [Télécharger dossier](#id-section9)    
    - [Consulter l'historique des visites](#id-section10)   
    - [Consulter l'historique des traitements](#id-section11)   
    - [Consulter les antécédents](#id-section12)   
    - [Consulter les coordonnées](#id-section13)   
    - [Ajouter une nouvelle visite](#id-section14)   
    - [Ajouter un nouvel antécédent](#id-section15) 
    - [Modifier les coordonnées ](#id-section16) 
    - [Annuler les modifications ](#id-section17) 
    - [Enregistrer le dossier ](#id-section18) 
    - [Reconstruire le dossier par date](#id-section19) 
    - [Reconstruire le dossier par modification](#id-section20) 

- [Diagramme de packages](#id-section21)

- [Diagramme de composants](#id-section22)

- [Diagramme de déploiement de système](#id-section23)


<div id='id-section1'/>

## Diagramme des usecases

![Diagramme des usecases](models/Diagramme_Usecases/usecase_diagram.png)

<div id='id-section2'/>

## Diagrammes de classe

<div id='id-section3'/>

### Diagramme de classe De l'application du medecin

![Diagramme de classe du medecin](models/Diagramme_Classes/medecin/class_diagram_medecin_mvc.png)

<div id='id-section4'/>

### Diagramme de classe De l'application du professionnel

![Diagramme de classe du professionnel](models/Diagramme_Classes/professionnel/class_diagram_professionnel_mvc.png)

<div id='id-section5'/>

### Diagramme de classe De l'application de l'assuré (application web et mobile) 

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

![Diagramme de séquence consulter historique visites](models/Diagramme_Sequences/Consulter_historiqe_visites/sequence_diagram_consulter_historique.png)


<div id='id-section11'/>

### Diagramme de séquence : Consulter l'historique des traitements

![Diagramme de séquence Consulter l'historique des traitements](models/Diagramme_Sequences/Consulter_historique_traitements/sequence_diagram_consulter_traitements.png)


<div id='id-section12'/>

### Diagramme de séquence : Consulter les antécédents

![Diagramme de séquence Consulter les antécédents](models/Diagramme_Sequences/Consulter_antecedents/sequence_diagram_consulter_antecedents.png)


<div id='id-section13'/>

### Diagramme de séquence : Consulter les coordonnées

![Diagramme de séquence Consulter les coordonnées](models/Diagramme_Sequences/Consulter_coordonnees/sequence_diagram_consulter_coordonnees.png)


<div id='id-section14'/>

### Diagramme de séquence : Ajouter une nouvelle visite

![Diagramme de séquence Ajouter une nouvelle visite](models/Diagramme_Sequences/Creer_visite/sequence_diagram_nouvelle_visite.png)


<div id='id-section15'/>

### Diagramme de séquence : Ajouter un nouvel antécédent

![Diagramme de séquence Ajouter un nouvel antécédent](models/Diagramme_Sequences/Ajouter_antecedent/sequence_diagram_ajouter_antecedent.png)


<div id='id-section16'/>

### Diagramme de séquence : modifier les coordonnées

![Diagramme de séquence modifier les coordonnées]()


<div id='id-section17'/>

### Diagramme de séquence : Annuler les modifications

![Diagramme de séquence Annuler les modifications](models/Diagramme_Sequences/Annuler_modification/sequence_diagram_annuler_modification.png)


<div id='id-section18'/>

### Diagramme de séquence : Enregistrer le dossier

![Diagramme de séquence Enregistrer le dossier](models/Diagramme_Sequences/Enregistrer_dossier/sequence_diagram_enregistrer_dossier.png)

<div id='id-section19'/>

### Diagramme de séquence : Reconstruire le dossier par date

![Diagramme de séquence Reconstruire le dossier par date]()


<div id='id-section20'/>

### Diagramme de séquence : Reconstruire le dossier par modification

![Diagramme de séquence Reconstruire le dossier par modification]()


<div id='id-section21'/>

## Diagramme de packages

![Diagramme de packages](models/Diagramme_Package/package_diagram.png)


<div id='id-section22'/>

## Diagramme de composants

![Diagramme de composants]()


<div id='id-section23'/>

## Diagramme de déploiement du système

![Diagramme de déploiement](models/Diagramme_Deploiement_Systeme/system_deployment_diagram.png)