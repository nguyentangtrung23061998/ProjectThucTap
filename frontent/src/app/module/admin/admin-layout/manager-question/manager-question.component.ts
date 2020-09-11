import { Component, OnInit } from '@angular/core';
import { ManagerQuestionService } from './manager-question.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import * as $ from 'jquery';
@Component({
  selector: 'app-manager-question',
  templateUrl: './manager-question.component.html',
  styleUrls: ['./manager-question.component.scss'],
  providers: [ManagerQuestionService]
})
export class ManagerQuestionComponent implements OnInit {

  selectLecture: FormGroup;
  formQuestion: FormGroup;
  public arrayQuestion: any[] = [];
  public arrayLecture: any[] = [];
  private idLecture: number = 0;
  private idQuestion: number = 0;
  public _isAdd = true;
  public submitted = false;
  filter:string;

  public arrayCorrectAnswer: any[] = [
    { 'correctTitle': 'A' },
    { 'correctTitle': 'B' },
    { 'correctTitle': 'C' },
    { 'correctTitle': 'D' },
  ];

  public config = {
    itemsPerPage: 5,
    currentPage: 1,
    totalItems: this.arrayQuestion.length
  }

  onPageChange(event) {
    this.config.currentPage = event;
  }

  constructor(private questionService: ManagerQuestionService,
    private formBuilder: FormBuilder) { }

  getAllLecture() {
    this.questionService.listLectureAll().subscribe(
      (res) => {
        console.log(res)
        if (res.status == ServerResponseCode.SUCCESS) {
          this.arrayLecture = res.data;
          this.idLecture = this.arrayLecture[0].id;
          this.selectLecture.patchValue({
            lecture: this.arrayLecture[0].id
          })
          this.formQuestion.patchValue({
            idCourse: this.arrayLecture[0].id
          })
        }
      }
    )
  }

  getAllQuestion() {
    this.questionService.listQuestionAll().subscribe(
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

  getQuestionByLectureId(id) {
    this.questionService.listQuestionByLectureId(id).subscribe(
      (kq: any) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.arrayQuestion = kq.data;
        }
      }, (error: any) => {
        console.log(error)
      }
    )
  }

  onChangeLecture() {
    console.log(this.selectLecture.value.lecture)
    this.getQuestionByLectureId(this.selectLecture.value.lecture);
  }

  resetForm() {
    this.formQuestion = this.formBuilder.group({
      question: [],
      answerfirst: [],
      answersecond: [],
      answerthird: [],
      answerfourth: [],
      correctanswer: [this.arrayCorrectAnswer[0].correctTitle],
      lecture: [this.arrayLecture[0].id]
    })
  }

  get f() {
    return this.formQuestion.controls;
  }

  showModelAdd() {
    this._isAdd = true;
    this.resetForm();
  }

  onSaveQuestion() {
    console.log(this._isAdd)
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
      this.questionService.addQuestionByLectureId(this.formQuestion.value, this.idLecture).subscribe(
        (kq) => {
          console.log(kq)
          if (kq.status === ServerResponseCode.SUCCESS) {
            this.submitted = false;
            this.getAllQuestion();
            this.resetForm();
            $('#modalQuestion').trigger('click');
          }
        }, error => {
          console.log(error)
        }
      )
    } else {
      this.questionService.updateQuestionById(this.formQuestion.value, this.idQuestion).subscribe(
        (kq) => {
          this.submitted = false;
          this.getAllQuestion();
          this.resetForm();
          $('#modalQuestion').trigger('click');
        }
      )
    }
  }

  updateQuestion(id) {
    this._isAdd = false;
    this.questionService.getQuestionById(id).subscribe(
      (kq) => {
        console.log(kq)
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

  deleteQuestion(id){
    this.questionService.deleteQuestion(id).subscribe(
      (kq) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.getAllQuestion();
        }
      }, (error) => {
        console.log("error: ", error)
      }
    )
  }

  ngOnInit() {
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
      lecture: []
    })
    this.getAllLecture();
    this.getAllQuestion();
  }

}
