
export function underShip ({ origin, vertical, size }, { x, y }) {
  return origin &&
      x >= origin.x &&
      y >= origin.y &&
      x <= origin.x + (vertical ? size - 1 : 0) &&
      y <= origin.y + (vertical ? 0 : size - 1)
}

export function getSquares (ship) {
  const list = []
  const { x, y } = ship.origin
  for (let i = 0; i < ship.size; i++) {
    if (ship.vertical) {
      list.push({ x: x + i, y })
    } else {
      list.push({ x, y: y + i })
    }
  }
  return list
}

export function getShipKind (size) {
  switch (size) {
    case 2: return 'MINESWEEPER'
    case 3: return 'DESTROYER'
    case 4: return 'BATTLESHIP'
    default: return null
  }
}

export function isOccupied ({ ships }, sq) {
  return ships.find(s => underShip(s, sq))
}

export function isAttacked ({ attacks }, { x, y }) {
  return attacks.find(a => a.x === x && a.y === y)
}

export function isSonared ({ sonars }, { x, y }) {
  return sonars.find(s => Math.abs(s.x - x) + Math.abs(s.y - y) <= 2)
}

export function shipSunk (board, ship) {
  const list = getSquares(ship)
  const sunk1 = list.filter(sq => !isAttacked(board, sq)).length === 0
  const sunk2 = false
  return sunk1 || sunk2
}

export function allShipsSunk (board) {
  if (!board) return false
  if (board.ships.length === 0) return false
  return board.ships.filter(ship => !shipSunk(board, ship)).length === 0
}
