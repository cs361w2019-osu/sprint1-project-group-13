var placedShips = 0
var game = null
var vertical = false

function shipFromSize (i) {
  switch (i) {
    case 1: return 'MINESWEEPER'
    case 2: return 'DESTROYER'
    case 3: return 'BATTLESHIP'
    default: return ''
  }
}

function currentSize () {
  return placedShips + 1
}

function currentShip () {
  return shipFromSize(currentSize())
}

function makeGrid (table, isPlayer) {
  for (let i = 0; i < 10; i++) {
    let row = document.createElement('tr')
    for (let j = 0; j < 10; j++) {
      let cell = document.createElement('td')
      if (isPlayer) {
        cell.addEventListener('click', allyCellClick)
        cell.addEventListener('mouseover', cellHoverOn)
        cell.addEventListener('mouseleave', cellHoverOff)
      } else {
        cell.addEventListener('click', enemyCellClick)
      }
      row.appendChild(cell)
    }
    table.appendChild(row)
  }
}

function markHits (board, elementId, surrenderText) {
  board.attacks.forEach((attack) => {
    let className
    if (attack.result === 'MISS') {
      className = 'miss'
    } else if (attack.result === 'HIT') {
      className = 'hit'
    } else if (attack.result === 'SUNK') {
      className = 'hit'
    } else if (attack.result === 'SURRENDER') {
      alert(surrenderText)
    }
    document.getElementById(elementId).rows[attack.location.row - 1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add(className)
  })
}

function redrawGrid () {
  Array.from(document.getElementById('opponent').childNodes).forEach((row) => row.remove())
  Array.from(document.getElementById('player').childNodes).forEach((row) => row.remove())
  makeGrid(document.getElementById('opponent'), false)
  makeGrid(document.getElementById('player'), true)
  if (game === undefined) {
    return
  }

  game.playersBoard.ships.forEach((ship) => ship.occupiedSquares.forEach((square) => {
    document.getElementById('player').rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add('occupied')
  }))

  markHits(game.opponentsBoard, 'opponent', 'You won the game')
  markHits(game.playersBoard, 'player', 'You lost the game')
}

function redrawGuide () {
  const guide = document.getElementById('guidetext')
  const bonus = document.getElementById('bonustext')
  if (placedShips < 3) {
    guide.innerText = `👈 Place ${currentShip()}`
    bonus.innerText = '(Press R to rotate)'
  } else {
    guide.innerText = `☝️ Click to attack!`
    bonus.innerText = ''
  }
}

function cellHoverOn (e) {
  if (!currentShip()) return
  let row = e.target.parentNode.rowIndex
  let col = e.target.cellIndex
  console.log('hover', row, col)
  if (vertical && (row + currentSize() >= 10)) return;
  if (!vertical && (col + currentSize() >= 10)) return;
  for (let i = 0; i <= currentSize(); i++) {
    if (vertical) {
      document.getElementById('player').rows[row + i].cells[col].classList.add('hover')
    } else {
      document.getElementById('player').rows[row].cells[col + i].classList.add('hover')
    }
  }
}

function cellHoverOff (e) {
  const els = document.getElementsByClassName('hover')
  while (els[0]) {
    els[0].classList.remove('hover')
  }
}

function allyCellClick () {
  let row = this.parentNode.rowIndex + 1
  let col = String.fromCharCode(this.cellIndex + 65)
  if (currentShip()) {
    const shipType = currentShip()
    sendXhr('POST', '/place', { game, shipType, x: row, y: col, isVertical: vertical }, function (data) {
      game = data
      placedShips++
      redrawGrid()
      redrawGuide()
    })
  }
}

function enemyCellClick () {
  let row = this.parentNode.rowIndex + 1
  let col = String.fromCharCode(this.cellIndex + 65)
  if (!currentShip()) {
    sendXhr('POST', '/attack', { game: game, x: row, y: col }, function (data) {
      game = data
      redrawGrid()
    })
  }
}

function sendXhr (method, url, data, handler) {
  const error = document.getElementById('errortext')
  error.innerText = ''

  var req = new XMLHttpRequest()
  req.addEventListener('load', function (event) {
    if (req.status !== 200) {
      error.innerText = 'Cannot complete that action!'
      return
    }
    handler(JSON.parse(req.responseText))
  })
  req.open(method, url)
  req.setRequestHeader('Content-Type', 'application/json')
  req.send(JSON.stringify(data))
}

function place (size) {
  return function () {
    let row = this.parentNode.rowIndex
    let col = this.cellIndex
    vertical = document.getElementById('is_vertical').checked
    let table = document.getElementById('player')
    for (let i = 0; i < size; i++) {
      let cell
      if (vertical) {
        let tableRow = table.rows[row + i]
        if (tableRow === undefined) {
          // ship is over the edge; let the back end deal with it
          break
        }
        cell = tableRow.cells[col]
      } else {
        cell = table.rows[row].cells[col + i]
      }
      if (cell === undefined) {
        // ship is over the edge; let the back end deal with it
        break
      }
      cell.classList.toggle('placed')
    }
  }
}

// Initialize game immediately.
(function () {
  makeGrid(document.getElementById('opponent'), false)
  makeGrid(document.getElementById('player'), true)

  document.onkeypress = function (e) {
    if (e.code === 'KeyR') {
      cellHoverOff()
      vertical = !vertical
    }
  }

  sendXhr('GET', '/game', {}, function (data) {
    game = data
    redrawGuide()
  })
})()
