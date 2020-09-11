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
      data.course.name.includes(filterKey)
        || data.course.description.toLowerCase().includes(filterKey)
        || data.course.createddate.includes(filterKey)
        || data.teacher.fullname.toLowerCase().includes(filterKey)
    );
  }
}