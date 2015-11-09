/**
 * @providesModule react-native-digits
 * @flow
 */
'use strict';

import React, { Component, NativeModules, PropTypes } from 'react-native'
const { RNDigits } = NativeModules

export default class Digits extends Component {
  componentWillReceiveProps(props) {
    if (props.visible && this.props.visible == false) {
      this.show()
    }
  }

  show() {
    RNDigits.view((err, session) => {
      if (err) {
        this.props.onError(err)
      } else {
        this.props.onLogin(session)
      }
    })
  }

  render() {
    return false
  }
}

Digits.propTypes = {
  onError: PropTypes.func,
  onLogin: PropTypes.func.isRequired,
  visible: PropTypes.bool.isRequired,
}

Digits.defaultProps = {
  onError: (err) => console.warn(err),
  visible: false,
}
