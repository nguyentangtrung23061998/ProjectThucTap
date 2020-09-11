import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/share/services/http.service';

@Injectable()
export class QuizLectureService {

  constructor(private _http:HttpService) { }
  
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

  listQuestionByLectureId(id) {
    const path = "/questions/lectures/"+id;
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
