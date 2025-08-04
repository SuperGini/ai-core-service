import {inject, Pipe, PipeTransform} from '@angular/core';
import {DomSanitizer} from '@angular/platform-browser';

@Pipe({
  name: 'newlineToBr'
})
export class NewlineToBrPipe implements PipeTransform {

    private sanitizer = inject(DomSanitizer);

  transform(value: string) {
      if (!value) {
          return '';
      }
      // Replace all newline characters with <br> tags
      const transformedValue = value.replace(/\n/g, '<br>');
      // Return the value as SafeHtml to prevent Angular from sanitizing the <br> tags
      return this.sanitizer.bypassSecurityTrustHtml(transformedValue);
  }

}
