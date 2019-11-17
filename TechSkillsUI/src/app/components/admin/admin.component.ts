import { Component, OnInit } from '@angular/core';
import { PersonService } from '../../services/person.service';
@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  public persons;

   constructor(private personService: PersonService) { }

   ngOnInit() {
     this.getPersons();
   }

   getPersons() {
      this.personService.getPersons().subscribe(
        data => { this.persons = data},
        err => console.error(err),
        () => console.log('persons loaded')
      );
    }

}
