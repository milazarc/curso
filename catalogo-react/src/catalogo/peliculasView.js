import React, { Component } from "react";
import {
    ValidationMessage,
    ErrorMessage,
    Esperando,
    PaginacionCmd as Paginacion,
} from "../biblioteca/comunes";
import { titleCase } from "../biblioteca/formateadores";

export default class FilmsView extends Component {
    constructor(props) {
        super(props);
        this.state = { elemento: props.elemento, msgErr: [], invalid: false, actores: [], categorias: [], idiomas: [] };
        this.urlActores = (process.env.REACT_APP_API_URL || "http://localhost:8010/") + "/api/actores/v1";
        this.urlCategorias = (process.env.REACT_APP_API_URL || "http://localhost:8010/") + "/api/categorias/v1";
        this.urlIdiomas = (process.env.REACT_APP_API_URL || "http://localhost:8010/") + "/api/idiomas/v1";
        this.urlPeliculas = (process.env.REACT_APP_API_URL || "http://localhost:8010/") + "/api/peliculas/v1";
        this.onCancel = () => {
            if (this.props.onCancel) this.props.onCancel();
        };
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
                        }
                        : (error) => this.setError(`${error.status}: ${error.error}`)
                );
            })
            .catch((error) => this.setError(error));
    }

    getListCategorias() {
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
                        }
                        : (error) => this.setError(`${error.status}: ${error.error}`)
                );
            })
            .catch((error) => this.setError(error));
    }

    getListIdiomas() {
        this.setState({ loading: true });
        fetch(`${this.urlIdiomas}`)
            .then((response) => {
                response.json().then(
                    response.ok
                        ? (data) => {
                            this.setState({
                                idiomas: data,
                                loading: false,
                            });
                        }
                        : (error) => this.setError(`${error.status}: ${error.error}`)
                );
            })
            .catch((error) => this.setError(error));
    }

    getElement() {
        let id = this.state.elemento.id;
        console.log("getElement",this.state.elemento)
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


        return (
            <>
                <form
                    ref={(tag) => {
                        this.form = tag;
                    }}
                >
                    <div className="form-group">
                        <label htmlFor="id"><b>Código</b></label>
                        <input
                            type="number"
                            className="form-control-plaintext"
                            value={this.state.elemento.id}
                            readOnly={true}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="nombre"><b>Titulo</b></label>
                        <input
                            type="text"
                            className="form-control-plaintext"
                            value={this.state.elemento.title}
                            readOnly={true}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="nombre"><b>Descripcion</b></label>
                        <input
                            type="text"
                            className="form-control-plaintext"
                            value={this.state.elemento.description}
                            readOnly={true}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="nombre"><b>Rating</b></label>
                            <input
                                type="text"
                                className="form-control-plaintext"
                                value={[{id: "G", nombre: "General audiences"},
                                {id: "PG", nombre: "Parental guidance suggested"},
                                {id: "PG-13", nombre: "Parents strongly cautioned"},
                                {id: "R", nombre: "Restricted"},
                                {id: "NC-17", nombre: "Adults only"}].find(element => element.id === this.state.elemento.rating)?.nombre}
                                readOnly={true}
                            />
                    </div>

                    <div className="form-group">
                        <label htmlFor="nombre"><b>Año emision</b></label>
                        <input
                            type="text"
                            className="form-control-plaintext"
                            value={this.state.elemento.releaseYear}
                            readOnly={true}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="nombre"><b>Duracion</b></label>
                        <input
                            type="text"
                            className="form-control-plaintext"
                            value={this.state.elemento.length}
                            readOnly={true}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="nombre"><b>Duracion Rental</b></label>
                        <input
                            type="text"
                            className="form-control-plaintext"
                            value={this.state.elemento.rentalDuration}
                            readOnly={true}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="nombre"><b>Precio Rental</b></label>
                        <input
                            type="text"
                            className="form-control-plaintext"
                            value={this.state.elemento.rentalRate}
                            readOnly={true}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="nombre"><b>Coste Remplazo</b></label>
                        <input
                            type="text"
                            className="form-control-plaintext"
                            value={this.state.elemento.replacementCost}
                            readOnly={true}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="nombre"><b>Idioma</b></label>
                        <th className="input-group">
                            <input
                                type="text"
                                className="form-control-plaintext"
                                value={titleCase(this.state.idiomas.find(element => element.id === this.state.elemento.languageId)?.nombre)}
                                readOnly={true}
                            />
                        </th>
                    </div>

                    <div className="form-group">
                        <label htmlFor="nombre"><b>Idioma Original</b></label>
                            <input
                                type="text"
                                className="form-control-plaintext"
                                value={titleCase(this.state.idiomas.find(element => element.id === this.state.elemento.languageVOId)?.nombre)}
                                readOnly={true}
                            />
                    </div>


                    <div className="form-group">
                        <div className="row">

                            {this.state.elemento.actors && this.state.actores &&
                                <div className="col-2 w-50">

                                    <table className="table table-hover table-striped">
                                        <thead className="table-info">
                                            <tr>
                                                <th>Actores</th>
                                            </tr>
                                        </thead>
                                        <tbody className="table-group-divider">
                                            {this.state.elemento.actors.map((item) => (
                                                <tr key={item.id}>
                                                    <td>{titleCase(this.state.actores.find(element => element.id === item).nombre + ' ' + this.state.actores.find(element => element.id === item).apellidos)}</td>
                                                </tr>
                                            ))}
                                        </tbody>
                                    </table>
                                </div>
                            }

                            {this.state.elemento.actors && this.state.actores &&
                                <div className="col-2 w-50">

                                    <table className="table table-hover table-striped">
                                        <thead className="table-info">
                                            <tr>
                                                <th>Categorias</th>
                                            </tr>
                                        </thead>
                                        <tbody className="table-group-divider">
                                            {this.state.elemento.categories.map((item) => (
                                                <tr key={item.id}>
                                                    <td>{this.state.categorias.find(element => element.id === item).nombre}</td>
                                                </tr>
                                            ))}
                                        </tbody>
                                    </table>
                                </div>
                            }
                        </div>
                    </div>


                    <div className="form-group">
                        <button
                            className="btn btn-primary"
                            type="button"
                            onClick={this.onCancel}
                        >
                            Volver
                        </button>
                    </div>
                </form>

            </>

        );
    }
}