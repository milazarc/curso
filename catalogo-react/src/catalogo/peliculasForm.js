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
                            console.log("categorie", data)
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
        console.log(id, this.state.elemento)
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
        console.log(this.state.categorias)
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
                            id="title"
                            name="title"
                            value={this.state.elemento.title}
                            onChange={this.handleChange}
                            required
                            minLength="2"
                            maxLength="45"
                        />
                        <ValidationMessage msg={this.state.msgErr.nombre} />
                    </div>

                    <div className="form-group">
                        <label htmlFor="nombre">Descripcion</label>
                        <input
                            type="text"
                            className="form-control"
                            id="descripcion"
                            name="descripcion"
                            value={this.state.elemento.description}
                            onChange={this.handleChange}
                        />
                        <ValidationMessage msg={this.state.msgErr.descripcion} />
                    </div>

                    <div className="form-group">
                        <label htmlFor="nombre">Rating</label>
                        <input
                            type="text"
                            className="form-control"
                            id="rating"
                            name="rating"
                            value={this.state.elemento.rating}
                            onChange={this.handleChange}

                        />
                        <ValidationMessage msg={this.state.msgErr.rating} />
                    </div>

                    <div className="form-group">
                        <label htmlFor="nombre">Año emision</label>
                        <input
                            type="text"
                            className="form-control"
                            id="releaseYear"
                            name="releaseYear"
                            value={this.state.elemento.releaseYear}
                            onChange={this.handleChange}
                        />
                        <ValidationMessage msg={this.state.msgErr.releaseYear} />
                    </div>

                    <div className="form-group">
                        <label htmlFor="nombre">Duracion Rental</label>
                        <input
                            type="text"
                            className="form-control"
                            id="rentalDuration"
                            name="rentalDuration"
                            value={this.state.elemento.rentalDuration}
                            onChange={this.handleChange}
                        />
                        <ValidationMessage msg={this.state.msgErr.rentalDuration} />
                    </div>

                    <div className="form-group">
                        <label htmlFor="nombre">Precio Rental</label>
                        <input
                            type="text"
                            className="form-control"
                            id="rentalRate"
                            name="rentalRate"
                            value={this.state.elemento.rentalRate}
                            onChange={this.handleChange}
                        />
                        <ValidationMessage msg={this.state.msgErr.rentalRate} />
                    </div>

                    <div className="form-group">
                        <label htmlFor="nombre">Coste Remplazo</label>
                        <input
                            type="text"
                            className="form-control"
                            id="replacementCost"
                            name="replacementCost"
                            value={this.state.elemento.replacementCost}
                            onChange={this.handleChange}
                        />
                        <ValidationMessage msg={this.state.msgErr.replacementCost} />
                    </div>

                    <div className="form-group">
                        <label htmlFor="nombre">Idioma</label>
                        <input
                            type="text"
                            className="form-control"
                            id="languageId"
                            name="languageId"
                            value={this.state.elemento.languageId}
                            onChange={this.handleChange}
                        />
                        <ValidationMessage msg={this.state.msgErr.languageId} />
                    </div>

                    <div className="form-group">
                        <label htmlFor="nombre">Idioma Original</label>
                        <input
                            type="text"
                            className="form-control"
                            id="languageVOId"
                            name="languageVOId"
                            value={this.state.elemento.languageVOId}
                            onChange={this.handleChange}
                        />
                        <ValidationMessage msg={this.state.msgErr.languageVOId} />
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
                                            <td className="text-end">
                                                <div className="btn-group text-end" role="group">
                                                    <input
                                                        type="button"
                                                        className="btn btn-danger"
                                                        value="Eliminar"
                                                    // onClick={(e) => props.onDelete(item.id)}
                                                    />
                                                </div>
                                            </td>
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
                                            <td className="text-end">
                                                <div className="btn-group text-end" role="group">
                                                    <input
                                                        type="button"
                                                        className="btn btn-danger"
                                                        value="Eliminar"
                                                    // onClick={(e) => props.onDelete(item.id)}
                                                    />
                                                </div>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    }
                </div>
            </>

        );
    }
}