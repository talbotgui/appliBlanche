// Sources : http://www.javascripter.net/faq/browsern.htm

import { Injectable } from '@angular/core';

/** Classe permettant la détection du navigateur et de l'OS */
@Injectable()
export class AnimationComponent {

  /**
   * Scroll vers le bas pour affiche le cadre.
   * (avec un petit décalage temporel pour laisser le temps au cadre de s'afficher).
   * @param idComposant Identifiant unique du composant.
   * @param focus Donne le focus au premier champ trouvé dans la div.
   */
  deplacerLaVueSurLeComposant(idComposant: string, focus: boolean = false): void {

    const execution = () => {

      // Recherche du cadre par son ID
      const cadre = document.getElementById(idComposant);
      if (cadre) {

        // Scroll
        cadre.scrollIntoView({ behavior: 'smooth', block: 'end', inline: 'center' });

        // Focus sur le premier champ du formulaire
        if (focus) {
          const premierChamp = cadre.querySelector('input:not([ng-reflect-disabled="true"])') as HTMLElement;
          if (premierChamp) {
            const estDatePicker = !!premierChamp.attributes.getNamedItem('ng-reflect-mat-datepicker');
            if (estDatePicker) {
              const datePicker = cadre.querySelector('mat-datepicker-toggle button') as HTMLElement;
              if (datePicker) {
                setTimeout(() => { datePicker.click(); }, 400);
              }
            } else {
              premierChamp.focus();
            }
          }
        }
      }
    };

    // Petit timeout pour laisser le formulaire s'afficher
    setTimeout(execution, 200);
  }
}
