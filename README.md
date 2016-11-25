# Vie Artificielle à refactorer

## Présentation du sujet

Cette application représente un monde où de la vie est simulée.

Pour lancer l'application, executer la classe `Main`

La fenêtre est découpée en 3 : la vue sur la simalution, le graph de suivie de la démographie du monde et un résumé textuel du monde.

L'univers est composé :

 * D'un sol energétique. Il est représenté par des cases en nuance de gris. Plus une cellule est pleine d'energie, plus elle est blanche. L'énérgie du monde est à somme constante, ce qui veut dire qu'aucune energie ne doit jamais être perdue.
 * Des plantes poussent sur ce sol en y pompant de l'énérgie via ses racines. La croissance des plantes suit un modèle fractal de L-system. Une plante est composé de tige (en vert), de racine (en bleu), de graine (en orange) et de jounctions (non visible).
 * Des vaches vivent sur le sol et se nourissent des graines qui poussent sur les plantes. Elles cherchent à manger jusqu'à ne plus avoir faim, auquel cas elle cherchent à se reproduire avec une autre vache. La nouvelle vache crée après une reproduction hérite ses gènes des ses parents et subit des mutations.
 * Des loups chassent les vaches. Ils suivent le même comportement que les vaches : trouver à manger puis se reproduire.

L'univers est à géométrie discrète torique.

## Évolution à apporter

Nous désirons apporter les modifications suivantes (par ordre de priorité) :

 1. Les loups doivent abandonner la poursuite d'une proie après N tours passés à la poursuivre et choisir une nouvelle vache (N doit être configurable comme le reste de l'application et suivre l'évolution génétique au même titre que ses autres caractèristiques).
 2. Les plantes ne doivent plus toutes mourrir en même temps. Nous voulons introduire un facteur aléatoire sur la durée de vie d'une plante. Ainsi, une plante peut vivre la durée de vie configurée plus ou moins X% (X étant configurable).
 3. Nous souhaitons pouvoir changer la configuration sans passer par le code et directement via l'interface graphique. Ceci est valable pour tous les paramêtre de configuration actuel.
 4. Les performances de l'application sont vraiment pas terrible. Pouvez-vous optimiser tout ça ?

Ces évolutions nous tiennent vraiment à cœur et comme l'application nous a déjà rapporté beaucoup d'argent, nous avons décidé de vous attribuer un budget illimité pour faire ce travail.
Vous êtes bien entendu libre de retravailler le code à votre guise pour arriver à vos faim, cependant il est très important que vous ne cassiez rien et que les fonctionnalités actuelles restent telquel !

Bon courage !
