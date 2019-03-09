import Board from './Board.js'
import Guide from './Guide.js'
import { allShipsSunk } from './utils.js'

/* eslint-env browser */
/* global React */
const { createElement: h, Component } = React

/** Root Game component and all state. */
export default class Game extends Component {
  constructor () {
    super()
    this.state = {
      game: { opponentsBoard: { ships: [] }, playersBoard: { ships: [] } },
      yetToPlace: ['minesweeper', 'destroyer', 'battleship'],
      vertical: false,
      submerged: false,
      sonarMode: false,
      conclusion: '',
      error: null
    }
  }

  async send (url, body) {
    this.setState({ error: null })
    try {
      const resp = await fetch(url, {
        method: body ? 'post' : 'get',
        headers: body ? { 'Content-Type': 'application/json' } : {},
        body: body ? JSON.stringify(body) : null
      })
      const game = await resp.json()
      this.setState({ game })
    } catch (err) {
      this.setState({ error: 'Unable to do that!' })
      throw err
    }
  }

  async place (origin) {
    const { game, yetToPlace: [type, ...remaining], vertical, submerged } = this.state
    const ship = { type, origin, vertical, submerged }
    await this.send('/place', { game, ally: ship, enemy: ship })
    this.setState({ yetToPlace: remaining })
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
      const { vertical, submerged, sonarMode, game } = this.state
      const canSonar = game.opponentsBoard && game.opponentsBoard.canSonar
      const canSubmerge = game.playersBoard.ships.length >= 3
      if (e.code === 'KeyR') this.setState({ vertical: !vertical })
      if (e.code === 'KeyP') this.setState({ sonarMode: canSonar ? !sonarMode : false })
      if (e.code === 'KeyS') this.setState({ submerged: canSubmerge ? !submerged : false })
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
    const { game, conclusion, error } = this.state
    const { yetToPlace, vertical, submerged, sonarMode } = this.state

    const nextShip = yetToPlace[0]
    const canAttack = !nextShip && !conclusion
    const { canSonar } = game.opponentsBoard

    return h('div', { className: 'game' },
      h(Board, {
        board: game.opponentsBoard,
        disable: !canAttack,
        onSquare: sq => sonarMode ? this.sonar(sq) : this.attack(sq)
      }),
      h('div', { className: 'player' },
        h(Board, {
          board: game.playersBoard,
          disable: !nextShip,
          type: nextShip,
          vertical,
          submerged,
          player: true,
          onSquare: sq => this.place(sq, vertical, submerged)
        }),
        h(Guide, {
          nextShip,
          sonarMode,
          conclusion,
          canSonar,
          error
        })
      )
    )
  }
}
