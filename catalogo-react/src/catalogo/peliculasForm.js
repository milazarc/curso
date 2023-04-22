import React, { Component } from "react";
import {
    ValidationMessage,
    ErrorMessage,
    Esperando,
    PaginacionCmd as Paginacion,
} from "../biblioteca/comunes";
import { titleCase } from "../biblioteca/formateadores";

export default class FilmsForm extends Component {
    constructor(props) {
        super(props);
        this.state = { elemento: props.elemento, msgErr: [], invalid: false, actores: [], categorias: [], idiomas: [] };
        this.handleChange = this.handleChange.bind(this);
        this.urlActores = (process.env.REACT_APP_API_URL || "http://localhost:8010/") + "/api/actores/v1";
        this.urlCategorias = (process.env.REACT_APP_API_URL || "http://localhost:8010/") + "/api/categorias/v1";
        this.urlIdiomas = (process.env.REACT_APP_API_URL || "http://localhost:8010/") + "/api/idiomas/v1";
        this.urlPeliculas = (process.env.REACT_APP_API_URL || "http://localhost:8010/") + "/api/peliculas/v1";
        this.onSend = () => {
            if (this.props.onSend) this.props.onSend(this.state.elemento);
        };
        this.onCancel = () => {
            if (this.props.onCancel) this.props.onCancel();
        };
    }
    handleChange(event) {
        const cmp = event.target.name;
        const valor = event.target.value;
        this.setState((prev) => {
            prev.elemento[cmp] = valor;
            return { elemento: prev.elemento };
        });
        this.validar();
    }

    validar() {
        if (this.form) {
            const errors = {};
            let invalid = false;
            for (var cntr of this.form.elements) {
                if (cntr.name) {

                    errors[cntr.name] = cntr.validationMessage;
                    invalid = invalid || !cntr.validity.valid;
                }
            }
            this.setState({ msgErr: errors, invalid: invalid });
        }
    }

    getListActores() {
        let id = this.state.elemento.id;
        console.log("llamandoActores", id)
        this.setState({ loading: true });
        fetch(`${this.urlActores}`)
            .then((response) => {
                response.json().then(
                    response.ok
                        ? (data) => {
                            this.setState({
                                actores: data,
                                loading: false,
                            });
                            // console.log(data)
                        }
                        : (error) => this.setError(`${error.status}: ${error.error}`)
                );
            })
            .catch((error) => this.setError(error));
    }

    getListCategorias() {
        let id = this.state.elemento.id;
        console.log("llamandoCategorias", id)
        this.setState({ loading: true });
        fetch(`${this.urlCategorias}`)
            .then((response) => {
                response.json().then(
                    response.ok
                        ? (data) => {
                            this.setState({
                                categorias: data,
                                loading: false,
                            });
                            // console.log(data)
                        }
                        : (error) => this.setError(`${error.status}: ${error.error}`)
                );
            })
            .catch((error) => this.setError(error));
    }

    getListIdiomas() {
        let id = this.state.elemento.id;
        console.log("llamandoIdiomas", id)
        this.setState({ loading: true });
        fetch(`${this.urlCategorias}`)
            .then((response) => {
                response.json().then(
                    response.ok
                        ? (data) => {
                            this.setState({
                                idiomas: data,
                                loading: false,
                            });
                            // console.log(data)
                        }
                        : (error) => this.setError(`${error.status}: ${error.error}`)
                );
            })
            .catch((error) => this.setError(error));
    }

    getElement() {
        console.log("llamandoElement")
        let id = this.state.elemento.id;
        console.log( id, this.state.elemento)
        this.setState({ loading: true });
        fetch(`${this.urlPeliculas}/${id}/complete`)
            .then((response) => {
                response.json().then(
                    response.ok
                        ? (data) => {
                            this.setState({
                                elemento: data,
                                loading: false,
                            });
                        }
                        : (error) => this.setError(`${error.status}: ${error.error}`)
                );
            })
            .catch((error) => this.setError(error));
    }

    componentDidMount() {

        console.log("componentDidMount", this.state.elemento)
        this.getElement();
        this.getListActores();
        this.getListCategorias();
        this.getListIdiomas();
        this.validar();
    }
    render() {
        console.log(this.state.actores)
        return (
            <>
                <form
                    ref={(tag) => {
                        this.form = tag;
                    }}
                >
                    <div className="form-group">
                        <label htmlFor="id">Código</label>
                        <input
                            type="number"
                            className={"form-control" + (this.props.isAdd ? "" : "-plaintext")}
                            id="id"
                            name="id"
                            value={this.state.elemento.id}
                            onChange={this.handleChange}
                            required
                            readOnly={!this.props.isAdd}
                        />
                        <ValidationMessage msg={this.state.msgErr.id} />
                    </div>
                    <div className="form-group">
                        <label htmlFor="nombre">Titulo</label>
                        <input
                            type="text"
                            className="form-control"
                            id="nombre"
                            name="nombre"
                            value={this.state.elemento.nombre}
                            onChange={this.handleChange}
                            required
                            minLength="2"
                            maxLength="45"
                        />
                        <ValidationMessage msg={this.state.msgErr.nombre} />
                    </div>



                    <div className="form-group">
                        <button
                            className="btn btn-primary"
                            type="button"
                            disabled={this.state.invalid}
                            onClick={this.onSend}
                        >
                            Enviar
                        </button>
                        <button
                            className="btn btn-primary"
                            type="button"
                            onClick={this.onCancel}
                        >
                            Volver
                        </button>
                    </div>
                </form>
                {this.state.elemento.actors && this.state.actores &&
                    <ElementList name="Actores" listado={this.state.elemento.actors} base={this.state.actores}/>}
            </>

        );
    }
}

function ElementList(props) {
    console.log("renderizando", props.listado, "listdo", props.base, "bases")
    return (
        <>
            <table className="table table-hover table-striped">
                <thead className="table-info">
                    <tr>
                        <th>{props.name}</th>
                        <th className="text-end">
                            <input
                                type="button"
                                className="btn btn-primary"
                                value="Añadir"
                                onClick={(e) => props.onAdd()}
                            />
                        </th>
                    </tr>
                </thead>
                <tbody className="table-group-divider">
                    {props.listado.map((item) => (
                        <tr key={item.id}>
                            <td>{titleCase(props.base.find(element => element.id === item.id).nombre + ' ' + props.base.find(element => element.id).apellidos)}</td>
                            <td className="text-end">
                                <div className="btn-group text-end" role="group">
                                    <input
                                        type="button"
                                        className="btn btn-danger"
                                        value="Borrar"
                                        onClick={(e) => props.onDelete(item.id)}
                                    />
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <Paginacion
                actual={props.pagina}
                total={props.paginas}
                onChange={(num) => props.onChangePage(num)}
            />
        </>
    );
}
