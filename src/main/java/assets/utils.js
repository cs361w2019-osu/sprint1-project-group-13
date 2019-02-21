
export function underShip ({ origin, vertical, size }, { x, y }) {
  return origin &&
      x >= origin.x &&
      y >= origin.y &&
      x <= origin.x + (vertical ? 0 : size - 1) &&
      y <= origin.y + (vertical ? size - 1 : 0)
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

export function isAnyShipSunk (board) {
  if (!board) return false
  if (board.ships.length === 0) return false
  return board.ships.filter(ship => ship.sunk).length > 0
}

export function canSonar (board) {
  return isAnyShipSunk(board) && board.sonars.length < 2
}

export function allShipsSunk (board) {
  if (!board) return false
  if (board.ships.length === 0) return false
  return board.ships.filter(ship => !ship.sunk).length === 0
}
