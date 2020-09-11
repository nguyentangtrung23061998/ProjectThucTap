import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterCourse'
})
export class FilterCoursePipe implements PipeTransform {

  transform(items: any[], filter: string): any[] {
    if (items.length === 0 || filter === undefined) {
      return items;
    }
    const filterKey = filter.trim().toLowerCase();
    return items.filter(
      data =>
      data.course.name.toLowerCase().includes(filterKey)
        || data.course.description.toLowerCase().includes(filterKey)
        || data.course.createddate.includes(filterKey)
        || data.teacher.fullname.toLowerCase().includes(filterKey)
    );
  }
}

@Pipe({
  name: 'filterLecture'
})
export class FilterLecturePipe implements PipeTransform {

  transform(items: any[], filter: string): any[] {
    if (items.length === 0 || filter === undefined) {
      return items;
    }
    const filterKey = filter.trim().toLowerCase();
    return items.filter(
      data =>
      data.name.toLowerCase().includes(filterKey)
        || data.description.toLowerCase().includes(filterKey)
        || data.createddate.includes(filterKey)
    );
  }
}


@Pipe({
  name: 'filterQuestion'
})
export class FilterQuestionPipe implements PipeTransform {

  transform(items: any[], filter: string): any[] {
    if (items.length === 0 || filter === undefined) {
      return items;
    }
    const filterKey = filter.trim().toLowerCase();
    return items.filter(
      data =>
      data.question.toLowerCase().includes(filterKey)
        || data.answerfirst.toLowerCase().includes(filterKey)
        || data.answersecond.toLowerCase().includes(filterKey)
        || data.answerthird.toLowerCase().includes(filterKey)
        || data.answerfourth.toLowerCase().includes(filterKey)
    );
  }
}

@Pipe({
  name: 'filterUser'
})
export class FilterUserPipe implements PipeTransform {

  transform(items: any[], filter: string): any[] {
    if (items.length === 0 || filter === undefined) {
      return items;
    }
    const filterKey = filter.trim().toLowerCase();
    return items.filter(
      data =>
        data.username.toLowerCase().includes(filterKey)
        || data.email.toLowerCase().includes(filterKey)
        || data.fullname.toLowerCase().includes(filterKey)
        || data.createddate.includes(filterKey)
    );
  }
}
