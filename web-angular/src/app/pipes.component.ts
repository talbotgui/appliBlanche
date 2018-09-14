import { Pipe, PipeTransform } from '@angular/core';

/**
 * Composant permettant dans les vue de transformer un tableau en Map
 *  @see https://webcake.co/looping-over-maps-and-sets-in-angular-2s-ngfor/
 * */
@Pipe({ name: 'mapValues' })
export class MapValuesPipe implements PipeTransform {

  /**
   * Angular invokes the `transform` method with the value of a binding
   * as the first argument, and any parameters as the second argument in list form.
   * @param value 
   * @param args 
   */
  transform(value: any, args?: any[]): Array<{ key: string, val: string }> {
    const returnArray: any[] = [];

    if (value) {
      value.forEach((entryVal: any, entryKey: any) => {
        returnArray.push({
          key: entryKey,
          val: entryVal
        });
      });
    }

    return returnArray;
  }
}

/** Transformation d'une liste d'attributs en MAP */
@Pipe({ name: 'attributesToMap' })
export class AttributesToMapPipe implements PipeTransform {

  /**
   * Angular invokes the `transform` method with the value of a binding
   * as the first argument, and any parameters as the second argument in list form.
   * @param value 
   * @param args 
   */
  transform(value: any, args?: any[]): Array<{ key: string, val: string }> {
    const returnArray = [];

    for (const prop in value) {
      if (value.hasOwnProperty(prop)) {
        returnArray.push({ key: prop, val: value[prop] });
      }
    }
    return returnArray;
  }
}
