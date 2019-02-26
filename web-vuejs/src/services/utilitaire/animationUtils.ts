export default class AnimationUtils {

    /**
     * Méthode déplaçant la vue jusqu'au composant HTML spécifié
     * @param idComposant Id du composant HTML
     * @param focus True pour donner le focus au premier champ présent dans le composant HTML
     */
    public deplacerLaVueSurLeComposant(idComposant: string, focus: boolean = false) {
        const execution = () => {
            const cadre = document.getElementById(idComposant);
            if (cadre) {

                // Scroll
                cadre.scrollIntoView({ behavior: 'smooth', block: 'end', inline: 'center' });

                // Focus sur le premier champ du formulaire
                if (focus) {
                    const premierChamp = cadre.querySelector('input:not([disabled="disabled"])') as HTMLElement;
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
