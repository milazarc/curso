import React, { Component } from "react";
import { ErrorMessage, Esperando } from "../../comunes";

export default class LanguageScreen extends Component {
  constructor(props) {
    super(props);
    this.state = {
      listado: null,
      loading: true,
      error: null,
    };
  }

  setError(msg) {
    this.setState({ error: msg });
  }
  load(num) {
    this.setState({ loading: true });
    fetch("http://localhost:8010/api/idiomas/v1")
      .then((resp) => {
        if (resp.ok) {
          resp.json().then((data) => {
            this.setState({ listado: data });
          });
        } else {
          this.setError(resp.status);
        }
      })
      .catch((err) => this.setError(JSON.stringify(err)))
      .finally(() => this.setState({ loading: false }));
  }
  componentDidMount() {
    this.load(1);
    console.log("componentDidMount");
  }

  render() {
    return (
      <>
        {this.state.error && <ErrorMessage msg={this.state.error} />}
        <h1>Idiomas</h1>
        {this.state.listado &&
          this.state.listado.map((item, index) => {
            return <Line {...item} key={item.id} />;
          })}
        <div></div>
      </>
    );
  }
}
class Line extends Component {
  ç;

  constructor(props) {
    super(props);
    this.state = {
      id: props.id,
      nombre: props.nombre ?? "Ninguno",
    };
  }

  render() {
    const { id, nombre } = this.state;
    return (
      <>
        <div className="row">
          <div className="border col">{id}</div>
          <div className="border col">{nombre}</div>
          <button className="btn col btn-success">Modify</button>
          <button className="btn col btn-danger">X</button>
        </div>
      </>
    );
  }
}
