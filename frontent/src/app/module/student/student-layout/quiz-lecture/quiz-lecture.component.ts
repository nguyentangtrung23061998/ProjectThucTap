import { Component, OnInit, HostListener } from '@angular/core';
import { Quiz } from 'src/app/share/model/quiz.module';
import { QuizConfig } from 'src/app/share/model/quiz_config.module';
import { QuizLectureService } from './quiz-lecture.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Question } from 'src/app/share/model/question.module';
import { Option } from 'src/app/share/model/option.module';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';

@Component({
  selector: 'app-quiz-lecture',
  templateUrl: './quiz-lecture.component.html',
  styleUrls: ['./quiz-lecture.component.scss'],
  providers: [QuizLectureService]
})
export class QuizLectureComponent implements OnInit {
  quizes: any[];
  quiz: Quiz = new Quiz(null);
  mode = "quiz";
  quizId = 0;
  public idLecture = 0;

  config: QuizConfig = {
    allowBack: true,
    allowReview: false,
    autoMove: true, // if true, it will move to next question automatically when answered.
    duration: 300, // indicates the time (in secs) in which quiz needs to be completed. 0 means unlimited.
    pageSize: 1,
    requiredAll: false, // indicates if you must answer all the questions before submitting.
    richText: false,
    shuffleQuestions: false,
    shuffleOptions: false,
    showClock: false,
    showPager: true,
    theme: "none"
  };

  pager = {
    index: 0,
    size: 1,
    count: 1
  };

  timer: any = null;
  startTime: Date;
  endTime: Date;
  ellapsedTime = "00:00";
  duration = "";

  constructor(private quizService: QuizLectureService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.idLecture = parseInt(this.route.snapshot.paramMap.get('id'));
    console.log(this.idLecture)
    this.getAllQuestionByLectureId(this.idLecture);
  }

  getAllQuestionByLectureId(id) {
    this.quizService.listQuestionByLectureId(id).subscribe(
      (kq: any) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.quizes = kq.data;
          this.quizId = this.quizes[0].id;
          this.loadQuiz(this.quizId);
          console.log(this.quizes)
        }
      }, (error) => {
        console.log(error)
      }
    )
  }

  loadQuiz(quizId) {
   this.quizService.getQuestionById(quizId).subscribe(
    (kq: any)=>{
      if(kq.status === ServerResponseCode.SUCCESS){
        this.quiz =new Quiz(kq.data);
        console.log(this.quiz)
        this.pager.count = this.quizes.length;
        this.startTime = new Date();
        this.ellapsedTime = "00:00";
        this.timer = setInterval(() => {
          this.tick();
        }, 1000);
        this.duration = this.parseTime(this.config.duration);
        this.mode = "quiz";
      }
    },(error:any)=>{
      console.log(error)
    }
   )
    // console.log(get)
    // this.quiz = new Quiz(getQuestion);
    console.log(this.quiz)
   
  }

  tick() {
    const now = new Date();
    const diff = (now.getTime() - this.startTime.getTime()) / 1000;
    if (diff >= this.config.duration) {
      this.onSubmit();
    }
    this.ellapsedTime = this.parseTime(diff);
  }

  parseTime(totalSeconds: number) {
    let mins: string | number = Math.floor(totalSeconds / 60);
    let secs: string | number = Math.round(totalSeconds % 60);
    mins = (mins < 10 ? "0" : "") + mins;
    secs = (secs < 10 ? "0" : "") + secs;
    return `${mins}:${secs}`;
  }

  get filteredQuestions() {
    return this.quizes
      ? this.quizes.slice(
        this.pager.index,
        this.pager.index + this.pager.size
      )
      : [];
  }

  onSelect(quiz: Quiz, answer) {
    // if (quiz.id === 1) {
    //   question.options.forEach(x => {
    //     if (x.id !== option.id) x.selected = false;
    //   });
    // }
    quiz.optionValue=answer;
    if(quiz.correctanswer === answer){
      quiz.selected =true;
    }else{
      quiz.selected =false;
    }

    if (this.config.autoMove) {
      this.goTo(this.pager.index + 1);
    }
  }

  goTo(index: number) {
    if (index >= 0 && index < this.pager.count) {
      this.pager.index = index;
      this.mode = "quiz";
    }
  }

  isAnswered(question: Quiz) {
    return question.selected ? "Answered" : "Not Answered";
  }

  isCorrect(question: Quiz) {
    return question.selected
      ? "correct"
      : "wrong";
  }

  onSubmit() {
    let answers = [];
    this.quizes.forEach(x=>{
      answers.push({
        questionId: x.id,
        answered: x.correctanswer
      })
    })
    console.log(this.quizes)
    // this.quiz.questions.forEach(x =>
    //   answers.push({
    //     quizId: this.quiz.id,
    //     questionId: x.id,
    //     answered: x.answered
    //   })
    // );

    // Post your data to the server here. answers contains the questionId and the users' answer.
    this.mode = "result";
  }

  @HostListener("window:focus", ["$event"])
  onFocus(event: any): void {
    //console.log("On Focus");
  }

  @HostListener("window:blur", ["$event"])
  onBlur(event: any): void {
    console.log("On Blur");
  }
  @HostListener("window:beforeunload", ["$event"])
  unloadNotification($event: any) { }
}
