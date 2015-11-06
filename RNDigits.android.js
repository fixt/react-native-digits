/**
 * Stub of RNDigits for Android.
 *
 * @providesModule RNDigits
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
  view: function(callback) {
    NativeRNDigits.view(callback);
  },

  logout: function() {
    NativeRNDigits.logout();
  }
};

module.exports = RNDigits;

