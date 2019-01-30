import { Injectable } from '@angular/core';
import * as FileSaver from 'file-saver';
import * as XLSX from 'xlsx';
import { HttpProxy } from './httpProxy.component';

const EXCEL_MIME_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
const EXCEL_EXTENSION = '.xlsx';

@Injectable()
export class ExportService {

  constructor(private http: HttpProxy) { }

  public exporterTableauEnExcel(json: any[], nomOnglet: string, nomFichierExcel: string): void {
    const excelBuffer: any = this.creerExcelDepuisTableau(json, nomOnglet);
    this.declencherTelechargement(excelBuffer, nomFichierExcel);
  }

  private creerExcelDepuisTableau(json: any[], nomOnglet: string): any {
    const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(json);
    const workbook: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, nomOnglet);
    return XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
  }

  private declencherTelechargement(buffer: any, nomFichier: string): void {
    const data: Blob = new Blob([buffer], { type: EXCEL_MIME_TYPE });
    const dateDuJour = this.http.formaterDate(new Date());
    FileSaver.saveAs(data, nomFichier + '_' + dateDuJour + EXCEL_EXTENSION);
  }
}
