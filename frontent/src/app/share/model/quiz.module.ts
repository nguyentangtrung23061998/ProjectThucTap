import { QuizConfig } from './quiz_config.module';
import { Question } from './question.module';

export class Quiz {
    id: number;
    question: string;
    answerfirst: string;
    answersecond: string;
    answerthird:string;
    answerfourth:string;
    correctanswer:string;
    optionValue:string;
    config: QuizConfig;
    selected:boolean;

    constructor(data: any) {
        if (data) {
            this.id = data.id;
            this.question=data.question;
            this.answerfirst = data.answerfirst;
            this.answersecond = data.answersecond;
            this.answerthird=data.answerthird;
            this.answerfourth = data.answerfourth;
            this.config = new QuizConfig(data.config);
        }
    }
}
