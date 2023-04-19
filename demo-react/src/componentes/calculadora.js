import React, { Component } from "react";

export default class Calculadora extends Component {
  constructor(props) {
    super(props);
    this.state = {
      acumulate: "0",
      result: "0",
      operand: "",
      number: '0',
      clear: true
    };
  }

  addDigit(digit) {
    const { clear, number } = this.state;
    if(clear) this.setState({ result: digit, number: digit, clear: false});
    else{

        if(number === '0') this.setState({number: digit, result: digit})
        else{
            let aux = number + digit
            this.setState({ result: aux, number: aux });
        }
    } 
  }

  doOperation(value) {
    console.log("clicado", value)
    const { result, number, acumulate, clear } = this.state;
    let aux = 0;
      switch (value) {
        case "+":
          aux =  +acumulate + +number;
          this.setState({ result: number, acumulate: aux });
          break;
        case "-":
            aux =  +acumulate - +number;
          this.setState({ result: number, acumulate: aux });
          break;
        case "*":
            aux =  +acumulate * +number;
          this.setState({ result: number, acumulate: aux });
          break;
        case "/":
            
            aux =  +acumulate / +number;
            console.log(aux)
          this.setState({ result: number, acumulate: aux });
          break;

        default:
          console.log("invalid");
          break;
      }
      this.setState({number : '0', operand : value})
    
  }

  calculate() {
    this.doOperation(this.state.operand);
  }

  other(action) {
    switch (action) {
      case "CE":
        this.setState({ result: "0", number : '0', clear: true});
        break;
      case "C":
        this.setState({ result: "0", acumulate: "0", number : '0', clear: true});
        break;
      case "<=":
        let aux = this.state.result.slice(0, -1);
        if (aux === "")aux = "0";
      
        this.setState({ result: aux, number : aux });
        break;
      default:
        console.log("invalid");
        break;
    }
  }

  render() {
    const {acumulate, result, operand} = this.state
    return (
      <main className="container-fluid w-25">
        <h1 className="text-center">
          <b>Calculadora</b>
        </h1>
        <div className="row">
          <div className="col btn btn-outline-dark disabled text-end bg-dark text-white border">
            {acumulate}{operand}
          </div>
        </div>
        <div className="row">
          <div className="col btn btn-outline-dark disabled text-end bg-dark text-white border">
            {this.state.result}
          </div>
        </div>
        <div className="row">
          <input
            type="button"
            defaultValue="CE"
            className="col btn btn-outline-dark"
            onClick={() => this.other("CE")}
          />
          <input
            type="button"
            defaultValue="C"
            className="col btn btn-outline-dark"
            onClick={() => this.other("C")}
          />
          <input
            type="button"
            defaultValue="<="
            className="col btn btn-outline-dark"
            onClick={() => this.other("<=")}
          />
          <input
            type="button"
            defaultValue="/"
            className="col btn btn-outline-dark"
            onClick={() => this.doOperation("/")}
          />
        </div>
        <div className="row">
          <input
            type="button"
            defaultValue={7}
            className="col btn btn-outline-primary border-dark"
            onClick={() => this.addDigit("7")}
          />
          <input
            type="button"
            defaultValue={8}
            className="col btn btn-outline-primary border-dark"
            onClick={() => this.addDigit("8")}
          />
          <input
            type="button"
            defaultValue={9}
            className="col btn btn-outline-primary border-dark"
            onClick={() => this.addDigit("9")}
          />
          <input
            type="button"
            defaultValue="*"
            className="col btn btn-outline-dark "
            onClick={() => this.doOperation("*")}
          />
        </div>
        <div className="row">
          <input
            type="button"
            defaultValue={4}
            className="col btn btn-outline-primary border-dark"
            onClick={() => this.addDigit("4")}
          />
          <input
            type="button"
            defaultValue={5}
            className="col btn btn-outline-primary border-dark"
            onClick={() => this.addDigit("5")}
          />
          <input
            type="button"
            defaultValue={6}
            className="col btn btn-outline-primary border-dark"
            onClick={() => this.addDigit("6")}
          />
          <input
            type="button"
            defaultValue="-"
            className="col btn btn-outline-dark "
            onClick={() => this.doOperation("-")}
          />
        </div>
        <div className="row">
          <input
            type="button"
            defaultValue={1}
            className="col btn btn-outline-primary border-dark"
            onClick={() => this.addDigit("1")}
          />
          <input
            type="button"
            defaultValue={2}
            className="col btn btn-outline-primary border-dark"
            onClick={() => this.addDigit("2")}
          />
          <input
            type="button"
            defaultValue={3}
            className="col btn btn-outline-primary border-dark"
            onClick={() => this.addDigit("3")}
          />
          <input
            type="button"
            defaultValue="+"
            className="col btn btn-outline-dark "
            onClick={() => this.doOperation("+")}
          />
        </div>
        <div className="row">
          <input
            type="button"
            defaultValue="±"
            className="col btn btn-outline-dark"
          />
          <input
            type="button"
            defaultValue={0}
            className="col btn btn-outline-primary border-dark"
            onClick={() => this.addDigit("0")}
          />
          <input
            type="button"
            defaultValue=","
            className="col btn btn-outline-dark"
          />
          <input
            type="button"
            defaultValue="="
            className="col btn btn-outline-success"
          />
        </div>
      </main>
    );
  }
}
