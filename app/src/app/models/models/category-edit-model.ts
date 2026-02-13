export class CategoryEditModel {
  id: number;
  name: String;

  constructor(id: number, name: String) {
    ((this.id = id), (this.name = name));
  }
}
