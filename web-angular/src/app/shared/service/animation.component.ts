// Sources : http://www.javascripter.net/faq/browsern.htm

import { Injectable } from '@angular/core';

/** Classe permettant la détection du navigateur et de l'OS */
@Injectable()
export class AnimationComponent {

  /**
   * Scroll vers le bas pour affiche le cadre.
   * (avec un petit décalage temporel pour laisser le temps au cadre de s'afficher)
   * @param idComposant Identifiant unique du composant
   */
  deplacerLaVueSurLeComposant(idComposant: string): void {
    setTimeout(() => {
      const cadre = document.getElementById(idComposant);
      if (cadre) {
        cadre.scrollIntoView({ behavior: 'smooth', block: 'end', inline: 'center' });
      }
    }, 200);
  }
}
