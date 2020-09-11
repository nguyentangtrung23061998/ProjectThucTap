import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/share/services/http.service';

@Injectable()
export class VideoCourseService {

  constructor(private _http: HttpService) { }
  
  getCourse(id) {
    const path = "/courses/" + id;
    return this._http.get(path);
  }

  getLecture(id) {
    const path = "/lectures/" + id;
    return this._http.get(path);
  }
}
