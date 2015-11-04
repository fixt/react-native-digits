/**
 * @providesModule RNDigits
 * @flow
 */
'use strict';

var NativeRNDigits = require('NativeModules').RNDigits;
var invariant = require('invariant');

/**
 * High-level docs for the RNDigits iOS API can be written here.
 */

var RNDigits = {
  test: function() {
    NativeRNDigits.test();
  }
};

module.exports = RNDigits;
