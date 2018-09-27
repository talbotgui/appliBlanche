// Sources : http://www.javascripter.net/faq/browsern.htm

import { Injectable } from '@angular/core';

@Injectable()
export class BrowserComponent {

  private browserName: string;
  private fullVersion: string;
  private majorVersion: number;
  private os: string;
  private osVersion: string;

  /** Contructeur dans lequel on définit le browser et l'OS */
  constructor() {

    const nVer = navigator.appVersion;
    const nAgt = navigator.userAgent;
    this.browserName = navigator.appName;
    this.fullVersion = '' + parseFloat(navigator.appVersion);
    this.majorVersion = parseInt(navigator.appVersion, 10);
    var nameOffset, verOffset, ix;

    // In Opera, the true version is after "Opera" or after "Version"
    if ((verOffset = nAgt.indexOf("Opera")) != -1) {
      this.browserName = "Opera";
      this.fullVersion = nAgt.substring(verOffset + 6);
      if ((verOffset = nAgt.indexOf("Version")) != -1)
        this.fullVersion = nAgt.substring(verOffset + 8);
    }
    // In MSIE, the true version is after "MSIE" in userAgent
    else if ((verOffset = nAgt.indexOf("MSIE")) != -1) {
      this.browserName = "Microsoft Internet Explorer";
      this.fullVersion = nAgt.substring(verOffset + 5);
    }
    // In Chrome, the true version is after "Chrome" 
    else if ((verOffset = nAgt.indexOf("Chrome")) != -1) {
      this.browserName = "Chrome";
      this.fullVersion = nAgt.substring(verOffset + 7);
    }
    // In Safari, the true version is after "Safari" or after "Version" 
    else if ((verOffset = nAgt.indexOf("Safari")) != -1) {
      this.browserName = "Safari";
      this.fullVersion = nAgt.substring(verOffset + 7);
      if ((verOffset = nAgt.indexOf("Version")) != -1)
        this.fullVersion = nAgt.substring(verOffset + 8);
    }
    // In Firefox, the true version is after "Firefox" 
    else if ((verOffset = nAgt.indexOf("Firefox")) != -1) {
      this.browserName = "Firefox";
      this.fullVersion = nAgt.substring(verOffset + 8);
    }
    // In most other browsers, "name/version" is at the end of userAgent 
    else if ((nameOffset = nAgt.lastIndexOf(' ') + 1) <
      (verOffset = nAgt.lastIndexOf('/'))) {
      this.browserName = nAgt.substring(nameOffset, verOffset);
      this.fullVersion = nAgt.substring(verOffset + 1);
      if (this.browserName.toLowerCase() == this.browserName.toUpperCase()) {
        this.browserName = navigator.appName;
      }
    }
    // trim the fullVersion string at semicolon/space if present
    if ((ix = this.fullVersion.indexOf(";")) != -1)
      this.fullVersion = this.fullVersion.substring(0, ix);
    if ((ix = this.fullVersion.indexOf(" ")) != -1)
      this.fullVersion = this.fullVersion.substring(0, ix);

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
      this.osVersion = ('' + (/CPU.*OS ([0-9_]{1,5})|(CPU like).*AppleWebKit.*Mobile/i.exec(userAgent) || [0, ''])[1]).replace('undefined', '3_2').replace('_', '.').replace('_', '') || '0';
    } else if (/Windows/.test(userAgent)) {
      this.os = 'Windows';
      this.osVersion = windowsPlatformsVersionsCorrespondant[windowsPlatformsVersions.indexOf(userAgent.split(" ")[3])];
    } else if (/Android/.test(userAgent)) {
      this.os = 'Android';
    } else if (/Linux/.test(userAgent)) {
      this.os = 'Linux';
    }
  }

  public get nomOs(): string {
    return this.os;
  }

  public get versionOs(): string {
    return this.osVersion;
  }

  public get nomNavigateur(): string {
    return this.browserName;
  }

  public get versionCompleteNavigateur(): string {
    return this.fullVersion;
  }

  public get versionMajeurNavigateur(): number {
    return this.majorVersion;
  }

}