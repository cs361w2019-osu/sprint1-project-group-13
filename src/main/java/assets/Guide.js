/* global React */
const { createElement: h, Component } = React

/** Tell the player what can be done next. */
export default class Guide extends Component {
  guideText () {
    const { nextShip, usingLaser, sonarMode, conclusion } = this.props
    if (conclusion) {
      return conclusion
    } else if (nextShip) {
      return `👈 Place ${nextShip}`
    } else if (sonarMode) {
      return `☝️ Click to deploy sonar pulse!`
    } else {
      return `☝️ Click to fire ${usingLaser ? 'space laser' : 'missile'}!`
    }
  }

  bonusText () {
    const { nextShip, canSonar, sonarMode, conclusion } = this.props

    if (conclusion) return 'Reload to start a new game.'

    if (nextShip) {
      if (nextShip === 'submarine') {
        return `(Press R to rotate, S to submerge)`
      } else {
        return `(Press R to rotate)`
      }
    }

    if (sonarMode) {
      return `(Press P to switch to attack)`
    } else if (canSonar) {
      return `(Press P to switch to sonar pulse)`
    }
  }

  movementText () {
    const { canMove } = this.props
    return canMove ? `(Arrow keys move your fleet)` : ''
  }

  render () {
    return h('div', { className: 'guide' },
      h('p', {}, this.guideText()),
      h('p', {}, this.bonusText()),
      h('p', {}, this.movementText()),
      h('p', { className: 'error' }, this.props.error)
    )
  }
}
