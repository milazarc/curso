import React, { Component } from "react";
import { ErrorMessage, Esperando } from "../comunes";

export default class Muro extends Component {
  constructor(props) {
    super(props);
    this.state = {
      listado: null,
      loading: true,
      error: null,
      page: 1,
      size: 10,
    };
  }
  render() {
    const { loading, listado } = this.state;
    if (loading) return <Esperando />;

    return (
      <>
        {this.state.error && <ErrorMessage msg={this.state.error} />}
        <h1>Muro</h1>
        <div className="d-flex justify-content-center w-100">
          <button className="btn btn-dark" onClick={this.changePage.bind(this, -1)} disabled={this.state.page==1}>
            Previous Page
          </button>
          <button className="btn btn-dark" onClick={this.changePage.bind(this, 1)}>
            Next Page
          </button>
        </div>
        {/* {JSON.stringify(this.state.listado)} */}
        <div className="row">
          {listado &&
            listado.map((item, index) => {
              // console.log(item);
              return <Item key={index} {...item} />;
            })}
        </div>
        <div className="d-flex justify-content-center w-100">
        <button className="btn btn-dark" onClick={this.changePage.bind(this, -1)} disabled={this.state.page==1}>
            Previous Page
          </button>
          <button className="btn btn-dark" onClick={this.changePage.bind(this, 1)}>
            Next Page
          </button>
        </div>
      </>
    );
  }

  setError(msg) {
    this.setState({ error: msg });
  }

  changePage(number) {
    let aux = Math.max(1, this.state.page + number)
    this.setState({ page: aux });
    console.log(aux)
    this.load(aux)
  }

  load(page) {
    this.setState({ loading: true });
    fetch(
      `https://picsum.photos/v2/list?page=${page}&limit=${this.state.size}`
    )
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
    this.load(this.state.page);
    console.log("componentDidMount");
  }
}

class Item extends Component {
  constructor(props) {
    super(props);
    this.state = {
      mostrar: props.mostrar ?? false,
    };
  }

  toggleFoto() {
    this.setState({ mostrar: !this.state.mostrar });
  }

  render() {
    const mostrar = this.state.mostrar;
    const { author, download_url, id } = this.props;
    return (
      <>
        <div className="card" style={{ width: "18rem" }}>
          <div className="card-body">
            <h5 className="card-title">{author}</h5>
            {mostrar && (
              <img
                src={download_url}
                className="d-flex justify-content-center"
                style={{ width: "12rem", height: "8rem" }}
                alt="text"
              />
            )}
            {!mostrar && (
              <div
                className="fs-1 d-flex justify-content-center align-items-center bg-secondary text-white"
                style={{ width: "12rem", height: "8rem" }}
              >
                {id}
              </div>
            )}
            <br />
            <div
              className="btn btn-success"
              onClick={this.toggleFoto.bind(this)}
            >
              Show
            </div>
          </div>
        </div>
      </>
    );
  }
}
