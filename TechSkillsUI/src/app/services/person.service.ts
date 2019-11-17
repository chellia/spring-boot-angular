import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable} from 'rxjs';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  constructor(private http:HttpClient) { }

  getPersons() {
      return this.http.get('/server/api/v1/persons',
        {headers: new HttpHeaders().set('Authorization', 'Basic ' + 'dXNlcjpwYXNzd29yZA==')}
      );
    }
}
