/**
 * @providesModule react-native-digits
 * @flow
 */
'use strict';

var { NativeModules } = require('react-native');
var NativeRNDigits = NativeModules.RNDigits;
var invariant = require('invariant');

/**
 * High-level docs for the RNDigits iOS API can be written here.
 */

var RNDigits = {
  view(callback) {
    NativeRNDigits.view(callback);
  },

  logout() {
    NativeRNDigits.logout();
  }
}

module.exports = RNDigits;
