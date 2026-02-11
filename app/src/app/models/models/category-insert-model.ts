export class CategoryInsertModel {
  id?: number;
  name: String;

  constructor(id: number | undefined, name: String) {
    ((this.id = id), (this.name = name));
  }
}
