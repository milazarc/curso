import logo from './logo.svg'
import './App.css'

import React, { Component } from 'react'

export default class App extends Component {
  render () {
    return (
      <>
        <Home />
        <DemosJSX />
      </>

    )
  }
}

class DemosJSX extends Component {
  render () {
    const nombre = 'mundo'
    const estilo = 'App-link'
    const saluda = <h1> Hola {nombre.toUpperCase() + '!'}</h1>
    return (
      <>
        <h2 className={estilo}>Hola {saluda} </h2>
        <div>App</div>
        <img src={logo} className='App-logo' alt='logo' width={100} />
      </>
    )
  }
}

function Home () {
  return (
    <div className='App'>
      <header className='App-header'>
        <img src={logo} className='App-logo' alt='logo' />
        <h1>Hola mundo</h1>
        <h2>{process.env.REACT_APP_API_URL}</h2>
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className='App-link'
          href='https://reactjs.org'
          target='_blank'
          rel='noopener noreferrer'
        >
          Learn React
        </a>
      </header>
    </div>
  )
}
