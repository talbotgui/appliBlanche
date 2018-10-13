// Sources : http://www.javascripter.net/faq/browsern.htm

import { Injectable } from '@angular/core';

/** Classe permettant la détection du navigateur et de l'OS */
@Injectable()
export class BrowserComponent {

    /** Nom du navigateur */
    private browserName: string;

    /** Version complète du navigateur (string) */
    private fullVersion: string;

    /** Version majeure du navigateur */
    private majorVersion: number;

    /** Nom de l'OS */
    private os: string;

    /** Version de l'OS */
    private osVersion: string;

    /** Contructeur dans lequel on définit le browser et l'OS */
    constructor() {

        const nAgt = navigator.userAgent;
        this.browserName = navigator.appName;
        this.fullVersion = '' + parseFloat(navigator.appVersion);
        this.majorVersion = parseInt(navigator.appVersion, 10);
        let nameOffset;
        let verOffset;
        let ix;

        // In Opera, the true version is after "Opera" or after "Version"
        if (nAgt.indexOf('Opera') !== -1) {
            verOffset = nAgt.indexOf('Opera');
            this.browserName = 'Opera';
            this.fullVersion = nAgt.substring(verOffset + 6);
            if (nAgt.indexOf('Version') !== -1) {
                verOffset = nAgt.indexOf('Version');
                this.fullVersion = nAgt.substring(verOffset + 8);
            }
        }
        // In MSIE, the true version is after "MSIE" in userAgent
        else if (nAgt.indexOf('MSIE') !== -1) {
            verOffset = nAgt.indexOf('MSIE');
            this.browserName = 'Microsoft Internet Explorer';
            this.fullVersion = nAgt.substring(verOffset + 5);
        }
        // In Chrome, the true version is after "Chrome"
        else if (nAgt.indexOf('Chrome') !== -1) {
            verOffset = nAgt.indexOf('Chrome');
            this.browserName = 'Chrome';
            this.fullVersion = nAgt.substring(verOffset + 7);
        }
        // In Safari, the true version is after "Safari" or after "Version"
        else if (nAgt.indexOf('Safari') !== -1) {
            verOffset = nAgt.indexOf('Safari');
            this.browserName = 'Safari';
            this.fullVersion = nAgt.substring(verOffset + 7);
            if (nAgt.indexOf('Version') !== -1) {
                verOffset = nAgt.indexOf('Version');
                this.fullVersion = nAgt.substring(verOffset + 8);
            }
        }
        // In Firefox, the true version is after "Firefox"
        else if (nAgt.indexOf('Firefox') !== -1) {
            verOffset = nAgt.indexOf('Firefox');
            this.browserName = 'Firefox';
            this.fullVersion = nAgt.substring(verOffset + 8);
        }
        // In most other browsers, "name/version" is at the end of userAgent
        else if (nAgt.lastIndexOf(' ') + 1 < nAgt.lastIndexOf('/')) {
            nameOffset = nAgt.lastIndexOf(' ') + 1;
            verOffset = nAgt.lastIndexOf('/');
            this.browserName = nAgt.substring(nameOffset, verOffset);
            this.fullVersion = nAgt.substring(verOffset + 1);
            if (this.browserName.toLowerCase() === this.browserName.toUpperCase()) {
                this.browserName = navigator.appName;
            }
        }
        // trim the fullVersion string at semicolon/space if present
        if (this.fullVersion.indexOf(';') !== -1) {
            ix = this.fullVersion.indexOf(';');
            this.fullVersion = this.fullVersion.substring(0, ix);
        }
        if (this.fullVersion.indexOf(' ') !== -1) {
            ix = this.fullVersion.indexOf(' ');
            this.fullVersion = this.fullVersion.substring(0, ix);
        }

        this.majorVersion = parseInt('' + this.fullVersion, 10);
        if (isNaN(this.majorVersion)) {
            this.fullVersion = '' + parseFloat(navigator.appVersion);
            this.majorVersion = parseInt(navigator.appVersion, 10);
        }

        // Calcul de l'OS
        const userAgent = window.navigator.userAgent;
        const windowsPlatformsVersions = ['10.0;', '6.2;', '6.1;', '6.0;', '5.1;', '5.0;'];
        const windowsPlatformsVersionsCorrespondant = ['10.0', '8', '7', 'vista', 'XP', '2000'];

        if (/Macintosh/.test(userAgent)) {
            this.os = 'Mac OS';
        } else if (/.*OS [0-9]/.test(userAgent)) {
            this.os = 'iOS';
            this.osVersion = ('' + (/CPU.*OS ([0-9_]{1,5})|(CPU like).*AppleWebKit.*Mobile/i.exec(userAgent) || [0, ''])[1])
                .replace('undefined', '3_2').replace('_', '.').replace('_', '') || '0';
        } else if (/Windows/.test(userAgent)) {
            this.os = 'Windows';
            this.osVersion = windowsPlatformsVersionsCorrespondant[windowsPlatformsVersions.indexOf(userAgent.split(' ')[3])];
        } else if (/Android/.test(userAgent)) {
            this.os = 'Android';
        } else if (/Linux/.test(userAgent)) {
            this.os = 'Linux';
        }
    }

    /** Getter du nom de l'OS */
    public get nomOs(): string {
        return this.os;
    }

    /** Getter de la version de l'OS */
    public get versionOs(): string {
        return this.osVersion;
    }

    /** Getter du nom du navigateur */
    public get nomNavigateur(): string {
        return this.browserName;
    }

    /** Getter de la version complete du navigateur */
    public get versionCompleteNavigateur(): string {
        return this.fullVersion;
    }

    /** Getter de la version majeure du navigateur */
    public get versionMajeurNavigateur(): number {
        return this.majorVersion;
    }
}
