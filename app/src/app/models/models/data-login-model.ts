export class DataLoginModel {
  name: String;
  token: String;

  constructor(name: String, token: String) {
    ((this.name = name), (this.token = token));
  }
}
