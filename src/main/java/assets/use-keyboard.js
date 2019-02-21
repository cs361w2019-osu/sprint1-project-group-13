/* global React */

const { useState, useEffect } = React

/** Keyboard input */
export default function () {
  const [vertical, setVertical] = useState(false)
  const [sonar, setSonar] = useState(false)

  function keypress (e) {
    if (e.code === 'KeyR') setVertical(!vertical)
    if (e.code === 'KeyS') setSonar(!sonar)
  }

  useEffect(() => {
    document.addEventListener('keypress', keypress)
    return () => {
      document.removeEventListener('keypress', keypress)
    }
  })

  return [vertical, sonar]
}
