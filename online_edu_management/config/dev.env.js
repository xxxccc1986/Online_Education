'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  //BASE_API: '"https://easy-mock.com/mock/5950a2419adc231f356a6636/vue-admin"',
  // BASE_API: '"http://192.168.231.134:9001/api"',
  BASE_API: '"http://116.205.178.255:9528/api"',
  // BASE_API: '"http://localhost:8010"',
  // BASE_API: '"http://116.205.178.255:9528"',
})
