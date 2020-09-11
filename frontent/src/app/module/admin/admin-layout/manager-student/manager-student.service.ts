import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/share/services/http.service';
import { Student } from './manager-student.interface';
@Injectable()
export class ManagerStudentService {

    constructor(private _http: HttpService) { }

    getStudent(id){
        const path="/users/"+id;
        return this._http.get(path);
    }

    addStudent(student:Student){
        const path="/auth/signup";
        return this._http.create(path,student);
    }

    listStudent(){
        const path="/users/student";
        return this._http.get(path);
    }

    delete(id:any){
        const path="/users/"+id;
        return this._http.delete(path);
    }
    update(student:Student,id:any){
        const path ="/users/update_profile/"+id;
        return this._http.update(path,student);
    }
}
