import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';

import * as model from '../model/model';

@Injectable()
export class UtilisateurService {

  constructor(private http: HttpClient) { }

  listerUtilisateur(): Observable<{ utilisateur: model.Utilisateur[] }> {
    return this.http.get<{ utilisateur: model.Utilisateur[] }>('http://localhost:9090/applicationBlanche/v1/utilisateurs');
  }

}
