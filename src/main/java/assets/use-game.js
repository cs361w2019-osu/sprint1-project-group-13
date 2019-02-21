/* eslint-env browser */

import { allShipsSunk } from './utils.js'

/* global React */

const { useState, useEffect } = React

/** Game state and communication */
export default function () {
  const [game, setGame] = useState({})
  const [conclusion, conclude] = useState('')

  async function common (url, body) {
    const resp = await fetch(url, {
      method: body ? 'post' : 'get',
      headers: body ? { 'Content-Type': 'application/json' } : {},
      body: body ? JSON.stringify(body) : null
    })
    setGame(await resp.json())
  }

  async function place ({ square, size, vertical }) {
    await common('/place', { game, square, size, vertical })
  }

  async function attack (square) {
    await common('/attack', { game, square })
  }

  async function sonar (square) {
    await common('/sonar', { game, square })
  }

  useEffect(() => {
    common('/game')
  }, [])

  useEffect(() => {
    if (allShipsSunk(game.opponentsBoard)) conclude('Victory!')
    if (allShipsSunk(game.playersBoard)) conclude('Defeat!')
  }, [game])

  return [game, place, attack, sonar, conclusion]
}
