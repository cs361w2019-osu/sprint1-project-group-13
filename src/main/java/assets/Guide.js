/* global React */
const { createElement: h, Component } = React

/** Tell the player what can be done next. */
export default class Guide extends Component {
  guideText () {
    const { kind, sonarMode, conclusion } = this.props
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

  bonusText () {
    const { kind, sonarMode, conclusion } = this.props
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

  render () {
    return h('div', { className: 'guide' },
      h('p', {}, this.guideText()),
      h('p', {}, this.bonusText())
    )
  }
}
