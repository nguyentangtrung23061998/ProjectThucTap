import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { ManagerLectureDetailService } from './manager-lecture-detail.service';
import { api_url } from '../../../../config/api_url.config';
import { ActivatedRoute } from '@angular/router';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import { Observable } from 'rxjs';
import { FormGroup, FormBuilder } from '@angular/forms';
@Component({
  selector: 'app-manager-lecture-detail',
  templateUrl: './manager-lecture-detail.component.html',
  styleUrls: ['./manager-lecture-detail.component.scss'],
  providers: [ManagerLectureDetailService]
})
export class ManagerLectureDetailComponent implements OnInit {
  @ViewChild('video', { static: false })
  public video: ElementRef;

  fileToUpload: File = null;
  public urlUpload = api_url.upload;
  public lectureObj = {};
  private lectureId = 0;
  selectLecture: FormGroup;
  formQuestion: FormGroup;
  public arrayLecture: any[] = [];
  public arrayQuestion: any[] = [];
  public _isAdd = true;
  
  displayedColumns: string[] = ['question', 'answerA','answerB', 'answerC','answerD','correct'];
  dataSource: Observable<any[]> = new Observable();

  public arrayCorrectAnswer: any[] = [
    { 'correctTitle': 'A' },
    { 'correctTitle': 'B' },
    { 'correctTitle': 'C' },
    { 'correctTitle': 'D' },
  ];

  constructor(private lectureDetailService: ManagerLectureDetailService,
    private router: ActivatedRoute,
    private formBuilder : FormBuilder) { }

  getLectureObj() {
    let id = parseInt(this.router.snapshot.paramMap.get('id'));
    this.lectureDetailService.getLecture(id).subscribe(
      (kq: any) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.lectureObj = kq.data;
          this.lectureId = kq.data.id;
          console.log(this.lectureObj)
        }
      }
    )
  }

  getQuestionByLectureId(id) {
    this.lectureDetailService.listQuestionByLectureId(id).subscribe(
      (kq: any) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.arrayQuestion = kq.data;
          this.dataSource=  kq.data;
        }
      }, (error: any) => {
        console.log(error)
      }
    )
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
    console.log(this.fileToUpload)
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

  showModelAdd() {
    this._isAdd = true;
    this.resetForm();
  }

  uploadVideo() {
    if (this.fileToUpload !== null) {
      this.lectureDetailService.uploadFile(this.fileToUpload, this.lectureId).subscribe(
        (kq) => {
          const valueFile = <HTMLInputElement>document.getElementById('file');
          valueFile.value = "";
          this.getLectureObj();
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
    this.getLectureObj();
  }

}
