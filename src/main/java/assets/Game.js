import Board from './Board.js'
import Guide from './Guide.js'
import { allShipsSunk, getShipKind } from './utils.js'

/* eslint-env browser */
/* global React */
const { createElement: h, Component } = React

/** Root Game component and all state. */
export default class Game extends Component {
  constructor () {
    super()
    this.state = {
      game: {},
      size: 2,
      vertical: false,
      sonarMode: false,
      conclusion: ''
    }
  }

  async send (url, body) {
    const resp = await fetch(url, {
      method: body ? 'post' : 'get',
      headers: body ? { 'Content-Type': 'application/json' } : {},
      body: body ? JSON.stringify(body) : null
    })
    const game = await resp.json()
    this.setState({ game })
  }

  async place (square) {
    const { game, size, vertical } = this.state
    await this.send('/place', { game, square, size, vertical })
    this.setState({ size: size < 4 ? size + 1 : 1 })
  }

  async attack (square) {
    const { game } = this.state
    await this.send('/attack', { game, square })
  }

  async sonar (square) {
    const { game } = this.state
    await this.send('/sonar', { game, square })
    this.setState({ sonarMode: false })
  }

  componentDidMount () {
    const keypress = e => {
      const { vertical, sonarMode } = this.state
      if (e.code === 'KeyR') this.setState({ vertical: !vertical })
      if (e.code === 'KeyS') this.setState({ sonarMode: !sonarMode })
    }
    document.addEventListener('keypress', keypress)
    this.send('/game')
  }

  componentWillUpdate (_, newState) {
    const { game, conclusion } = newState
    if (!conclusion) {
      if (allShipsSunk(game.opponentsBoard)) {
        this.setState({ conclusion: 'Victory!' })
      }
      if (allShipsSunk(game.playersBoard)) {
        this.setState({ conclusion: 'Defeat!' })
      }
    }
  }

  render () {
    const { game, size, vertical, sonarMode, conclusion } = this.state

    // TODO ignore sonarMode if no ships sank
    // TODO ignore sonarMode if two sonars used

    return h('div', { className: 'game' },
      h(Board, {
        board: game.opponentsBoard,
        disable: getShipKind(size) || conclusion,
        size: 1,
        vertical: false,
        player: false,
        onSquare: sq => sonarMode ? this.sonar(sq) : this.attack(sq)
      }),
      h('div', { className: 'player' },
        h(Board, {
          board: game.playersBoard,
          disable: !getShipKind(size) || conclusion,
          size,
          vertical,
          player: true,
          onSquare: sq => this.place(sq, vertical, size)
        }),
        h(Guide, {
          kind: getShipKind(size),
          sonarMode,
          conclusion
        })
      )
    )
  }
}
