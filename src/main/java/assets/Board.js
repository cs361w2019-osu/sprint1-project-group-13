import { underShip, isOccupied, isAttacked } from './utils.js'

/* global React */
const { createElement: h, Component } = React

/** Both player and opponent boards. */
export default class Board extends Component {
  constructor () {
    super()
    this.state = {
      origin: null
    }
  }

  isHovered (sq) {
    const { size, vertical } = this.props
    const { origin } = this.state
    return underShip({ origin, size, vertical }, sq)
  }

  hover (origin) {
    const { disable } = this.props
    if (disable) return
    this.setState({ origin })
  }

  boardClass () {
    const { player, disable } = this.props
    return [
      'board',
      player ? 'allied' : '',
      disable ? 'disabled' : ''
    ].join(' ')
  }

  squareClass (square) {
    const { disable, board } = this.props
    return [
      !disable && this.isHovered(square) ? 'hover' : '',
      // TODO add "sonar" class to square if close to pulse (isSonared)
      isOccupied(board, square) ? 'occupied' : '',
      isAttacked(board, square) ? 'hit' : ''
    ].join(' ')
  }

  render () {
    const { board, disable, onSquare } = this.props
    const { origin } = this.state
    const range = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

    return h('div',
      {
        className: this.boardClass(),
        onClick: () => !disable && onSquare(origin),
        onMouseLeave: () => this.hover(null)
      },
      // Fill grid in row by row
      range.map(y => {
        return range.map(x => {
          const sq = { x, y }
          return h('div', {
            key: `${x}:${y}`,
            className: board ? this.squareClass(sq) : '',
            onMouseEnter: () => this.hover(sq)
          })
        })
      }).concat()
    )
  }
}
