function squaresFromType (type) {
  switch (type) {
    case 'minesweeper':
      return [
        { col: 0, row: 0 },
        { col: 1, row: 0 }
      ]
    case 'destroyer':
      return [
        { col: 0, row: 0 },
        { col: 1, row: 0 },
        { col: 2, row: 0 }
      ]
    case 'battleship':
      return [
        { col: 0, row: 0 },
        { col: 1, row: 0 },
        { col: 2, row: 0 },
        { col: 3, row: 0 }
      ]
    case 'submarine':
      return [
        { col: 0, row: 0 },
        { col: 1, row: 0 },
        { col: 2, row: 0 },
        { col: 2, row: -1 },
        { col: 3, row: 0 }
      ]
  }
}

function reflectShip ({ squares }) {
  squares.forEach(s => {
    let x = s.row
    s.row = s.col
    s.col = x
  })
}

function offsetShip ({ squares }, { col, row }) {
  squares.forEach(s => {
    s.col += col
    s.row += row
  })
}

export const sameSquareAs = a => b => a.col === b.col && a.row === b.row

export function isInNextShip ({ type, origin, vertical, submerged }, sq) {
  const squares = squaresFromType(type)
  if (vertical) reflectShip({ squares })
  offsetShip({ squares }, origin)
  return squares.find(sameSquareAs(sq))
}

export function isSonared ({ sonars = [] }, { col, row }) {
  return sonars.find(s => Math.abs(s.col - col) + Math.abs(s.row - row) <= 2)
}

export function isOccupied ({ ships = [] }, sq) {
  return ships.find(({ squares }) => squares.find(sameSquareAs(sq)))
}

export function isSubOnly ({ ships = [] }, sq) {
  const hasSub = ships.find(({ submerged, squares }) => submerged && squares.find(sameSquareAs(sq)))
  const hasSurf = ships.find(({ submerged, squares }) => !submerged && squares.find(sameSquareAs(sq)))
  return hasSub && !hasSurf
}

export function isSunk ({ ships = [] }, sq) {
  return ships.find(({ sunk, squares }) => sunk && squares.find(sameSquareAs(sq)))
}

export function isMissiled ({ attacks = [] }, sq) {
  return attacks.find(sameSquareAs(sq))
}

export function isLasered ({ lasers = [] }, sq) {
  return lasers.find(sameSquareAs(sq))
}

export function allShipsSunk ({ ships = [] }) {
  if (ships.length === 0) return false
  return ships.filter(ship => !ship.sunk).length === 0
}
