import { isOccupied, isAttacked, isSonared, underShip, getShipKind } from './utils.js'
import useKeyboard from './use-keyboard.js'
import useGame from './use-game.js'

/* global React */

const { createElement: h, useState } = React

/** Both player and opponent boards. */
function Board ({ board, disable, onSquare, size = 1, vertical = false, player = false }) {
  const [origin, setOrigin] = useState(null)

  function isHovered (sq) {
    return underShip({ origin, size, vertical }, sq)
  }

  function boardClass () {
    return [
      'board',
      player ? 'allied' : '',
      disable ? 'disabled' : ''
    ].join(' ')
  }

  function squareClass (square) {
    return [
      !disable && isHovered(square) ? 'hover' : '',
      // TODO add "sonar" class to square if close to pulse (isSonared)
      isOccupied(board, square) ? 'occupied' : '',
      isAttacked(board, square) ? 'hit' : ''
    ].join(' ')
  }

  const range = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

  return h('div',
    {
      className: boardClass(),
      onClick: () => !disable && onSquare(origin),
      onMouseLeave: () => setOrigin(null)
    },
    range.map(x => {
      return range.map(y => {
        const sq = { x, y }
        return h('div', {
          key: `${x}:${y}`,
          className: board ? squareClass(sq) : '',
          onMouseEnter: disable ? null : () => setOrigin(sq)
        })
      })
    }).concat()
  )
}

/** The root Game component and all state. */
function Guide ({ kind, sonarMode, conclusion }) {
  function guideText () {
    if (conclusion) {
      return conclusion
    } else if (kind) {
      return `👈 Place ${kind}`
    } else if (sonarMode) {
      return `☝️ Click to deploy sonar pulse!`
    } else {
      return `☝️ Click to attack a square!`
    }
  }

  function bonusText () {
    if (conclusion) {
      return 'Reload to start a new game.'
    } else if (kind) {
      return `(Press R to rotate)`
    } else if (sonarMode) {
      return `(Press S to switch to attack)`
    } else {
      // TODO hide sonar pulse message if unuseable
      return `(Press S to switch to sonar pulse)`
    }
  }

  return h('div', { className: 'guide' },
    h('p', {}, guideText()),
    h('p', {}, bonusText())
  )
}

/** The root Game component and all state. */
function Game () {
  const [game, place, attack, sonar, conclusion] = useGame()
  const [vertical, sonarMode] = useKeyboard()
  const [size, setSize] = useState(2)

  // TODO ignore sonarMode if no ships sank
  // TODO ignore sonarMode if two sonars used

  return h('div', { className: 'game' },
    h(Board, {
      board: game.opponentsBoard,
      disable: getShipKind(size) || conclusion,
      onSquare: sq => sonarMode ? sonar(sq) : attack(sq)
    }),
    h('div', { className: 'player' },
      h(Board, {
        board: game.playersBoard,
        disable: !getShipKind(size) || conclusion,
        size,
        vertical,
        player: true,
        onSquare: square => {
          place({ square, vertical, size })
          setSize(size < 4 ? size + 1 : 1)
        }
      }),
      h(Guide, {
        kind: getShipKind(size),
        sonarMode,
        conclusion
      })
    )
  )
}

/* global ReactDOM */

ReactDOM.render(h(Game), document.getElementById('game'))
