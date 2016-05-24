/**
 * @providesModule react-native-digits
 * @flow
 */
'use strict';

import React, { Component, PropTypes } from 'react';
import { NativeModules } from 'react-native'
const { RNDigits } = NativeModules

export default class Digits extends Component {
  static logout() {
    RNDigits.logout()
  }

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
  accentColor: PropTypes.string,
  backgroundColor: PropTypes.string,
  onError: PropTypes.func,
  onLogin: PropTypes.func.isRequired,
  visible: PropTypes.bool.isRequired,
}

Digits.defaultProps = {
  onError: (err) => console.warn(err),
  visible: false,
}
