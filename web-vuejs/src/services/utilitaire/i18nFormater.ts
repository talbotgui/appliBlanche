import VueI18n from 'vue-i18n';

export class I18nFormatter implements VueI18n.Formatter {
    /**
     * interpolate
     *
     * @param {string} message string of list or named format.
     * @param {Object | Array} values values of `message` interpolation.
     * @return {Array<any>} interpolated values. you need to return the following.
     */
    public interpolate(message: any, values: any) {
        let messageFinal = message;
        if (values) {
            // Values n'est pas un array mais un observable
            for (let i = 0; i < 10; i++) {
                if (values[i]) {
                    messageFinal = messageFinal.replace('{{' + i + '}}', values[i]);
                } else {
                    break;
                }
            }
        }
        return [messageFinal];
    }
}
