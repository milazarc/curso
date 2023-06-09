import React, { Component } from "react";
import {
    ValidationMessage,
    ErrorMessage,
    Esperando,
    PaginacionCmd as Paginacion,
} from "../biblioteca/comunes";
import { titleCase } from "../biblioteca/formateadores";
import FilmsForm from "./peliculasForm";
import FilmsView from "./peliculasView";
export class FilmsMnt extends Component {
    constructor(props) {
        super(props);
        this.state = {
            modo: "list",
            listado: null,
            elemento: null,
            error: null,
            loading: true,
            pagina: 0,
            paginas: 0,
        };
        this.idOriginal = null;
        this.url =
            (process.env.REACT_APP_API_URL || "http://localhost:8010/") +
            "/api/peliculas/v1";
    }

    setError(msg) {
        this.setState({ error: msg, loading: false });
    }

    list(num) {
        let pagina = this.state.pagina;
        if (num || num === 0) pagina = num;
        this.setState({ loading: true });
        fetch(`${this.url}?page=${pagina}&size=10`)
            .then((response) => {
                response.json().then(
                    response.ok
                        ? (data) => {
                            this.setState({
                                modo: "list",
                                listado: data.content,
                                loading: false,
                                pagina: data.number,
                                paginas: data.totalPages,
                            });
                        }
                        : (error) => this.setError(`${error.status}: ${error.error}`)
                );
            })
            .catch((error) => this.setError(error));
    }

    add() {
        this.setState({
            modo: "add",
            elemento: {
                "id": 0,
                "description": "",
                "length": 0,
                "rating": "PG",
                "releaseYear": "2000",
                "rentalDuration": 1,
                "rentalRate": 0.01,
                "replacementCost": 0.01,
                "title": "",
                "languageId": 1,
                "languageVOId": null,
                "actors": [],
                "categories": []
            }
        });
    }
    edit(key) {
        this.setState({ loading: true });
        fetch(`${this.url}/${key}`)
            .then((response) => {
                response.json().then(
                    response.ok
                        ? (data) => {
                            this.setState({
                                modo: "edit",
                                elemento: data,
                                loading: false,
                            });
                            this.idOriginal = key;
                        }
                        : (error) => this.setError(`${error.status}: ${error.error}`)
                );
            })
            .catch((error) => this.setError(error));
    }
    view(key) {
        this.setState({ loading: true });
        fetch(`${this.url}/${key}`)
            .then((response) => {
                response.json().then(
                    response.ok
                        ? (data) => {
                            this.setState({
                                modo: "view",
                                elemento: data,
                                loading: false,
                            });
                        }
                        : (error) => this.setError(`${error.status}: ${error.error}`)
                );
            })
            .catch((error) => this.setError(error));
    }
    delete(key) {
        if (!window.confirm("¿Seguro?")) return;
        this.setState({ loading: true });
        fetch(`${this.url}/${key}`, { method: "DELETE" })
            .then((response) => {
                if (response.ok) this.list();
                else
                    response.json().then((error) =>
                        this.setError(`${error.status}:
    ${error.error}`)
                    );
                this.setState({ loading: false });
            })
            .catch((error) => this.setError(error));
    }
    componentDidMount() {
        this.list(0);
    }

    cancel() {
        this.list();
    }
    send(elemento) {
        this.setState({ loading: true });
        // eslint-disable-next-line default-case
        switch (this.state.modo) {
            case "add":
                fetch(`${this.url}`, {
                    method: "POST",
                    body: JSON.stringify(elemento),
                    headers: {
                        "Content-Type": "application/json",
                    },
                })
                    .then((response) => {
                        if (response.ok) this.cancel();
                        else
                            response.json().then((error) =>
                                this.setError(`${error.status}:${error.detail}`)
                            );
                        this.setState({ loading: false });
                    })
                    .catch((error) => this.setError(error));
                break;
            case "edit":
                fetch(`${this.url}/${this.idOriginal}`, {
                    method: "PUT",
                    body: JSON.stringify(elemento),
                    headers: {
                        "Content-Type": "application/json",
                    },
                })
                    .then((response) => {
                        if (response.ok) this.cancel();
                        else
                            response.json().then((error) =>
                                this.setError(`${error.status}:${error.detail}`)
                            );
                        this.setState({ loading: false });
                    })
                    .catch((error) => this.setError(error));
                break;
        }
    }

    render() {
        console.log("estado", this.state.modo)
        if (this.state.loading) return <Esperando />;
        let result = [
            <ErrorMessage
                key="error"
                msg={this.state.error}
                onClear={() => this.setState({ error: null })}
            />,
        ];
        switch (this.state.modo) {
            case "add":
            case "edit":
                result.push(
                    <FilmsForm
                        key="main"
                        isAdd={this.state.modo === "add"}
                        formType={'edit'}
                        elemento={this.state.elemento}
                        onCancel={(e) => this.cancel()}
                        onSend={(e) => this.send(e)}
                    />
                );
                break;
            case "view":
                result.push(
                    <FilmsView
                    key="main"
                    formType={'view'}
                    elemento={this.state.elemento}
                    onCancel={(e) => this.cancel()}
                />
                );
                break;
            default:
                if (this.state.listado)
                    result.push(
                        <FilmsList
                            key="main"
                            listado={this.state.listado}
                            pagina={this.state.pagina}
                            paginas={this.state.paginas}
                            onAdd={(e) => this.add()}
                            onView={(key) => this.view(key)}
                            onEdit={(key) => this.edit(key)}
                            onDelete={(key) => this.delete(key)}
                            onChangePage={(num) => this.list(num)}
                        />
                    );
                break;
        }
        return result;
    }
}

function FilmsList(props) {
    return (
        <>
            <table className="table table-hover table-striped">
                <thead className="table-info">
                    <tr>
                        <th>Lista de Peliculas</th>
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
                            <td>{titleCase(item.title)}</td>
                            <td className="text-end">
                                <div className="btn-group text-end" role="group">
                                    <input
                                        type="button"
                                        className="btn btn-primary"
                                        value="Ver"
                                        onClick={(e) => props.onView(item.id)}
                                    />
                                    <input
                                        type="button"
                                        className="btn btn-primary"
                                        value="Editar"
                                        onClick={(e) => props.onEdit(item.id)}
                                    />
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

