import React, { Component } from "react";
import { ErrorMessage, Esperando } from "../comunes";

export default class Muro extends Component {
  constructor(props) {
    super(props);
    this.state = {
      listado: null,
      loading: true,
      error: null,
    };
  }
  render() {
    const { loading, listado } = this.state;
    if (loading) return <Esperando />;

    return (
      <>
        {this.state.error && <ErrorMessage msg={this.state.error} />}
        <h1>Muro</h1>
        {/* {JSON.stringify(this.state.listado)} */}
        <div className="row">
          {listado &&
            listado.map((item, index) => {
              // console.log(item);
              return <Item />;
            })}
        </div>
      </>
    );
  }

  setError(msg) {
    this.setState({ error: msg });
  }
  load(num) {
    this.setState({ loading: true });
    fetch("https://picsum.photos/v2/list")
      .then((resp) => {
        if (resp.ok) {
          resp.json().then((data) => this.setState({ listado: data }));
        } else {
          this.setError(resp.status);
        }
      })
      .catch((err) => this.setError(JSON.stringify(err)))
      .finally(() => this.setState({ loading: false }));
  }
  componentDidMount() {
    this.load(1);
  }
}

class Item extends Component {
  constructor(props) {
    super(props);
    this.state = {
      mostrar: false,
      nombre: props.nombre ?? "Missing",
    };
  }

  toggleFoto() {
    this.setState({ mostrar: !this.state.mostrar });
    console.log(this.state.mostrar, 1);
  }

  render() {
    return (
      <>
        <div className="card" style={{ width: "18rem" }}>
          {/* <img src="..." class="card-img-top" alt="..."> */}
          <div className="card-body">
            <h5 className="card-title">Card title</h5>
            <p className="card-text">
              Some quick example text to build on the card title and make up the
              bulk of the card's content.
            </p>
            <a className="btn btn-success" onClick={this.toggleFoto.bind(this)}>
              Show
            </a>
          </div>
        </div>
      </>
    );
  }
}
