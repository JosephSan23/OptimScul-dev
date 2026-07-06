import { Directive, TemplateRef } from '@angular/core';

@Directive({ selector: '[filaTabla]' })
export class FilaTablaDirective {
  constructor(public templateRef: TemplateRef<any>) {}
}
