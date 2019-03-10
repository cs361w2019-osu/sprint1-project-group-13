import {
  isOccupied,
  isMissiled,
  isLasered,
  isSonared,
  isSunk,
  isSubmerged,
  sameSquareAs,
  isInNextShip
} from './utils.js'

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
    const { vertical, submerged, type, player, disable } = this.props
    const { origin } = this.state
    if (disable) return false
    if (!origin) return false
    if (!player) return sameSquareAs(sq)(origin)
    return isInNextShip({ type, vertical, origin, submerged }, sq)
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
    const { board, submerged } = this.props
    return [
      this.isHovered(square) ? `hover ${submerged ? 'submerged' : ''}` : '',
      isSonared(board, square) ? 'sonar' : '',
      isSubmerged(board, square) ? 'submerged' : '',
      isOccupied(board, square) ? 'occupied' : '',
      isMissiled(board, square) ? 'hit' : '',
      isLasered(board, square) ? 'hit laser' : '',
      isSunk(board, square) ? 'sunk' : ''
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
        onMouseLeave: () => this.setState({ origin: null })
      },
      // Fill grid in row by row
      range.map(row => {
        return range.map(col => {
          const sq = { col, row }
          return h('div', {
            key: `${col}:${row}`,
            className: board ? this.squareClass(sq) : '',
            onMouseEnter: () => this.setState({ origin: sq })
          })
        })
      }).concat()
    )
  }
}
