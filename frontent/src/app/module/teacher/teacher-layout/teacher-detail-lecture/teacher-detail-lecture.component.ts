import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { api_url } from '../../../../config/api_url.config';
import { Observable } from 'rxjs';
import { ManagerLectureDetailService } from 'src/app/module/admin/admin-layout/manager-lecture-detail/manager-lecture-detail.service';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import { TeacherDetailLectureService } from './teacher-detail-lecture.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import * as $ from 'jquery';
@Component({
  selector: 'app-teacher-detail-lecture',
  templateUrl: './teacher-detail-lecture.component.html',
  styleUrls: ['./teacher-detail-lecture.component.scss'],
  providers: [TeacherDetailLectureService]
})
export class TeacherDetailLectureComponent implements OnInit {
  @ViewChild('video', { static: false })
  public video: ElementRef;

  fileToUpload: File = null;
  public urlUpload = api_url.upload;
  public lectureObj = {};
  private lectureId = 0;
  public arrQuestion: any[] = [];

  displayedColumns: string[] = ['question', 'answerA', 'answerB', 'answerC', 'answerD', 'correct', 'action'];
  dataSource: Observable<any[]> = new Observable();

  selectLecture: FormGroup;
  formQuestion: FormGroup;
  public arrayQuestion: any[] = [];
  public arrayLecture: any[] = [];
  private idLecture: number = 0;
  private idQuestion: number = 0;
  public _isAdd = true;
  public submitted = false;
  filter: string;

  public arrayCorrectAnswer: any[] = [
    { 'correctTitle': 'A' },
    { 'correctTitle': 'B' },
    { 'correctTitle': 'C' },
    { 'correctTitle': 'D' },
  ];

  constructor(private router: ActivatedRoute,
    private lectureDetailService: TeacherDetailLectureService,
    private formBuilder: FormBuilder) {
      console.log(this.lectureObj)
  }

  getLectureObj(id) {
   
    this.lectureDetailService.getLecture(id).subscribe(
      (kq: any) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.lectureObj = kq.data;
          this.lectureId = kq.data.id;
          this.getQuestionByLectureId(this.lectureId);
        }
      }
    )
  }

  // showModelAdd() {
  //   this._isAdd = true;
  //   // this.resetForm();
  // }

  resetForm() {
    this.formQuestion = this.formBuilder.group({
      question: [],
      answerfirst: [],
      answersecond: [],
      answerthird: [],
      answerfourth: [],
      correctanswer: [this.arrayCorrectAnswer[0].correctTitle],
      lecture: [this.lectureId]
    })
  }

  getAllQuestion() {
    this.lectureDetailService.listQuestionAll().subscribe(
      (res) => {
        console.log(res)
        if (res.status === ServerResponseCode.SUCCESS) {
          this.arrayQuestion = res.data;
        }
      }, (error: any) => {
        console.log(error)
      }
    )
  }

  get f(){
    return this.formQuestion.controls;
  }

  showModelAdd() {
    this._isAdd = true;
    this.formQuestion = this.formBuilder.group({
      question: [],
      answerfirst: [],
      answersecond: [],
      answerthird: [],
      answerfourth: [],
      correctanswer: [this.arrayCorrectAnswer[0].correctTitle],
      lecture: [this.lectureId]
    })
  }

  onSaveQuestion() {
    this.submitted = true;
    this.idLecture = this.formQuestion.value.lecture;
    if (this.formQuestion.value.question === null || this.formQuestion.value.question === '') {
      return;
    }
    if (this.formQuestion.value.answerfirst === null || this.formQuestion.value.answerfirst === '') {
      return;
    }
    if (this.formQuestion.value.answersecond === null || this.formQuestion.value.answersecond === '') {
      return;
    }
    if (this.formQuestion.value.answerthird === null || this.formQuestion.value.answerthird === '') {
      return;
    }
    if (this.formQuestion.value.answerfourth === null || this.formQuestion.value.answerfourth === '') {
      return;
    }
    if (this._isAdd) {
      this.lectureDetailService.addQuestionByLectureId(this.formQuestion.value, this.idLecture).subscribe(
        (kq) => {
          if (kq.status === ServerResponseCode.SUCCESS) {
            this.submitted = false;
            this.getQuestionByLectureId(this.lectureId)
            this.resetForm();
            $('#modalQuestion').trigger('click');
          }
        }, error => {
          console.log(error)
        }
      )
    } else {
      this.lectureDetailService.updateQuestionById(this.formQuestion.value, this.idQuestion).subscribe(
        (kq) => {
          this.submitted = false;
          this.getQuestionByLectureId(this.lectureId)
          this.resetForm();
          $('#modalQuestion').trigger('click');
        }
      )
    }
  }

  updateQuestion(id) {
    this._isAdd = false;
    this.lectureDetailService.getQuestionById(id).subscribe(
      (kq) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.formQuestion = this.formBuilder.group({
            question: [kq.data.question],
            answerfirst: [kq.data.answerfirst],
            answersecond: [kq.data.answersecond],
            answerthird: [kq.data.answerthird],
            answerfourth: [kq.data.answerfourth],
            correctanswer: [kq.data.correctanswer]
          })
        }
        this.idQuestion = kq.data.id;
      }, (error: any) => {
        console.log("error: ", error)
      }
    )
  }

  deleteQuestion(id) {
    this.lectureDetailService.deleteQuestion(id).subscribe(
      (kq) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.getQuestionByLectureId(this.lectureId);
        }
      }, (error) => {
        console.log("error: ", error)
      }
    )
  }

  getQuestionByLectureId(id) {
    this.lectureDetailService.listQuestionByLectureId(id).subscribe(
      (kq: any) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.arrQuestion = kq.data;
          this.dataSource = kq.data;
        }
      }, (error: any) => {
        console.log(error)
      }
    )
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

  uploadVideo() {
    if (this.fileToUpload !== null) {
      this.lectureDetailService.uploadFile(this.fileToUpload, this.lectureId).subscribe(
        (kq) => {
          const valueFile = <HTMLInputElement>document.getElementById('file2');
          valueFile.value = "";
          this.getLectureObj(this.lectureId);
          this.video.nativeElement.src =this.urlUpload + '/videos/'+ kq.data.video;
          this.video.nativeElement.load();
          this.video.nativeElement.play();
        }, error => {
          console.log(error)
        }
      )
    }
  }

  ngOnInit() {
    let id = parseInt(this.router.snapshot.paramMap.get('id'));
    this.lectureId = id;
    this.getLectureObj(id);
    this.selectLecture = this.formBuilder.group({
      lecture: []
    })
    this.formQuestion = this.formBuilder.group({
      question: [],
      answerfirst: [],
      answersecond: [],
      answerthird: [],
      answerfourth: [],
      correctanswer: [this.arrayCorrectAnswer[0].correctTitle],
      lecture: [id]
    })
  }

}
