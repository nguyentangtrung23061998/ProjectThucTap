import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/share/services/http.service';

@Injectable()
export class ManagerLectureDetailService {

  constructor(private _http: HttpService) { }

  getLecture(id) {
    const path = "/lectures/" + id;
    return this._http.get(path);
  }

  update(lecture: any, id: any) {
    const path = "/lectures/" + id;
    return this._http.update(path, lecture);
  }
  uploadFile(formData,idLecture){
    const path = "/lectures/upload/" + idLecture;
    return this._http.postFile(formData,path);
  }
  listQuestionByLectureId(id) {
    const path = "/questions/lectures/"+id;
    return this._http.get(path);
  }
}
