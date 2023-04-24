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
        this.state = {
            elemento: props.elemento,
            msgErr: [],
            invalid: false,
            actores: [],
            categorias: [],
            idiomas: [],

        };
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
        this.categoriaSeleccionada = 1
        this.actorSeleccionado = 1
        this.primero = true
        this.formType = props.formType
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
        console.log("llamandoActores")
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
        console.log("llamandoCategorias")
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
                            // console.log("categorie", data)
                        }
                        : (error) => this.setError(`${error.status}: ${error.error}`)
                );
            })
            .catch((error) => this.setError(error));
    }

    getListIdiomas() {
        console.log("llamandoIdiomas")
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
        if (this.primero) {
            if (this.formType !== 'add') this.getElement();
            this.getListActores();
            this.getListCategorias();
            this.getListIdiomas();
            this.validar();
            this.primero = false
        }

    }
    render() {
        // console.log("render", this.state.elemento, this.state.categorias, this.categoriaSeleccionada)
        return (
            <>
                <form
                    ref={(tag) => {
                        this.form = tag;
                    }}
                >
                    <div className="form-group"> {JSON.stringify(this.state.elemento)} </div>
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
                            minLength="1"
                            maxLength="128"
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
                            onChange={ev => {
                                let aux = this.state.elemento
                                aux.description = ev.target.value
                                this.setState({ elemento: { ...aux } })
                            }}
                            minLength="2"
                        />
                        <ValidationMessage msg={this.state.msgErr.descripcion} />
                    </div>

                    <div className="form-group">
                        <label htmlFor="nombre">Rating</label>
                        <th className="input-group">
                            <select
                                className="form-select"
                                id="rating"
                                name="rating"
                                onChange={this.handleChange}
                            >
                                <option key="G" value="G" selected={this.state.elemento?.rating ==="G" ? 'selected' : ''}>General audiences</option>
                                <option key="PG" value="PG" selected={this.state.elemento?.rating ==="PG" ? 'selected' : ''}>Parental guidance suggested</option>
                                <option key="PG-13" value="PG-13" selected={this.state.element?.rating ==="PG-13" ? 'selected' : ''} >Parents strongly cautioned</option>
                                <option key="R" value="R" selected={this.state.elemento?.rating ==="R" ? 'selected' : ''}>Restricted</option>
                                <option key="NC-17" value="NC-17" selected={this.state.elemento?.rating ==="NC-17" ? 'selected' : ''}>Adults only</option>
                            </select>
                        </th>
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
                            min={1895} max={(new Date()).getFullYear()}
                        />
                        <ValidationMessage msg={this.state.msgErr.releaseYear} />
                    </div>

                    <div className="form-group">
                        <label htmlFor="nombre">Duracion</label>
                        <input
                            type="text"
                            className="form-control"
                            id="length"
                            name="length"
                            value={this.state.elemento.length}
                            onChange={this.handleChange}
                            min={1}
                        />
                        <ValidationMessage msg={this.state.msgErr.length} />
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
                            min={1}
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
                            min={0.01}
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
                            min={0.01}
                        />
                        <ValidationMessage msg={this.state.msgErr.replacementCost} />
                    </div>

                    <div className="form-group">
                        <label htmlFor="nombre">Idioma</label>
                        <th className="input-group">
                            <select className="form-select" id="languageId" name="languageId" onChange={this.handleChange}>
                                {this.state.idiomas.map(item => <option key={item.id} value={item.id} selected={this.state.elemento?.languageId === item.id ? 'selected' : ''}>{item.nombre}</option>)}
                            </select>
                        </th>
                        <ValidationMessage msg={this.state.msgErr.languageId} />
                    </div>

                    <div className="form-group">
                        <label htmlFor="nombre">Idioma Original</label>
                        <th className="input-group">
                            <select className="form-select" id="languageVOId" name="languageVOId" onChange={this.handleChange}>
                                <option value=''></option>
                                {this.state.idiomas.map(item => <option key={item.id} value={item.id} selected={this.state.elemento?.languageVOId === item.id ? 'selected' : ''}>{item.nombre}</option>)}
                            </select>
                        </th>
                        <ValidationMessage msg={this.state.msgErr.languageVOId} />
                    </div>

                    <div className="row">

                        {this.state.elemento.actors && this.state.actores &&
                            <div className="col-2 w-50">

                                <table className="table table-hover table-striped">
                                    <thead className="table-info">
                                        <tr>
                                            <th>Actores</th>
                                            <th className="input-group">
                                                <select className="form-select" id="actors" name="actors" onChange={ev => this.actorSeleccionado = +ev.target.value}>
                                                    {this.state.actores.map(item => <option key={item.id} value={item.id}>{item.nombre} {item?.apellidos}</option>)}
                                                </select>
                                                <button className="btn btn-outline-secondary" type="button" onClick={() => {
                                                    if (this.state.elemento.actors.includes(this.actorSeleccionado)) return
                                                    this.state.elemento.actors.push(this.actorSeleccionado)
                                                    // this.state.elemento.actors.sort((a, b) => a-b)
                                                    this.setState({ elemento: { ...this.state.elemento } })
                                                }} ><i className="far fa-plus"></i>+</button>
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody className="table-group-divider">
                                        {this.state.elemento.actors.map((item) => (
                                            <tr key={item.id}>
                                                <td>{titleCase(this.state.actores.find(element => element.id === item)?.nombre + ' ' + this.state.actores.find(element => element.id === item)?.apellidos)}</td>
                                                <td className="text-end">
                                                    <div className="btn-group text-end" role="group">
                                                        <input
                                                            type="button"
                                                            className="btn btn-danger"
                                                            value="Eliminar"
                                                            onClick={(e) => {
                                                                // this.state.elemento.actors.splice(this.state.elemento.actors.indexOf(element => element === item), 1)
                                                                this.state.elemento.actors = this.state.elemento.actors.filter(element => element !== item)
                                                                this.setState({ elemento: { ...this.state.elemento } })
                                                            }}
                                                        />
                                                    </div>
                                                </td>
                                            </tr>
                                        ))}
                                    </tbody>
                                </table>
                            </div>
                        }

                        {this.state.elemento.categories && this.state.categorias &&
                            <div className="col-2 w-50">

                                <table className="table table-hover table-striped">
                                    <thead className="table-info">
                                        <tr>
                                            <th>Categorias</th>
                                            <th className="input-group">
                                                <select className="form-select" id="categories" name="categories" onChange={ev => this.categoriaSeleccionada = +ev.target.value}>
                                                    {this.state.categorias.map(item => <option key={item.id} value={item.id}>{item.nombre}</option>)}
                                                </select>
                                                <button className="btn btn-outline-secondary" type="button" onClick={() => {
                                                    if (this.state.elemento.categories.includes(this.categoriaSeleccionada)) return
                                                    this.state.elemento.categories.push(this.categoriaSeleccionada)
                                                    this.setState({ elemento: { ...this.state.elemento } })
                                                }} ><i className="far fa-plus"></i>+</button>
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody className="table-group-divider">
                                        {this.state.elemento.categories.map((item) => (
                                            <tr key={item}>
                                                <td>{this.state.categorias.find(element => element.id === item)?.nombre}</td>
                                                <td className="text-end">
                                                    <div className="btn-group text-end" role="group">
                                                        <input
                                                            type="button"
                                                            className="btn btn-danger"
                                                            value="Eliminar"
                                                            onClick={(e) => {
                                                                this.state.elemento.categories.splice(this.state.elemento.categories.indexOf(element => element === item), 1)
                                                                this.setState({ elemento: { ...this.state.elemento } })
                                                            }}
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
            </>

        );
    }
}