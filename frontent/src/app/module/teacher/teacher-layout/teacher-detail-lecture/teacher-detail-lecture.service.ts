import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/share/services/http.service';

@Injectable()
export class TeacherDetailLectureService {

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
  getQuestionById(id){
    const path = "/questions/"+id;
    return this._http.get(path);
  }

  listLectureAll(){
    const path = "/lectures";
    return this._http.get(path);
  }

  listQuestionAll() {
    const path = "/questions";
    return this._http.get(path);
  }

  addQuestionByLectureId(question,idLecture){
    const path = "/questions/lectures/"+idLecture;
    return this._http.create(path,question);
  }

  updateQuestionById(question,idQuestion){
    const path = "/questions/"+idQuestion;
    return this._http.update(path,question);
  }

  deleteQuestion(idQuestion){
    const path="/questions/"+idQuestion;
    return this._http.delete(path);
  }
}
