class Calculadora {
  constructor() {
    this.numero = "";
    this.operation = "";
    this.action = "";
    this.result = "";
    this.acumulado = "";
    this.acumuladoPantalla = document.querySelector(".acumulate");
    this.resultPantalla = document.querySelector(".result");
  }

  addDigit(valor) {
    this.numero += valor;
    this.result = this.numero;
    this.showResult();
  }

  showAcumulado() {
    this.acumuladoPantalla.textContent = this.acumulado;
  }

  showResult() {
    this.resultPantalla.textContent = this.result;
  }

  suma() {
    this.acumulado = +this.acumulado + +this.numero;
    this.numero = "";
    this.operation = "+";
  }

  resta() {
    this.acumulado = +this.acumulado - +this.numero;
    this.numero = "";
    this.operation = "-";
  }

  divide() {
    if (this.acumulado === "") this.acumulado = this.numero;
    else this.acumulado = +this.acumulado / +this.numero;
    this.numero = "";
    this.operation = "/";
  }

  multiplica() {
    if (this.acumulado === "") this.acumulado = this.numero;
    else this.acumulado = +this.acumulado * +this.numero;
    this.numero = "";
    this.operation = "*";
  }

  selectOperation(action) {
    // console.log(operation);
    this.action = action;
    switch (action) {
      case "+":
        this.suma();
        break;
      case "*":
        this.multiplica();
        break;
      case "-":
        this.resta();
        break;
      case "/":
        this.divide();
        break;
      case "CE":
        this.result = 0;
        this.numero = "";
        break;
      case "C":
        this.result = 0;
        this.acumulado = 0;
        this.numero = "";
        break;
      case "<=":
        console.log("delete");
        this.result = this.result.slice(0, -1);
        this.numero = this.result;
        break;
      case "±":
        this.result = +this.result * -1;
        this.numero = this.result;
        break;
      case ",":
        console.log("coma");
        break;
      case "=":
        console.log("=======", this.operation);
        switch (this.operation) {
          case "+":
            this.result = +this.acumulado + +this.result;
            this.acumulado = this.result;
            break;
          case "-":
            this.result = +this.acumulado - +this.result;
            this.acumulado = this.result;
            break;
          case "*":
            if (this.acumulado === "") this.acumulado = this.numero;
            else this.acumulado = +this.acumulado * +this.numero;
            this.result = +this.acumulado * +this.result;
            this.acumulado = this.result;
            break;
          case "/":
            if (this.acumulado === "") this.acumulado = this.numero;
            else this.acumulado = +this.acumulado / +this.numero;
            this.result = +this.acumulado / +this.result;
            this.acumulado = this.result;
            break;
        }
    }
    this.showAcumulado();
    this.showResult();
  }
}
